module web.calcmath {
    requires javafx.controls;
    requires javafx.fxml;


    opens web.calcmath to javafx.fxml;
    exports web.calcmath;
}