package org.example;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.LectureFichierCol.lireFichierCol;

public class Main {
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
            Map<String,Integer> res= new HashMap<String, Integer>();
            int minpoints=Integer.MAX_VALUE;
            int maxpoints=-1;
            int minaretes=Integer.MAX_VALUE;
            int maxaretes=-1;
            int nbFichiers=0;
            String minpointsname="";
            String maxpointsname="";
            String minaretesname="";
            String maxaretesname="";
            for (String nomFichier : fichiersCOL) {
                nbFichiers++;
                ColorationGrapheCSP grapheCSP = lireFichierCol(cheminDuRepertoire+"/"+nomFichier);
                if(grapheCSP.getNbSommets() <minpoints){
                    minpoints=grapheCSP.getNbSommets();
                    minpointsname=nomFichier;
                }
                if(grapheCSP.getNbSommets() >maxpoints){
                    maxpoints=grapheCSP.getNbSommets();
                    maxpointsname=nomFichier;
                }
                if(grapheCSP.getAretes().toArray().length <minaretes){
                    minaretes=grapheCSP.getAretes().toArray().length;
                    minaretesname=nomFichier;
                }
                if(grapheCSP.getAretes().toArray().length >maxaretes){
                    maxaretes=grapheCSP.getAretes().toArray().length;
                    maxaretesname=nomFichier;
                }
                //grapheCSP.afficherAretes();
                System.out.println(nomFichier);
                grapheCSP.resolve(0);
            }
            System.out.println("min arrete "+minaretes+" "+minaretesname);
            System.out.println("max arrete "+maxaretes+" "+maxaretesname);
            System.out.println("min point "+minpoints+" "+minpointsname);
            System.out.println("max points "+maxpoints+" "+maxpointsname);
            System.out.println(nbFichiers+" fichier lu");
            System.out.println(res);
        } else {
            System.out.println("Aucun fichier .col trouvé dans le répertoire.");
        }
    }

}
