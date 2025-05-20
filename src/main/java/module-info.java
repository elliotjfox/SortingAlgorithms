module com.example.javafxsortingalgorithms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.javafxsortingalgorithms to javafx.fxml;
    exports com.example.javafxsortingalgorithms;
    exports com.example.javafxsortingalgorithms.settings;
    opens com.example.javafxsortingalgorithms.settings to javafx.fxml;
    exports com.example.javafxsortingalgorithms.algorithms;
    opens com.example.javafxsortingalgorithms.algorithms to javafx.fxml;
    exports com.example.javafxsortingalgorithms.algorithms.algorithmsettings;
    opens com.example.javafxsortingalgorithms.algorithms.algorithmsettings to javafx.fxml;
    exports com.example.javafxsortingalgorithms.arraydisplay;
    opens com.example.javafxsortingalgorithms.arraydisplay to javafx.fxml;
    exports com.example.javafxsortingalgorithms.animation;
    opens com.example.javafxsortingalgorithms.animation to javafx.fxml;
}