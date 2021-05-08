package graphs;
public class Edge {
    Node source;
    Node destination;
    int weight;
    Long edgeId;

    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        edgeId= IdManager.generateUniqueId();
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
    public Long getEdgeId() {
        return edgeId;
    }


}
