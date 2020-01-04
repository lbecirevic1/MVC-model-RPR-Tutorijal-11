package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

//Ovo je sada model klasa
public class GeografijaDAO {

    private static GeografijaDAO instance;
    private int slobodanIdGrada, slobodanIdDrzave;
    private Connection conn; // = null;

    private  PreparedStatement dajGradoveStatement, dodajGrad, dodajDrzavu;
    private  PreparedStatement dajDrzavuStatement, dajGlavniGradStatement, dajGradStatement;
    private  PreparedStatement obrisiDrzavuStatement, dajIdDrzaveStatement, obrisiGradStatement, dajDrzavuPoNazivu, updateGrad;
    private  PreparedStatement uzmiMaxIdDrzave, uzmiMaxIdGrada, dajDrzave, dajGrad, obrisiGrad, glavniGradUpit, dajGradPoNazivu;

    private ObservableList<Grad> listaGradova = FXCollections.observableArrayList();
    private ObservableList<Drzava> listaDrzava = FXCollections.observableArrayList();

    private GeografijaDAO ()  {
        //konstruktor kreira konekcije i sve pripremljene upite
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            upiti();
        } catch(SQLException e) {
            e.printStackTrace();
            try {
                generisiBazu();
                upiti();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        listaGradova.addAll(gradovi());
        listaDrzava.addAll(drzave());
    }


    private void generisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka... nastavljam sa praznom bazom");
        }
    }

    public static GeografijaDAO getInstance() {
        if (instance == null) {
            instance = new GeografijaDAO();
        }
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        //if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
       // }
        instance = null;
    }


    public ArrayList<Grad> gradovi() {
        //vraca listu svih gradova u bazi, sortiranih po broju stanovnika
        //u opadajucem redoslijedu
        ArrayList<Grad> lista = new ArrayList<>();
        try {
            ResultSet result = dajGradoveStatement.executeQuery();  //sadrzi tabelu gradovi (drzava - id drzave)
            while(result.next()) {
                Grad grad = new Grad(result.getInt("id"), result.getString("naziv"), result.getInt("broj_stanovnika"), null);
                dajDrzavuStatement.setInt(1, result.getInt("drzava"));
                ResultSet resultSet = dajDrzavuStatement.executeQuery(); //sadrzi drzavu sa poslanim id-em iz prethodne linije
                if (resultSet.next()) { //da postavi na prvi red
                    Drzava drzava = new Drzava(resultSet.getInt("id"), resultSet.getString("naziv"), grad);
                    grad.setDrzava(drzava);
                    lista.add(grad);
                } else {
                    // grad.setDrzava(null);
                    lista.add(grad);
                }
                resultSet.close();
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Grad glavniGrad(String drzava) {
        //vraca null ako drzava ne postoji
        try {
            dajGlavniGradStatement.setString(1, drzava);
            ResultSet result1 = dajGlavniGradStatement.executeQuery();  //sadrzi id grada
            if (result1.next()) {
                dajGradStatement.setInt(1, result1.getInt(1));
                ResultSet result2 = dajGradStatement.executeQuery();

                if (result2.next()) {
                    Grad grad = new Grad(result2.getInt("id"), result2.getString("naziv"), result2.getInt("broj_stanovnika"), null);
                    dajDrzavuStatement.setInt(1, result2.getInt("id"));
                    ResultSet result3 = dajDrzavuStatement.executeQuery();
                    if (result3.next()) {
                        Drzava dr = new Drzava(result3.getInt("id"), result3.getString("naziv"), grad);
                        grad.setDrzava(dr);
                        return grad;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void obrisiDrzavu(String naziv) {
        //brise drzavu sa datim nazivom i sve gradove u toj drzavi
        //dajGlavniGradStatement vraca idgradatj glavni_grad iz drzava po nazivu
        try {
            dajIdDrzaveStatement.setString(1, naziv);
            ResultSet result1 = dajIdDrzaveStatement.executeQuery();  //vraca id drzave sa zadatim nazivom
            //moram obrisati sve gradove ciji je red "drzava" jednak vracenom id-u - obrisiGradStatement
            if (result1.next()) {
                obrisiGradStatement.setInt(1, result1.getInt(1));
                obrisiGradStatement.executeUpdate();
            }
            obrisiDrzavuStatement.setString(1, naziv);
            obrisiDrzavuStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void obrisiGrad (Grad grad) {
        try {
            obrisiGrad.setInt(1,grad.getId());
            obrisiGrad.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Drzava nadjiDrzavu(String naziv) {
        try {
            dajDrzavuPoNazivu.setString(1, naziv);
            ResultSet result1 = dajDrzavuPoNazivu.executeQuery();  //rs sadrzi drzavu sa datim imenom
            //treba naci grad
            if (result1.next()) {
                Drzava drzava = new Drzava(result1.getInt("id"), result1.getString("naziv"), null);
                dajGradStatement.setInt(1, result1.getInt("glavni_grad"));
                ResultSet result2 = dajGradStatement.executeQuery();
                if (result2.next()) {
                    Grad grad = new Grad(result2.getInt("id"),result2.getString("naziv"), result2.getInt("broj_stanovnika"), drzava);
                    drzava.setGlavniGrad(grad);
                    return drzava;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dodajGrad(Grad grad) {
            try {
                ResultSet result = uzmiMaxIdGrada.executeQuery();
                if (result.next()) {
                    slobodanIdGrada = result.getInt(1);
                    slobodanIdGrada++;
                }
                dodajGrad.setInt(1, slobodanIdGrada);
                dodajGrad.setString(2, grad.getNaziv());
                dodajGrad.setInt(3, grad.getBrojStanovnika());
                dodajGrad.setInt(4, grad.getDrzava().getId());
                dodajGrad.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        listaGradova.clear();
        listaGradova.addAll(gradovi());
    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            ResultSet result2 = uzmiMaxIdDrzave.executeQuery();
            if (result2.next()) {
                slobodanIdDrzave = result2.getInt(1);
                slobodanIdDrzave++;
            }
            dodajDrzavu.setInt(1, slobodanIdDrzave);
            dodajDrzavu.setString(2, drzava.getNaziv());
            dodajDrzavu.setInt(3, drzava.getGlavniGrad().getId());
            dodajDrzavu.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listaDrzava.clear();
        listaDrzava.addAll(drzave());
    }

    public void izmijeniGrad(Grad grad) {
        try {
            updateGrad.setString(1, grad.getNaziv());
            updateGrad.setInt(2, grad.getBrojStanovnika());
            updateGrad.setInt(3, grad.getDrzava().getId());
            updateGrad.setInt(4, grad.getId());
            updateGrad.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listaGradova.clear();
        listaGradova.addAll(gradovi());
    }


    private void upiti () throws SQLException {
        dajGradoveStatement = conn.prepareStatement("SELECT * FROM grad order by broj_stanovnika desc");
        dajDrzavuStatement = conn.prepareStatement("SELECT * FROM drzava where id=?");
        dodajGrad = conn.prepareStatement("INSERT INTO grad VALUES (?,?,?,?)");
        dodajDrzavu = conn.prepareStatement("insert into drzava values (?,?,?)");
        dajGlavniGradStatement = conn.prepareStatement("SELECT glavni_grad FROM drzava where naziv=?");
        dajGradStatement = conn.prepareStatement("SELECT * FROM grad WHERE id=?");
        dajIdDrzaveStatement = conn.prepareStatement("SELECT id from drzava where naziv=?");
        obrisiDrzavuStatement = conn.prepareStatement("DELETE FROM drzava where naziv=?");
        obrisiGradStatement = conn.prepareStatement("DELETE from grad where drzava=?");
        updateGrad = conn.prepareStatement("UPDATE grad SET naziv=?,broj_stanovnika=?,drzava=? WHERE id=?");
        dajDrzavuPoNazivu = conn.prepareStatement("SELECT * FROM drzava where naziv=?");
        dajGradPoNazivu = conn.prepareStatement("SELECT * FROM grad where naziv=?");
        uzmiMaxIdGrada = conn.prepareStatement("SELECT MAX(id) FROM grad");
        uzmiMaxIdDrzave = conn.prepareStatement("SELECT MAX(id) FROM drzava");
        dajDrzave = conn.prepareStatement("SELECT * FROM drzava");
        dajGrad = conn.prepareStatement("SELECT * FROM grad where id=?");
        obrisiGrad = conn.prepareStatement("DELETE FROM grad WHERE id=?");

    }

    public ArrayList<Drzava> drzave ()  {
        ArrayList<Drzava> listaDrzava = new ArrayList<>();
        ResultSet result1 = null;
        try {
            result1 = dajDrzave.executeQuery();
            while (result1.next()) {
                Drzava drzava = new Drzava(result1.getInt("id"), result1.getString("naziv"), null);
                dajGrad.setInt(1, result1.getInt("id"));
                ResultSet result2 = dajGrad.executeQuery();
                if (result2.next()) {
                    Grad grad = new Grad(result2.getInt("id"), result2.getString("naziv"), result2.getInt("broj_stanovnika"), drzava);
                    drzava.setGlavniGrad(grad);
                    listaDrzava.add(drzava);
                } else {
                    listaDrzava.add(drzava);
                }
                result2.close();
            }
            result1.close();
        }
            catch (SQLException e) {
                e.printStackTrace();
            }
        return listaDrzava;
    }

    public Grad nadjiGrad(String naziv) {
        try {
            dajGradPoNazivu.setString(1, naziv);
            ResultSet result1 = dajGradPoNazivu.executeQuery();
            if (result1.next()) {
                Grad grad = new Grad(result1.getInt("id"), result1.getString("naziv"), result1.getInt("broj_stanovnika"), null);
                dajGradStatement.setInt(1, result1.getInt("id"));
                ResultSet result2 = dajGradStatement.executeQuery();
                if (result2.next()) {
                    Drzava drzava = new Drzava(result2.getInt("id"),result2.getString("naziv"), null);
                    grad.setDrzava(drzava);
                    return grad;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//da bismo pristupili bazi trebaju driveri
//+ data source , skidamo bibl ya pristup bazi
//klasa koja pristupa bazi pod preko drivera, singleton klasa
//kad se nesto pise u bazu, baza.sql se zaklj i ne moze se citati iz nje - exception
//da bismo to rijesili koristimo singleton jer on ne dozv 2 pristupa na istu bazu
//remove instance, gasimo onog ko komunivira sa bazom
