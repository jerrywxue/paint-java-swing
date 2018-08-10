import java.io.Serializable;
import java.util.ArrayList;
import java.awt.*;
import javax.vecmath.*;


public class Path implements Serializable{
    ArrayList<Point2d> points = new ArrayList<Point2d>();
    Boolean pointsChanged = false; // dirty bit
    int[] xpoints, ypoints;
    int npoints = 0;
    Color colour;
    float strokeThickness;


    public Path(Color c, float stroke) {
        colour = c;
        strokeThickness = stroke;
    }

    int getsize(){
        return points.size();
    }

    void cachePointsArray() {
        xpoints = new int[points.size()];
        ypoints = new int[points.size()];
        for (int i=0; i < points.size(); i++) {
            xpoints[i] = (int)points.get(i).x;
            ypoints[i] = (int)points.get(i).y;
        }
        npoints = points.size();
        pointsChanged = false;
    }

    public void clearPoints() {
        points = new ArrayList<Point2d>();
        pointsChanged = true;
    }

    public void addPoint(Point2d p) {
        if (points == null) clearPoints();
        points.add(p);
        pointsChanged = true;
    }

    public void addPoint(double x, double y){
        if (points == null) clearPoints();
        addPoint(new Point2d(x, y));
    }

    public void deletehalf(int points_amount){
        ArrayList<Point2d> temp = points;
        points = new ArrayList<Point2d>();
        for (int i=0; i < points_amount; i++) {
            points.add(temp.get(i));
        }
        pointsChanged = true;
    }

    public void draw(Graphics2D g2, int points_amount) {

        // don't draw if points are empty (not shape)
        if (points == null) return;

        // see if we need to update the cache
        if (pointsChanged) cachePointsArray();

        g2.setColor(colour);
        g2.setStroke(new BasicStroke(strokeThickness));

        if (points_amount == -1) {
            g2.drawPolyline(xpoints, ypoints, npoints);
        } else { //you dont have enough points
            xpoints = new int[points_amount];
            ypoints = new int[points_amount];
            for (int i = 0; i < points_amount; i++) {
                xpoints[i] = (int)points.get(i).x;
                ypoints[i] = (int)points.get(i).y;
            }
            npoints = points_amount;
            g2.drawPolyline(xpoints, ypoints, npoints);
            pointsChanged = true;
        }
    }
}