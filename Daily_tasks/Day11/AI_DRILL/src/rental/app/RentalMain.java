package rental.app;

import rental.exception.MaintenanceConflictException;
import rental.model.Booking;
import rental.model.MaintenanceBlock;
import rental.model.Vehicle;
import rental.repository.InMemoryRepository;
import rental.service.FleetService;

import java.time.LocalDate;
import java.util.Map;

public class RentalMain {

    public static void main(String[] args) {
        InMemoryRepository<Vehicle, Integer> vehicles = new InMemoryRepository<>(Vehicle::getId);
        InMemoryRepository<Booking, Integer> bookings = new InMemoryRepository<>(Booking::getId);
        InMemoryRepository<MaintenanceBlock, Integer> blocks = new InMemoryRepository<>(MaintenanceBlock::getId);

        vehicles.save(new Vehicle(1, "TN01AB1234", "Chennai", 1800));
        vehicles.save(new Vehicle(2, "TN02CD5678", "Chennai", 2200));
        vehicles.save(new Vehicle(3, "TN38EF9012", "Coimbatore", 1500));

        bookings.save(new Booking(1, 1, LocalDate.of(2026, 11, 2), LocalDate.of(2026, 11, 7)));
        bookings.save(new Booking(2, 1, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 20)));
        bookings.save(new Booking(3, 2, LocalDate.of(2026, 11, 5), LocalDate.of(2026, 11, 9)));
        bookings.save(new Booking(4, 3, LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 16)));

        FleetService fleet = new FleetService(vehicles, bookings, blocks);

        System.out.println("=== FR7: block vehicle for maintenance ===");
        MaintenanceBlock ok = fleet.blockForMaintenance(1, LocalDate.of(2026, 11, 25), LocalDate.of(2026, 11, 30), "Engine service");
        System.out.println("  created: " + ok);

        try {
            fleet.blockForMaintenance(1, LocalDate.of(2026, 11, 5), LocalDate.of(2026, 11, 8), "Tyre change");
        } catch (MaintenanceConflictException e) {
            System.out.println("  rejected (booking overlap): " + e.getMessage());
        }
        try {
            fleet.blockForMaintenance(1, LocalDate.of(2026, 11, 28), LocalDate.of(2026, 12, 3), "Paint");
        } catch (MaintenanceConflictException e) {
            System.out.println("  rejected (already blocked): " + e.getMessage());
        }
        try {
            fleet.blockForMaintenance(9, LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 2), "Check");
        } catch (IllegalArgumentException e) {
            System.out.println("  rejected (unknown vehicle): " + e.getMessage());
        }

        LocalDate from = LocalDate.of(2026, 11, 1);
        LocalDate to = LocalDate.of(2026, 12, 1);

        System.out.println("\n=== FR8: utilisation for November 2026 ===");
        for (Vehicle v : vehicles.findAll()) {
            System.out.printf("  %-28s %6.1f%%%n", v, fleet.utilisationPercent(v.getId(), from, to));
        }
        System.out.println("\n  by branch:");
        for (Map.Entry<String, Double> e : fleet.utilisationByBranch(from, to).entrySet()) {
            System.out.printf("    %-12s %6.1f%%%n", e.getKey(), e.getValue());
        }
    }
}
