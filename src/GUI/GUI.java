package GUI;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import graphs.Graph;
import graphs.Node;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class GUI extends JFrame {
    mxGraph graph;
    JPanel mainPane;
    JPanel panelComp;
    Graph myGraph ;
    public static final Color white_blue = new Color(51,153,255);
    public static final  Color col = new Color(223, 255, 0);
    public static final Color col2 = new Color(255, 45, 150 );
    public static final Color col3 = new Color(251, 251, 251);
    JButton addVertexBtn;
    JButton startBtn;
    JButton removeGraphBtn;
    JLabel WarningLabel;
    JTextArea display;
    public String output = "Heloo hvjhasjfjdafhjfdafdhafhjadsfhjfdashfvshvhcvhafchfh\njghjfsjhsvahvxhajfcjafdhfhdvhavhjvshavdhfhdfahfshafhdfahfdjafdhfahvcxhvbxv\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj\nbjkvahdfhafhdfshfahdfhsafdj";
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
    graphComponent.getViewport().setBackground(col3);
    graph.getModel().setGeometry(graph.getDefaultParent(),
            new mxGeometry(980, 750,
                    0, 0));
    graph.setMinimumGraphSize(new mxRectangle(980, 750, 0, 0));
    graph.setCollapseToPreferredSize(true);
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
    graph.addListener(mxEvent.LABEL_CHANGED, (sender, evt) -> {
         mxICell vertex =(mxICell) evt.getProperties().get("vertex");
    }

    );
    mainPane.add(graphComponent);
    setEdgeStyle();
    }

    private void initComponents() {
        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        getContentPane().setLayout(new BorderLayout());
        mainPane = new JPanel();
        mainPane.setBackground(col3);
        panelComp = new JPanel();
        panelComp.setLayout(null);
        panelComp.setBackground(white_blue);
        getContentPane().add(panelComp, BorderLayout.CENTER);
        getContentPane().add(mainPane, BorderLayout.EAST);
        redo = new LinkedList<Object>();
        //BUTTONS
        Font boldFont = new Font("SansSerif", Font.BOLD, 16);
        //FOR ADDING A VERTEX
        addVertexBtn = new JButton("Add Node");
        addVertexBtn.setBounds(70,105,150, 40);
        addVertexBtn.setBackground(col);
        addVertexBtn.setForeground(Color.gray);
        addVertexBtn.setFont(boldFont);
        addVertexBtn.setBorder(BorderFactory.createEmptyBorder());
        addVertexBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                addvertex();
            }
        });
        //FOR STARTING CALCULATION
        startBtn = new JButton("Start Analysis");
        startBtn.setBounds(70,160,150,40);
        startBtn.setBackground(col);
        startBtn.setForeground(Color.gray);
        startBtn.setFont(boldFont);
        startBtn.setBorder(BorderFactory.createEmptyBorder());
        startBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(checkEdgesValue()){
                    startCalculation();
                }
            }
        });
        //FOR REMOVING THE WHOLE GRAPH
        removeGraphBtn = new JButton("Remove Graph");
        removeGraphBtn.setBounds(70,215,150,40);
        removeGraphBtn.setBackground(col);
        removeGraphBtn.setForeground(Color.gray);
        removeGraphBtn.setFont(boldFont);
        removeGraphBtn.setBorder(BorderFactory.createEmptyBorder());
        removeGraphBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
                graph.getModel().beginUpdate();
                graph.getModel().endUpdate();
                addvertex();
            }
        });
        boldFont = new Font("SansSerif", Font.ITALIC, 14);
        //FOR UNDO :
        JButton undoBtn = new JButton("Undo");
        undoBtn.setBounds(250,135,80,30);
        undoBtn.setBackground(col2);
        undoBtn.setForeground(Color.white);
        undoBtn.setFont(boldFont);
        undoBtn.setBorder(BorderFactory.createEmptyBorder());
        undoBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                undoChanges();
            }
        });
        //FOR REDO:
        JButton redoBtn = new JButton("Redo");
        redoBtn.setBounds(250,175,80,30);
        redoBtn.setBackground(col2);
        redoBtn.setForeground(Color.white);
        redoBtn.setFont(boldFont);
        redoBtn.setBorder(BorderFactory.createEmptyBorder());
        redoBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                redoChanges();
            }
        });
        //FOR EXIT BUTTON:
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(20,810,80,30);
        exitBtn.setBackground(col2);
        exitBtn.setForeground(Color.white);
        exitBtn.setFont(boldFont);
        exitBtn.setBorder(BorderFactory.createEmptyBorder());
        exitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        //FOR warning label
        WarningLabel=new JLabel();
        WarningLabel.setBounds(30,250,350,50);
        WarningLabel.setForeground(white_blue);
        boldFont = new Font("SansSerif", Font.BOLD, 16);
        WarningLabel.setFont(boldFont);
        WarningLabel.setText("Make sure your entry is not wrong!");
        //FOR  TITLE
        JLabel lbl = new JLabel();
        lbl.setBounds(15, 15, 360, 60);
        lbl.setForeground(col3);
        lbl.setText("Signal Flow Graph Solver ©");
        boldFont = new Font("SansSerif", Font.ITALIC + Font.BOLD, 27);
        lbl.setFont(boldFont);
        //User guide
        JLabel userGuide = new JLabel();
        userGuide.setBounds(245, 190, 360, 80);
        userGuide.setForeground(col3);
        userGuide.setText("<html>*Double click on nodes and<br/> edges to edit the text.</html>");
        boldFont = new Font("SansSerif", Font.ITALIC + Font.BOLD, 13);
        userGuide.setFont(boldFont);
        //label for input :
        JLabel lb = new JLabel();
        lb.setBounds(20, 310, 100, 20);
        lb.setForeground(col3);
        lb.setText("Input: ");
        boldFont = new Font("SansSerif", Font.BOLD, 20);
        lb.setFont(boldFont);
        //input :
        display = new JTextArea();
        display.setText(output);

        display.setEditable(false); // set textArea non-editable
        display.setBackground(new Color(176, 175, 179 ));
        boldFont = new Font("SansSerif", Font.ITALIC, 17);
        display.setForeground(col3);
        display.setFont(boldFont);
        display.setBorder(new EmptyBorder(20,20,0,0));//top,left,bottom,right
        JScrollPane scroll = new JScrollPane(display);
        scroll.setBounds(20, 350, 400, 400);
        scroll.setSize(400, 430);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelComp.add(scroll);
        panelComp.add(userGuide);
        panelComp.add(lbl);
        panelComp.add(lb);
        panelComp.add(exitBtn);
        panelComp.add(redoBtn);
        panelComp.add(undoBtn);
        panelComp.add(addVertexBtn);
        panelComp.add(startBtn);
        panelComp.add(removeGraphBtn);
        panelComp.add(WarningLabel);
    }
    public void resetGraph(){
        //code here
        graph.refresh();
    }
    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
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
        stil.put(mxConstants.STYLE_STROKECOLOR, "black");
        stil.put(mxConstants.STYLE_FONTCOLOR, "white");
        stil.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "black");
        stil.put(mxConstants.STYLE_FONTSIZE, "18");
        stil.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL);
        foo.setDefaultEdgeStyle(stil);
        graph.setStylesheet(foo);
    }

    public void addvertex(){
        Object v = graph.insertVertex(graph.getDefaultParent(), null,"Text" , 30, 30,
                60, 60);
        mxICell ver = (mxICell) v;
        setVertexStyle(ver, "#FF2D96");
        resetGraph();
    }


    public boolean checkEdgesValue(){
        Object[] list = graph.getChildEdges(graph.getDefaultParent());
        for(int i=0; i< list.length; i++){
           if(!isInteger((String) graph.getModel().getValue(list[i]),10)){
               WarningLabel.setForeground(new Color(225, 5, 108));
               return false;
           }
        }
        WarningLabel.setForeground(white_blue);
        return true;
    }

    public void startCalculation()
    {
        Object[] list = graph.getChildVertices(graph.getDefaultParent());
        LinkedList vertices = new LinkedList(Arrays.asList(list));
        Node[] nodes = new Node[list.length];
        for(int i=0; i<list.length; i++)
        {
            Node node = new Node(i, (String)((mxICell)list[i]).getValue());
            nodes[i] = node;
        }
        myGraph = new Graph(list.length);
        System.out.println(list.length);
        for(int i=0; i< list.length; i++)
        {
            mxICell vertex = (mxICell) list[i];
            Object[] edges = graph.getEdges(vertex, graph.getDefaultParent(), false, true, true);
            for (int j =0; j<edges.length; j++)
            {
                Node sourceNode = new Node(i, (String) vertex.getValue());
                String dest =(String) (((mxICell)edges[j]).getTerminal(false)).getValue();
                int index = vertices.indexOf(((mxICell)edges[j]).getTerminal(false));
                System.out.println("index = " + index);
                Node destNode = new Node(index, dest);
                myGraph.addEgde(sourceNode, destNode, Integer.valueOf((String) graph.getModel().getValue(edges[j])));
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

