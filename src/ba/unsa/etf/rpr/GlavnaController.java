package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaController {

    public GeografijaDAO dao;
    GradController gradController;
    DrzavaController drzavaController;

    public TableView tableViewGradovi;
    public TableColumn colGradId;
    public TableColumn colGradNaziv;
    public TableColumn colGradStanovnika;
    public TableColumn colGradDrzava;
    public Button btnDodajGrad;
    public Button btnDodajDrzavu;
    public Button btnIzmijeniGrad;
    public Button btnObrisiGrad;

    public GlavnaController (GeografijaDAO d) {
        dao = d;
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
