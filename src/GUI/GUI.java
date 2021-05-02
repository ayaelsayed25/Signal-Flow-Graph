package GUI;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class GUI extends JFrame {
    mxGraph graph;
    Object parent;
    Queue start;
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
            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
                    30);
            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
                    80, 30);
            graph.insertEdge(parent, null, "Edge", v1, v2);
        }
        finally
        {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        getContentPane().add(graphComponent);

    }

    private void initComponents() {
        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        getContentPane().setLayout(new BorderLayout());
        //contentPane.add(dialogPane, BorderLayout.CENTER);
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

}
