package webServices;

import java.sql.SQLException;
import java.util.List;
import classesDAO.UsuarioDAO;
import tabelas.Usuario;
import java.io.File;
import java.security.cert.X509Certificate;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/usuario")
public class webServiceUsuario  {
	
	@GET
    @Path("/consulta")
    @Produces (MediaType.APPLICATION_XML)
    public List<Usuario> getUsuarios() throws ClassNotFoundException, SQLException {
    	
		UsuarioDAO user = new UsuarioDAO();
		List<Usuario> users = user.buscarDados();
		return users;
		
    }
	
	@GET
    @Path("/consultaID/{id}")
    @Produces (MediaType.APPLICATION_XML)
    public Usuario getUsuario(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
    	
		UsuarioDAO userDao = new UsuarioDAO();
		Usuario user = userDao.buscarUsuario(id);
		return user;
		
    }
	
	@POST
    @Path("/inserir/{nome}/{email}")
    @Produces (MediaType.APPLICATION_XML)
    public void inserirUsuario(@PathParam("nome") String nome, @PathParam("email") String email) throws Exception {
		
		X509Certificate cert = Certificado.geraCertificado(nome);
        String cert64 = Certificado.certParaBase64(cert);
        
		Usuario user = new Usuario();
		user.setNM_USUARIO(nome);
		user.setEMAIL(email);
		user.setCERTIFICADO(cert64);
		
		UsuarioDAO userDao = new UsuarioDAO();
		userDao.inserirDados(user);
		
		if (!email.equals("")){
            Certificado.extraiCertificado(cert, "Anhembi Morumbi - " + nome);
            Email.enviaEmail(email, new File(System.getProperty("user.home") + File.separator + "Desktop\\" + "Anhembi Morumbi - " + nome + ".cer"), nome);                    
        } else {
            Certificado.extraiCertificado(cert, "Anhembi Morumbi - " + nome);
        }
		
    }
	
	@POST
    @Path("/atualizar/{id}/{nome}/{email}")
    @Produces (MediaType.APPLICATION_XML)
    public void atualizarUsuario(@PathParam("id") int id, @PathParam("nome") String nome, @PathParam("email") String email) throws Exception {
		
		Usuario user = new Usuario();
		user.setID_USUARIO(id);
		user.setNM_USUARIO(nome);
		user.setEMAIL(email);
		
		UsuarioDAO userDao = new UsuarioDAO();
		userDao.atualizarDados(user);
		
    }
	
	@POST
    @Path("/deletar/{id}")
    @Produces (MediaType.APPLICATION_XML)
    public void removerUsuario(@PathParam("id") int id) throws Exception {
		
		Usuario user = new Usuario();
		user.setID_USUARIO(id);
		
		UsuarioDAO userDao = new UsuarioDAO();
		userDao.deletarUsuario(id);
		
    }

}
