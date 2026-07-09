package com.example.geometry;

/**
 * Module de calcul du périmètre (circonférence) d'un cercle.
 * Formule : P = 2 * PI * r
 */
public final class CircleCalculator {

    private CircleCalculator() {
        // Classe utilitaire : pas d'instanciation
    }

    /**
     * Calcule le périmètre d'un cercle à partir de son rayon.
     *
     * @param rayon le rayon du cercle (doit être positif ou nul)
     * @return le périmètre du cercle
     * @throws IllegalArgumentException si le rayon est négatif
     */
    public static double perimetre(double rayon) {
        if (rayon < 0) {
            throw new IllegalArgumentException("Le rayon ne peut pas être négatif : " + rayon);
        }
        return 2 * Math.PI * rayon;
    }

    /**
     * Calcule le périmètre d'un cercle à partir de son diamètre.
     *
     * @param diametre le diamètre du cercle (doit être positif ou nul)
     * @return le périmètre du cercle
     * @throws IllegalArgumentException si le diamètre est négatif
     */
    public static double perimetreDepuisDiametre(double diametre) {
        if (diametre < 0) {
            throw new IllegalArgumentException("Le diamètre ne peut pas être négatif : " + diametre);
        }
        return Math.PI * diametre;
    }
}
