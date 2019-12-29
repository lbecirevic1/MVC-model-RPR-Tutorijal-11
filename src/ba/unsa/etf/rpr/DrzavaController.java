package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DrzavaController  {
    public TextField fieldNaziv;
    public ChoiceBox choiceGrad;
    public Button btnOk;
    public Button btnCancel;

    public void zatvoriProzor(javafx.event.ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void validirajPolje(ActionEvent actionEvent) {
        if(fieldNaziv.getText().trim().equals("")) {
            fieldNaziv.getStyleClass().removeAll("ispravno");
            fieldNaziv.getStyleClass().add("nijeIspravno");
        } else {
            fieldNaziv.getStyleClass().removeAll("nijeIspravno");
            fieldNaziv.getStyleClass().add("ispravno");
        }

        //Bez klika postaje crveno
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

}
