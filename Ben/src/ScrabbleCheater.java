import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

public class ScrabbleCheater {
        public static void main(String[] args) throws IOException {
            WordParser parser = new WordParser();
            String filepath = "/Users/benedictlippold/Desktop/Lab13/Collins Scrabble Words (2019).txt";
            //String filepath = "/Users/benedictlippold/Desktop/Lab13/Test Dict.txt";

            HashSet<String> words = parser.parseWords(filepath);
            Dictionary dictionary = new Dictionary(parser.getCounter());
            for (String name : words) {
                dictionary.addObject(dictionary.normalize(name));
            }
            //System.out.println(dictionary.generateHashCode("Hallo"));
            System.out.println(dictionary.getObject("Hallo"));
            //System.out.println(dictionary.getlongestChain());

            //System.out.println(dictionary.getlongestChain());
            //System.out.println(parser.getCounter());
        }
}
