public class ExtendedNode extends Node{
    private int distance;
    
    public ExtendedNode(String word, Node parent, int distance){
        super(word, parent);
        this.distance = distance;
    }
    
    public int get_distance(){
        return this.distance;
    }
    
    @Override
    public String get_path_from_root(){
        if (this.parent == null){
            return this.word;
        }
        
        return this.parent.get_path_from_root() + " " + this.word;
    }
}