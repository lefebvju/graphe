package org.example;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class ColorationGrapheCSP {
    private Model model;
    private int nbSommets;
    private int nbCouleurs;
    private IntVar[] couleur;

    public List<int[]> getAretes() {
        return aretes;
    }

    private List<int[]> aretes;
    private Solver solver;
    public ColorationGrapheCSP(int nbSommets, List<int[]> aretes){
        // Définir le nombre de sommets et le nombre de couleurs disponibles
        this.nbSommets = nbSommets;

        // Contraintes : Les sommets adjacents ne peuvent pas avoir la même couleur
        // Exemple : Sommets 1 et 2, Sommets 2 et 3, Sommets 3 et 4
        this.aretes = aretes;
    }

    public boolean resolve(int nbCouleurs){
        this.setNbCouleurs(nbCouleurs);
        // Créer un solveur
        this.solver = model.getSolver();
        // Lancer la résolution
        if (this.solver.solve()) {
            /*for (int i = 0; i < this.nbSommets; i++) {
                System.out.println("Sommet " + (i + 1) + " : Couleur " + this.couleur[i].getValue());
            }*/
            return true;
        } else {
            //System.out.println("Pas de solution trouvée.");
            return false;
        }
    }
    public void afficherAretes() {
        System.out.println(this.aretes.toArray().length);
        for (int[] arete : this.aretes) {
            System.out.println("Arete : " + arete[0] + " - " + arete[1]);
        }
    }
    public int getNbSommets() {
        return nbSommets;
    }

    public void setNbSommets(int nbSommets) {
        this.nbSommets = nbSommets;
    }

    public int getNbCouleurs() {
        return nbCouleurs;
    }

    private void setNbCouleurs(int nbCouleurs) {
        this.model = new Model("Coloration de graphe en CSP");
        this.nbCouleurs = nbCouleurs;
        this.couleur = model.intVarArray("couleur", this.nbSommets, 1, this.nbCouleurs);
        for (int[] arete : aretes) {
            int sommet1 = arete[0] - 1;
            int sommet2 = arete[1] - 1;
            this.model.arithm(couleur[sommet1], "!=", couleur[sommet2]).post();
        }
    }
}

