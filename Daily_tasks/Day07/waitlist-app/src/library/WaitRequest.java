package library;

public class WaitRequest implements Comparable<WaitRequest> {

    private final String member;
    private final boolean faculty;
    private final int order;      // arrival number, used to keep things fair inside a group

    public WaitRequest(String member, boolean faculty, int order) {
        this.member = member;
        this.faculty = faculty;
        this.order = order;
    }

    // faculty first, then whoever came earlier
    @Override
    public int compareTo(WaitRequest other) {
        if (this.faculty != other.faculty) {
            return this.faculty ? -1 : 1;
        }
        return Integer.compare(this.order, other.order);
    }

    @Override
    public String toString() {
        return member + (faculty ? " [faculty]" : "") + " #" + order;
    }
}
