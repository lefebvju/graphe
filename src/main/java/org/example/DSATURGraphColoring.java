package org.example;
import java.util.*;

public class DSATURGraphColoring {

    public static void main(String[] args) {
        int numVertices = 5;
        int[][] edges = {
                {0, 1}, {0, 2}, {1, 2}, {1, 3}, {3, 4}
        };

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] colors = dsatur(graph, numVertices);

        // Affichage des résultats
        for (int i = 0; i < numVertices; i++) {
            System.out.println("Sommet " + i + " : Couleur " + colors[i]);
        }
    }

    private static int[] dsatur(Map<Integer, Set<Integer>> graph, int numVertices) {
        int[] colors = new int[numVertices];
        Arrays.fill(colors, -1);

        PriorityQueue<DegreeNode> pq = new PriorityQueue<>(Comparator.reverseOrder());

        for (int vertex : graph.keySet()) {
            pq.add(new DegreeNode(vertex, graph.get(vertex).size()));
        }

        int color = 1;
        while (!pq.isEmpty()) {
            DegreeNode node = pq.poll();
            int vertex = node.vertex;

            if (colors[vertex] == -1) {
                colors[vertex] = color++;

                for (int neighbor : graph.get(vertex)) {
                    if (colors[neighbor] == -1) {
                        pq.add(new DegreeNode(neighbor, graph.get(neighbor).size()));
                    }
                }
            }
        }

        return colors;
    }

    static class DegreeNode implements Comparable<DegreeNode> {
        int vertex;
        int degree;

        public DegreeNode(int vertex, int degree) {
            this.vertex = vertex;
            this.degree = degree;
        }

        @Override
        public int compareTo(DegreeNode other) {
            return Integer.compare(this.degree, other.degree);
        }
    }
}
