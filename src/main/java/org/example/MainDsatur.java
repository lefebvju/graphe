package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.fileReaderDsatur.lireFichierCol;

public class MainDsatur {
    public static void main(String[] args) {
        String cheminDuRepertoire = "bench";
        File repertoire = new File(cheminDuRepertoire);

        // Créer un filtre pour les fichiers finissant par ".col"
        FilenameFilter filtre = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String nom) {
                return nom.toLowerCase().endsWith(".col");
            }
        };

        // Récupérer la liste des fichiers qui passent le filtre
        String[] fichiersCOL = repertoire.list(filtre);

        if (fichiersCOL != null) {
            System.out.println("Fichiers se terminant par .col :");
            Map<String,String> res= new HashMap<String, String>();
            for (String nomFichier : fichiersCOL) {
                DsaturGraphColoring dsatur = lireFichierCol(cheminDuRepertoire+"/"+nomFichier);
                System.out.println(nomFichier);
                long startTime = System.currentTimeMillis();
                int color=dsatur.solve();
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                System.out.println("Le temps d'exécution de votreFonction est de : " + executionTime + " millisecondes");
                res.put(nomFichier,executionTime+","+color);
            }
            System.out.println(res);
            printResult(res);
        } else {
            System.out.println("Aucun fichier .col trouvé dans le répertoire.");
        }
    }
    public static void printResult(Map<String,String> res){
        // écrire dans un fichier csv
        String csvFile = "DSATUR"+".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            // Écriture du texte dans le fichier
            for (Map.Entry<String, String> entry : res.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                writer.write(key + "," + value+"\n");
            }
        } catch (IOException e) {
        }

    }

}
