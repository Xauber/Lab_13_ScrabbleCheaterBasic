import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class WordParser {

    int counter = 0;
    public WordParser() {
    }

    public HashSet<String> parseWords(String path) throws FileNotFoundException, IOException{

        HashSet<String> words = new HashSet<String>();
        File file = new File(path);
        Scanner scanner = new Scanner(file);


        while (scanner.hasNextLine()){
            words.add(scanner.nextLine());
            counter++;
        }
        scanner.close();
        return words;
    }

    public int getCounter() {
        return counter;
    }
}
