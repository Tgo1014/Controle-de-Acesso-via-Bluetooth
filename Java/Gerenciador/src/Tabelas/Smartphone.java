
package Tabelas;

public class Smartphone {
    
    private String IMEI;
    private String ICCID;
    private String NM_USUARIO;
    private int ID_USUARIO;
    
    public Smartphone(){
        
    }

    public String getNM_USUARIO() {
        return NM_USUARIO;
    }

    public void setNM_USUARIO(String NM_USUARIO) {
        this.NM_USUARIO = NM_USUARIO;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }
       
    
}
