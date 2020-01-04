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
    public ChoiceBox<Grad> choiceGrad;
    public Button btnOk;
    public Button btnCancel;
    private ArrayList<Grad> listaGradova;
    private Drzava trenutnaDrzava;

    public DrzavaController(Drzava drzava, ArrayList<Grad> gradovi) {
        listaGradova = gradovi;
        trenutnaDrzava = drzava;
    }

    public void initialize () {
        ObservableList<Grad> gradovi = FXCollections.observableArrayList(listaGradova);
        choiceGrad.setItems(gradovi);
        if (trenutnaDrzava != null) {
            fieldNaziv.setText(trenutnaDrzava.getNaziv());
            choiceGrad.getSelectionModel().select(trenutnaDrzava.getGlavniGrad());
        } else {
            choiceGrad.getSelectionModel().selectFirst();
        }
    }
    public void zatvoriProzor(javafx.event.ActionEvent actionEvent) {
        trenutnaDrzava = null;
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void validirajPolje(ActionEvent actionEvent) {
        boolean poljaIspravna = false;
        if(fieldNaziv.getText().trim().equals("")) {
            fieldNaziv.getStyleClass().removeAll("ispravno");
            fieldNaziv.getStyleClass().add("nijeIspravno");
        } else {
            fieldNaziv.getStyleClass().removeAll("nijeIspravno");
            fieldNaziv.getStyleClass().add("ispravno");
            poljaIspravna = true;
        }
        if (trenutnaDrzava == null)
            trenutnaDrzava = new Drzava();
        if (!poljaIspravna)
            return;

        trenutnaDrzava.setNaziv(fieldNaziv.getText());
        trenutnaDrzava.setGlavniGrad(choiceGrad.getSelectionModel().getSelectedItem());
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public Drzava getDrzava() {
        return trenutnaDrzava;
    }

}


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
