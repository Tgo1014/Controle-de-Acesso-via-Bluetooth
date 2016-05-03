package tcc.appbluetooth;

public class Smartphone {
    private String IMEI;
    private String SIM_ID;

    public Smartphone(String IMEI, String SIM_ID) {
        this.IMEI = IMEI;
        this.SIM_ID = SIM_ID;
    }

    public Smartphone() {
        this.IMEI = null;
        this.SIM_ID = null;
    }

    public String getSIM_ID() {
        return SIM_ID;
    }

    public void setSIM_ID(String SIM_ID) {
        this.SIM_ID = SIM_ID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }
}
