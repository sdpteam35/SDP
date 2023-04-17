package circuitryapp.components;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

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

    public double getNodeResistance(){
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
            ArrayList<Double> resistanceSum = new ArrayList<Double>(); //To be used to calculate the resistance in series for each path
            Node endingNode = null; //To be used to avoid repeated calculations at the ending node
            double Res = 0; //Value to be stored when solving resistance in parallel
            double V = 0; //Value to be stored when solving voltage in parallel
            Component currentComponent = incomingParts.get(i);

            while ( true ) {

                if (currentComponent.getType() == ComponentType.Node) {
                    Node n = (Node)currentComponent;
                    if (n == endingNode) {
                        break; // We want to avoid adding the same node multiple times as each path will end at this particular node
                    }

                    endingNode = n; //Update the "ending node" for the previous conditional
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
                    resistanceSum.add(r.getResistance()); //Add to the array to solve the resistance in series
                }

                currentComponent = currentComponent.getInComp(); //Go back one previous component

            }

            for (Double r : resistanceSum){Res += r;} //Perform series summing
            resistanceTerms.add(Res); //Add to the array for parallel summing
            
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
