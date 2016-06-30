package webServices;

import java.sql.SQLException;
import java.util.List;
import classesDAO.GrupoDAO;
import tabelas.Grupo;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/grupo")
public class webServiceGrupo {
	
	@GET
    @Path("/consulta")
    @Produces (MediaType.APPLICATION_XML)
    public List<Grupo> getDispositivos() throws ClassNotFoundException, SQLException, Exception {
    	
		GrupoDAO gDao = new GrupoDAO();
		List<Grupo> gs = gDao.buscarDados();
	
		return gs;
		
    }

}
