package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       String url = "jdbc:sqlite:baza.db";
        System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();
    }

    private static void glavniGrad() {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        System.out.println("Unesite ime drzave: ");
        Scanner scanner = new Scanner (System.in);
        String s = null;
        s = scanner.nextLine();
        Grad grad = dao.glavniGrad(s);
        if (grad == null)
            System.out.println("Nepostojeća država");
        else {
//            Glavni grad države Država je Grad
            System.out.println("Glavni grad drzave " + s + " je " + grad.getNaziv());
        }
        
    }

    public static String ispisiGradove() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        String ispis = "";
        GeografijaDAO dao = GeografijaDAO.getInstance();
        gradovi = dao.gradovi();
        for (int i = 0; i < gradovi.size(); i = i + 1) {
            ispis = ispis + gradovi.get(i).getNaziv() + " (" + gradovi.get(i).getDrzava() + ") - " + gradovi.get(i).getBrojStanovnika() + "\n";
        }
        return ispis;
    }

}
