/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.utility;

import com.sift.financial.member.MemberContributionDAO;
import com.sift.financial.member.MemberHoldingsDAO;
import com.sift.financial.member.OperandInterface;
import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.functions.NaryFunction;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Stack;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
  


/**
 *
 * @author baydel200
 */
public class MemberContent {
    
private String stckPlace;
private String stockQueryHolder;
private String contrPlace;
private String contribQueryHolder;
private MemberHoldingsDAO memberHoldingsDAO;
private MemberContributionDAO memberContributionDAO;
private Integer branchId;
private Integer companyId;

private Object[] theVariables;

    //public Object getMemberValue(Integer memberId, String memberCode, Object otherInfo) throws Exception;

//@Override
public Object eval(Object[] obj) throws EvaluationException {
        
  Object returnVal = null;
        
        if(getTheVariables() instanceof Object[])
        {
         String type = (String)theVariables[0];
         Integer memberId = (Integer)theVariables[1];
         String memberNo = (String)theVariables[2];
         Object others =  theVariables[3];
       
         try
         {
            if(type.equals(stckPlace))
            {
                returnVal =  getMemberHoldingsValue(memberId, memberNo, others);
            }
            
            if(type.equals(contrPlace))
            {
                returnVal = getMemberContribValue(memberId, memberNo, others);
                
            }
            else
            {
                returnVal = 0.0;
            }
         }
         catch(Exception ex)
         {
        
         }
        
        }
        
        
       return returnVal;   
}


/**public void run(Stack<Object> inStack) throws EvaluationException {

   // check the stack
   checkStack(inStack);

   // get the parameter from the stack
   Object param = inStack.pop();

   // check whether the argument is of the right type
   if (param instanceof Double) {
      // calculate the result
      double r = ((Double)param).doubleValue() / 2;
      // push the result on the inStack
      inStack.push(new Double(r)); 
   } else {
      throw new EvaluationException("Invalid parameter type");
   }
}
* */
    
    
     //@Override
    public Object getMemberHoldingsValue(Integer memberId, String memberCode, Object otherInfo) throws Exception {
        
        Session sess =  null;
        Object result = 0.0;
       
            if(otherInfo != null)
            {
                if (otherInfo instanceof HashMap)
                {
                        try
                        {
                            sess =  getMemberHoldingsDAO().getHibernateTemplate().getSessionFactory().openSession();
                            HashMap<String, Object> theObj= ( HashMap<String, Object>)otherInfo;
                            Integer stckType = Integer.parseInt(theObj.get(OperandInterface.PRODINFOCUSID).toString());
                            String sql = getStockQueryHolder();
                           //String sql = "select SUM(CASE WHEN movement_type = \"C\" THEN movement_holdings ELSE -movement_holdings END) AS total from  member_holdings_movement where member_id=:memcode and stock_id= :stckcode and effective_date < = :effcDate ";
                            SimpleDateFormat dateFormatter2 = new SimpleDateFormat ("yyyy-MM-dd");
		            String datestr = dateFormatter2.format(theObj.get(OperandInterface.EFFECTIVEDATE));
                            Query query = sess.createSQLQuery(sql).setParameter("memcode", memberCode).setParameter("stckcode", stckType).setParameter("effcDate", theObj.get(OperandInterface.EFFECTIVEDATE));
                            /// System.out.println(" after executing query for member  " + memberId + " " +  query.toString());
                           result = ((java.math.BigDecimal) query.uniqueResult()).doubleValue();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                            //result = 0.0;
                        }
                        finally
                        {
                            sess.close();
                        }
                }
                else
                {
                     throw new Exception ("Inappropriate Object passed");
                }
            }
        
     return result;
    }
    
    public Object getMemberContribValue(Integer memberId, String memberCode, Object otherInfo)  throws Exception {
        
        Session sess =  null;
        Object result = 0.0;
       ///System.out.println("executingfor member2  " + memberId);
            if(otherInfo != null)
            {
               
               ///  System.out.println("executing query for member3c  " + otherInfo.getClass().getName());
                if (otherInfo instanceof HashMap)
                {
                  ///    System.out.println("executing query for member3b  " + memberId);
                    try
                        {
                         ///   System.out.println("executing query for member3  " + memberId);
                            sess =  getMemberContributionDAO().getHibernateTemplate().getSessionFactory().openSession();
                          ////   System.out.println("executing query for member4  " + memberId);
                            HashMap<String, Object> theObj= ( HashMap<String, Object>)otherInfo;
                          ////   System.out.println("executing query for member5  " + memberId);
                            //Integer stckType = Integer.parseInt((String)theObj.get(OperandInterface.PRODINFOCUSID));
                           ////  System.out.println("executing query for member6  " + memberId);
                            String sql = getContribQueryHolder();
                            //String sql = "select SUM(CASE WHEN movement_type = \"C\" THEN movement_holdings ELSE -movement_holdings END) AS total from  member_holdings_movement where member_id=:memcode and stock_id= :stckcode and effective_date < = :effcDate ";
                          ////   System.out.println("executing query for member  " + memberId);
                             SimpleDateFormat dateFormatter2 = new SimpleDateFormat ("yyyy-MM-dd");
		            String datestr = dateFormatter2.format(theObj.get(OperandInterface.EFFECTIVEDATE));
                            Query query = sess.createSQLQuery(sql).setParameter("branch", getBranchId()).setParameter("company", getCompanyId()).setParameter("memcode", memberCode).setParameter("effcDate", theObj.get(OperandInterface.EFFECTIVEDATE));
                             
                         ///////   System.out.println(" after executing query for member  " + memberId + " " +  query.toString());
                            result = ((java.math.BigDecimal) query.uniqueResult()).doubleValue();
                          //  System.out.println(" result2  " + result.toString());
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                           // result = 0.0;
                        }
                        finally
                        {
                            sess.close();
                        }
                }
                else
                {
                    
                     throw new Exception ("Inappropriate Object passed");
                }
                
                ///////
              
            }
        //System.out.println(" result  " + result.toString());
     return result;
    }
    
    
    
    public MemberHoldingsDAO getMemberHoldingsDAO() {
        return memberHoldingsDAO;
    }
    
    public void setMemberHoldingsDAO(MemberHoldingsDAO memberHoldingsDAO) {
        this.memberHoldingsDAO = memberHoldingsDAO;
    }

    public MemberContributionDAO getMemberContributionDAO() {
        return memberContributionDAO;
    }

    public void setMemberContributionDAO(MemberContributionDAO memberContributionDAO) {
        this.memberContributionDAO = memberContributionDAO;
    }

public Object[] getTheVariables() {
    return theVariables;
}

public void setTheVariables(Object[] theVariables) {
    this.theVariables = theVariables;
}

    public String getStckPlace() {
        return stckPlace;
    }

    public void setStckPlace(String stckPlace) {
        this.stckPlace = stckPlace;
    }

    public String getContrPlace() {
        return contrPlace;
    }

    public void setContrPlace(String contrPlace) {
        this.contrPlace = contrPlace;
    }

    public String getStockQueryHolder() {
        return stockQueryHolder;
    }

    public void setStockQueryHolder(String stockQueryHolder) {
        this.stockQueryHolder = stockQueryHolder;
    }

    public String getContribQueryHolder() {
        return contribQueryHolder;
    }

    public void setContribQueryHolder(String contribQueryHolder) {
        this.contribQueryHolder = contribQueryHolder;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
    

}
