
package tabelas;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Smartphone")
public class Smartphone {
    
    private String IMEI;
    private String ICCID;
    private String NM_USUARIO;
    private int ID_USUARIO;
    private int ID;
    
    public Smartphone(){
        
    }
    
    @XmlAttribute(name = "id")
    public int getID_SMARTPHONE() {
        return ID;
    }
    public void setID_SMARTPHONE(int ID) {
        this.ID = ID;
    }

    @XmlAttribute(name = "nome")
    public String getNM_USUARIO() {
        return NM_USUARIO;
    }
    public void setNM_USUARIO(String NM_USUARIO) {
        this.NM_USUARIO = NM_USUARIO;
    }

    @XmlAttribute(name = "imei")
    public String getIMEI() {
        return IMEI;
    }
    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    @XmlAttribute(name = "iccid")
    public String getICCID() {
        return ICCID;
    }
    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    @XmlAttribute(name = "usuarioId")
    public int getID_USUARIO() {
        return ID_USUARIO;
    }
    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }
       
    
}
