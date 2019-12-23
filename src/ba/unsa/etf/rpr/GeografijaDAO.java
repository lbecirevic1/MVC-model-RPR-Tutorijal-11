package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.sql.*;

public class GeografijaDAO {
    private static GeografijaDAO geografijaDAO = null;
    private Connection conn;
    private PreparedStatement dajGradoveStatement;
    private GeografijaDAO (){
        //konstruktor kreira konekcije i sve pripremljene upite
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            // Statement stmt = conn.createStatement();
            dajGradoveStatement = conn.prepareStatement("SELECT naziv FROM grad " +
                    "order by broj_stanovnika desc");
//            dajStudenteStatement = conn.prepareStatement("SELECT id, ime, prezime, brojindexa FROM studenti");
//            dajNoviId = conn.prepareStatement("SELECT MAX(id)+1 FROM studenti");
//            dodajStudenta = conn.prepareStatement("INSERT INTO studenti VALUES (?,?,?,?)");

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
        return null;
    }


    public Grad glavniGrad(String naziv) {
        return null;
    }

    public void obrisiDrzavu(String naziv) {

    }

    public Drzava nadjiDrzavu(String naziv) {
        return null;
    }

    public void dodajGrad(Grad grad) {

    }

    public void dodajDrzavu(Drzava drzava) {

    }

    public void izmijeniGrad(Grad grad) {

    }
}
