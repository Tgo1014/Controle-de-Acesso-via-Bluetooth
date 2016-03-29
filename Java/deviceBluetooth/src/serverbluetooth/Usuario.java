package serverbluetooth;

public class Usuario {
    private String IMEI;
    private String SIM_ID;

    public Usuario(String IMEI, String SIM_ID) {
        this.IMEI = IMEI;
        this.SIM_ID = SIM_ID;
    }
    
    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getSIM_ID() {
        return SIM_ID;
    }

    public void setSIM_ID(String SIM_ID) {
        this.SIM_ID = SIM_ID;
    }
    
    
}
