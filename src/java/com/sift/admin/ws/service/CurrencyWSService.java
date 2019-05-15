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

	@Path("/wscurrency/")
	public interface CurrencyWSService{
	    //http://localhost:7070/EazyCoopFin/services/wscurrency/addcur
	    @PUT
	    @Path("addcur")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    public String createOrSaveNewCurrency(CurrencyBean currency);

	    //http://localhost:7070/EazyCoopFin/services/wscurrency/getcur/10
	    @GET
	    @Path("getcur/{id}")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    public CurrencyBean getCurrencyInfo(@PathParam("id") int id);

	    //http://localhost:7070/EazyCoopFin/services/wscurrency/updatecur
	    @POST
	    @Path("/updateCur")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    public String updateCurrencyInfo(CurrencyBean currency);

	    //http://localhost:7070/EazyCoopFin/services/wscurrency/deletecur
	    @DELETE
	    @Path("/delCur/{id}")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    public String deleteCurrencyInfo(@PathParam("id") int id);

	    //http://localhost:7070/EazyCoopFin/services/wscurrency/getcurs
	    @GET
	    @Path("/gcurs")
	    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	    public CurrencyList getAllCurrencies();
	}