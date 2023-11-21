package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fileReaderDsatur {
    public static DsaturGraphColoring lireFichierCol(String nomFichier) {
        List<int[]> edges = new ArrayList<>();
        int nbVertices = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                if (ligne.startsWith("e")) {
                    String[] parties = ligne.split(" ");
                    int vertex1 = Integer.parseInt(parties[1]);
                    int vertex2 = Integer.parseInt(parties[2]);
                    edges.add(new int[]{vertex1, vertex2});
                } else if (ligne.startsWith("p edge")) {
                    nbVertices = Integer.parseInt(ligne.split(" ")[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new DsaturGraphColoring(edges,nbVertices);
    }
}
