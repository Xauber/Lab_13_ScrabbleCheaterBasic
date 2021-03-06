import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class Dictionary {
    public Vertex [] hashtable;


    public Dictionary(int size) {
        hashtable = new Vertex[size];
    }

    // Vertex class to later access all the collisions and permutations chained --> like a linked list
    public class Vertex {
        String data;
        Vertex previous;
        Vertex next;

        public Vertex(String data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    // normalizes the word & sorts it aswell, so that every permutation will later have the same hashcode
    public String normalize(String word) {
        String result = "";
        word = word.toLowerCase();
        char[] splittedWord = word.toCharArray();
        Arrays.sort(splittedWord);

        result = new String(splittedWord);
        //System.out.println(result);
        //System.out.println(result);
        return result;

    }

    // method to generate a hashcode for a word / reused the code from the lecture
    public long generateHashCode(String s) {
        long hashCode = 0;
        for (int i = 0; i < s.length(); i++) {
            //System.out.println(i);
            hashCode = 31 * hashCode + s.charAt(i);
            hashCode = Math.abs(hashCode);
        }
        //System.out.println(hashCode);
        return hashCode;
    }

    // method to search for a word in the dict and get the chain of words that are permutations or just are collisions,
    // having the same hashcode
    public String getObject(String word) {
        long hashCode = generateHashCode(word);
        ArrayList<String> output = new ArrayList<>();
        int position = (int) (generateHashCode(word) % hashtable.length);

        if (hashtable[position] != null) {
            Vertex temp = hashtable[position];
            if (temp.next == null) {
                output.add(temp.data);
                while (temp != null) {
                    output.add(temp.data);
                    temp = temp.next;
                }
            }
        }
        String collisionChain = output.toString();
        return collisionChain;

    }


    // method to add a word to the hashtable based on its hashcode, if there are too many collisions the hashtable resizes
    public void addObject(String word) {
        int position = (int) (generateHashCode(word) % hashtable.length);
        int chainCounter = 0;
        Vertex newVert = new Vertex(word);
        try {
            if (hashtable[position] == null)
            {
                hashtable[position] = newVert;
            }
            else {
                Vertex currentVert = hashtable[position];
                chainCounter++;
                while (currentVert.next != null) {
                    currentVert = currentVert.next;
                    chainCounter++;
                }
                currentVert.next = newVert;
                newVert.previous = currentVert;
                //System.out.println(newVert.data);
                //System.out.println(currentVert.data);
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            //System.out.println(position);
        }

        if (chainCounter >= 16) {
            //System.out.println("Maximum number of collisions reached");
            resizeHashTable();

        }
        //System.out.println(chainCounter);
    }


    // method to get number of entries of the dictionary should normally be equal to the count I implemented for the WordParser
    // therefore this is not really needed, but I realized too late that the counter inside WordParser is much easier,
    // and would have saved some time
    public int getTableEntries() {
        int numberOfEntries = 0;

        for (int i = 0; i < hashtable.length; i++) {
            if (hashtable[i] != null) {
                Vertex vert = hashtable[i];
                while (vert != null) {
                    vert = vert.next;
                    numberOfEntries++;
                }
            }
        }
        return numberOfEntries;
    }

    //method to get the longest collision chain of the dictionary
    public String getlongestChain() {
        int longestChain = 0;
        ArrayList<String> longestChainArray = new ArrayList<>();
        for (int i = 0; i < hashtable.length; i++) {
            int tempCount = 0;
            ArrayList<String> tempChainArray = new ArrayList<>();
            for (Vertex element : hashtable) {
                Vertex v = hashtable[i];

                while (v != null) {
                    tempChainArray.add(v.data);
                    v = v.next;
                    tempCount++;
                }
            }
            if (tempCount > longestChain) {

                longestChain = tempCount;
            }
            if (tempChainArray.size() > longestChainArray.size()) {
                longestChainArray = tempChainArray;
            }

        }
        String output = longestChain + longestChainArray.toString();
        return output;
    }

    // method to double the size of the hashtable
    private void resizeHashTable() {
        Vertex[] neu = new Vertex[hashtable.length*2];
        Vertex[] alt = hashtable;
        hashtable = neu;
        //for(int i = 0; i < alt.length; i++) {
        for(Vertex element : alt){
            Vertex x = element;
            //Vertex x = alt[i];
            //while (x != null) {
            while (x != null) {
                addObject(element.data);
                x = x.next;}
        }
    }
}
