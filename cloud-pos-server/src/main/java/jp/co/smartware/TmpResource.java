package jp.co.smartware;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/tmp")
public class TmpResource {

    @GET
    public String hello() {
        return "Hello World";
    }

    @Path("/{name}")
    @GET
    public String greet(String name) {
        return "Hello " + name;
    }

}
