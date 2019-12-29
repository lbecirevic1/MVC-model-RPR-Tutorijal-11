package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GradController {
    public TextField fieldNaziv;
    public ChoiceBox choiceDrzava;
    public Button btnOk;
    public Button btnCancel;
    public TextField fieldBrojStanovnika;


    public void zatvoriProzor(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }


    public void validiraj(ActionEvent actionEvent) {
        boolean jesteInt = true;
        boolean jestePrazno = false;
         try {
                Integer.parseInt(fieldBrojStanovnika.getText());
            } catch(NumberFormatException e) {
                jesteInt = false;
            } catch(NullPointerException e) {
                jestePrazno = true;
            }
        if(jesteInt && !jestePrazno && Integer.parseInt(fieldBrojStanovnika.getText()) > 0 && !fieldNaziv.getText().trim().equals("")) {
            fieldNaziv.getStyleClass().removeAll("nijeIspravno");
            fieldNaziv.getStyleClass().add("ispravno");
            fieldBrojStanovnika.getStyleClass().removeAll("nijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("ispravno");
        } else {
            fieldNaziv.getStyleClass().removeAll("ispravno");
            fieldNaziv.getStyleClass().add("nijeIspravno");
            fieldBrojStanovnika.getStyleClass().removeAll("ispravno");
            fieldBrojStanovnika.getStyleClass().add("nijeIspravno");
        }
    }
}
