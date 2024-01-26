module c195.c195assessment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens c195.c195assessment.controller to javafx.fxml;
    opens c195.c195assessment.model to javafx.base;
    exports c195.c195assessment;
}