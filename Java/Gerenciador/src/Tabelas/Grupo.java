
package Tabelas;

import java.sql.Date;
import java.sql.Time;

public class Grupo {
    
    private int ID_GRUPO;
    private String NM_GRUPO;
    private Date DT_INICIO_ACESSO;
    private Date DT_FIM_ACESSO;
    private Time HR_INICIO_ACESSO;
    private Time HR_FIM_ACESSO;
    
    public Grupo(){
        
    }

    public int getID_GRUPO() {
        return ID_GRUPO;
    }

    public void setID_GRUPO(int ID_GRUPO) {
        this.ID_GRUPO = ID_GRUPO;
    }

    public String getNM_GRUPO() {
        return NM_GRUPO;
    }

    public void setNM_GRUPO(String NM_GRUPO) {
        this.NM_GRUPO = NM_GRUPO;
    }

    public Date getDT_INICIO_ACESSO() {
        return DT_INICIO_ACESSO;
    }

    public void setDT_INICIO_ACESSO(Date DT_INICIO_ACESSO) {
        this.DT_INICIO_ACESSO = DT_INICIO_ACESSO;
    }

    public Date getDT_FIM_ACESSO() {
        return DT_FIM_ACESSO;
    }

    public void setDT_FIM_ACESSO(Date DT_FIM_ACESSO) {
        this.DT_FIM_ACESSO = DT_FIM_ACESSO;
    }

    public Time getHR_INICIO_ACESSO() {
        return HR_INICIO_ACESSO;
    }

    public void setHR_INICIO_ACESSO(Time HR_INICIO_ACESSO) {
        this.HR_INICIO_ACESSO = HR_INICIO_ACESSO;
    }

    public Time getHR_FIM_ACESSO() {
        return HR_FIM_ACESSO;
    }

    public void setHR_FIM_ACESSO(Time HR_FIM_ACESSO) {
        this.HR_FIM_ACESSO = HR_FIM_ACESSO;
    }
    
}
