public class Node {
    protected String word;
    protected Node parent;

    public Node(String word, Node parent){
        this.word = word;
        this.parent = parent;
    }

    public String getWord(){
        return this.word;
    }

    public Node getParent(){
        return this.parent;
    }

    public String get_path_from_root(){
        if (this.parent == null){
            return this.word;
        }
        
        return this.parent.get_path_from_root() + " " + this.word;
    }
}