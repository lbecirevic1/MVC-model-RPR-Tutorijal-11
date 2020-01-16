package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

public class GradController {
    public TextField fieldNaziv;
    public ChoiceBox<Drzava> choiceDrzava;
    public TextField fieldBrojStanovnika;
    public TextField fieldPostanskiBroj;
    public Button btnOk;
    public Button btnCancel;
    public ImageView gradSlika;
    private ArrayList<Drzava> listaDrzava;
    private Grad editujGrad;
  //  private Grad trenutniGrad = null;

    //btnCancel
    public void zatvoriProzor(ActionEvent actionEvent) {
        editujGrad = null;
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public GradController(Grad editujGrad, ArrayList<Drzava> listaDrzava) {
        this.listaDrzava = listaDrzava;
        this.editujGrad = editujGrad;
    }

    @FXML
    public void initialize () {
        ObservableList<Drzava> drzave = FXCollections.observableArrayList(listaDrzava);
        choiceDrzava.setItems(drzave);
        if (editujGrad != null) {
            fieldNaziv.setText(editujGrad.getNaziv());
            fieldBrojStanovnika.setText(String.valueOf(editujGrad.getBrojStanovnika()));
            for (Drzava drzava : listaDrzava)
                if (drzava.getId() == editujGrad.getDrzava().getId())
                    choiceDrzava.getSelectionModel().select(drzava);
            fieldPostanskiBroj.setText(String.valueOf(editujGrad.getPostanskiBroj()));
            URI uri = null;
            File f = new File(editujGrad.getSlika());
            uri = f.toURI();
            gradSlika.setImage(new Image(uri.toString()));

        } else {
            choiceDrzava.getSelectionModel().selectFirst();
        }

    }

    //btnOk
    public void validiraj(ActionEvent actionEvent) {
        boolean jesteInt = true;
        boolean nijePrazno = true;
        boolean ispravnoPoljeNaziv = false;
        boolean ispravnoPoljeStanovnici = false;
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
            ispravnoPoljeStanovnici = true;
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("ispravno");
            fieldBrojStanovnika.getStyleClass().add("nijeIspravno");
        }
        if (!fieldNaziv.getText().trim().equals("")) {
            fieldNaziv.getStyleClass().removeAll("nijeIspravno");
            fieldNaziv.getStyleClass().add("ispravno");
            ispravnoPoljeNaziv = true;
        } else {
            fieldNaziv.getStyleClass().removeAll("ispravno");
            fieldNaziv.getStyleClass().add("nijeIspravno");
        }
        if (!ispravnoPoljeNaziv || !ispravnoPoljeStanovnici)
            return;

        if (editujGrad == null) {
            editujGrad = new Grad();
        }

        editujGrad.setNaziv(fieldNaziv.getText());
        editujGrad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
        editujGrad.setPostanskiBroj(Integer.parseInt(fieldPostanskiBroj.getText()));
        editujGrad.setDrzava(choiceDrzava.getValue());

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public Grad getGrad() {
        return editujGrad;
    }

    public void promijeniSliku(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
      //  dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Unesite adresu slike: ");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            //Promijeni sliku
            String adresa = result.get();

            File f = new File(adresa);
            URI uri = f.toURI();
            gradSlika.setImage(new Image(uri.toString()));

        }

// The Java 8 way to get the response value (with lambda expression).
// result.ifPresent(name -> System.out.println("Your name: " + name));
    }
}
