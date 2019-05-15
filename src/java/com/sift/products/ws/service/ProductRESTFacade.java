/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.products.ws.service;

import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.Products;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.orm.PersistentException;
import javax.ws.rs.core.MediaType;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.orm.PersistentSession;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author logzy
 */
@Path("/productws")
public class ProductRESTFacade {
private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductRESTFacade.class);
    public ProductRESTFacade() {
    }

    @POST
    @Path("/save")
    @Consumes({"application/xml", "application/json"})
    public Response saveProduct(Products product) throws PersistentException {
        Response response = null;
        int companyId = product.getCompanyId();
        String accountNumber= product.getControlAccount();
        product.setCode(getProductCode(product.getBranchId(), product.getCompanyId()));
        product.setSegmentCode(getSegmentCode(accountNumber,companyId));
        if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().save(product)) {
            ProductAccount productAccount = new ProductAccount();
                productAccount.setGlAccountNumber(product.getControlAccount());
                productAccount.setBranchId(product.getBranchId());
                productAccount.setCompanyId(product.getCompanyId());
                productAccount.setProductId(product.getId());
                productAccount.setProductAccountTypeCode("CTR");
                
                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().save(productAccount);
              
                
            return Response.status(201).entity(product).build();
        }
        else {
            return Response.status(404).entity("Unable to save").build();
        }

    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") int id) throws PersistentException {

        Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(id);
        if (product != null) {
            return Response.status(200).entity(product).build();
        } else {
            return Response.status(404).entity("The product with the id " + id + " does not exist").build();
        }
    }
    public String getSegmentCode(String accountNumber, int companyId) {
        PersistentSession session;
        String segmentCode = null;
        String acStruct = null;
        String strCode = null;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("select * from `accounts`  account where AccountNo = '" + accountNumber + "'");

            List<Object[]> rows = query.list();
            if(rows.isEmpty()){
                segmentCode = "";
            }else{
            Object[] row = rows.get(0);
            acStruct = row[6].toString();

            SQLQuery accountStrSql = session.createSQLQuery("select * from `accountstructures` where StructureCode = '" + acStruct + "' and companyid='" + companyId + "'");

            List<Object[]> structure = accountStrSql.list();

            Object[] strRow = structure.get(0);


            if (Integer.valueOf(strRow[3].toString()) == 3) {
                segmentCode = row[7].toString();
            }
            if (Integer.valueOf(strRow[4].toString()) == 3) {
                segmentCode = row[8].toString();
            }
            if (Integer.valueOf(strRow[5].toString()) == 3) {
                segmentCode = row[9].toString();
            }
            if (Integer.valueOf(strRow[6].toString()) == 3) {
                segmentCode = row[10].toString();
            }
            if (Integer.valueOf(strRow[7].toString()) == 3) {
                segmentCode = row[11].toString();
            }
            if (Integer.valueOf(strRow[8].toString()) == 3) {
                segmentCode = row[12].toString();
            }
            if (Integer.valueOf(strRow[9].toString()) == 3) {
                segmentCode = row[13].toString();
            }
            if (Integer.valueOf(strRow[10].toString()) == 3) {
                segmentCode = row[14].toString();
            }
            if (Integer.valueOf(strRow[11].toString()) == 3) {
                segmentCode = row[15].toString();
            }
            if (Integer.valueOf(strRow[12].toString()) == 3) {
                segmentCode = row[16].toString();
            }


            System.out.println("Structure Code" + segmentCode);
            }
        } catch (PersistentException ex) {
            // Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            segmentCode = "";
        }
        if (segmentCode != null && segmentCode.length() == 1) {
            segmentCode = "00" + segmentCode;
        } else if (segmentCode != null && segmentCode.length() == 2) {
            segmentCode = "0" + segmentCode;
        }
        return segmentCode;
    }

  
    public synchronized String getProductCode(int branchId, int companyId) {
        PersistentSession session;
        String productCode = null;

        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            //Query query = session.createSQLQuery("select count(*) from `products` where branch_id = '" + branchId + "'");
            Query query = session.createSQLQuery("select max(cast(code as DECIMAL(0))) from `products` where branch_id = '" + branchId + "'");
            int value = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
            productCode = String.valueOf(value + 1);

            if (productCode != null && productCode.length() == 1) {
                productCode = "00" + productCode;
            } else if (productCode != null && productCode.length() == 2) {
                productCode = "0" + productCode;
            }
            System.out.println("Product Code: " + productCode);


        } catch (PersistentException ex) {
             _logger.error("ProductRESTFacade.getProductCode()", ex);
        }

        return productCode;
    }
}
