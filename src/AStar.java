import java.util.*;

public class AStar {
    private String endWord;
    private PriorityQueue<ExtendedNode> prioQueue;
    private Set<String> visited;

    public AStar(String endWord) {
        this.endWord = endWord;
        this.prioQueue = new PriorityQueue<>(Comparator.comparingInt((currentNode) -> {
            int missChar = 0;
            String word = currentNode.getWord();
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) != endWord.charAt(i)) {
                    missChar++;
                }
            }
            
            return missChar + currentNode.get_distance();
        }));
        this.visited = new HashSet<>();
    }

    public App.Pair<Integer, List<String>> search(ExtendedNode startNode) {
        if (startNode == null) {
            return new App.Pair<>(visited.size(), Collections.emptyList());
        }
    
        prioQueue.offer(startNode);
    
        while (!prioQueue.isEmpty()) {
            ExtendedNode currentNode = prioQueue.poll();
    
            if (currentNode.getWord().equals(endWord)) {
                return new App.Pair<>(visited.size()+1, Arrays.asList(currentNode.get_path_from_root().split(" ")));
            }
    
            visited.add(currentNode.getWord());
    
            for (String nextWord : App.getNeighbor(currentNode.getWord())) {
                if (!visited.contains(nextWord)) {
                    int newCost = currentNode.get_distance() + 1;
                    ExtendedNode nextNode = new ExtendedNode(nextWord, currentNode, newCost);
                    prioQueue.offer(nextNode);
                }
            }
        }

        return new App.Pair<>(visited.size(), Collections.emptyList());
    }
}