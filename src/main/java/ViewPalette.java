

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.swing.Icon;
import javax.swing.JSlider;


public class ViewPalette extends JPanel implements Observer{
    private Model model;
    private JButton red;
    private JButton yellow;
    private JButton blue;
    private JButton pink;
    private JButton orange;
    private JButton choosecolor;
    private JPanel current;
    private JButton skinnystroke;
    private JButton medstroke;
    private JButton largestroke;
    ImageIcon spec = new ImageIcon("choose.jpg");

    public class Width implements Icon {
        int height;
        public Width(int height){
            this.height = height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y){
            g.setColor(Color.BLACK);
            g.fill3DRect(x,y, getIconWidth(), height, true);
        }

        public int getIconWidth(){
            return 150;
        }
        public int getIconHeight(){
            return height;
        }
    }


    /**
     * Create a new View.
     */

    public ViewPalette(Model model, Controller controller) {
        // Set up the window.
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hook up this observer so that it will be notified when the model
        // changes.

        //this.setPreferredSize(new Dimension(100, 480));
        this.setLayout(new GridLayout(3,1,2,2));

        this.model = model;
        model.addObserver(this);
        red = new JButton();
        red.setActionCommand("red");
        yellow = new JButton();
        yellow.setActionCommand("yellow");
        blue = new JButton();
        blue.setActionCommand("blue");
        pink = new JButton();
        pink.setActionCommand("pink");
        orange = new JButton();
        orange.setActionCommand("orange");
        choosecolor = new JButton();
        choosecolor.setActionCommand("cc");
        current = new JPanel();

        Image newimg = spec.getImage().getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH );

        choosecolor.setIcon(new ImageIcon(newimg));
        current.setBackground(model.getcolor());
        red.setBackground(Color.RED);
        red.setOpaque(true);
        red.setBorderPainted(false);
        yellow.setBackground(Color.YELLOW);
        yellow.setOpaque(true);
        yellow.setBorderPainted(false);
        blue.setBackground(Color.BLUE);
        blue.setOpaque(true);
        blue.setBorderPainted(false);
        pink.setBackground(Color.PINK);
        pink.setOpaque(true);
        pink.setBorderPainted(false);
        orange.setBackground(Color.ORANGE);
        orange.setOpaque(true);
        orange.setBorderPainted(false);
        choosecolor.setBackground(model.getcolor());
        choosecolor.setBorderPainted(false);
        choosecolor.setOpaque(true);
        JPanel palette = new JPanel(new GridLayout(3,2,2,2));
        palette.setBackground(Color.WHITE);

        palette.add(red);
        palette.add(yellow);
        palette.add(blue);
        palette.add(yellow);
        palette.add(blue);
        palette.add(pink);
        palette.add(orange);
        palette.add(choosecolor);

        JPanel strokes = new JPanel(new GridLayout(3,1,2,2));

        skinnystroke = new JButton();
        skinnystroke.setIcon(new Width(5));
        skinnystroke.setActionCommand("skinny");
        strokes.add(skinnystroke);

        medstroke = new JButton();
        medstroke.setIcon(new Width(9));
        medstroke.setActionCommand("med");
        strokes.add(medstroke);

        largestroke= new JButton();
        largestroke.setIcon(new Width(13));
        largestroke.setActionCommand("large");
        strokes.add(largestroke);

        choosecolor.addActionListener(controller);
        red.addActionListener(controller);
        yellow.addActionListener(controller);
        pink.addActionListener(controller);
        blue.addActionListener(controller);
        orange.addActionListener(controller);
        skinnystroke.addActionListener(controller);
        medstroke.addActionListener(controller);
        largestroke.addActionListener(controller);

        this.add(palette);
        this.add(strokes);
        this.add(current);
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }


    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        current.setBackground(model.getcolor());
        Color now = model.getcolor();
        if ((now != Color.RED) && (now != Color.BLUE) && (now != Color.YELLOW) && (now != Color.PINK) &&
                (now != Color.ORANGE)) {
            choosecolor.setForeground(model.getcolor());
            choosecolor.setBackground(model.getcolor());
        }
        this.repaint();
    }
}
