package graphs;

import java.util.ArrayList;
import java.util.LinkedList;

public class TransferFunctionFinder {

    private Graph graph;

    // variables to show
    private LinkedList<LinkedList<Edge>> paths;
    private ArrayList<ArrayList<Edge>> loops;
    private ArrayList<ArrayList<int[]>> nonTouchingLoops;
    private ArrayList<Integer> loopGains;
    private LinkedList<Integer> pathsGains;
    private int delta;
    private int[] deltaI;
    private double transferFun;

    // classes used
    private ForwardPathsFinder forwardPathsFinder ;
    private LoopsFinder loopsFinder = new LoopsFinder();
    private NonTouchingLoops nonTouchingFinder = new NonTouchingLoops();
    private Calculation cal = new Calculation();

    // constructor
    public TransferFunctionFinder(Graph graph){
        this.graph = graph;
    }

    public void analyzeSignalFlowGraph(){
        findPaths();
        findLoops();
        findNonTouchingloops();
        findLoopGains();
        findPathGains();
        findDelta();
        findDeltaI();
        findOverallFunction();
    }

    // To show output of all in GUI
    public String showAnalysisInGUI(){
        String result = this.forwardPathsFinder.getPathsInString();
        result += this.loopsFinder.showLoops();
        result += this.nonTouchingFinder.nonTouchingLoopsToStrings(this.nonTouchingLoops);
        result += this.cal.getResults(this.delta, this.deltaI, this.transferFun);
        return result;
    }

    /// finding forward paths
    private void findPaths(){
        forwardPathsFinder = new ForwardPathsFinder(this.graph);
        this.paths = forwardPathsFinder.findPaths();
    }

    /// finding loops
    private void findLoops(){
        this.loops = loopsFinder.loopsFinder(this.graph.adjacencylist);
    }

    /// finding non touching loops
    private void findNonTouchingloops(){
        this.nonTouchingLoops = nonTouchingFinder.nonTouchingloops(this.loops);
    }

    /// finding loop gains
    private void findLoopGains() {
        this.loopGains = cal.loopGains(this.loops);
    }

    /// finding paths gain
    private void findPathGains(){
        this.pathsGains = forwardPathsFinder.findGains();
    }

    /// finding deltas
    private void findDelta(){
        this.delta = cal.delta(this.nonTouchingLoops, this.loopGains);
    }

    private void findDeltaI(){
        this.deltaI = cal.deltaI(this.graph, this.paths);
    }

    private void findOverallFunction(){
        this.transferFun = cal.overallFunction(this.delta, this.deltaI, this.pathsGains);
    }


}
