
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Model{
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private boolean isPlay = false;
    private float xs = 1;
    private float xy = 1;
    private float strokeThickness = 5.0f;
    private Color colour = Color.BLACK;
    private int playback = 100;
    private ArrayList<Path> paths = new ArrayList();


    public void scalar(float x, float y){
        xs = x;
        xy = y;
        notifyObservers();
    }

    public float getxs(){
        return xs;
    }

    public float getxy(){
        return xy;
    }

    public void setPlay(boolean play){
        isPlay=play;
    }

    public boolean getPlay(){
        return isPlay;
    }

    public int getpathsize(){
        return paths.size();
    }

    public void clear(){
        playback = 100;
        colour = Color.BLACK;
        paths = new ArrayList();
        strokeThickness = 5.0f;
    }

    public void checkpath(){
        if(paths.get(paths.size()-1).getsize() == 0 || paths.get(paths.size()-1).getsize() == 1  ){
            paths.remove(paths.size()-1);
        }
    }

    public void addpath(Path path){
        paths.add(path);
    }

    public void deletepath(){
        int pathsize;
        if (getpathsize()!= 0) {
            pathsize = 100 / getpathsize();
        } else {
            pathsize = 0;
        }
        int slider = getplayback();
        int completelines;
        float halfline;

        if (pathsize != 0){
            completelines = slider / pathsize;
            halfline = slider % pathsize;
        } else {
            completelines = 0;
            halfline = 0;
        }

        if(pathsize !=0 && slider != 100){
            int percentofinterval = Math.round(halfline/pathsize * 100);
            int n = (int) (getpaths().get(completelines).getsize()*(percentofinterval/100.0f));
            if (n == 1 || n ==0) {
                completelines--;
            } else {
                getpaths().get(completelines).deletehalf(n);
            }
        }
        for (int i = getpathsize()-1; i > completelines; i--){
            getpaths().remove(i);
        }
    }

    public ArrayList<Path> getpaths(){
        return paths;
    }

    public float getstrokethickness(){
        return strokeThickness;
    }
    public void changethickness(float s){
        strokeThickness = s;
    }

    public Color getcolor(){
        return colour;
    }

    public void changecolor(Color c){
        colour = c;
    }

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public void updateplayback(int n){
        playback = n;
    }

    public int getplayback(){
        return playback;
    }

    public void set(ArrayList<Path> savedpaths){
        clear();
        paths = savedpaths;
    }




}
