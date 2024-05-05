import java.util.*;

public class GBFS {
    private String endWord;
    private PriorityQueue<ArrayList<String>> prioQueue;
    private Set<String> visited;

    public GBFS(String endWord) {
        this.endWord = endWord;
        this.prioQueue = new PriorityQueue<>(Comparator.comparingInt(this::greedySwitchChar));
        this.visited = new HashSet<>();
    }

    private int greedySwitchChar(ArrayList<String> path) {
        String current = path.get(path.size() - 1);
        return hamming(current);
    }

    private int hamming(String a) {
        int distance = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != endWord.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    public App.Pair<Integer, List<String>> search(String startWord) {
        if (startWord == null) {
            return new App.Pair<>(visited.size(), Collections.emptyList());
        }

        // Add the start word to the prioQueue
        prioQueue.add(new ArrayList<>(Arrays.asList(startWord)));
        visited.add(startWord);

        while (!prioQueue.isEmpty()) {
            ArrayList<String> currentPath = prioQueue.poll();
            String currentWord = currentPath.get(currentPath.size() - 1);

            if (currentWord.equals(endWord)) {
                return new App.Pair<>(visited.size() + 1, currentPath);
            }

            for (String nextWord : App.generateNextWords(currentWord)) {
                if (!visited.contains(nextWord)) {
                    ArrayList<String> nextPath = new ArrayList<>(currentPath);
                    nextPath.add(nextWord);
                    prioQueue.add(nextPath);
                    visited.add(nextWord);
                }
            }
        }

        return new App.Pair<>(visited.size(), Collections.emptyList());
    }
}