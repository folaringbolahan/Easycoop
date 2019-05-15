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

	@Path("/addressEntriesWS")
	public interface AddressEntriesWSService{
	    //http://localhost:7070/EazyCoopFin/services/addressEntriesWS/addentry
	    @POST
	    @Path("addentry")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
	    public String createOrSaveNewAddressEntries(AddressEntriesBean addressEntry);

	    //http://localhost:7070/EazyCoopFin/services/addressEntriesWS/getentry/10
	    @GET
	    @Path("getentry/{id}")
	    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    public AddressEntriesBean getAddressEntryInfo(@PathParam("id") int id);

	    //http://localhost:7070/EazyCoopFin/services/addressEntriesWS/updateentry
	    @PUT
	    @Path("updateentry")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
	    public String updateAddressEntryInfo(AddressEntriesBean addressEntry);

	    //http://localhost:7070/EazyCoopFin/services/addressEntriesWS/deleteentry
	    @DELETE
	    @Path("deleteentry")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
	    public String deleteAddressEntryInfo(AddressEntriesBean addressEntry);

	    //http://localhost:7070/EazyCoopFin/services/addressEntriesWS/getall
	    @GET
	    @Path("getall")
	    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	    public AddressEntriesList getAllAddressEntries();
	}