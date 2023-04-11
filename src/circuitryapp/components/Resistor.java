package circuitryapp.components;

public class Resistor extends Component {
    private double resistance; //ohms of resistor

    public Resistor(String id, double res) {
        super(id, ComponentType.Resistor);
        resistance = res;
    }

    public void changeResistance(double nres) {
        //if a user wants to edit a resistor's value later, allow them to do so
        //send message to console: "successfully changed resistor value!"
        resistance = nres;
    }
    public double getResistance(){
        //access to resistance
        return resistance;
    }
}
