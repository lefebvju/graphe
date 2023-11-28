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
    private IntVar[] couleur;
    private List<int[]> aretes;
    private Solver solver;
    public ColorationGrapheCSP(int nbSommets, List<int[]> aretes){
        this.nbSommets = nbSommets;
        this.aretes = aretes;
    }

    public String resolve(){
        this.model = new Model("Coloration de graphe en CSP");
        this.couleur = model.intVarArray("couleur", this.nbSommets, 1, this.nbSommets);
        for (int[] arete : aretes) {
            int sommet1 = arete[0] - 1;
            int sommet2 = arete[1] - 1;
            this.model.arithm(couleur[sommet1], "!=", couleur[sommet2]).post();
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
        int maxCouleurValue = 0;
        while(this.solver.solve()){
            maxCouleurValue = maxCouleur.getValue();
            System.out.println(maxCouleur);
            endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            System.out.println("Temps d'execution : " + executionTime + " ms");
        }
        return executionTime+","+maxCouleurValue;
    }
}

