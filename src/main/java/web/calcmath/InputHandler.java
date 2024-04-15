package web.calcmath;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;
import static web.calcmath.Main.*;

public class InputHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource().equals(APPROXIMATE_BUTTON)) {
            try {
                String input = DOT_INPUT.getText();
                String[] dotInputs = input.split("\\s*,\\s*");
                HashMap<Double, Double> dotMap = new HashMap<>();
                for (String dotInput : dotInputs) {
                    double x = Double.parseDouble(dotInput.split("\\s*;\\s*")[0].replace("(", ""));
                    double y = Double.parseDouble(dotInput.split("\\s*;\\s*")[1].replace(")", ""));
                    dotMap.put(x, y);
                }
                Pair<String, DoubleUnaryOperator> approximatedFunctionPair = approximateFunction(dotMap);
                DoubleUnaryOperator approximatedFunction = approximatedFunctionPair.getValue();
                String approximatedFunctionToString = approximatedFunctionPair.getKey();
                double standardDeviation = calculateStandardDeviation(dotMap, approximatedFunction);
                double determinationCoefficient = calculateDeterminationCoefficient(dotMap, approximatedFunction);
                double correlationCoefficient = calculateCorrelationCoefficient(dotMap);

                APPROXIMATED_FUNCTION_MESSAGE.setText("Приближенная функция: " + approximatedFunctionToString);
                STANDARD_DEVIATION_MESSAGE.setText("Среднеквадратичное отклонение: " + String.format("%.5f", standardDeviation));
                DETERMINATION_COEFFICIENT_MESSAGE.setText("Коэффициент детерминации: " + String.format("%.5f", determinationCoefficient) +
                        (0.95 <= determinationCoefficient ? " (Высокая точность)" :
                                (0.75 <= determinationCoefficient ? " (Удовлетворительная точность)" :
                                        (0.5 <= determinationCoefficient ? " (Низкая точность)" :
                                                (" (Недостаточная точность)")))));
                CORRELATION_COEFFICIENT_MESSAGE.setText("Коэффициент корреляции: " + String.format("%.5f", correlationCoefficient));

                createTable(dotMap, approximatedFunction);

                drawPlot(dotInputs, dotMap, approximatedFunction);
            } catch (NumberFormatException e) {
               setErrorMessage("Неверный формат ввода");
            } catch (ArithmeticException e) {
                setErrorMessage("Невозможно аппроксимировать функцию");
            }
        }
    }
    private static Pair<String, DoubleUnaryOperator> approximateFunction(HashMap<Double, Double> dotMap) {
        return switch (METHOD_INPUT.getValue().toString()) {
            case "Линейная аппроксимация" -> FunctionApproximator.approximateLinear(dotMap);
            case "Квадратичная аппроксимация" -> FunctionApproximator.approximateSquare(dotMap);
            case "Кубическая аппроксимация" -> FunctionApproximator.approximateCube(dotMap);
            case "Степенная аппроксимация" -> FunctionApproximator.approximatePower(dotMap);
            case "Экспоненциальная аппроксимация" -> FunctionApproximator.approximateExponent(dotMap);
            case "Логарифмическая аппроксимация" -> FunctionApproximator.approximateLogarithm(dotMap);
            default -> null;
        };
    }
    private static double calculateStandardDeviation(HashMap<Double, Double> dotMap, DoubleUnaryOperator approximatedFunction) {
        double approxYDevSquareSum = 0;
        for (Map.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            double approxY = approximatedFunction.applyAsDouble(x);
            double deviation = abs(approxY - y);
            approxYDevSquareSum += pow(deviation, 2);
        }
        return sqrt(approxYDevSquareSum / dotMap.size());
    }
    private static double calculateDeterminationCoefficient(HashMap<Double, Double> dotMap, DoubleUnaryOperator approximatedFunction) {
        double approxYSum = 0;
        double approxYDevSquareSum = 0;
        for (Map.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            double approxY = approximatedFunction.applyAsDouble(x);
            double deviation = abs(approxY - y);
            approxYSum += approxY;
            approxYDevSquareSum += pow(deviation, 2);
        }
        double approxYAverage = approxYSum / dotMap.size();

        double approxYAverageDevSquareSum = 0;
        for (Map.Entry<Double, Double> entry : dotMap.entrySet()) {
            double y = entry.getValue();
            approxYAverageDevSquareSum = pow(approxYAverage - y, 2);
        }
        return 1 - approxYDevSquareSum / approxYAverageDevSquareSum;
    }
    private static double calculateCorrelationCoefficient(HashMap<Double, Double> dotMap) {
        double xSum = 0;
        double ySum = 0;
        for (Map.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xSum += x;
            ySum += y;
        }
        double xAverage = xSum / dotMap.size();
        double yAverage = ySum / dotMap.size();
        double xDevYDevSum = 0;
        double xDevSquareSum = 0;
        double yDevSquareSum = 0;
        for (Map.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            xDevYDevSum += (x - xAverage) * (y - yAverage);
            xDevSquareSum += pow(x - xAverage, 2);
            yDevSquareSum += pow(y - yAverage, 2);
        }
        return xDevYDevSum / sqrt(xDevSquareSum*yDevSquareSum);
    }
    private static void createTable(HashMap<Double, Double> dotMap, DoubleUnaryOperator approximatedFunction) {
        ObservableList<TableRowData> tableData = FXCollections.observableArrayList(new TableRowData[0]);

        for (Map.Entry<Double, Double> entry : dotMap.entrySet()) {
            double x = entry.getKey();
            double y = entry.getValue();
            double approxY = approximatedFunction.applyAsDouble(x);
            double deviation = abs(approxY - y);
            tableData.add(new TableRowData(x, y, approxY, deviation));
        }

        TableView<TableRowData> table = new TableView<>();
        table.setItems(tableData);

        TableColumn<TableRowData, Double> xColumn = new TableColumn<>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));

        TableColumn<TableRowData, Double> yColumn = new TableColumn<>("y");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));

        TableColumn<TableRowData, Double> approxYColumn = new TableColumn<>("phi(x)");
        approxYColumn.setCellValueFactory(new PropertyValueFactory<>("approxY"));

        TableColumn<TableRowData, Double> deviationColumn = new TableColumn<>("ε");
        deviationColumn.setCellValueFactory(new PropertyValueFactory<>("deviation"));

        table.getColumns().addAll(xColumn, yColumn, approxYColumn, deviationColumn);

        Comparator<TableRowData> comparator = Comparator.comparingDouble(TableRowData::getX);
        FXCollections.sort(tableData, comparator);
        table.getSortOrder().clear();
        xColumn.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(xColumn);
        table.sort();

        table.prefWidthProperty().bind(TABLE_BOX.widthProperty());
        table.prefHeightProperty().bind(TABLE_BOX.heightProperty());

        TABLE_BOX.getChildren().clear();
        TABLE_BOX.getChildren().add(table);
    }
    private static void drawPlot(String[] dotInputs, HashMap<Double, Double> dotMap, DoubleUnaryOperator approximatedFunction) {
        double leftBorder = Double.parseDouble(dotInputs[0].split("\\s*;\\s*")[0].replace("(", "")) - 10;
        double rightBorder = Double.parseDouble(dotInputs[dotInputs.length - 1].split("\\s*;\\s*")[0].replace("(", "")) + 10;
        double lowerBorder = Double.parseDouble(dotInputs[0].split("\\s*;\\s*")[1].replace(")", "")) - 10;
        double upperBorder = Double.parseDouble(dotInputs[dotInputs.length - 1].split("\\s*;\\s*")[1].replace(")", "")) + 10;
        PlotDrawer.drawPlot(leftBorder, rightBorder, lowerBorder, upperBorder, dotMap, approximatedFunction);
    }
    private static void setErrorMessage(String errorMessage) {
        APPROXIMATED_FUNCTION_MESSAGE.setText("");
        STANDARD_DEVIATION_MESSAGE.setText("");
        DETERMINATION_COEFFICIENT_MESSAGE.setText("");
        CORRELATION_COEFFICIENT_MESSAGE.setText("");
        ERROR_MESSAGE.setText(errorMessage);
    }
}