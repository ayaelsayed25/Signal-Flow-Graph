package graphs;

import java.util.LinkedList;

public class ForwardPathsFinder {
    private LinkedList<LinkedList<Edge>> forwardPaths = new LinkedList<LinkedList<Edge>>();
    private Graph graph;
    private LinkedList<Edge> path = new LinkedList<Edge>();
    private LinkedList<Integer> gains ;
    private boolean[] visited;
    private int lastVertex;


    public ForwardPathsFinder( Graph graph ) {
        this.graph = graph;
        lastVertex = graph.adjacencylist.length -1;
        visited = new boolean[graph.adjacencylist.length];
    }


    @SuppressWarnings("unchecked")
    private  void findPathsRec(Edge edge) {
        if( visited[edge.destination.getId()] ) {
            return;
        }
        path.add(edge);
        visited[edge.destination.getId()] = true;
        if( edge.destination.isOutput()) {
            forwardPaths.add((LinkedList<Edge>) path.clone());
            path.removeLast();
            visited[edge.destination.getId()] = false;
            return;
        }
        LinkedList<Edge> nextEdges = graph.adjacencylist[edge.destination.getId()];
        for( int i = 0 ; i < nextEdges.size() ; i++ ) {
            this.findPathsRec(nextEdges.get(i));
        }
        path.removeLast();
        visited[edge.destination.id] = false;

    }





    public LinkedList<LinkedList<Edge>> findPaths(){
        LinkedList<Edge> firstVertexEdges = graph.adjacencylist[0];
        visited[0] = true;
        for( int i = 0 ; i < firstVertexEdges.size() ; i++ ) {
            //path.add(firstVertexEdges.get(i));
            this.findPathsRec(firstVertexEdges.get(i));
            path.clear();
        }
        return this.forwardPaths;
    }


    public void printPaths() {
        for( int i = 0 ; i < this.forwardPaths.size() ; i++ ) {
            LinkedList<Edge> forwardPath= forwardPaths.get(i);
            System.out.print(forwardPath.get(0).source.getId() + " ");
            for( int j = 0 ; j < forwardPath.size(); j++ ) {
                System.out.print(forwardPath.get(j).destination.getId() + " ");
            }
            System.out.println();
        }
    }

    public LinkedList<Integer> findGains(){

        gains = new LinkedList<Integer>();
        for( int i = 0 ; i < forwardPaths.size() ; i++ ) {
            LinkedList<Edge> path = forwardPaths.get(i);
            int gain = 1;
            for( int j = 0 ; j < path.size() ; j++ ) {
                gain*= path.get(j).weight;
            }
            gains.add(gain);
        }
        return gains;
    }

    public void printGains() {
        for( int gain : gains ) {
            System.out.print(gain+ " ");
        }
        System.out.println();

    }
    public String getPathsInString(){
        String pathsString = "FORWARD PATHS : \n";
        pathsString += "------------------------------\n";

        for (int i = 0 ; i < forwardPaths.size() ; i++ ){
            LinkedList<Edge> currentPath = forwardPaths.get(i);
            pathsString += "Forward path  " + (i+1) + " :    ";
            pathsString += currentPath.get(0).source.name ;
            for( int j = 0; j < currentPath.size() ; j++ ){
                pathsString += " --> " + currentPath.get(j).destination.name;
            }
            pathsString+= "\n";

        }
        pathsString += "------------------------------------------------------------------------\n";
        return pathsString;
    }
}
