package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.sql.*;

public class GeografijaDAO {
    private static GeografijaDAO geografijaDAO = null;
    private Connection conn;
    private PreparedStatement dajGradoveStatement, dajDrzaveStatement, dodajGrad, dodajDrzavu;
    private GeografijaDAO (){
        //konstruktor kreira konekcije i sve pripremljene upite
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.sql");
            // Statement stmt = conn.createStatement();
            dajGradoveStatement = conn.prepareStatement("SELECT naziv FROM grad " +
                    "order by broj_stanovnika desc");
            dajDrzaveStatement = conn.prepareStatement("SELECT naziv FROM drzava");
            dodajGrad = conn.prepareStatement("INSERT INTO grad VALUES (?,?,?,?)");

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
    public static void removeInstance() {
        if (geografijaDAO != null) {
            try {
                geografijaDAO.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        geografijaDAO = null;
    }

    public static GeografijaDAO getInstance() {
        if (geografijaDAO == null)
            geografijaDAO = new GeografijaDAO();
        return geografijaDAO;
    }

    public ArrayList<Grad> gradovi() {
        //vraca listu svih gradova u bazi, sortiranih po broju stanovnika
        //u opadajucem redoslijedu
        ArrayList<Grad> lista = new ArrayList<>();
        try {
            ResultSet result = dajGradoveStatement.executeQuery();
            while(result.next()) {
                Grad grad = new Grad(result.getInt(1), result.getString(2), result.getInt(3), (Drzava) result.getObject(4));
                lista.add(grad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public Grad glavniGrad(String drzava) {

        return null;
    }

    public void obrisiDrzavu(String naziv) {

    }

    public Drzava nadjiDrzavu(String naziv) {
        return null;
    }

    public void dodajGrad(Grad grad) {
        try {
            dodajGrad.setInt(1, grad.getId());
            dodajGrad.setString(2, grad.getNaziv());
            dodajGrad.setInt(3,grad.getBrojStanovnika());
            dodajGrad.setObject(4, grad.getDrzava());
            dodajGrad.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            dodajDrzavu.setInt(1, drzava.getId());
            dodajDrzavu.setString(2,drzava.getNaziv());
            dodajDrzavu.setObject(3, drzava.getGlavniGrad());
            dodajDrzavu.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void izmijeniGrad(Grad grad) {

    }
}

//da bismo pristupili bazi trebaju driveri
//+ data source , skidamo bibl ya pristup bazi
//klasa koja pristupa bazi pod preko drivera, singleton klasa
//kad se nesto pise u bazu, baza se zaklj i ne moze se citati iz nje - exception
//da bismo to rijesili koristimo singleton jer on ne dozv 2 pristupa na istu bazu
//remove instance, gasimo onog ko komunivira sa bazom
