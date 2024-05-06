import java.util.*;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    public static Set<String> dictionary;

    private JTextField startWordField;
    private JTextField endWordField;
    private JComboBox<String> algorithmComboBox;
    private JTextPane resultPane; // Text area to display ladder information

    public App() {
        setTitle("Word Ladder Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel startLabel = new JLabel("Start Word:");
        startLabel.setFont(startLabel.getFont().deriveFont(13.0f)); // Set font size to 13
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(startLabel, gbc);

        startWordField = new JTextField();
        startWordField.setFont(startWordField.getFont().deriveFont(13.0f)); // Set font size to 13
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2; // Column span for text field
        inputPanel.add(startWordField, gbc);

        JLabel endLabel = new JLabel("End Word:");
        endLabel.setFont(endLabel.getFont().deriveFont(13.0f)); // Set font size to 13
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1; // Reset column span
        inputPanel.add(endLabel, gbc);

        endWordField = new JTextField();
        endWordField.setFont(endWordField.getFont().deriveFont(13.0f)); // Set font size to 13
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2; // Column span for text field
        inputPanel.add(endWordField, gbc);

        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setFont(algorithmLabel.getFont().deriveFont(13.0f)); // Set font size to 13
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1; // Reset column span
        inputPanel.add(algorithmLabel, gbc);

        String[] algorithms = {"Uniform Cost Search (UCS)", "Greedy Best First Search (GBFS)", "A* Search"};
        algorithmComboBox = new JComboBox<>(algorithms);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2; // Column span for combo box
        inputPanel.add(algorithmComboBox, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; // Column span for search button
        inputPanel.add(searchButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        resultPane = new JTextPane(); // Initialize the text area
        resultPane.setContentType("text/html"); // Set content type to HTML
        resultPane.setText("<style> body { text-align: center; font-size: 16pt; } </style>"); // Set font size to 16pt
        JScrollPane scrollPane = new JScrollPane(resultPane); // Add scroll pane to handle large text
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startWord = startWordField.getText().toUpperCase();
                String endWord = endWordField.getText().toUpperCase();
                String searchAlgorithm = (String) algorithmComboBox.getSelectedItem();

                // Get dictionary words with same length from the file and store them in a set
                loadDictionary("src/dictionary.txt", startWord);

                // Perform the search algorithm and update the result area
                performSearch(startWord, endWord, searchAlgorithm);
            }
        });
    }

    private void performSearch(String startWord, String endWord, String searchAlgorithm) {
        StringBuilder result = new StringBuilder();

        // Check for valid input and dictionary initialization
        if (!isValidInput(startWord, endWord)) {
            resultPane.setText("Invalid input or dictionary not initialized.");
            return;
        }

        // Check if the start word and the end word are in the dictionary
        if (!dictionary.contains(startWord) || !dictionary.contains(endWord)) {
            resultPane.setText("Start word or end word not in the dictionary.");
            return;
        }

        long startTime = System.currentTimeMillis();
        long endTime;
        long executionTime;

        App.Pair<Integer, List<String>> wordLadder = null;

        switch (searchAlgorithm) {
            case "Uniform Cost Search (UCS)":
                UCS ucs = new UCS(endWord);
                ExtendedNode startUCSNode = new ExtendedNode(startWord, null, 0);
                wordLadder = ucs.search(startUCSNode);

                break;
            case "Greedy Best First Search (GBFS)":
                GBFS gbfs = new GBFS(endWord);
                Node startGFBSNode = new Node(startWord, null);
                wordLadder = gbfs.search(startGFBSNode);

                break;
            case "A* Search":
                AStar astar = new AStar(endWord);
                ExtendedNode startAStarNode = new ExtendedNode(startWord, null, 0);
                wordLadder = astar.search(startAStarNode);
                
                break;
            // default:
            //     result.append("Invalid search algorithm.");
            //     break;
        }

        if (!wordLadder.getSecond().isEmpty()){
            int size = wordLadder.getSecond().size();
            for (int i = 0; i < size; i++) {
                String word = wordLadder.getSecond().get(i);
                if (i == size - 1) {
                    result.append("<font color='green'>[" + i + "] " + word + "</font>");
                }
                else if (i == 0) {
                    result.append("<font color='blue'>[" + i + "] " + word + "</font>");
                }
                else {
                    result.append(word);
                }
                result.append("<br>");
            }
        }
        else{
            result.append("No ladder found.");
        }

        endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
        result.append("<br>" + wordLadder.getFirst() + " node has been visited in " + executionTime + " ms.");

        resultPane.setText(result.toString());
    }

    private boolean isValidInput(String startWord, String endWord) {
        if (!startWord.matches("[A-Z]+") || !endWord.matches("[A-Z]+")) {
            return false; // Alphabetic check failed
        }

        if (dictionary == null) {
            return false; // Dictionary not initialized
        }

        if (startWord.length() != endWord.length()) {
            return false; // Word length mismatch
        }

        return true;
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
        } 
        catch (FileNotFoundException e) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App(); // Create an instance of the application
            app.setVisible(true);
        });
    }


    public static List<String> getNeighbor(String word) {
        List<String> nextWords = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] chars = word.toCharArray();
            for (char c = 'A'; c <= 'Z'; c++) {
                chars[i] = c;
                String newWord = new String(chars);
                if (!newWord.equals(word) && dictionary.contains(newWord)) {
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
