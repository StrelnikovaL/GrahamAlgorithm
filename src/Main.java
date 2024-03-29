import java.io.*;
import java.util.*;

public class Main {
    static int k = 0;
    public static int minByXY(List<Point> points) {
        int min = 0;
        Point minPoint = points.get(min);
        double e = 1e-10;
        for (int i = 1; i < points.size(); i++) {
            k++;
            Point current = points.get(i);
            if (current.getX() - minPoint.getX() < 0.0 &&
                    (Math.abs(current.getX() - minPoint.getX())) > e ||
                    (Math.abs(current.getX() - minPoint.getX()) < e &&
                            current.getY() - minPoint.getY() < 0)) {
                min = i;
                minPoint = current;
            }
        }
        return min;
    }

    public static void sortByAngle(List<Point> points) {
        int left = minByXY(points);
        AngleComparator comparator = new AngleComparator(points.get(left));
        points.sort(comparator);
        k += comparator.k;
    }

    public static boolean rightTurn(Point p1, Point p2, Point p3) {
        Point top2 = new Point(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        Point top3 = new Point(p3.getX() - p1.getX(), p3.getY() - p1.getY());
        return (0.5 * ((top3.getX() * top2.getY()) - (top2.getX() * top3.getY()))) >= 0;
    }

    public static LinkedList<Point>[] input(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int numSets = Integer.parseInt(reader.readLine());
        LinkedList<Point>[] sets = new LinkedList[numSets];
        for (int i = 0; i < numSets; i++) {
            LinkedList<Point> points = new LinkedList();
            String[] element = reader.readLine().split(" ");
            for (String coordinate: element) {
                String[] coordinates = coordinate.split(",");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                points.add(new Point(x, y));
                sets[i] = points;
            }
        }
        return sets;
    }

    public static void main(String[] args) throws IOException {
        LinkedList<Point>[] sets = input(new File("Coordinates.txt"));
        File file = new File("Time.txt");
        File file2 = new File("Iterations.txt");
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        PrintWriter writer2 = new PrintWriter(new FileWriter(file2));
        for (int i = 0; i < sets.length; i++) {
            k = 0;
            long time = 0;
            long time_finish = 0;
            LinkedList<Point> points = sets[i];
            long time_start = System.nanoTime();
            sortByAngle(points);
            Deque<Point> convexHull = new LinkedList();
            if (points.size() == 1) {
                convexHull.add(points.get(0));
                time_finish = System.nanoTime();
                time = time_finish - time_start;
                System.out.println("The convex hull is a point");
            } else if (points.size() == 2) {
                time_finish = System.nanoTime();
                time = time_finish - time_start;
                System.out.println("Two points cannot form a convex hull");
            } else {
                convexHull.push(points.get(0));
                convexHull.push(points.get(1));
                convexHull.push(points.get(2));
                for (int j = 3; j < points.size(); j++) {
                    k++;
                    boolean stop = false;
                    while (!stop) {
                        k++;
                        Point last = convexHull.pop();
                        Point beforeLast = convexHull.peek();
                        Point newPoint = points.get(i);
                        if (rightTurn(beforeLast, last, newPoint)) {
                            convexHull.push(last);
                            convexHull.push(newPoint);
                            stop = true;
                        }
                    }
                }
                time_finish = System.nanoTime();
                time = time_finish - time_start;
                System.out.println("The convex hull insists of next points:");
            }
            while (convexHull.peek() != null) {
                convexHull.pop().logOut();
            }
            System.out.println("Number of elements: " + points.size());
            System.out.println("Time: "+ time);
            System.out.println("Number of iterations "+ k);
            writer.println (points.size() + ";" + time);
            writer2.println (points.size() + ";" + k);
        }
        writer.close();
        writer2.close();
    }
}