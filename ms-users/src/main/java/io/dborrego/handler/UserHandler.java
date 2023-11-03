package io.dborrego.handler;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;

import io.dborrego.domain.User;
import io.dborrego.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserHandler {

    @Inject
    UserService usersService;

    @GET
    public List<User> list() {
        return usersService.listAll();
    }

    @GET
    @Path("/{id}")
    public User get(Long id) {
        return usersService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User create(User user) {
        return usersService.create(user);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Void> update(User user, Long id) {
        try {
            usersService.update(user, id);
        } catch (Exception e) {
            return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return RestResponse.status(Response.Status.OK);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Void> delete(Long id) {
        if (usersService.deleteById(id))
            return RestResponse.status(Response.Status.OK);
        else
            return RestResponse.status(Response.Status.NOT_FOUND);
    }

}