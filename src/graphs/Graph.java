package graphs;

import java.util.LinkedList;

public class Graph {
    int vertices=0;
    LinkedList<LinkedList<Edge>> adjacencylist;

    public Graph() {
        adjacencylist = new LinkedList<LinkedList<Edge>>();
        //initialize adjacency lists for all the vertices

    }

    public  void addVertex(){
        adjacencylist.add(new LinkedList<>());
        vertices++;
    }


    public void addEgde(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencylist.get(source).addFirst(edge); //for directed graph
    }

    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            LinkedList<Edge> list = adjacencylist.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.println("vertex-" + i + " is connected to " +
                        list.get(j).destination + " with weight " + list.get(j).weight);
            }
        }
    }
    public void setVertices(int vertices) {
        this.vertices = vertices;
    }
}


