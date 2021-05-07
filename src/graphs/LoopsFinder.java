package graphs;

import java.util.ArrayList;
import java.util.LinkedList;

public class LoopsFinder {

    ArrayList<ArrayList<Edge>> loops = new ArrayList<ArrayList<Edge>>();
    ArrayList<Edge> tempLoop = new ArrayList<Edge>();

    public  boolean isLoop(int loopStart, LinkedList<Edge>[] adjacencylist , boolean[] visited, int currentVertex){
        if(visited[currentVertex]){
            if(currentVertex == loopStart) {
                if(!(tempLoop.get(tempLoop.size()-1).getSource().getId() < currentVertex)){
                    if(!(tempLoop.get(0).getDestination().getId() < currentVertex)) {
                        if(tempLoop.size() == 1){
                            addLoop();
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        boolean flag = false;
        for(int i = 0; i < adjacencylist[currentVertex].size(); i++){
            visited[currentVertex] = true;
            tempLoop.add(adjacencylist[currentVertex].get(i));
            flag = isLoop(loopStart, adjacencylist, visited, adjacencylist[currentVertex].get(i).getDestination().getId());
            visited[currentVertex] = false;
            if(flag==true){
                addLoop();
            }
            tempLoop.remove(adjacencylist[currentVertex].get(i));
        }

        return false;
    }


    public ArrayList<ArrayList<Edge>> loopsFinder(LinkedList<Edge>[] adjacencylist) {
        boolean[] visited = new boolean[adjacencylist.length];
        for (int i = 0; i < adjacencylist.length; i++) {
            visited[i] = true;
            for (int j = 0; j < adjacencylist[i].size(); ++j) {
                //ArrayList<Edge> loop = new ArrayList<Edge>();
                tempLoop.add(adjacencylist[i].get(j));
                boolean flag = isLoop(i, adjacencylist, visited, adjacencylist[i].get(j).getDestination().getId());
                tempLoop.remove(adjacencylist[i].get(j));
            }
            visited[i] = false;
        }
        return loops;
    }

    public void addLoop(){
        ArrayList<Edge> loop = new ArrayList<Edge>();
        for(int i = 0; i < tempLoop.size(); i++){
            loop.add(tempLoop.get(i));
        }
        loops.add(loop);
    }

    public void clearGraph(){
        loops = new ArrayList<ArrayList<Edge>>();
    }

    public void printLoops(ArrayList<ArrayList<Edge>> loop){
        for(int i = 0; i < loop.size(); i++){
            System.out.println("loop  "+i+":");
            System.out.println("-------------------------------");
            ArrayList<Edge> temp = loop.get(i);
            for(int j = 0; j < temp.size(); j++){
                System.out.println("from  " + temp.get(j).getSource().getId()+"   to   " + temp.get(j).getDestination().getId());
            }
        }
    }
    public String showLoops(){
        String loopPathsString = "LOOPS PATHS :\n--------------\n";
        if(loops.size() > 0){
            for(int i = 0; i < loops.size(); i++) {
                loopPathsString+="Loop "+ (i+1) +" :      "+loops.get(i).get(0).source.getName();
                for(int j = 0; j < loops.get(i).size(); j++){
                    loopPathsString+= " --> " + loops.get(i).get(j).destination.getName();
                }
                loopPathsString+= "\n";

            }
            loopPathsString+="-----------------------------------------------------------------------\n";
            return loopPathsString;
        }
        loopPathsString+="NO LOOPS\n";
        loopPathsString+="-----------------------------------------------------------------------\n";
        return loopPathsString;
    }


}
