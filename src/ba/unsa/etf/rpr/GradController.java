package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class GradController {
    public TextField fieldNaziv;
    public ChoiceBox<Drzava> choiceDrzava;
    public TextField fieldBrojStanovnika;
    public Button btnOk;
    public Button btnCancel;
    private ArrayList<Drzava> listaDrzava;
    private Grad editujGrad;
    private Grad trenutniGrad = null;

    //btnCancel
    public void zatvoriProzor(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public GradController(Grad editujGrad, ArrayList<Drzava> listaDrzava) {
        this.listaDrzava = listaDrzava;
        this.editujGrad = editujGrad;
     //   trenutniGrad = new Grad();
    }

    @FXML
    public void initialize () {
        ObservableList<Drzava> drzave = FXCollections.observableArrayList(listaDrzava);
        choiceDrzava.setItems(drzave);
        if (editujGrad != null) {
            fieldNaziv.setText(editujGrad.getNaziv());
            fieldBrojStanovnika.setText(String.valueOf(editujGrad.getBrojStanovnika()));
         //   choiceDrzava.setValue(editujGrad.getDrzava());
        }

    }

    //btnOk
    public void validiraj(ActionEvent actionEvent) {
        boolean jesteInt = true;
        boolean nijePrazno = true;
         try {
                Integer.parseInt(fieldBrojStanovnika.getText());
            } catch(NumberFormatException e) {
                jesteInt = false;
            } catch(NullPointerException e) {
                nijePrazno = false;
            }
        if(jesteInt && nijePrazno && Integer.parseInt(fieldBrojStanovnika.getText()) > 0) {
            fieldBrojStanovnika.getStyleClass().removeAll("nijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("ispravno");
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("ispravno");
            fieldBrojStanovnika.getStyleClass().add("nijeIspravno");
        }
        if (!fieldNaziv.getText().trim().equals("")) {
            fieldNaziv.getStyleClass().removeAll("nijeIspravno");
            fieldNaziv.getStyleClass().add("ispravno");
        } else {
            fieldNaziv.getStyleClass().removeAll("ispravno");
            fieldNaziv.getStyleClass().add("nijeIspravno");
        }
        if (!fieldNaziv.getText().trim().equals("") && !fieldBrojStanovnika.getText().trim().equals("") && !choiceDrzava.getSelectionModel().isEmpty()) {
            trenutniGrad = new Grad();
            trenutniGrad.setNaziv(fieldNaziv.getText());
            trenutniGrad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
            trenutniGrad.setDrzava(choiceDrzava.getSelectionModel().getSelectedItem());
        }

//        Node n = (Node) actionEvent.getSource();
//        Stage stage = (Stage) n.getScene().getWindow();
//        //   stage.getOnHiding();
//        stage.close();
    }

    public Grad getGrad() {
        return trenutniGrad;
    }

}
