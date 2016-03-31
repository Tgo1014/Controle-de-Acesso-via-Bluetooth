package restprj;

 
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
    public Status getStatus(@PathParam("imei") int imei, @PathParam("simid") int simid, @PathParam("mac") int mac) {
        if (imei == 4545 && simid == 4545 && mac == 4545) return new Status(1);
        else return new Status(0);
    }
    
    
}
