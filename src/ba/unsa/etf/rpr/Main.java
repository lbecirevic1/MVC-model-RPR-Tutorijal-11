package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       String url = "jdbc:sqlite:baza.sql";
       ArrayList<Grad> gradovi = new ArrayList<>();
        System.out.println("Gradovi su:\n" + ispisiGradove());
//        glavniGrad();
    }

    public static String ispisiGradove() {
        ArrayList<Grad> g = new ArrayList<>();

        return null;
    }
}
