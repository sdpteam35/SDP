package circuitryapp.components;

public class Wire {
    public double current;
    public double voltageChange;
    public Resistor start;
    public Resistor end;

    public Wire(Resistor  _start, Resistor _end) {
        start = _start;
        end = _end;
    }

    public Wire(double curr, double vChange, Resistor  _start, Resistor _end) {
        current = curr;
        voltageChange = vChange;
        start = _start;
        end = _end;
    }
}
