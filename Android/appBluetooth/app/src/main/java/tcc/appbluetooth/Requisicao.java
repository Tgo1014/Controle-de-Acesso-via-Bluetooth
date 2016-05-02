package tcc.appbluetooth;

import java.io.Serializable;
import java.security.cert.X509Certificate;

public class Requisicao implements Serializable{

    static final long serialVersionUID = 123456789123456789L;

    private String IMEI;
    private String SIM_ID;
    private X509Certificate certificado;

    public Requisicao(String IMEI, String SIM_ID, X509Certificate certificado) {
        this.IMEI = IMEI;
        this.SIM_ID = SIM_ID;
        this.certificado = certificado;
    }

    public Requisicao() {
        this.IMEI = null;
        this.SIM_ID = null;
        this.certificado = null;
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

    public X509Certificate getCertificado() {
        return certificado;
    }

    public void setCertificado(X509Certificate certificado) {
        this.certificado = certificado;
    }
}
