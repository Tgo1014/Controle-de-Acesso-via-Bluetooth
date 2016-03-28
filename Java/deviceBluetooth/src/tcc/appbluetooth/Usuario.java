package tcc.appbluetooth;

import java.io.Serializable;

public class Usuario implements Serializable{

    static final long serialVersionUID = 123456789123456789L;

    private String IMEI;
    private String SIM_ID;

    public Usuario(String IMEI, String SIM_ID) {
        this.IMEI = IMEI;
        this.SIM_ID = SIM_ID;
    }

    public Usuario() {
        this.IMEI = "";
        this.SIM_ID = "";
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
