package graphs;

import java.util.LinkedList;

public class Graph {
    LinkedList<Edge>[] adjacencylist;

    public Graph(int vertices) {
        adjacencylist = new LinkedList[vertices];
        addVertex();
    }
    public void addVertex()
    {
        for(int i=0; i<adjacencylist.length; i++)
        {
            adjacencylist[i] = new LinkedList<Edge>();
        }
    }
    public void addEgde(Node source, Node destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencylist[source.getId()].addFirst(edge); //for directed graph
    }

    public void printGraph() {
        for (int i = 0; i < adjacencylist.length; i++) {
            LinkedList<Edge> list = adjacencylist[i];
            for (int j = 0; j < list.size(); j++) {
                System.out.println("vertex-" + list.get(j).source.getName() + " index " + list.get(j).source.getId() + "Boolean : " + list.get(j).source.isOutput()+ " is connected to " +
                        list.get(j).destination.getName() + " index " + list.get(j).destination.getId()+ " Boolean: " + list.get(j).destination.isOutput()+ " with weight " + list.get(j).weight);
            }
        }
    }

}


