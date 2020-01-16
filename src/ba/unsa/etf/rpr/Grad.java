package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.xml.crypto.dsig.SignatureMethod;

public class Grad  {
    private SimpleIntegerProperty id;
    private SimpleStringProperty naziv;
    private SimpleIntegerProperty brojStanovnika;
    private SimpleObjectProperty<Drzava> drzava;
    private SimpleStringProperty slika;
    private SimpleIntegerProperty postanskiBroj;

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava, String slika, int postanskiBroj) {
        this.id = new SimpleIntegerProperty(id);
        this.naziv = new SimpleStringProperty(naziv);
        this.brojStanovnika = new SimpleIntegerProperty(brojStanovnika);
        this.drzava = new SimpleObjectProperty<>(drzava);
        this.slika = new SimpleStringProperty(slika);
        this.postanskiBroj = new SimpleIntegerProperty(postanskiBroj);
    }

    public Grad() {
        id = new SimpleIntegerProperty();
        naziv = new SimpleStringProperty();
        brojStanovnika = new SimpleIntegerProperty();
        drzava = new SimpleObjectProperty<>();
        slika = new SimpleStringProperty();
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
        return drzava.get();
    }

    public SimpleObjectProperty<Drzava> drzavaProperty() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava.set(drzava);
    }

    public String getSlika() {
        return slika.get();
    }

    public SimpleStringProperty slikaProperty() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika.set(slika);
    }

    public int getPostanskiBroj() {
        return postanskiBroj.get();
    }

    public SimpleIntegerProperty postanskiBrojProperty() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj.set(postanskiBroj);
    }

    @Override
    public String toString() {
        return naziv.get();
    }
}
