import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable {
    ArrayList<Path> paths;
    private int playback;
    Save( ArrayList<Path> paths, int playback ){
        this.paths = paths;
        this.playback = playback;
    }

    public ArrayList<Path> get(){
        return paths;
    }

    public int getplayback(){
        return playback;
    }

}
