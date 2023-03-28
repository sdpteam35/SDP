package circuitryapp.components;

public class Battery extends Component {
    private double voltage;
    public Battery(String id, double volt){
        super(id, ComponentType.Battery);
        voltage = volt;
    }
    public void changeVoltage(double nvolt) {
        //can manually change voltage of a Battery
        voltage = nvolt;
    }
    public double getVoltage(){
        //access to voltage
        return voltage;
    }
}
