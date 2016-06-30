package webServices;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class AppGerenciador {

	public static void main(String[] args) throws Exception {
		
		  ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
	      context.setContextPath("/");
	      
		  ResourceHandler resourceHandler= new ResourceHandler();
		  resourceHandler.setResourceBase("./src/main/java/sites");
		  resourceHandler.setWelcomeFiles(new String[]{"index.html"});
		  resourceHandler.setDirectoriesListed(true);
		  ContextHandler contextHandler = new ContextHandler("/gerenciador");
		  contextHandler.setHandler(resourceHandler);
	      
	      Server jettyServer = new Server();
	      ServerConnector connector = new ServerConnector(jettyServer);
	      connector.setPort(8080);
	      jettyServer.setConnectors(new Connector[] { connector });
	      
	      ContextHandlerCollection contexts = new ContextHandlerCollection();
	      contexts.setHandlers(new Handler[] { context, contextHandler });
	      jettyServer.setHandler(contexts);
	 
	      ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
	      jerseyServlet.setInitOrder(0);
	      jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "webServices");
	 
	      try {
	         jettyServer.start();
	         jettyServer.join();
	        } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	            jettyServer.destroy();
	      }
		
    }

}