package model;

public class Customer {

    private final int id;
    private final String name;
    private final String city;

    public Customer(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public static Customer fromCsv(String[] cols) {
        return new Customer(Integer.parseInt(cols[0].trim()), cols[1].trim(), cols[2].trim());
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return "#" + id + " " + name + " (" + city + ")";
    }
}
