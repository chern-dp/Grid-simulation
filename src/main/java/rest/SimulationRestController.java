package rest;



import service.Simulator;

import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("simulate")

@Produces(MediaType.APPLICATION_JSON)
public class SimulationRestController {
    @Inject
    private Simulator sim;


    @PUT
    @Path("steps")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response simulateGrid( Request req){

        sim.run(req.getSteps());

        if(req.getResponseType().equals("Array"))
            return Response.ok(new RespArray(sim.getArrayOutput())).build();

        return Response.ok(new RespString(sim.getStringOutput())).build();
    }
}
