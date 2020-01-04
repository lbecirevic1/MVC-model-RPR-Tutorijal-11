package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrzavaController  {
    public TextField fieldNaziv;
    public ChoiceBox choiceGrad;
    public Button btnOk;
    public Button btnCancel;
    private ArrayList<Grad> listaGradova;
    private Drzava trenutnaDrzava;

    public DrzavaController(Grad grad, ArrayList<Grad> gradovi) {
        listaGradova = gradovi;
        trenutnaDrzava = new Drzava();
    }

    public void initialize () {
        ObservableList<Grad> gradovi = FXCollections.observableArrayList(listaGradova);
        choiceGrad.setItems(gradovi);

    }
    public void zatvoriProzor(javafx.event.ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void validirajPolje(ActionEvent actionEvent) {
        if(fieldNaziv.getText().trim().equals("")) {
            fieldNaziv.getStyleClass().removeAll("ispravno");
            fieldNaziv.getStyleClass().add("nijeIspravno");
            trenutnaDrzava.setNaziv(fieldNaziv.getText());
            trenutnaDrzava.setGlavniGrad(choiceGrad.getSelectionModel().getSelectedItem());

        } else {
            fieldNaziv.getStyleClass().removeAll("nijeIspravno");
            fieldNaziv.getStyleClass().add("ispravno");
        }
//            Node n = (Node) actionEvent.getSource();
//            Stage stage = (Stage) n.getScene().getWindow();
//            stage.close();

//        //Bez klika postaje crveno
//        fieldNaziv.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.isEmpty()) {
//                fieldNaziv.getStyleClass().removeAll("nijeIspravno");
//                fieldNaziv.getStyleClass().add("ispravno");
//            } else {
//                fieldNaziv.getStyleClass().removeAll("ispravno");
//                fieldNaziv.getStyleClass().add("nijeIspravno");
//            }
//        });
    }

    public Drzava getDrzava() {
        return trenutnaDrzava;
    }
}
