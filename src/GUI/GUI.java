package GUI;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import graphs.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GUI extends JFrame {
    mxGraph graph;
    Object parent;
    JPanel mainPane;
    JPanel panelComp;
    Graph myGraph ;
    public static final Color white_blue = new Color(51,153,255);
    public static final  Color col = new Color(223, 255, 0);
    JButton addVertexBtn;
    JButton startBtn;
    JButton removeGraphBtn;
    public GUI() {
    super("Producer Consumer");

    initComponents();

    graph = new mxGraph(){
        @Override
       public boolean isCellSelectable(Object cell)
        {
            if (model.isEdge(cell))
            {
                return false;
            }

            return super.isCellSelectable(cell);
        }
    };
    parent = graph.getDefaultParent();
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
    graph.getModel().setGeometry(parent,
            new mxGeometry(850, 700,
                    0, 0));
    graph.setVertexLabelsMovable(false);
    graph.setAllowLoops(true);
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
                startCalculation();
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
                myGraph.setVertices(0);
                addvertex();
            }
        });
        panelComp.add(addVertexBtn);
        panelComp.add(startBtn);
        panelComp.add(removeGraphBtn);
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
        stil.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_SEGMENT);
        foo.setDefaultEdgeStyle(stil);
        graph.setStylesheet(foo);
    }

    public void addvertex(){
        Object v = graph.insertVertex(parent, null,Integer.toString(myGraph.getVertices()) , 30, 30,
                60, 60);
        mxICell ver = (mxICell) v;
        setVertexStyle(ver, "#FF5733");
        resetGraph();
        myGraph.addVertex();
    }
    public void startCalculation()
    {
        Object[] list = graph.getChildVertices(graph.getDefaultParent());
        for(int i=0; i< myGraph.getVertices(); i++)
        {
            mxICell vertex = (mxICell) list[i];
            int loops = 0;
            boolean flag = true;
            for (int j =0; j<vertex.getEdgeCount(); j++)
            {
                mxICell edge = vertex.getEdgeAt(j);
                String destination =(String) (edge.getTerminal(false)).getValue();
                if( i == Integer.valueOf(destination))
                {
                    loops++;
                    if(loops > 1 && flag)
                    {
                        myGraph.addEgde(i, Integer.valueOf(destination), Integer.valueOf((String) graph.getModel().getValue(edge)));
                        flag = false;
                    }
                }else
                {
                    myGraph.addEgde(i, Integer.valueOf(destination), Integer.valueOf((String) graph.getModel().getValue(edge)));
                }
            }
            
        }
        myGraph.printGraph();

    }
}

