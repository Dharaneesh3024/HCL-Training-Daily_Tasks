package availability;

public class BranchAvailability {

    private final String branch;
    private final int availableCars;
    private final boolean reachable;

    private BranchAvailability(String branch, int availableCars, boolean reachable) {
        this.branch = branch;
        this.availableCars = availableCars;
        this.reachable = reachable;
    }

    public static BranchAvailability of(String branch, int availableCars) {
        return new BranchAvailability(branch, availableCars, true);
    }

    public static BranchAvailability unknown(String branch) {
        return new BranchAvailability(branch, 0, false);
    }

    public String getBranch() { return branch; }
    public int getAvailableCars() { return availableCars; }
    public boolean isReachable() { return reachable; }

    @Override
    public String toString() {
        return reachable ? String.format("%-12s %2d cars", branch, availableCars) : String.format("%-12s unreachable", branch);
    }
}
