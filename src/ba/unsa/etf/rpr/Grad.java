package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Grad  {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty naziv = new SimpleStringProperty();
    private SimpleIntegerProperty brojStanovnika = new SimpleIntegerProperty();
    private SimpleObjectProperty drzava = new SimpleObjectProperty();


    public Grad () {
        id = new SimpleIntegerProperty();
        naziv = new SimpleStringProperty();
        brojStanovnika = new SimpleIntegerProperty();
        drzava = new SimpleObjectProperty();
    }
    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        this.id = new SimpleIntegerProperty(id);
        this.naziv = new SimpleStringProperty(naziv);
        this.brojStanovnika = new SimpleIntegerProperty(brojStanovnika);
        this.drzava = new SimpleObjectProperty(drzava);
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

    public int getBrojStanovnika() {
        return brojStanovnika.get();
    }

    public SimpleIntegerProperty brojStanovnikaProperty() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika.set(brojStanovnika);
    }

    public Drzava getDrzava() {
        return (Drzava) drzava.get();
    }

    public SimpleObjectProperty drzavaProperty() {
        return drzava;
    }

    public void setDrzava(Object drzava) {
        this.drzava.set(drzava);
    }

    @Override
    public String toString() {
        return getNaziv();
    }
}
