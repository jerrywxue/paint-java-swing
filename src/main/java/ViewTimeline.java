

import java.io.Serializable;
import java.util.ArrayList;
import java.awt.event.ActionEvent;


import javax.swing.*;
import java.awt.*;

public class ViewTimeline extends JPanel implements Observer {

    private Model model;
    private JSlider slider;
    private JButton playButton;
    private JButton startButton;
    private JButton endButton;
    private JButton reverseButton;
    private boolean p;
    ImageIcon pause = new ImageIcon("buttons/png/pause.png");
    ImageIcon play = new ImageIcon("buttons/png/play-button.png");
    ImageIcon back = new ImageIcon("buttons/png/back.png");
    ImageIcon next = new ImageIcon("buttons/png/next.png");
    ImageIcon replay = new ImageIcon("buttons/png/rewind.png");


    /**
     * Create a new View.
     */
    public ViewTimeline(Model model, Controller controller) {
        // Set up the window.
        this.model = model;
        model.addObserver(this);
        p = model.getPlay();
        slider = new JSlider(0,100,100);
        slider.setName("playList");
        playButton = new JButton();
        playButton.setActionCommand("play");
        reverseButton= new JButton();
        reverseButton.setActionCommand("reverse");
        startButton= new JButton();
        startButton.setActionCommand("start");
        endButton= new JButton();
        endButton.setActionCommand("end");
        playButton.setEnabled(false);
        reverseButton.setEnabled(false);
        endButton.setEnabled(false);
        startButton.setEnabled(false);

        Image newimg = play.getImage().getScaledInstance( 35, 35,  java.awt.Image.SCALE_SMOOTH );
        playButton.setIcon(new ImageIcon(newimg));

        Image newimg2 = replay.getImage().getScaledInstance( 35, 35,  java.awt.Image.SCALE_SMOOTH );
        reverseButton.setIcon(new ImageIcon(newimg2));

        Image newimg3 = back.getImage().getScaledInstance( 35, 35,  java.awt.Image.SCALE_SMOOTH );
        startButton.setIcon(new ImageIcon(newimg3));

        Image newimg4 = next.getImage().getScaledInstance( 35, 35,  java.awt.Image.SCALE_SMOOTH );
        endButton.setIcon(new ImageIcon(newimg4));

        this.setPreferredSize(new Dimension(400, 50));

        // Hook up this observer so that it will be notified when the model
        // changes.

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(slider);
        this.add(playButton);
        this.add(reverseButton);
        this.add(startButton);
        this.add(endButton);
        slider.addChangeListener(controller);
        playButton.addActionListener(controller);
        startButton.addActionListener(controller);
        endButton.addActionListener(controller);
        reverseButton.addActionListener(controller);
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
        if(model.getpathsize() != 0){
            slider.setMajorTickSpacing(100/model.getpathsize());
            slider.setPaintTicks(true);
        }
        if(p != model.getPlay()){
            p = model.getPlay();
            if(model.getPlay()){
                Image newimg = pause.getImage().getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );
                playButton.setIcon(new ImageIcon(newimg));
            } else {
                Image newimg = play.getImage().getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );
                playButton.setIcon(new ImageIcon(newimg));
            }
        }

        if(model.getpathsize() == 0){
            playButton.setEnabled(false);
            reverseButton.setEnabled(false);
            endButton.setEnabled(false);
            startButton.setEnabled(false);
        } else {
            slider.setEnabled(true);
        }

        if (model.getplayback() == 100){
            endButton.setEnabled(false);
            playButton.setEnabled(false);
        } else {
            endButton.setEnabled(true);
            playButton.setEnabled(true);
        }

        if (model.getplayback() == 0){
            startButton.setEnabled(false);
            reverseButton.setEnabled(false);
        } else {
            startButton.setEnabled(true);
            reverseButton.setEnabled(true);
        }
        slider.setValue(model.getplayback());
        this.repaint();
    }
}
