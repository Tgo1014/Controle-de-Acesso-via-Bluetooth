package tabelas;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Usuario")
public class Usuario {
    
    private int ID_USUARIO;
    private String NM_USUARIO;
    private String CERTIFICADO;
    private String EMAIL;
    
    public Usuario() {
        
    }

    @XmlAttribute(name = "email")
    public String getEMAIL() {
        return EMAIL;
    }
    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
    
    @XmlAttribute(name = "id")
    public int getID_USUARIO() {
        return ID_USUARIO;
    }
    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    @XmlAttribute(name = "nome")
    public String getNM_USUARIO() {
        return NM_USUARIO;
    }
    public void setNM_USUARIO(String NM_USUARIO) {
        this.NM_USUARIO = NM_USUARIO;
    }

    @XmlAttribute(name = "certificado")
    public String getCERTIFICADO() {
        return CERTIFICADO;
    }
    public void setCERTIFICADO(String CERTIFICADO) {
        this.CERTIFICADO = CERTIFICADO;
    }
    
}
