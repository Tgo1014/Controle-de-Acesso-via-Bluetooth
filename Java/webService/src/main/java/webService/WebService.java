package webService;

 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/xml/status")
public class WebService {
    
    public WebService(){
        
    }
    
    @GET
    @Path("/{imei}/{simid}/{mac}")
    @Produces (MediaType.APPLICATION_XML)
    public Status getStatus(@PathParam("imei") String imei, @PathParam("simid") String simid, @PathParam("mac") String mac) {
        if (imei == String.valueOf(4545) && simid == String.valueOf(4545) && mac == String.valueOf(4545)) return new Status(1);
        else return new Status(0);
    }
    
    
}
