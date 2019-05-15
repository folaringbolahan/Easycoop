    package com.sift.admin.ws.service;

	import javax.ws.rs.Consumes;
	import javax.ws.rs.DELETE;
	import javax.ws.rs.GET;
	import javax.ws.rs.POST;
	import javax.ws.rs.PUT;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.Produces;
	import javax.ws.rs.core.MediaType;
	import com.sift.admin.bean.*;

	@Path("/countryWS")
	public interface CountryWSService{
	    //http://localhost:7070/EazyCoopFin/services/countryWS/addcountry
	    @POST
	    @Path("addcountry")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
	    public String createOrSaveNewCountry(CountryBean country);

	    //http://localhost:7070/EazyCoopFin/services/countryWS/getcountry/10
	    @GET
	    @Path("getcountry/{id}")
	    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    public CountryBean getCountryInfo(@PathParam("id") int id);

	    //http://localhost:7070/EazyCoopFin/services/countryWS/updatecountry
	    @PUT
	    @Path("updatecountry")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
	    public String updateCountryInfo(CountryBean country);

	    //http://localhost:8080/ApacheCXF-Spring-Hibernate/services/countryWS/deletecountry
	    @DELETE
	    @Path("deletecountry")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
	    public String deleteCountryInfo(CountryBean country);

	    //http://localhost:7070/EazyCoopFin/services/countryWS/getallacountry
	    @GET
	    @Path("getallcountry")
	    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    public CountryList getAllCountries();
	}