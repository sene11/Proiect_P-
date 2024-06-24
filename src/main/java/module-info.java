module com.example.proiect_poo {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens com.example.proiect_poo to javafx.fxml;
    opens com.example.proiect_poo.entities to org.hibernate.orm.core; // Open the entities package to Hibernate
    exports com.example.proiect_poo;
}
