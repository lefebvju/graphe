package org.example;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.assignments.DecisionOperatorFactory;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.*;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;

import java.util.HashSet;
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

    public String resolve(){
        this.model = new Model("Coloration de graphe en CSP");
        this.couleur = model.intVarArray("couleur", this.nbSommets, 1, this.nbSommets);
        int[] degrees = new int[this.nbSommets];
        for (int[] arete : aretes) {
            int sommet1 = arete[0] - 1;
            int sommet2 = arete[1] - 1;
            this.model.arithm(couleur[sommet1], "!=", couleur[sommet2]).post();
            degrees[arete[0] - 1]++;
            degrees[arete[1] - 1]++;
        }
        // Créer un solveur
        this.solver = model.getSolver();
        solver.limitTime(60000);
        switch (Main.technique) {
            case 0:
                solver.setSearch(Search.domOverWDegSearch(couleur));
                break;
            case 1:
                solver.setSearch(Search.intVarSearch(

                        // Stratégie de sélection des variables
                        new InputOrder<>(model),

                        // Stratégie de choix des valeurs pour les variables
                        new IntDomainMin(),

                        // Variables à considérer
                        couleur));
                break;
            case 2:
                solver.setSearch(Search.activityBasedSearch(couleur));
                break;
            case 3:
                solver.setSearch(Search.intVarSearch(
                        // selects the variable of smallest domain size
                        new FirstFail(model),
                        // selects the smallest domain value (lower bound)
                        new IntDomainMin(),
                        // variables to branch on
                        couleur
                ));
                break;
        }

        IntVar maxCouleur = model.intVar("maxCouleur", 1, nbSommets);
        model.max(maxCouleur, couleur).post();
        model.setObjective(Model.MINIMIZE, maxCouleur);

        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long executionTime = 0;
        while(this.solver.solve()){
            System.out.println(maxCouleur);
            endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            System.out.println("Temps d'execution : " + executionTime + " ms");
        }

        System.out.println("Le temps d'exécution de votreFonction est de : " + executionTime + " millisecondes");


        // Lancer la résolution
        if (true) {
            /*for (int i = 0; i < this.nbSommets; i++) {
                System.out.println("Sommet " + (i + 1) + " : Couleur " + this.couleur[i].getValue());
            }*/
            return executionTime+","+compteCouleur();//+";"+getNbSommets()+";"+getAretes().size();
        } else {
            System.out.println("Pas de solution trouvée.");
            return executionTime+","+compteCouleur();//+";"+getNbSommets()+";"+getAretes().size();
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

    public int compteCouleur(){
        HashSet<Integer> elementsUniques = new HashSet<>();

        // Parcourir le tableau et ajouter chaque élément au HashSet
        for (IntVar element : this.couleur) {
            elementsUniques.add(element.getValue());
        }

        return elementsUniques.size();
    }
}

