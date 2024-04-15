package web.calcmath;

public class TableRowData {
    private final double x;
    private final double y;
    private final double approxY;
    private final double deviation;

    public TableRowData(double x, double y, double approxY, double deviation) {
        this.x = x;
        this.y = y;
        this.approxY = approxY;
        this.deviation = deviation;
    }
    public double getX() {
        return x;
    }

}