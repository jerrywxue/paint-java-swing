


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Observable;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MainMenuView extends JMenuBar implements Observer {

    private Model model;

    /**
     * Create a new View.
     */
    public MainMenuView(Model model, Controller controller) {
        // Set up the window.

        this.model = model;
        model.addObserver(this);

        JMenu fileMenu = new JMenu("File");

        JMenuItem New = new JMenuItem("New");
        JMenuItem SaveAs = new JMenuItem("Save As");
        JMenuItem Load = new JMenuItem("Load");
        JMenuItem Exit = new JMenuItem("Exit");

        fileMenu.add(New);
        fileMenu.add(SaveAs);
        fileMenu.add(Load);
        fileMenu.add(Exit);

        this.add(fileMenu);

        New.addActionListener(controller);
        SaveAs.addActionListener(controller);
        Load.addActionListener(controller);
        Exit.addActionListener(controller);

        setVisible(true);
    }


    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        this.repaint();
    }

}
