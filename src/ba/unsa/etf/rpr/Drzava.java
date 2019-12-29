package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Drzava {
   private SimpleIntegerProperty id;
    private  SimpleStringProperty naziv ;
    private  SimpleObjectProperty glavniGrad;

    public Drzava(int id, String naziv, Grad glavniGrad) {
        this.id = new SimpleIntegerProperty(id);
        this.naziv = new SimpleStringProperty(naziv);
        this.glavniGrad = new SimpleObjectProperty(glavniGrad);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNaziv() {
        return naziv.get();
    }

    public SimpleStringProperty nazivProperty() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public Grad getGlavniGrad() {
        return (Grad) glavniGrad.get();
    }

    public SimpleObjectProperty glavniGradProperty() {
        return glavniGrad;
    }

    public void setGlavniGrad(Object glavniGrad) {
        this.glavniGrad.set(glavniGrad);
    }

    @Override
    public String toString() {
        return getNaziv();
    }
}
