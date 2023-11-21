package org.example;
import org.chocosolver.solver.variables.IVariableMonitor;
import org.chocosolver.util.objects.graphs.IGraph;

import java.io.*;
import java.util.*;

import static org.example.LectureFichierCol.lireFichierCol;

public class DsaturGraphColoring {

    private int nbrVertices;
    private List<int[]> edges;

    private List<Integer> sortedVerticesDeg;

    private Map<Integer,Integer> dsaturVertices = new HashMap<>();

    private List<Integer> colors = new ArrayList<>();

    private Map<Integer,Integer> verticesColoring = new HashMap<>();

    public DsaturGraphColoring(List<int[]> edges, int nbrVertices) {
        this.nbrVertices = nbrVertices;
        this.edges = edges;
        setSortedVertices();
        for(Integer vertex : sortedVerticesDeg) {
            dsaturVertices.put(vertex,0);
        }
    }

    public static void main(String[] args){
        int numVertices = 5;
        int[][] edges = {
                {1,2}, {1, 3}, {2, 3}, {2, 4}, {4, 5}
        };
        List<int[]> listEdges = new ArrayList<>();
        Collections.addAll(listEdges, edges);
        DsaturGraphColoring dSatur = new DsaturGraphColoring(listEdges,numVertices);
        dSatur.solve();

    }

    public void solve() {
        colors.add(1);
        int firstVertex = sortedVerticesDeg.get(0);
        verticesColoring.put(firstVertex,1);
        for(Integer neighbor : getNeighbors(firstVertex)){
            updateDSatur(neighbor);
        }
        while(verticesColoring.size()<nbrVertices) {
            List<Integer> maxDSaturVertices = getMaxDSaturVertices();

            int maxDegreeVertex = getMaxDegreeVertex(maxDSaturVertices);
            int col = chooseValidColor(maxDegreeVertex);
            verticesColoring.put(maxDegreeVertex,col);
            sortedVerticesDeg.remove((Integer) maxDegreeVertex);
            for(Integer neighbor : getNeighbors(firstVertex)){
                updateDSatur(neighbor);
            }
        }
        System.out.println(colors.size());
        for(Integer vertex : verticesColoring.keySet()) {
            System.out.println(vertex + " : "+verticesColoring.get(vertex));
        }


    }

    public int chooseValidColor(int vertex) {
        for(Integer color : colors) {
            boolean valid = true;
            for(Integer neighbor : getNeighbors(vertex)) {
                if (verticesColoring.containsKey(neighbor) && verticesColoring.get(neighbor).equals(color)) {
                    valid = false;
                }
            }
            if(valid){
                return color;
            }
        }
        int nbrColors = colors.size();
        colors.add(nbrColors + 1);
        return nbrColors + 1;
    }

    public int getMaxDegreeVertex(List<Integer> vertices) {
        int maxIdx = -1;
        int maxDegreeVertex = -1;
        for (Integer vertex : vertices) {

            int idx = sortedVerticesDeg.indexOf(vertex);

            if (idx > maxIdx) {
                maxDegreeVertex = vertex;
                maxIdx = idx;
            }
        }
        return maxDegreeVertex;
    }

    public List<Integer> getMaxDSaturVertices() {
        List<Integer> maxDSaturVertices = new ArrayList<>();
        int maxDSatur = 0;
        for (Integer key : dsaturVertices.keySet()) {
            if(!verticesColoring.containsKey(key) && dsaturVertices.get(key)>maxDSatur) {
                maxDSatur = dsaturVertices.get(key);
                maxDSaturVertices.clear();
                maxDSaturVertices.add(key);
            } else if (!verticesColoring.containsKey(key) && dsaturVertices.get(key) == maxDSatur) {
                maxDSaturVertices.add(key);
            }
        }
        return maxDSaturVertices;
    }

    public void setSortedVertices(){
        Map<Integer,List<Integer>> verticesDegree = new HashMap<>();
        for(int i = 1; i<=nbrVertices;i++) {
            int degre = 0;
            for(int[] edge : edges) {
                if (edge[0] == i || edge[1] == i) {
                    degre++;

                }
            }
            if(verticesDegree.containsKey(degre)){
                verticesDegree.get(degre).add(i);
            } else {
                verticesDegree.put(degre,new ArrayList<>());
                verticesDegree.get(degre).add(i);
            }
        }
        List<Integer> keys = new ArrayList<>(verticesDegree.keySet());
        keys.sort(Comparator.reverseOrder());
        List<Integer> sortedVertices = new ArrayList<>();
        for(Integer key : keys) {
            sortedVertices.addAll(verticesDegree.get(key));
        }
        sortedVerticesDeg = sortedVertices;
    }

    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int[] edge : edges) {
            if (edge[0]==vertex) {
                neighbors.add(edge[1]);
            } else if (edge[1]==vertex) {
                neighbors.add(edge[0]);
            }
        }
        return neighbors;
    }

    public void updateDSatur(int vertex) {
        List<Integer> colors = new ArrayList<>();
        List<Integer> neighbors = getNeighbors(vertex);
        for(Integer neighbor : neighbors){
            if(verticesColoring.containsKey(neighbor) && !colors.contains(verticesColoring.get(neighbor))) {
                colors.add(verticesColoring.get(neighbor));
            }
        }
        if(dsaturVertices.containsKey(vertex)){
            dsaturVertices.replace(vertex,colors.size());
        } else {
            dsaturVertices.put(vertex,colors.size());

        }
    }
}
