import javax.swing.*;
import java.awt.*;
import java.io.Serializable;


public class Main implements Serializable {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model,controller);
        ViewPalette palette = new ViewPalette(model,controller);
        ViewTimeline timeline = new ViewTimeline(model,controller);

        MainMenuView menuView = new MainMenuView(model,controller);

        model.addObserver(view);
        model.addObserver(palette);
        model.addObserver(timeline);
        JFrame frame = new JFrame("Paint");
        JPanel p = new JPanel(new BorderLayout());
        frame.setJMenuBar(menuView);
        frame.getContentPane().add(p);
        p.add(view, BorderLayout.CENTER);
        p.add(palette, BorderLayout.WEST);
        p.add(timeline, BorderLayout.SOUTH);

        frame.setMinimumSize(new Dimension(480,420));
        frame.setSize(new Dimension(480,420));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
