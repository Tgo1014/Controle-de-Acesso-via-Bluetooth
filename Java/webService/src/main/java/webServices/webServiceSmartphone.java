package webServices;

import java.sql.SQLException;
import java.util.List;
import classesDAO.SmartphoneDAO;
import tabelas.Smartphone;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/smartphone")
public class webServiceSmartphone {

	@GET
    @Path("/consulta")
    @Produces (MediaType.APPLICATION_XML)
    public List<Smartphone> getSmartphones() throws ClassNotFoundException, SQLException {
    	
		SmartphoneDAO st = new SmartphoneDAO();
		List<Smartphone> sts = st.buscarDados();
		return sts;
		
    }
	
	@GET
    @Path("/consultaID/{id}")
    @Produces (MediaType.APPLICATION_XML)
    public Smartphone getSmartphone(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
    	
		SmartphoneDAO stDao = new SmartphoneDAO();
		Smartphone st = stDao.buscarSmartphone(id);
		return st;
		
    }
	
	@POST
    @Path("/inserir/{imei}/{iccid}/{usuarioId}/{usuarioNome}")
    @Produces (MediaType.APPLICATION_XML)
    public void inserirSmartphone(@PathParam("imei") String imei, @PathParam("iccid") String iccid, @PathParam("usuarioId") int usuarioId, @PathParam("usuarioNome") String usuarioNome) throws Exception {
		
		Smartphone st = new Smartphone();
		st.setIMEI(imei);
		st.setICCID(iccid);
		st.setID_USUARIO(usuarioId);
		st.setNM_USUARIO(usuarioNome);
		
		SmartphoneDAO stDao = new SmartphoneDAO();
		stDao.inserirDados(st);
		
    }
	
	@POST
    @Path("/atualizar/{id}/{imei}/{iccid}/{usuarioId}")
    @Produces (MediaType.APPLICATION_XML)
    public void atualizarSmartphone(@PathParam("id") int id, @PathParam("imei") String imei, @PathParam("iccid") String iccid, @PathParam("usuarioId") int usuarioId) throws Exception {
		
		Smartphone st = new Smartphone();
		st.setID_SMARTPHONE(id);
		st.setIMEI(imei);
		st.setICCID(iccid);
		st.setID_USUARIO(usuarioId);
		
		SmartphoneDAO stDao = new SmartphoneDAO();
		stDao.atualizarDados(st);
		
    }
	
	@POST
    @Path("/deletar/{id}")
    @Produces (MediaType.APPLICATION_XML)
    public void removerSmartphone(@PathParam("id") int id) throws Exception {
		
		Smartphone st = new Smartphone();
		st.setID_SMARTPHONE(id);
		
		SmartphoneDAO stDao = new SmartphoneDAO();
		stDao.deletarSmartphone(id);
		
    }
	
}
