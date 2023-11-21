package org.example;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.variables.InputOrder;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.search.strategy.selectors.values.*;
import org.chocosolver.solver.search.strategy.selectors.variables.*;

public class MainPres {
    public static void main(String[] args) {

        // Créer un modèle
        Model model = new Model("Exemple");

        // Variables : deux entiers compris entre 1 et 3
        IntVar v1 = model.intVar("v1", 1, 3);
        IntVar v2 = model.intVar("v2",1,3);

        // Contraintes : Les variables v1 et v2 doivent être différentes
        model.arithm(v1,"!=",v2).post();

        // Créer un solveur
        Solver solver = model.getSolver();

        // Paramètres du solveur
        solver.setSearch(Search.intVarSearch(

                // Stratégie de sélection des variables
                new InputOrder<>(model),

                // Stratégie de choix des valeurs pour les variables
                new IntDomainMin(),

                // Variables à considérer
                v1,v2)
        );

        // Lancer la recherche de toutes les solutions possible
        while(solver.solve()){
            System.out.println(v1+" "+v2);
        }

    }
}
