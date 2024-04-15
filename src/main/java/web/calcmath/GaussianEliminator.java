package web.calcmath;

public class GaussianEliminator {
    private static final double EPSILON = 1e-16;
    private final double[][] coefficients;
    private final double[] constants;
    private final double[] unknowns;
    private final int equationCount;
    public GaussianEliminator(double[][] coefficients, double[] constants, int equationCount) {
        this.equationCount = equationCount;
        this.coefficients = coefficients;
        this.constants = constants;
        unknowns = new double[equationCount];
    }
    public void solve() {

        for (int pivot = 0; pivot < equationCount; pivot++) {
            int maxPivot = pivot;
            for (int i = pivot + 1; i < equationCount; i++) {
                if (Math.abs(coefficients[i][pivot]) > Math.abs(coefficients[maxPivot][pivot])) {
                    maxPivot = i;
                }
            }
            if (pivot != maxPivot) {
                rowsSwap(pivot, maxPivot);
            }

            if (Math.abs(coefficients[pivot][pivot]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular.\n");
            }

            for (int i = pivot + 1; i < equationCount; i++) {
                double factor = coefficients[i][pivot] / coefficients[pivot][pivot];
                constants[i] -= factor * constants[pivot];
                for (int j = pivot; j < equationCount; j++) {
                    coefficients[i][j] -= factor * coefficients[pivot][j];
                }
            }
        }

        for (int i = equationCount - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < equationCount; j++) {
                sum += coefficients[i][j] * unknowns[j];
            }
            unknowns[i] = (constants[i] - sum) / coefficients[i][i];
        }
    }
    private void rowsSwap(int i, int j) {
        double[] aTemp = coefficients[i];
        coefficients[i] = coefficients[j];
        coefficients[j] = aTemp;
        double bTemp = constants[i];
        constants[i] = constants[j];
        constants[j] = bTemp;
    }
    public double[] getUnknowns() {
        return this.unknowns;
    }
}
