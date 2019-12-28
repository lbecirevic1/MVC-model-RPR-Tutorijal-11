package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;
import java.util.Scanner;

public class GeografijaDAO {
    private static GeografijaDAO instance;

    private static Connection conn = null;
    private static PreparedStatement dajGradoveStatement, dodajGrad, dodajDrzavu;
    private static PreparedStatement dajDrzavuStatement, dajGlavniGradStatement, dajGradStatement;
    private static PreparedStatement obrisiDrzavuStatement, dajIdDrzaveStatement, obrisiGradStatement, dajDrzavuPoNazivu, updateGrad;

    private GeografijaDAO (){
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
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
                 dajDrzavuStatement.setInt(1, result.getInt("id"));
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
            dodajGrad.setInt(1, grad.getId());
            dodajGrad.setString(2, grad.getNaziv());
            dodajGrad.setInt(3,grad.getBrojStanovnika());
            dodajGrad.setObject(4, grad.getDrzava());
            dodajGrad.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            dodajDrzavu.setInt(1, drzava.getId());
            dodajDrzavu.setString(2,drzava.getNaziv());
            dodajDrzavu.setObject(3, drzava.getGlavniGrad());
            dodajDrzavu.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    }


}

//da bismo pristupili bazi trebaju driveri
//+ data source , skidamo bibl ya pristup bazi
//klasa koja pristupa bazi pod preko drivera, singleton klasa
//kad se nesto pise u bazu, baza.sql se zaklj i ne moze se citati iz nje - exception
//da bismo to rijesili koristimo singleton jer on ne dozv 2 pristupa na istu bazu
//remove instance, gasimo onog ko komunivira sa bazom
