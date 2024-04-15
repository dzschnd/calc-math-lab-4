package web.calcmath;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;


public class FunctionApproximator {
    public static Pair<String, DoubleUnaryOperator> approximateLinear(HashMap<Double, Double> dotMap) {
        int dotCount = dotMap.size();
        double xSum = 0;
        double xPow2Sum = 0;
        double ySum = 0;
        double xySum = 0;
        for (HashMap.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xSum += x;
            xPow2Sum += pow(x, 2);
            ySum += y;
            xySum += x * y;
        }

        int equationCount = 2;
        double[][] coefficients = new double[][]{{dotCount, xSum},{xSum, xPow2Sum}};
        double[] constants = new double[]{ySum, xySum};
        GaussianEliminator matrixSolver = new GaussianEliminator(coefficients, constants, equationCount);
        matrixSolver.solve();
        double a = matrixSolver.getUnknowns()[0];
        double b = matrixSolver.getUnknowns()[1];
        return new Pair<>("("+String.format("%.5f", a)+") + ("+String.format("%.5f", b)+")" + "x" , x -> a + b*x);
    }
    public static Pair<String, DoubleUnaryOperator> approximateSquare(HashMap<Double, Double> dotMap) {
        int dotCount = dotMap.size();
        double xSum = 0;
        double xPow2Sum = 0;
        double xPow3Sum = 0;
        double xPow4Sum = 0;
        double ySum = 0;
        double xySum = 0;
        double xPow2ySum = 0;
        for (HashMap.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xSum += x;
            xPow2Sum += pow(x, 2);
            xPow3Sum += pow(x, 3);
            xPow4Sum += pow(x, 4);
            ySum += y;
            xySum += x * y;
            xPow2ySum += pow(x, 2)*y;
        }

        int equationCount = 3;
        double[][] coefficients = new double[][]{{dotCount, xSum, xPow2Sum},{xSum, xPow2Sum, xPow3Sum}, {xPow2Sum, xPow3Sum, xPow4Sum}};
        double[] constants = new double[]{ySum, xySum, xPow2ySum};
        GaussianEliminator matrixSolver = new GaussianEliminator(coefficients, constants, equationCount);
        matrixSolver.solve();
        double a = matrixSolver.getUnknowns()[0];
        double b = matrixSolver.getUnknowns()[1];
        double c = matrixSolver.getUnknowns()[2];
        return new Pair<>("("+String.format("%.5f", a)+") + ("+String.format("%.5f", b)+")x + ("+String.format("%.5f", c)+")x^2" , x -> a + b*x + c*pow(x, 2));
    }

    public static Pair<String, DoubleUnaryOperator> approximateCube(HashMap<Double, Double> dotMap) {
        int dotCount = dotMap.size();
        double xSum = 0;
        double xPow2Sum = 0;
        double xPow3Sum = 0;
        double xPow4Sum = 0;
        double xPow5Sum = 0;
        double xPow6Sum = 0;
        double ySum = 0;
        double xySum = 0;
        double xPow2ySum = 0;
        double xPow3ySum = 0;
        for (HashMap.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xSum += x;
            xPow2Sum += pow(x, 2);
            xPow3Sum += pow(x, 3);
            xPow4Sum += pow(x, 4);
            xPow5Sum += pow(x, 5);
            xPow6Sum += pow(x, 6);
            ySum += y;
            xySum += x * y;
            xPow2ySum += pow(x, 2)*y;
            xPow3ySum += pow(x, 3)*y;
        }

        int equationCount = 4;
        double[][] coefficients = new double[][]{{dotCount, xSum, xPow2Sum, xPow3Sum},
                                                {xSum, xPow2Sum, xPow3Sum, xPow4Sum},
                                                {xPow2Sum, xPow3Sum, xPow4Sum, xPow5Sum},
                                                {xPow3Sum, xPow4Sum, xPow5Sum, xPow6Sum}};
        double[] constants = new double[]{ySum, xySum, xPow2ySum, xPow3ySum};
        GaussianEliminator matrixSolver = new GaussianEliminator(coefficients, constants, equationCount);
        matrixSolver.solve();
        double a = matrixSolver.getUnknowns()[0];
        double b = matrixSolver.getUnknowns()[1];
        double c = matrixSolver.getUnknowns()[2];
        double d = matrixSolver.getUnknowns()[3];
        return new Pair<>("("+String.format("%.5f", a)+") + ("+String.format("%.5f", b)+")x + ("+String.format("%.5f", c)+")x^2 + ("+String.format("%.5f", d)+")x^3", x -> a + b*x + c*pow(x, 2) + d*pow(x, 3));
    }
    public static Pair<String, DoubleUnaryOperator> approximatePower(HashMap<Double, Double> dotMap) {
        int dotCount = dotMap.size();
        double xSum = 0;
        double xPow2Sum = 0;
        double ySum = 0;
        double xySum = 0;
        for (HashMap.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xSum += x;
            xPow2Sum += pow(x, 2);
            ySum += y;
            xySum += x * y;
        }

        int equationCount = 2;
        double[][] coefficients = new double[][]{{dotCount, xSum},{xSum, xPow2Sum}};
        double[] constants = new double[]{ySum, xySum};
        GaussianEliminator matrixSolver = new GaussianEliminator(coefficients, constants, equationCount);
        matrixSolver.solve();
        double a = pow(E, matrixSolver.getUnknowns()[0]);
        double b = matrixSolver.getUnknowns()[1];
        return new Pair<>(a + "x^("+b+")" , x -> a * pow(x, b));
    }
    public static Pair<String, DoubleUnaryOperator> approximateExponent(HashMap<Double, Double> dotMap) {
        int dotCount = dotMap.size();
        double xSum = 0;
        double xPow2Sum = 0;
        double ySum = 0;
        double xySum = 0;
        for (HashMap.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xSum += x;
            xPow2Sum += pow(x, 2);
            ySum += y;
            xySum += x * y;
        }

        int equationCount = 2;
        double[][] coefficients = new double[][]{{dotCount, xSum},{xSum, xPow2Sum}};
        double[] constants = new double[]{ySum, xySum};
        GaussianEliminator matrixSolver = new GaussianEliminator(coefficients, constants, equationCount);
        matrixSolver.solve();
        double a = pow(E, matrixSolver.getUnknowns()[0]);
        double b = matrixSolver.getUnknowns()[1];
        return new Pair<>("("+String.format("%.5f", a)+") + e^("+String.format("%.5f", b)+")x" , x -> a * pow(E, b*x));
    }
    public static Pair<String, DoubleUnaryOperator> approximateLogarithm(HashMap<Double, Double> dotMap) {
        int dotCount = dotMap.size();
        double xSum = 0;
        double xPow2Sum = 0;
        double ySum = 0;
        double xySum = 0;
        for (HashMap.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xSum += x;
            xPow2Sum += pow(x, 2);
            ySum += y;
            xySum += x * y;
        }

        int equationCount = 2;
        double[][] coefficients = new double[][]{{dotCount, xSum},{xSum, xPow2Sum}};
        double[] constants = new double[]{ySum, xySum};
        GaussianEliminator matrixSolver = new GaussianEliminator(coefficients, constants, equationCount);
        matrixSolver.solve();
        double a = matrixSolver.getUnknowns()[0];
        double b = matrixSolver.getUnknowns()[1];
        return new Pair<>("("+String.format("%.5f", a)+")" + " + ("+String.format("%.5f", b)+")ln(x)", x -> a + b*log(x));
    }
}
