package GUI;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    mxGraph graph;
    Object parent;
    JPanel mainPane;
    private JPanel dialogPane;
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
            //start = new Queue(id,graph,parent,1300,50);
            //start.setEdge(false);
            //queues.add(start);
            Object v1 = graph.insertVertex(parent, null, "Hello", 10, -30, 80,
                    30);
            setVertexStyle((mxICell) v1, "cyan");
            Object v2 = graph.insertVertex(parent, null, "World!", 20, -30,
                    80, 30);
            setVertexStyle((mxICell) v2, "cyan");
            Object edge = graph.insertEdge(parent, null, "15", v1, v2);
            setEdgeStyle((mxICell) edge);
        }
        finally
        {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        graphComponent.getViewport().setOpaque(true);
        graphComponent.getViewport().setBackground(Color.DARK_GRAY);
        graph.getModel().setGeometry(parent,
                new mxGeometry(500, 700,
                        1000, 1000));
        mainPane.add(graphComponent);
    }

    private void initComponents() {
        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        getContentPane().setLayout(new BorderLayout());
        mainPane = new JPanel();
        mainPane.setBackground(Color.white);
        getContentPane().add(mainPane, BorderLayout.CENTER);
        pack();
//        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }
    public void resetGraph(){
        //code here
        graph.refresh();
    }
    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 600);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
    //STYLES FOR VERTICES
    private void setVertexStyle(final mxICell vertex, final String colorstr) {
        String targetStyle = vertex.getStyle();
        targetStyle = mxStyleUtils.removeAllStylenames(targetStyle);
        targetStyle = mxStyleUtils.setStyle(targetStyle , mxConstants.STYLE_STROKECOLOR, "black" );
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FILLCOLOR, colorstr);
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FONTCOLOR, "black");
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FONTSIZE, "17");
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertex.setStyle(targetStyle);
    }
    private void setEdgeStyle(final mxICell edge) {
        String targetStyle = edge.getStyle();
        targetStyle = mxStyleUtils.removeAllStylenames(targetStyle);
        targetStyle = mxStyleUtils.setStyle(targetStyle , mxConstants.STYLE_STROKECOLOR, "white" );
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FONTCOLOR, "magenta");
        targetStyle = mxStyleUtils.setStyle(targetStyle, mxConstants.STYLE_FONTSIZE, "22");
        edge.setStyle(targetStyle);
    }

}
