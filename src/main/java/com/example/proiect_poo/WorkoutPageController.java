package com.example.proiect_poo;

import com.example.proiect_poo.entities.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorkoutPageController implements Initializable {
    public TableColumn colName;
    public TableColumn colCategory;
    public TableColumn colSets;
    public TableColumn colRepetitions;

    @FXML
    public Button button_logout;

    @FXML
    private TableView tableview;
    public VBox V_Box;
    private String category;
//    List<Training> trainings = new ArrayList<>();

    private ObservableList<ObservableList> data;

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mySQLPersistence");

    public void setCategory(String category) {
        this.category = category;
        getTrainings(category);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Main.fxml", "Log in!", null);
            }
        });
    }

    public void getTrainings(String category) {
        EntityManager em = emf.createEntityManager();
        data = FXCollections.observableArrayList();

        try {
            TypedQuery<Training> query = em.createQuery("SELECT t FROM Training t WHERE t.category = :category", Training.class);
//            SELECT t FROM Training t: Selects all instances of the Training entity
            query.setParameter("category", category);
            List<Training> trainings = query.getResultList();

            for (Training training : trainings) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(training.getName());
                row.add(training.getCategory());
                row.add(String.valueOf(training.getSets()));
                row.add(String.valueOf(training.getRepetitions()));
                data.add(row);
            }

            setupTableColumns();

            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error on Building Data");
        } finally {
            em.close();
        }
    }

    private void setupTableColumns() {
        tableview.getColumns().clear();

        String[] columnNames = {"Name", "Category", "Sets", "Repetitions"};
        for (int i = 0; i < columnNames.length; i++) {
            final int j = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(columnNames[i]);
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j));
                }
            });

            tableview.getColumns().add(col);
        }
    }

    private void showAlert(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.show();
    }
}


//package com.example.proiect_poo;
//
//import com.example.proiect_poo.entities.Training;
//import javafx.collections.FXCollections;
//
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableColumn.CellDataFeatures;
//import javafx.scene.control.TableView;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//import javafx.util.Callback;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class WorkoutPageController implements Initializable {
//    public TableColumn colName;
//    public TableColumn colCategory;
//    public TableColumn colSets;
//    public TableColumn colRepetitions;
//
//    @FXML
//    public Button button_logout;
//
//    @FXML
//    private TableView tableview;
//    public VBox V_Box;
//    private String category;
//    List<Training> trainings = new ArrayList<>();
//
//    private ObservableList<ObservableList> data;
//
//    public void setCategory(String category) {
//        this.category = category;
//
//        getTrainings(category);
//    }
//
//
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        button_logout.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                DBUtils.changeScene(event, "Main.fxml", "Log in!", null);
//            }
//        });
//    }
//
//    public void getTrainings(String category) {
//        Connection c =null;
//        PreparedStatement preparedStatement = null;
////            ResultSet rs = null;
//        data = FXCollections.observableArrayList();
//        try {
//            c = DBConnect.connect();
//            //SQL FOR SELECTING ALL
//            String SQL = "SELECT * FROM training where category = ?";
//            preparedStatement = c.prepareStatement(SQL);
//            preparedStatement.setString(1, category );
//            //ResultSet
//            ResultSet rs = preparedStatement.executeQuery();
//
////                 * TABLE COLUMN ADDED DYNAMICALLY *
//
//            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
//                //We are using non property style for making dynamic table
//                final int j = i;
//                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
//                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
//                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                    }
//                });
//
//                tableview.getColumns().addAll(col);
//                System.out.println("Column [" + i + "] ");
//            }
//
//
////                  Data added to ObservableList *
//
//            while (rs.next()) {
//                //Iterate Row
//                ObservableList<String> row = FXCollections.observableArrayList();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    //Iterate Column
//                    row.add(rs.getString(i));
//                }
//                System.out.println("Row [1] added " + row);
//                data.add(row);
//
//            }
//            //FINALLY ADDED TO TableView
//            tableview.setItems(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error on Building Data");
//        }
//    }
//
//}
//
////creezi alt tabel de legatura in care sa am 3 col, id(ai), user id si training id
//
//
//
