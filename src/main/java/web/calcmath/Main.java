package web.calcmath;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    static final Label HEADER_MESSAGE = new Label("Введите точки (формат ввода: (x1;y1),(x2;y2)...)");
    static final TextField DOT_INPUT = new TextField("(1;2),(2;5),(3;9),(4;19),(5;22),(6;40),(7;50),(8;90)");
    static final Button APPROXIMATE_BUTTON = new Button("Аппроксимировать");
    static final Label ERROR_MESSAGE = new Label();
    static final Label APPROXIMATED_FUNCTION_MESSAGE = new Label();
    static final Label STANDARD_DEVIATION_MESSAGE = new Label();
    static final Label DETERMINATION_COEFFICIENT_MESSAGE = new Label();
    static final Label CORRELATION_COEFFICIENT_MESSAGE = new Label();
    static final HBox TABLE_BOX = new HBox();
    static final HBox PLOT_BOX = new HBox();
    static final VBox ROOT_LAYOUT = new VBox();
    @Override
    public void start(Stage stage) {
        InputHandler inputHandler = new InputHandler();

        APPROXIMATE_BUTTON.setOnAction(inputHandler);

        VBox.setMargin(HEADER_MESSAGE, new Insets(5));
        VBox.setMargin(DOT_INPUT, new Insets(5));
        VBox.setMargin(APPROXIMATE_BUTTON, new Insets(5));
        VBox.setMargin(APPROXIMATED_FUNCTION_MESSAGE, new Insets(5));
        VBox.setMargin(STANDARD_DEVIATION_MESSAGE, new Insets(5));
        VBox.setMargin(DETERMINATION_COEFFICIENT_MESSAGE, new Insets(5));
        VBox.setMargin(CORRELATION_COEFFICIENT_MESSAGE, new Insets(5));
        VBox.setMargin(ERROR_MESSAGE, new Insets(5));
        VBox.setMargin(TABLE_BOX, new Insets(5));
        VBox.setMargin(PLOT_BOX, new Insets(5));

        DOT_INPUT.setStyle("-fx-max-width: 350");
        TABLE_BOX.setStyle("-fx-max-width: 350; -fx-min-height: 220");
        HEADER_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 24");
        APPROXIMATED_FUNCTION_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");
        STANDARD_DEVIATION_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");
        DETERMINATION_COEFFICIENT_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");
        CORRELATION_COEFFICIENT_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");
        ERROR_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");

        TABLE_BOX.setAlignment(Pos.CENTER);
        PLOT_BOX.setAlignment(Pos.CENTER);
        ROOT_LAYOUT.setAlignment(Pos.TOP_CENTER);

        ROOT_LAYOUT.setStyle("-fx-background-color: #2d2d2d;");
        ROOT_LAYOUT.getChildren().addAll(HEADER_MESSAGE, DOT_INPUT, APPROXIMATE_BUTTON, ERROR_MESSAGE, APPROXIMATED_FUNCTION_MESSAGE, STANDARD_DEVIATION_MESSAGE, DETERMINATION_COEFFICIENT_MESSAGE, CORRELATION_COEFFICIENT_MESSAGE, TABLE_BOX, PLOT_BOX);

        Scene scene = new Scene(ROOT_LAYOUT, 890, 780);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.setTitle("Вычмат...");
        stage.show();
    }
}