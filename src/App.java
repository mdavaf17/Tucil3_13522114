import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class App {
    public static Set<String> dictionary;

    public static void main(String[] args) {
        while (true) {
            System.out.print("Input the Start Word: ");
            String startWord = System.console().readLine().toUpperCase();

            System.out.print("Input the End Word: ");
            String endWord = System.console().readLine().toUpperCase();

            System.out.print("Input the Search Algorithm (UCS / GBF / A*): ");
            String searchAlgorithm = System.console().readLine().toUpperCase();

            // Check if the start word and the end word are alphabetic
            if (!startWord.matches("[A-Z]+") || !endWord.matches("[A-Z]+")) {
                System.out.println("The start word and the end word must be alphabetic.");
                return;
            }

            // Check if the start word and the end word length are different
            if (startWord.length() != endWord.length()) {
                System.out.println("The length of the start word and the end word must be the same.");
                return;
            }

            // Check if the start word and the end word are already the same
            // if (startWord.equals(endWord)) {
            //     System.out.println("The start word and the end word are already the same.");
            //     return;
            // }

            // Get dictionary words with same length from the file and store them in a set
            loadDictionary("src/dictionary.txt", startWord);

            // Check if the start word and the end word are in the dictionary
            if (!dictionary.contains(startWord) || !dictionary.contains(endWord)) {
                System.out.println("The start word or the end word is not in the dictionary.");
                return;
            }

            long startTime = System.currentTimeMillis();
            long endTime;
            long executionTime;
            Pair<Integer, List<String>> wordLadder;
            switch (searchAlgorithm) {
                case "UCS":
                    UCS ucs = new UCS(endWord);

                    // Create the starting node
                    ExtendedNode startUCSNode = new ExtendedNode(startWord, null, 0);
            
                    // Perform the UCS search
                    wordLadder = ucs.search(startUCSNode);

                    if (!wordLadder.second.isEmpty()) {
                        System.out.println("Explored node: " + wordLadder.first);
                        System.out.println("UCS Ladder: " + wordLadder.second);
                    }
                    else {
                        System.out.println("No ladder found using UCS.");
                    }

                    break;
                case "GBF":
                    GBFS gbfs = new GBFS(endWord);

                    Node startGFBSNode = new Node(startWord, null);
            
                    // Perform the UCS search
                    wordLadder = gbfs.search(startGFBSNode);

                    if (!wordLadder.second.isEmpty()) {
                        System.out.println("Explored node: " + wordLadder.first);
                        System.out.println("UCS Ladder: " + wordLadder.second);
                    }
                    else {
                        System.out.println("No ladder found using UCS.");
                    }

                    break;
                case "A*":
                    AStar astar = new AStar(endWord);

                    // Create the starting node
                    ExtendedNode startAStarNode = new ExtendedNode(startWord, null, 0);
            
                    // Perform the UCS search
                    wordLadder = astar.search(startAStarNode);

                    if (!wordLadder.second.isEmpty()) {
                        System.out.println("Explored node: " + wordLadder.first);
                        System.out.println("UCS Ladder: " + wordLadder.second);
                    }
                    else {
                        System.out.println("No ladder found using UCS.");
                    }
                    break;
                default:
                    System.out.println("Invalid search algorithm.");
                    break;
            }
            endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            System.out.println("Execution time: " + executionTime + " milliseconds\n");
        }
    }

    private static void loadDictionary(String filePath, String startWord) {
        dictionary = new HashSet<>();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                if (word.length() == startWord.length()) {
                    dictionary.add(word.toUpperCase());
                }
            }
            scanner.close();

            // Check if the dictionary is empty
            if (dictionary.isEmpty()) {
                System.out.println("The dictionary is empty.");
                return;
            }
            else{
                System.out.println("Dictionary length: " + dictionary.size());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static List<String> generateNextWords(String word) {
        List<String> nextWords = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] chars = word.toCharArray();
            for (char c = 'A'; c <= 'Z'; c++) {
                chars[i] = c;
                String newWord = new String(chars);
                if (!newWord.equals(word) && App.dictionary.contains(newWord)) {
                    nextWords.add(newWord);
                }
            }
        }

        return nextWords;
    }

    public static class Pair<T, U> {
        private final T first;
        private final U second;
    
        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    
        public T getFirst() {
            return first;
        }
    
        public U getSecond() {
            return second;
        }
    }
}