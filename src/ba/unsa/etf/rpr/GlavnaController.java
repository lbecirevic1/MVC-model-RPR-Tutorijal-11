package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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
        try {
            Stage myStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
            loader.load();
            drzavaController = loader.getController();

            myStage.setTitle("Drzave");
            myStage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
            // myStage.setOnHiding(event -> labela.setText(drzavaController.getVrijednost()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dodajGrad (ActionEvent actionEvent) throws IOException {
        Parent root = null;
        try {
            Stage myStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
            loader.load();
            gradController = loader.getController();

            myStage.setTitle("Graovi");
            myStage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
            // myStage.setOnHiding(event -> labela.setText(drzavaController.getVrijednost()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void promijeniGrad (ActionEvent actionEvent) throws IOException {
        Parent root = null;
        try {
            Stage myStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
            loader.load();
            gradController = loader.getController();

            myStage.setTitle("Gradovi");
            myStage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
            // myStage.setOnHiding(event -> labela.setText(drzavaController.getVrijednost()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
