package com.rest.project.rest;

import com.rest.project.models.Person;
import com.rest.project.repositories.PersonRepository;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
@ApplicationScoped
public class PersonResource {

    @Inject
    protected PersonRepository personRepository;

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<List<Person>> findByName(@PathParam("name") String name){
        return personRepository.getByName(name);
    }


    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create(@RequestBody(required = true, description = "Body della richiesta.") Person person) {
        return personRepository.save(person);
    }


    @DELETE
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteByName(@PathParam("name") String name){
        return personRepository.deleteByName(name);
    }



    @DELETE
    @Path("{name}/{book}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteByNameBook(@PathParam("name") String name, @PathParam("book") String book){
        return personRepository.deleteByNameAndBook(name, book);
    }
}
