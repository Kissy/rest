package fr.kissy.rest.resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api("/api")
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class ApiListingResource extends ApiListingResourceJSON {
}