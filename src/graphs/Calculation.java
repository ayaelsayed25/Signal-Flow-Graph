package graphs;

import java.util.ArrayList;
import java.util.LinkedList;

public class Calculation {
    public Graph removePath(Graph graph, LinkedList<Edge> path){
        Graph newGraph = new Graph(graph.adjacencylist.length);
        boolean[] array = new boolean[graph.adjacencylist.length];
        int i;
        for(i=0; i<path.size(); i++){
            array[path.get(i).getSource().getId()]=true;
        }
        array[path.get(path.size()-1).getDestination().getId()]=true;
        for(int j=0; j<newGraph.adjacencylist.length; j++){
            if(!array[j]){
                for(int k=0; k<graph.adjacencylist[j].size(); k++){
                    if(!array[graph.adjacencylist[j].get(k).getDestination().getId()]){
                        newGraph.adjacencylist[j].add(graph.adjacencylist[j].get(k));
                    }
                }
            }
        }
        return newGraph;
    }

    public ArrayList<Integer> loopGains(ArrayList<ArrayList<Edge>> loop) {
        ArrayList<Integer> finalGains= new ArrayList<>();
        for(int i=0; i<loop.size(); i++){
            ArrayList<Edge> current = loop.get(i);
            int gain=1;
            for(int j=0; j<current.size(); j++){
                gain*=current.get(j).getWeight();
            }
            finalGains.add(gain);
        }
        return finalGains;
    }

    public int getGain(int index, ArrayList<Integer> gains){
        return gains.get(index);
    }

    public int sumOfIndividualLoops(ArrayList<Integer> gains){
        int sum=0;
        for(int i=0; i<gains.size(); i++){
            sum+=gains.get(i);
        }
        return sum;
    }

    public int sumOfNonTouchingLoops(ArrayList<int[]> nonTouching, ArrayList<Integer> gains){
        int sum=0;
        int product;
        for(int j=0; j<nonTouching.size(); j++){
            int[] array = nonTouching.get(j);
            product=1;
            for(int k=0; k<array.length; k++){
                product*=getGain(array[k],gains);
            }
            sum+=product;
        }
        return sum;
    }

    public int delta(ArrayList<ArrayList<int[]>> nonTouching, ArrayList<Integer> gains) {
        int sumIndividualLoops = sumOfIndividualLoops(gains);
        int result = 1 - sumIndividualLoops;
        for (int i = 0; i < nonTouching.size(); i++) {
            ArrayList<int[]> current = nonTouching.get(i);
            int sum = sumOfNonTouchingLoops(current, gains);
            result += Math.pow(-1, i) * sum;
        }
        return result;
    }
    public int[] deltaI(Graph graph, LinkedList<LinkedList<Edge>> path){
        int[] result=new int[path.size()];
        NonTouchingLoops nl = new NonTouchingLoops();
        LoopsFinder l = new LoopsFinder();
        for(int i=0; i< path.size(); i++){
            Graph newGraph = removePath(graph,path.get(i));
            l.clearGraph();
            ArrayList<ArrayList<Edge>> loop = l.loopsFinder(newGraph.adjacencylist);
            ArrayList<Integer> loopGain=loopGains(loop);
            int sumIndevidualLoop=sumOfIndividualLoops(loopGain);
            ArrayList<ArrayList<int[]>> nonTouching =nl.nonTouchingloops(loop);
            result[i]=delta(nonTouching,loopGain);
        }
        return result;
    }

    public double overallFunction(int delta, int[] deltaI, LinkedList<Integer> pathsGain){
        double result=0;
        for (int i=0; i<pathsGain.size(); i++){
            result+=pathsGain.get(i)*deltaI[i];
        }
        result=result/delta;
        return result;
    }
    public String getResults(int delta, int[] deltaI, double result){
        String finalString = "OVERALL SYSTEM TRANSFER FUNCTION:\n-----------------------------------------------------------\n"+"delta = "+delta+"\n";
        for(int i=0; i<deltaI.length; i++){
            finalString+="delta"+(i+1)+" = "+deltaI[i]+"\n";
        }
        finalString+="Transfer Function = "+result+"\n-----------------------------------------------------------------------\n";
        return finalString;
    }


}
