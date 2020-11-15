package nk.gk.wyl.doc.entity.result;

public class Pager {
    private int total;
    private int start;
    private int limit;

    public Pager() {
    }

    public Pager(int total, int start, int limit) {
        this.total = total;
        this.start = start;
        this.limit = limit;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    @Override
    public String toString() {
        return "Paper{" +
                "total=" + total +
                ", start=" + start +
                ", limit=" + limit +
                '}';
    }
}
