
package tabelas;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Dispositivo")
public class Dispositivo {
    
    private String MAC_ADRESS;
    private String NM_DISPOSITIVO;
    
    public Dispositivo(){
        
    }

    @XmlAttribute(name = "mac")
    public String getMAC_ADRESS() {
        return MAC_ADRESS;
    }
    public void setMAC_ADRESS(String MAC_ADRESS) {
        this.MAC_ADRESS = MAC_ADRESS;
    }

    @XmlAttribute(name = "nome")
    public String getNM_DISPOSITIVO() {
        return NM_DISPOSITIVO;
    }
    public void setNM_DISPOSITIVO(String NM_DISPOSITIVO) {
        this.NM_DISPOSITIVO = NM_DISPOSITIVO;
    }
    
}
