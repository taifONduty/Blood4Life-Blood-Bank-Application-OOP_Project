module bloodbank.blood4life {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires jdk.jsobject;
    requires javax.mail.api;
    requires com.gluonhq.maps;
    requires com.jfoenix;

    opens bloodbank.blood4life to javafx.fxml;
    exports bloodbank.blood4life;
}