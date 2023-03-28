package circuitryapp.components;

public class Battery extends AbstractComponent {
    private double voltage;
    public Battery(String id, ComponentType ctype, double volt){
        super(id, ctype);
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
