package circuitryapp.components;
import java.util.*;

public class Node extends Component{
    ArrayList<Component> incomingParts;
    ArrayList<Component> outgoingParts;

    private double incomingVoltage;
    private double incomingResistance;
    private double outgoingVoltage; //This requires the total resistance from the circuit

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

    public double getNodeVoltage(){
        return outgoingVoltage;
    }

    public double getNodeResistnace(){
        return incomingResistance;
    }

    //private boolean CheckIfNode(Component C){
    //    ComponentType CType = C.getType();
    //    if (CType == ComponentType.Node) {return false;} 
    //    else {return true;}
    //}

    public void UpdateNode(){
        ArrayList<Double> resistanceTerms = new ArrayList<Double>();
        ArrayList<Double> voltageTerms = new ArrayList<Double>();

        for (int i = 0; i < incomingParts.size(); i++){ //The main loop, this calculates incoming voltage and resistance
            double Res = 0;
            double V = 0;
            Component currentComponent = incomingParts.get(i);

            while ( true ) {

                if (currentComponent.getType() == ComponentType.Node) {
                    Node n = (Node)currentComponent;
                    V = n.getNodeVoltage();
                    voltageTerms.add(V);
                    break; 
                }

                if (currentComponent.getType() == ComponentType.Battery) {
                    Battery b = (Battery)currentComponent;
                    V = b.getVoltage();
                    voltageTerms.add(V);
                    break; //This should not break but rather keep going and check if there are batteries in series behind it, then break
                }

                if (currentComponent.getType() == ComponentType.Resistor) {
                    Resistor r = (Resistor)currentComponent;
                    Res += r.getResistance();
                    resistanceTerms.add(Res);
                }

                currentComponent = currentComponent.getInComp(); //Go back one previous component

            }
        }

        //Doing the calculations
        double vSum = 0; //Temporary sum value
        for (int i = 0; i < voltageTerms.size(); i++){
            vSum += voltageTerms.get(i);
        }
        vSum /= voltageTerms.size(); //Parallel voltage is the average amount of voltage
        incomingVoltage = vSum; //Update voltage

        double resSum = 0; //Temporary sum value
        for (int i = 0; i < resistanceTerms.size(); i++){
            resSum += (1 / resistanceTerms.get(i)); //Parallel resistance is the sum of reciprocated resistances...
        }
        incomingResistance = (1 / resSum); //...which is then reciprocated again, resistance is updated
    }

    public void setNodeOutVoltage(double res){ //Input is the circuit's total resistance
        outgoingVoltage = incomingVoltage - (incomingVoltage * (incomingResistance / res));
    }

}
