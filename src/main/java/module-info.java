module ote.passmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ote.passmanager to javafx.fxml;
    exports ote.passmanager;
}
