
package Tabelas;

public class Usuario {
    
    private int ID_USUARIO;
    private String NM_USUARIO;
    private String CERTIFICADO;
    
    public Usuario() {
        
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public Usuario(String NM_USUARIO) {
        this.NM_USUARIO = NM_USUARIO;
    }

    public String getNM_USUARIO() {
        return NM_USUARIO;
    }

    public void setNM_USUARIO(String NM_USUARIO) {
        this.NM_USUARIO = NM_USUARIO;
    }

    public String getCERTIFICADO() {
        return CERTIFICADO;
    }

    public void setCERTIFICADO(String CERTIFICADO) {
        this.CERTIFICADO = CERTIFICADO;
    }
    
}
