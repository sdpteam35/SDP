package circuitryapp.components;

public class Wire {
    public double current;
    public double voltageChange;
    public Object start;
    public Object end;

    public Wire(Object  _start, Object _end) {
        start = _start;
        end = _end;
    }

    public Wire(double curr, double vChange, Object  _start, Object _end) {
        current = curr;
        voltageChange = vChange;
        start = _start;
        end = _end;
    }
    public Object getStart(){
        return start;
    }
    public Object getEnd(){
        return end;
    })
}
