import java.util.*;

public class GBFS {
    private String endWord;
    private PriorityQueue<Node> prioQueue;
    private Set<String> visited;

    public GBFS(String endWord) {
        this.endWord = endWord;
        this.prioQueue = new PriorityQueue<>(Comparator.comparingInt((currentNode) -> {
            String a = currentNode.getWord();
            int missChar = 0;
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) != endWord.charAt(i)) {
                    missChar++;
                }
            }

            return missChar;
        }));
        this.visited = new HashSet<>();
    }

    public App.Pair<Integer, List<String>> search(Node startNode) {
        if (startNode == null) {
            return new App.Pair<>(visited.size(), Collections.emptyList());
        }

        // Add the start word to the prioQueue
        prioQueue.offer(startNode);
        visited.add(startNode.getWord());

        while (!prioQueue.isEmpty()) {
            Node currentNode = prioQueue.poll();
            String currentWord = currentNode.getWord();

            if (currentWord.equals(endWord)) {
                return new App.Pair<>(visited.size() + 1, Arrays.asList(currentNode.get_path_from_root().split(" ")));
            }

            for (String nextWord : App.generateNextWords(currentWord)) {
                if (!visited.contains(nextWord)) {
                    Node nextNode = new Node(nextWord, currentNode);
                    prioQueue.offer(nextNode);
                    visited.add(nextWord);
                }
            }
        }

        return new App.Pair<>(visited.size(), Collections.emptyList());
    }
}