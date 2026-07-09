package com.example;

import com.example.auth.AuthService;
import com.example.geometry.CircleCalculator;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, World! Ce build a réussi grâce à Jenkins et Maven. Test du build automatisé. #5");

        // Démonstration du module d'authentification
        AuthService auth = new AuthService();
        auth.register("thierry", "motdepasse");
        System.out.println("Authentification de 'thierry' : "
                + (auth.authenticate("thierry", "motdepasse") ? "réussie" : "échouée"));

        // Démonstration du module de calcul du périmètre d'un cercle
        double rayon = 5.0;
        System.out.println("Périmètre d'un cercle de rayon " + rayon + " : "
                + CircleCalculator.perimetre(rayon));
    }
}
