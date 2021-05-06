package GUI;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.*;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import graphs.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Map;

public class GUI extends JFrame {
    mxGraph graph;
    JPanel mainPane;
    JPanel panelComp;
    Graph myGraph ;
    public static final Color white_blue = new Color(51,153,255);
    public static final  Color col = new Color(223, 255, 0);
    JButton addVertexBtn;
    JButton startBtn;
    JButton removeGraphBtn;
    JLabel WarningLabel;
    LinkedList<Object> redo;
    public GUI() {
    super("Producer Consumer");

    initComponents();

    graph = new mxGraph(){

    };
    graph.setAllowDanglingEdges(false);
    graph.getModel().beginUpdate();
    try
    {
        addvertex();
    }
    finally
    {
        graph.getModel().endUpdate();
    }
    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    graphComponent.setConnectable(true);
    graphComponent.getViewport().setOpaque(true);
    graphComponent.getViewport().setBackground(Color.gray);
    graph.getModel().setGeometry(graph.getDefaultParent(),
            new mxGeometry(850, 700,
                    0, 0));
    graph.setVertexLabelsMovable(false);
    graph.setEdgeLabelsMovable(false);
    graph.setAllowLoops(true);
    graph.addListener(mxEvent.CELL_CONNECTED, (sender, evt) -> {

                if (!(boolean) evt.getProperties().get("source")) {
                    mxICell edge =(mxICell) evt.getProperties().get("edge");
                    edge.setValue("1");
                }
            }

    );
    mainPane.add(graphComponent);
    setEdgeStyle();
    }

