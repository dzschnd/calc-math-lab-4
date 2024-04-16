package web.calcmath;

public class TableRowData {
    private double x;
    private double y;
    private double approxY;
    private double deviation;

    public TableRowData(double x, double y, double approxY, double deviation) {
        this.x = x;
        this.y = y;
        this.approxY = approxY;
        this.deviation = deviation;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getApproxY() {
        return approxY;
    }

    public void setApproxY(double approxY) {
        this.approxY = approxY;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }
}