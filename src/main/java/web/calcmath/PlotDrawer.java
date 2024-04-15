package web.calcmath;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.function.DoubleUnaryOperator;

import static web.calcmath.Main.*;

public class PlotDrawer {
    private static final NumberAxis X_AXIS = new NumberAxis();
    private static final NumberAxis Y_AXIS = new NumberAxis();
    private static final double STEP = 0.01;
    static {
        X_AXIS.setLabel("x");
        Y_AXIS.setLabel("y");
    }
    public static void drawPlot(double leftBorder, double rightBorder, double lowerBorder, double upperBorder, HashMap<Double, Double> dotMap, DoubleUnaryOperator f) {
        LineChart<Number, Number> plot = new LineChart<>(X_AXIS, Y_AXIS);
        XYChart.Series<Number, Number> dotSeries = new XYChart.Series<>();
        dotSeries.setName("Исходная функция");

        for (HashMap.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            dotSeries.getData().add(new XYChart.Data<>(x, y));
        }

        XYChart.Series<Number, Number> approximationSeries = new XYChart.Series<>();
        approximationSeries.setName("Приближенная функция");
        for (double x = leftBorder; x <= rightBorder; x += STEP) {
            double y = f.applyAsDouble(x);
            approximationSeries.getData().add(new XYChart.Data<>(x, y));
        }

        plot.getData().addAll(dotSeries, approximationSeries);

        Y_AXIS.setAutoRanging(false);
        Y_AXIS.setLowerBound(lowerBorder);
        Y_AXIS.setUpperBound(upperBorder);

        PLOT_BOX.getChildren().clear();
        ROOT_LAYOUT.getChildren().add(plot);
    }
}
