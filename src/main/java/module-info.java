module com.frm.tradecalculatormaven {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.frm.tradecalculatormaven to javafx.fxml;
    opens com.frm.tradecalculatormaven.models to javafx.base;
    exports com.frm.tradecalculatormaven;
}
