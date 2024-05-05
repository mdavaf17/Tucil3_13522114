import java.util.*;

public class AStar {
    private String endWord;
    private PriorityQueue<ExtendedNode> frontier;
    private Set<String> visited;

    public AStar(String endWord) {
        this.endWord = endWord;
        this.frontier = new PriorityQueue<>(Comparator.comparingInt(ExtendedNode::get_distance));
        this.visited = new HashSet<>();
    }

    private int greedySwitchChar(ExtendedNode lastNode) {
        String current = lastNode.getWord();
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

    public App.Pair<Integer, List<String>> search(ExtendedNode startNode) {
        if (startNode == null) {
            return new App.Pair<>(visited.size(), Collections.emptyList());
        }
    
        frontier.offer(startNode);
    
        while (!frontier.isEmpty()) {
            ExtendedNode currentNode = frontier.poll();
    
            if (currentNode.getWord().equals(endWord)) {
                return new App.Pair<>(visited.size()+1, Arrays.asList(currentNode.get_path_from_root().split(" ")));
            }
    
            visited.add(currentNode.getWord());
    
            for (String nextWord : App.generateNextWords(currentNode.getWord())) {
                if (!visited.contains(nextWord)) {
                    int newCost = currentNode.get_distance() + 1;
                    ExtendedNode nextNode = new ExtendedNode(nextWord, currentNode, newCost);
                    frontier.offer(nextNode);
                }
            }
        }

        return new App.Pair<>(visited.size(), Collections.emptyList());
    }
}