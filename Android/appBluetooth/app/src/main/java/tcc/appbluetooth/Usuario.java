package tcc.appbluetooth;

import java.io.File;
import java.io.Serializable;

public class Usuario implements Serializable {

    static final long serialVersionUID = 123456789123456789L;

    private String IMEI;
    private String SIM_ID;
    private File   certificado;


    public Usuario(String IMEI, String SIM_ID, File certificado) {
        this.IMEI = IMEI;
        this.SIM_ID = SIM_ID;
        this.certificado = certificado;
    }

    public Usuario() {
        this.IMEI = "";
        this.SIM_ID = "";
        this.certificado = null;
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

    public File getCertificado() { return certificado; }

    public void setCertificado(File certificado) { this.certificado = certificado; }
}
