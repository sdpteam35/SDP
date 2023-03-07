package circuitryapp.components;

public class Resistor {
    private double resistance; //ohms of resistor

    public Resistor(double res) {
        //
        resistance = res;
    }

    public void changeResistance(double nres) {
        //if a user wants to edit a resistor's value later, allow them to do so
        //send message to console: "successfully changed resistor value!"
        resistance = nres;
    }
}
