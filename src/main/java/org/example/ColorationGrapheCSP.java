package org.example;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class ColorationGrapheCSP {

    public static void main(String[] args) {
        // Créer un modèle
        Model model = new Model("Coloration de graphe en CSP");

        // Définir le nombre de sommets et le nombre de couleurs disponibles
        int nbSommets = 4;
        int nbCouleurs = 4;

        // Variables : couleur[sommet] représente la couleur attribuée au sommet
        IntVar[] couleur = model.intVarArray("couleur", nbSommets, 1, nbCouleurs);

        // Contraintes : Les sommets adjacents ne peuvent pas avoir la même couleur
        // Exemple : Sommets 1 et 2, Sommets 2 et 3, Sommets 3 et 4
        int[][] aretes = {{1, 2}, {2, 3}, {3, 4}};

        for (int[] arete : aretes) {
            int sommet1 = arete[0] - 1;
            int sommet2 = arete[1] - 1;
            model.arithm(couleur[sommet1], "!=", couleur[sommet2]).post();
        }

        // Créer un solveur
        Solver solver = model.getSolver();

        // Lancer la résolution
        if (solver.solve()) {
            for (int i = 0; i < nbSommets; i++) {
                System.out.println("Sommet " + (i + 1) + " : Couleur " + couleur[i].getValue());
            }
        } else {
            System.out.println("Pas de solution trouvée.");
        }
    }
}

