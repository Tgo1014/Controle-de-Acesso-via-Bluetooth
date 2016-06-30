package webServices;

import java.sql.SQLException;
import java.util.List;
import classesDAO.DispositivoDAO;
import classesDAO.Dispositivo_GrupoDAO;
import classesDAO.SmartphoneDAO;
import tabelas.Dispositivo;
import tabelas.Dispositivo_Grupo;
import tabelas.Smartphone;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/dispositivo")
public class webServiceDispositivo {
	
	@GET
    @Path("/consulta")
    @Produces (MediaType.APPLICATION_XML)
    public List<Dispositivo> getDispositivos() throws ClassNotFoundException, SQLException {
    	
		DispositivoDAO dDao = new DispositivoDAO();
		List<Dispositivo> ds = dDao.buscarDados();
		return ds;
		
    }
	
	@POST
    @Path("/inserir/{nome}/{mac}/{grupoId}")
    @Produces (MediaType.APPLICATION_XML)
    public void inserirSmartphone(@PathParam("nome") String nome, @PathParam("mac") String mac, @PathParam("grupoId") int grupoId) throws Exception {
		
		Dispositivo dispositivo = new Dispositivo();
        dispositivo.setMAC_ADRESS(mac);
        dispositivo.setNM_DISPOSITIVO(nome);

        DispositivoDAO d = new DispositivoDAO();
        int ID_DISPOSITIVO = d.inserirDadosRetID(dispositivo);


        Dispositivo_Grupo dg = new Dispositivo_Grupo();
        dg.setTB_DISPOSITIVOS_ID_DISPOSITIVO(ID_DISPOSITIVO);
        dg.setTB_GRUPOS_ID_GRUPO(grupoId);

        Dispositivo_GrupoDAO dgDAO = new Dispositivo_GrupoDAO();
        dgDAO.inserirDados(dg);
		
    }

}
