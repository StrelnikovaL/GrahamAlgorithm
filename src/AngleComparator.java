import java.util.Comparator;
public class AngleComparator implements Comparator<Point> {
    Point left;
    int k;
    public AngleComparator(Point left) {
        this.left = left;
    }
    @Override
    public int compare(Point p1, Point p2) {
        k++;
        int result = 0;
        if (p1.equals(left)) { result = -1; }
        else if (p2.equals(left)) { result = 1; }
        else {
            double angleP1 = Math.atan2(p1.getY()-left.getY(), p1.getX()-left.getX());
            double angleP2 = Math.atan2(p2.getY() - left.getY(), p2.getX()- left.getX());
            result =  - Double.compare(angleP1,angleP2);
        }
        return result;
    }
}
