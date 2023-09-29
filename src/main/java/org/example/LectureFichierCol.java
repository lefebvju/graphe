package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class LectureFichierCol {

    public static ColorationGrapheCSP lireFichierCol(String nomFichier) {
        List<int[]> aretes = new ArrayList<>();
        int nbPoints=0;

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                if (ligne.startsWith("e")) {
                    String[] parties = ligne.split(" ");
                    int sommet1 = Integer.parseInt(parties[1]);
                    int sommet2 = Integer.parseInt(parties[2]);
                    aretes.add(new int[]{sommet1, sommet2});
                } else if (ligne.startsWith("p edge")) {
                    nbPoints = Integer.parseInt(ligne.split(" ")[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new ColorationGrapheCSP(nbPoints,aretes);
    }
}
