package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.nio.ch.ThreadPool;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GlavnaController {

    public GeografijaDAO dao;
    GradController gradController;
    DrzavaController drzavaController;

    public TableView <Grad> tableViewGradovi;
    public TableColumn<Grad, Integer> colGradId;
    public TableColumn<Grad, String> colGradNaziv;
    public TableColumn<Grad, Integer> colGradStanovnika;
    public TableColumn<Grad, Integer> colGradDrzava;
    public Button btnDodajGrad;
    public Button btnDodajDrzavu;
    public Button btnIzmijeniGrad;
    public Button btnObrisiGrad;

    public GlavnaController (GeografijaDAO d) {
        dao = d;
    }

    public GlavnaController() {

    }


    @FXML
    public void initialize () {
        ObservableList<Grad> listaGradova = FXCollections.observableArrayList(dao.gradovi());
        tableViewGradovi.setItems(listaGradova);
        colGradId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGradNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        colGradStanovnika.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));
        colGradDrzava.setCellValueFactory(new PropertyValueFactory<>("drzava"));
        tableViewGradovi.getColumns().setAll(colGradId, colGradNaziv, colGradStanovnika, colGradDrzava);
    }

    public void resetujBazu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        dao = GeografijaDAO.getInstance();
    }


    public void dodajDrzavu(ActionEvent actionEvent) throws IOException {
        Parent root = null;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        loader.setController(new DrzavaController(null, dao.gradovi()));
        drzavaController = loader.getController();
        root = loader.load();
        myStage.setTitle("Drzave");

        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
        //myStage.setOnHiding(event -> dao.dodajDrzavu(drzavaController.getDrzava()));

//               myStage.setOnHiding(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("Application Closed by click to Close Button(X)");
//                        if (drzavaController.getDrzava() != null) {
//                            dao.dodajDrzavu(drzavaController.getDrzava());
//                        }
//                        System.exit(0);
//                    }
//                });
//            }
//        });
    }
    public void dodajGrad (ActionEvent actionEvent) throws IOException, SQLException {
        Parent root = null;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        loader.setController(new GradController(null, dao.drzave()));
        gradController = loader.getController();
        root = loader.load();
        myStage.setTitle("Gradovi");

        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
       // if (gradController.getGrad().getNaziv() != null && gradController.getGrad().getBrojStanovnika() != 0 && gradController.getGrad().getDrzava() != null) {
            if (gradController.getGrad() != null) {
                myStage.setOnHiding(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Application Closed by click to Ok Button");
//                            if (gradController.getGrad().getNaziv() != null && gradController.getGrad().getBrojStanovnika() != 0 && gradController.getGrad().getDrzava() != null) {
                                dao.dodajGrad(gradController.getGrad());
                                //}
                                System.exit(0);
                            }
                        });
                    }
                });
            }
    }

    public void promijeniGrad (ActionEvent actionEvent) throws IOException {
        Parent root = null;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        loader.setController(new GradController(dao.getTrenutniGrad(), dao.drzave())); //staviti grad koji se edituje umjesto null
        gradController = loader.getController();
        root = loader.load();
        myStage.setTitle("Gradovi");
        gradController = loader.getController();

        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
        if (gradController.getGrad() != null) {
            myStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Application Closed by click to Close Button(X)");
                            if (gradController.getGrad() != null) {
                                dao.izmijeniGrad(gradController.getGrad());
                            }
                            Platform.exit();
                            System.exit(0);
                        }
                    });
                }
            });
        }
    }

    public void obrisiGrad (ActionEvent actionEvent) throws IOException {
        Parent root = null;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        loader.setController(new GradController(null, dao.drzave()));
        gradController = loader.getController();
        root = loader.load();
        myStage.setTitle("Gradovi");

        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();

//        myStage.setOnHiding(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("Application Closed by click to Close Button(X)");
//                        if (gradController.getGrad() != null) {
//                            dao.izmijeniGrad(gradController.getGrad());
//                        }
//                        Platform.exit();
//                        System.exit(0);
//                    }
//                });
//            }
//        });

    }
}
