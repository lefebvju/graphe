package org.example;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;

public class ColorationGrapheSAT {

    public static void main(String[] args) {
        // Créer un modèle
        Model model = new Model("Coloration de graphe en SAT");

        // Définir le nombre de sommets et le nombre de couleurs disponibles
        int nbSommets = 4;
        int nbCouleurs = 4;

        // Variables booléennes : color[i][c] indique si le sommet i est coloré avec la couleur c
        BoolVar[][] color = model.boolVarMatrix(nbSommets, nbCouleurs);

        // Chaque sommet doit être coloré avec exactement une couleur
        for (int i = 0; i < nbSommets; i++) {
            BoolVar[] colorsOfNode = color[i];
            Constraint exactlyOneColor = model.sum(colorsOfNode, "=", 1);
            model.post(exactlyOneColor);
        }

        // Les sommets adjacents ne peuvent pas avoir la même couleur
        int[][] aretes = {{1, 2}, {2, 3}, {3, 4}};

        for (int[] arete : aretes) {
            int sommet1 = arete[0] - 1;
            int sommet2 = arete[1] - 1;
            for (int c = 0; c < nbCouleurs; c++) {
                model.arithm(color[sommet1][c], "+", color[sommet2][c], "<", 2).post();
            }
        }

        // Créer un solveur
        Solver solver = model.getSolver();

        // Lancer la résolution
        if (solver.solve()) {
            for (int i = 0; i < nbSommets; i++) {
                System.out.print("Sommet " + (i + 1) + " : ");
                for (int c = 0; c < nbCouleurs; c++) {
                   /* if (model.lit(color[i][c] == true)) {
                        System.out.print("Couleur " + (c + 1) + " ");
                    }*/
                }
                System.out.println();
            }
        } else {
            System.out.println("Pas de solution trouvée.");
        }
    }
}