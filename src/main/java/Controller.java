import java.awt.event.*;
import java.io.*;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Controller extends MouseAdapter implements ChangeListener, ActionListener {
     Model model;
     Path  path;
     Timer timer;
     Boolean forward = true;

    Controller(Model model){
        this.model= model;
        timer = new Timer(2, this);
        timer.setActionCommand("time");
    }

    public void mousePressed(MouseEvent e){
        if(model.getplayback() == 0 ){
            model.clear();
        }
        if(model.getplayback() != 0){
            //your are overwritting stuff now
            model.deletepath();
            model.updateplayback(100);
        }
        path = new Path(model.getcolor(), model.getstrokethickness());
        model.addpath(path);
        model.notifyObservers();

    }

    public int savenow(ActionEvent actionEvent){
        return JOptionPane.showConfirmDialog((Component) actionEvent.getSource(), "Do you to save your masterpiece?", "Save",
                JOptionPane.YES_NO_OPTION);
    }

    //buttons
    public void actionPerformed(ActionEvent actionEvent){
        String buttontype = actionEvent.getActionCommand();
        if(buttontype.equals("red")){
            model.changecolor(Color.RED);
        } else if (buttontype.equals("blue")){
            model.changecolor(Color.BLUE);
        } else if (buttontype.equals("yellow")){
            model.changecolor(Color.YELLOW);
        } else if (buttontype.equals("pink")){
            model.changecolor(Color.PINK);
        } else if (buttontype.equals("orange")){
            model.changecolor(Color.ORANGE);
        } else if (buttontype.equals("cc")){
            model.changecolor(JColorChooser.showDialog(null, "JColorChooser", Color.BLACK));
        } else if(buttontype.equals("play")) {
            if (model.getPlay()){
                timer.stop();
                model.setPlay(false);
            } else {
                forward= true;
                timer.start();
                model.setPlay(true);
            }
        } else if(buttontype.equals("time")){
            if(forward){
                int value = model.getplayback() + 1;
                if (value > 100) {
                    timer.stop();
                    model.setPlay(false);
                } else {
                    model.updateplayback(value);

                }
            } else {
                int value = model.getplayback() - 1;
                if (value <  0) {
                    timer.stop();
                    model.setPlay(false);
                } else {
                    model.updateplayback(value);
                }
            }
        } else if(buttontype.equals("start")){
            model.updateplayback(0);
        } else if(buttontype.equals("end")){
            model.updateplayback(100);
        } else if(buttontype.equals("reverse")){
            model.setPlay(true);
            forward= false;
            timer.start();
        } else if (buttontype.equals("New")){ //
            if (savenow(actionEvent) == 1){
                model.clear();
            } else {
                save();
            }
        } else if (buttontype.equals("Load")){
            if (savenow(actionEvent) == 1){
                load();
            } else {
                save();
            }
        } else if (buttontype.equals("skinny")){
            model.changethickness(5);
        } else if (buttontype.equals("med")){
            model.changethickness(9);
        } else if (buttontype.equals("large")){
            model.changethickness(13);
        } else if (buttontype.equals("Save As")){
            save();
        } else if (buttontype.equals("Exit")){
            if (savenow(actionEvent) == 1){
            } else {
                save();
            }
            System.exit(0);
        }
        model.notifyObservers();
    }

    public void mouseReleased(MouseEvent e){
        model.checkpath();
    }

    public void mouseClicked(MouseEvent e){
        if (e.getSource() instanceof ViewTimeline) {
            JButton b = (JButton) e.getSource();
            if(b.getName() == "play"){
                forward= true;
                timer.start();
            }
        }
    }

    public void mouseDragged(MouseEvent e){
        path.addPoint(e.getX()/model.getxs(),e.getY()/model.getxy());
        model.notifyObservers();

    }

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        model.updateplayback(slider.getValue());
        model.notifyObservers();
    }

    public void save(){
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Doodle (.doods)", "doods"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Binary (.bin)", "bin"));

        int ret = fc.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                FileNameExtensionFilter f = (FileNameExtensionFilter) fc.getFileFilter();
                File filenew;
                if (f.getExtensions()[0].equals("bin")) {
                    filenew = new File(file.toString()+".bin");
                } else {
                    filenew = new File(file.toString()+".doods");
                }
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filenew));
                Save s = new Save(model.getpaths(), model.getplayback());
                os.writeObject(s);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        Save mo = null;
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Doodle", "doods"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Binary", "bin"));
        int ret = fc.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                mo = (Save) is.readObject();
                model.set(mo.get());
                model.updateplayback(mo.getplayback());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
