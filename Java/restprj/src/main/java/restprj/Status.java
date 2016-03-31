package restprj;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name="resposta")
public class Status {
 
    private int status;
    
    public Status(){}
    
    public Status(int status) {
        super();
        this.status = status;
    }
 
    @XmlAttribute(name = "status")
    public int getStatus() {return status;}
    public void setStatus(int status)
    {this.status = status;}

    
    @Override
    public String toString() {
        return String.format("Resposta Status " +
            "[status=%d]",
            status);
    }
    
}