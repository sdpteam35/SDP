package circuitryapp.components;
import java.util.*;

public class Node extends Component{
    ArrayList<Component> incomingParts;
    ArrayList<Component> outgoingParts;
    public Node(String id){
        super(id, ComponentType.Node);
        incomingParts = new ArrayList<Component>();
        outgoingParts = new ArrayList<Component>();
    }
    public ArrayList<Component> getInParts(){
        return incomingParts;
    }
    public ArrayList<Component> getOutParts(){
        return outgoingParts;
    }
}