    private void initComponents() {
        myGraph =new Graph();
        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        getContentPane().setLayout(new BorderLayout());
        mainPane = new JPanel();
        mainPane.setBackground(Color.GRAY);
        panelComp = new JPanel();
        panelComp.setLayout(null);
        panelComp.setBackground(white_blue);
        getContentPane().add(panelComp, BorderLayout.CENTER);
        getContentPane().add(mainPane, BorderLayout.WEST);
        redo = new LinkedList<Object>();
        //BUTTONS
        //FOR ADDING A VERTEX
        addVertexBtn = new JButton("Add Node");
        addVertexBtn.setBounds(170,30,180, 50);
        addVertexBtn.setBackground(col);
        addVertexBtn.setForeground(Color.white);
        Font boldFont = new Font("sans serif", Font.BOLD, 20);
        addVertexBtn.setFont(boldFont);
        addVertexBtn.setBorder(BorderFactory.createEmptyBorder());
        addVertexBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                addvertex();
            }
        });
        //FOR STARTING CALCULATION
        startBtn = new JButton("Start Calculations");
        startBtn.setBounds(170,90,180,50);
        startBtn.setBackground(col);
        startBtn.setForeground(Color.white);
        boldFont = new Font("sans serif", Font.BOLD, 20);
        startBtn.setFont(boldFont);
        startBtn.setBorder(BorderFactory.createEmptyBorder());
        startBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                myGraph.removeGraph();
                if(checkEdgesValue()){
                    startCalculation();
                }

            }
        });
        //FOR REMOVING THE WHOLE GRAPH
        removeGraphBtn = new JButton("Remove Graph");
        removeGraphBtn.setBounds(170,150,180,50);
        removeGraphBtn.setBackground(col);
        removeGraphBtn.setForeground(Color.white);
        boldFont = new Font("sans serif", Font.BOLD, 20);
        removeGraphBtn.setFont(boldFont);
        removeGraphBtn.setBorder(BorderFactory.createEmptyBorder());
        removeGraphBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
                graph.getModel().beginUpdate();
                graph.getModel().endUpdate();
                myGraph.removeGraph();
                addvertex();
            }
        });
        //FOR UNDO :
        JButton undoBtn = new JButton("Undo");
        undoBtn.setBounds(250,500,100,30);
        undoBtn.setBackground(col);
        undoBtn.setForeground(Color.white);
        boldFont = new Font("sans serif", Font.BOLD, 20);
        undoBtn.setFont(boldFont);
        undoBtn.setBorder(BorderFactory.createEmptyBorder());
        undoBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                undoChanges();
            }
        });
        //FOR REDO:
        JButton redoBtn = new JButton("Undo");
        redoBtn.setBounds(250,600,100,30);
        redoBtn.setBackground(col);
        redoBtn.setForeground(Color.white);
        boldFont = new Font("sans serif", Font.BOLD, 20);
        redoBtn.setFont(boldFont);
        redoBtn.setBorder(BorderFactory.createEmptyBorder());
        redoBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                redoChanges();
            }
        });

        //FOR warning label
        WarningLabel=new JLabel();
        WarningLabel.setBounds(170,300,180,50);
        panelComp.add(redoBtn);
        panelComp.add(undoBtn);
        panelComp.add(addVertexBtn);
        panelComp.add(startBtn);
        panelComp.add(removeGraphBtn);
        panelComp.add(WarningLabel);
        pack();

    }
    public void resetGraph(){
        //code here
        graph.refresh();
    }
    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
    //STYLES FOR VERTICES
    private void setVertexStyle(final mxICell vertex, final String colorstr) {
        String targetStyle = vertex.getStyle();
        targetStyle = mxStyleUtils.removeAllStylenames(targetStyle);
        targetStyle = mxStyleUtils.setStyle(targetStyle , mxConstants.STYLE_STROKECOLOR, "black" );
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FILLCOLOR, colorstr);
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FONTCOLOR, "white");
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FONTSIZE, "17");
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertex.setStyle(targetStyle);
    }
    private void setEdgeStyle() {
        final mxStylesheet foo = graph.getStylesheet();
        final Map<String, Object> stil = foo.getDefaultEdgeStyle();
        stil.put(mxConstants.STYLE_ROUNDED, true);
        stil.put(mxConstants.STYLE_STROKECOLOR, "white");
        stil.put(mxConstants.STYLE_FONTCOLOR, "white");
        stil.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "black");
        stil.put(mxConstants.STYLE_FONTSIZE, "22");
        stil.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL);
        foo.setDefaultEdgeStyle(stil);
        graph.setStylesheet(foo);
    }

    public void addvertex(){
        Object v = graph.insertVertex(graph.getDefaultParent(), null,"Text" , 30, 30,
                60, 60);
        mxICell ver = (mxICell) v;
        setVertexStyle(ver, "#FF5733");
        resetGraph();
    }


    public boolean checkEdgesValue(){
        Object[] list = graph.getChildEdges(graph.getDefaultParent());
        for(int i=0; i< list.length; i++){
           if(!isInteger((String) graph.getModel().getValue(list[i]),10)){
               WarningLabel.setText("please make sure that all the gains are numeric values");
               return false;
           }
        }
        WarningLabel.setText("");
        return true;
    }

    public void startCalculation()
    {
        Object[] list = graph.getChildVertices(graph.getDefaultParent());
        System.out.println(list.length);
        for(int i=0; i< list.length; i++)
        {
            mxICell vertex = (mxICell) list[i];
            myGraph.addVertex();
            Object[] edges = graph.getEdges(vertex, graph.getDefaultParent(), false, true, true);
            for (int j =0; j<edges.length; j++)
            {
                String destination =(String) (((mxICell)edges[j]).getTerminal(false)).getValue();
                String source =(String) (((mxICell)edges[j]).getTerminal(true)).getValue();
                myGraph.addEgde(source, destination, Integer.valueOf((String) graph.getModel().getValue(edges[j])));
            }
            
        }
        myGraph.printGraph();

    }

    public  boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
    public void  undoChanges()
    {
        Object[] list=graph.getChildCells(graph.getDefaultParent());
        if(list.length == 0)
            return;
        Object obj = list[list.length - 1];
        redo.add(obj);
        graph.getModel().remove(obj);
    }
    public void redoChanges()
    {
        if(!redo.isEmpty())
        {
            Object obj = redo.removeLast();
            graph.addCell((mxICell)obj);
        }
    }
}

