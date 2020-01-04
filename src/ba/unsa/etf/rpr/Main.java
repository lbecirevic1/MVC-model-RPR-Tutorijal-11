package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

//    private static void glavniGrad() {
//        GeografijaDAO dao = GeografijaDAO.getInstance();
//        System.out.println("Unesite ime drzave: ");
//        Scanner scanner = new Scanner (System.in);
//        String s = null;
//        s = scanner.nextLine();
//        Grad grad = dao.glavniGrad(s);
//        if (grad == null)
//            System.out.println("Nepostojeća država");
//        else {
////            Glavni grad države Država je Grad
//            System.out.println("Glavni grad drzave " + s + " je " + grad.getNaziv());
//        }
//        // dao.removeInstance();
//
//    }
//
    public static String ispisiGradove() {
        ArrayList<Grad> gradovi;
        String ispis = "";
        GeografijaDAO dao = GeografijaDAO.getInstance();
        gradovi = dao.gradovi();
        for (int i = 0; i < gradovi.size(); i = i + 1) {
            ispis = ispis + gradovi.get(i).getNaziv() + " (" + gradovi.get(i).getDrzava() + ") - " + gradovi.get(i).getBrojStanovnika() + "\n";
        }
        //   dao.removeInstance();
        return ispis;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        loader.setController(new GlavnaController(dao));
        Parent root = loader.load();
        primaryStage.setTitle("Glavna");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }
    public static void main (String[]args){
        launch(args);
    }

}


