package lab;

import java.util.ArrayList;
import java.util.List;

public class RentalDemo {

    static class Vehicle {
        final String plate;
        final double dailyRate;

        Vehicle(String plate, double dailyRate) {
            this.plate = plate;
            this.dailyRate = dailyRate;
        }
    }

    static class Booking {
        final Vehicle vehicle;
        final int days;
        final double total;

        Booking(Vehicle vehicle, int days, double total) {
            this.vehicle = vehicle;
            this.days = days;
            this.total = total;
        }
    }

    static final List<Booking> bookings = new ArrayList<>();

    static Booking book(Vehicle vehicle, int days) {
        double total = vehicle.dailyRate * days;
        Booking booking = new Booking(vehicle, days, total);
        bookings.add(booking);
        return booking;
    }

    public static void main(String[] args) {
        Vehicle swift = new Vehicle("TN01AB1234", 1800);
        int days = 3;
        Booking result = book(swift, days);
        System.out.println(result.vehicle.plate + " for " + result.days + " days = " + result.total);
    }
}
