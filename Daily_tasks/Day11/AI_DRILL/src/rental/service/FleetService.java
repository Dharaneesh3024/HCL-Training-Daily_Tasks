package rental.service;

import rental.exception.MaintenanceConflictException;
import rental.model.Booking;
import rental.model.MaintenanceBlock;
import rental.model.Vehicle;
import rental.repository.CrudRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;

public class FleetService {

    private final CrudRepository<Vehicle, Integer> vehicles;
    private final CrudRepository<Booking, Integer> bookings;
    private final CrudRepository<MaintenanceBlock, Integer> blocks;
    private int nextBlockId = 1;

    public FleetService(CrudRepository<Vehicle, Integer> vehicles,
                        CrudRepository<Booking, Integer> bookings,
                        CrudRepository<MaintenanceBlock, Integer> blocks) {
        this.vehicles = vehicles;
        this.bookings = bookings;
        this.blocks = blocks;
    }

    public MaintenanceBlock blockForMaintenance(int vehicleId, LocalDate from, LocalDate to, String reason) {
        if (!vehicles.existsById(vehicleId)) {
            throw new IllegalArgumentException("no vehicle with id " + vehicleId);
        }
        MaintenanceBlock block = new MaintenanceBlock(nextBlockId, vehicleId, from, to, reason);

        for (Booking b : bookings.findAll()) {
            if (b.getVehicleId() == vehicleId && overlapDays(from, to, b.getStart(), b.getEnd()) > 0) {
                throw new MaintenanceConflictException(
                        "vehicle " + vehicleId + " has a booking from " + b.getStart() + " to " + b.getEnd());
            }
        }
        for (MaintenanceBlock other : blocks.findAll()) {
            if (other.getVehicleId() == vehicleId && overlapDays(from, to, other.getFrom(), other.getTo()) > 0) {
                throw new MaintenanceConflictException(
                        "vehicle " + vehicleId + " is already blocked from " + other.getFrom() + " to " + other.getTo());
            }
        }

        nextBlockId++;
        return blocks.save(block);
    }

    public double utilisationPercent(int vehicleId, LocalDate from, LocalDate to) {
        if (!vehicles.existsById(vehicleId)) {
            throw new IllegalArgumentException("no vehicle with id " + vehicleId);
        }
        long periodDays = ChronoUnit.DAYS.between(from, to);
        if (periodDays <= 0) {
            throw new IllegalArgumentException("period must be at least one day");
        }

        long booked = 0;
        for (Booking b : bookings.findAll()) {
            if (b.getVehicleId() == vehicleId) {
                booked += overlapDays(from, to, b.getStart(), b.getEnd());
            }
        }
        long blocked = 0;
        for (MaintenanceBlock m : blocks.findAll()) {
            if (m.getVehicleId() == vehicleId) {
                blocked += overlapDays(from, to, m.getFrom(), m.getTo());
            }
        }

        long available = periodDays - blocked;
        if (available <= 0) {
            return 0.0;
        }
        return booked * 100.0 / available;
    }

    public Map<String, Double> utilisationByBranch(LocalDate from, LocalDate to) {
        Map<String, Double> sums = new TreeMap<>();
        Map<String, Integer> counts = new TreeMap<>();
        for (Vehicle v : vehicles.findAll()) {
            double pct = utilisationPercent(v.getId(), from, to);
            sums.merge(v.getBranch(), pct, Double::sum);
            counts.merge(v.getBranch(), 1, Integer::sum);
        }
        Map<String, Double> averages = new TreeMap<>();
        for (Map.Entry<String, Double> e : sums.entrySet()) {
            averages.put(e.getKey(), e.getValue() / counts.get(e.getKey()));
        }
        return averages;
    }

    static long overlapDays(LocalDate aStart, LocalDate aEnd, LocalDate bStart, LocalDate bEnd) {
        LocalDate start = aStart.isAfter(bStart) ? aStart : bStart;
        LocalDate end = aEnd.isBefore(bEnd) ? aEnd : bEnd;
        return Math.max(0, ChronoUnit.DAYS.between(start, end));
    }
}
