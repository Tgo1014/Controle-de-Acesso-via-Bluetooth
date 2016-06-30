
package tabelas;

import java.sql.Date;
import java.sql.Time;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Grupo")
public class Grupo {
    
    private int ID_GRUPO;
    private String NM_GRUPO;
    private String DT_INICIO_ACESSO;
    private String DT_FIM_ACESSO;
    private String HR_INICIO_ACESSO;
    private String HR_FIM_ACESSO;
    
    public Grupo(){
        
    }

    @XmlAttribute(name = "id")
    public int getID_GRUPO() {
        return ID_GRUPO;
    }
    public void setID_GRUPO(int ID_GRUPO) {
        this.ID_GRUPO = ID_GRUPO;
    }

    @XmlAttribute(name = "nome")
    public String getNM_GRUPO() {
        return NM_GRUPO;
    }
    public void setNM_GRUPO(String NM_GRUPO) {
        this.NM_GRUPO = NM_GRUPO;
    }

    @XmlAttribute(name = "dInicio")
    public String getDT_INICIO_ACESSO() {
        return DT_INICIO_ACESSO;
    }
    public void setDT_INICIO_ACESSO(String DT_INICIO_ACESSO) {
        this.DT_INICIO_ACESSO = DT_INICIO_ACESSO;
    }

    @XmlAttribute(name = "dFim")
    public String getDT_FIM_ACESSO() {
        return DT_FIM_ACESSO;
    }
    public void setDT_FIM_ACESSO(String DT_FIM_ACESSO) {
        this.DT_FIM_ACESSO = DT_FIM_ACESSO;
    }

    @XmlAttribute(name = "hInicio")
    public String getHR_INICIO_ACESSO() {
        return HR_INICIO_ACESSO;
    }
    public void setHR_INICIO_ACESSO(String HR_INICIO_ACESSO) {
        this.HR_INICIO_ACESSO = HR_INICIO_ACESSO;
    }

    @XmlAttribute(name = "hFim")
    public String getHR_FIM_ACESSO() {
        return HR_FIM_ACESSO;
    }
    public void setHR_FIM_ACESSO(String HR_FIM_ACESSO) {
        this.HR_FIM_ACESSO = HR_FIM_ACESSO;
    }
    
}
