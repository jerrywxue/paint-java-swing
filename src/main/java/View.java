import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class View extends JPanel implements Observer {
    private Model model;

    /**
     * Create a new View.
     */
    public View(Model model, Controller controller) {
        this.setMinimumSize(new Dimension(480, 600));
        this.setPreferredSize(new Dimension(800, 600));
        this.model = model;
        model.addObserver(this);
        this.setBackground(Color.WHITE);
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Component c = (Component)e.getSource();
                model.scalar(c.getSize().width / 800f, c.getSize().height / 600f);
            }
        });

        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.scale(model.getxs(), model.getxy());
        if (model.getpathsize()!= 0) {
            int pathsize = 100 / model.getpathsize();
            int slide = model.getplayback();
            int completelines = slide / pathsize;
            float halfline = slide % pathsize;
            ArrayList<Path> currentPaths = model.getpaths();
            for (int i = 0; i < completelines; ++i) {
                currentPaths.get(i).draw(g2, -1);
            }
            if (completelines <  model.getpathsize()) {
                float percentofinterval = (halfline / pathsize) ;
                int n = Math.round(currentPaths.get(completelines).getsize() * (percentofinterval));
                model.getpaths().get(completelines).draw(g2, n);
            }
        }
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        this.repaint();
    }

}
