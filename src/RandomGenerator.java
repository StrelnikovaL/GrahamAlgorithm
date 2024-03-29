import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.io.*;

public class RandomGenerator {
    File file;
    public void generate() throws IOException {
        file = new File("Coordinates.txt");
        PrintWriter writer = new PrintWriter(new FileWriter(file));

        Random random = new Random();
        int numSets = random.nextInt(51) + 50;

        writer.println(numSets);

        for (int i = 0; i < numSets; i++) {
            int numElements = random.nextInt(9901) + 100;
            for (int j = 0; j < numElements; j++) {
                double x =(double)((int)((random.nextDouble() * 200 - 100)* 100)) / 100;
                double y = (double)((int)((random.nextDouble() * 200 - 100) * 100)) / 100;
                writer.print(x + "," + y + " ");
            }
            writer.println();
        }
        writer.close();
    }
}

