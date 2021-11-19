import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationModel;
import org.apache.commons.collections15.Factory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A class that uses a factory to create instances of graphs.
 */
class GraphFactory implements Factory<Graph<Integer,String>> {
    /**
     * @return graph
     */
    public Graph<Integer,String> create() {
        return new SparseMultigraph<Integer,String>();
    }
}

/**
 * A class that uses a factory to create instances of vertex.
 */
class VertexFactory implements Factory<Integer> {

    int a = 0;

    /**
     * @return vertex
     */
    public Integer create() {
        return a++;
    }

}

/**
 * A class that uses a factory to create instances of edge.
 */
class EdgeFactory implements Factory<String> {
    char aa = 'a';

    /**
     * @return edge
     */
    public String create() {
        return Character.toString(aa++);
    }

}

/**
 * A class that implements the creation of a graph, as well as its visualization and saving.
 */
public class Main {

    /**
     * the starting point of the program
     * @param args
     */
    public static void main(String[] args) {

        JLabel LNumV = new JLabel("Количество вершин");
        JLabel LNumE = new JLabel("Количество ребер");
        JLabel LR = new JLabel("Коэффициент R");

        JTextField  TBNumV = new JTextField(5);
        JTextField  TBNumE = new JTextField(5);
        JTextField  TBR = new JTextField(5);

        JButton button = new JButton("Построить граф");
        JButton buttonSave = new JButton("Сохранить граф");

        JPanel panel0 = new JPanel(new FlowLayout());
        panel0.setLayout(new GridLayout(3, 3, 5, 12));
        panel0.add(LNumV);
        panel0.add(LNumE);
        panel0.add(LR);
        panel0.add(TBNumV);
        panel0.add(TBNumE);
        panel0.add(TBR);
        panel0.add(button);
        panel0.add(buttonSave);

        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.setBounds(0,0,750,750);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(panel0);
        panel.add(panel1);

        JFrame frame = new JFrame("Graph Small World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(750,750);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.removeAll();

                Graph<Integer, String>  Eppstein = new EppsteinPowerLawGenerator<Integer,String>(new GraphFactory(),
                        new VertexFactory(), new EdgeFactory(), Integer.parseInt(TBNumV.getText()),
                        Integer.parseInt(TBNumE.getText()), Integer.parseInt(TBR.getText())).create();

                Layout<Integer, String> layout = new CircleLayout(Eppstein);
                layout.setSize(new Dimension(600,600)); // sets the initial size of the space
                // The BasicVisualizationServer<V,E> is parameterized by the edge types
                BasicVisualizationServer<Integer,String> vv =
                        new BasicVisualizationServer<Integer,String>(layout);
                vv.setPreferredSize(new Dimension(650,650)); //Sets the viewing area size

                panel1.add(vv);
                frame.validate();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BufferedImage image = new BufferedImage(
                        600, 600
                        , BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = image.createGraphics();
                panel1.paint(g2);
                try
                {
                    ImageIO.write(image, "png", new File("graph.png"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
