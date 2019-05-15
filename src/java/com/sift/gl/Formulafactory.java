/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;

import com.sift.hp.HpgenericException;
import com.sift.hp.model.Hpvalidationrules;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import org.nfunk.jep.JEP;
import org.nfunk.jep.SymbolTable;
import org.nfunk.jep.Variable;
//import org.nfunk.jep.VariableFactory
import org.springframework.beans.factory.annotation.Value;


/**
 *
 * @author yomi
 */
public class Formulafactory {
    String productcode = "";
    String dformulacode = "";
    int branchid = 0;
    int companyid = 0;
    String formulamessage = "";
    double formularesult = 0.0;
    Hpvalidationrules hpvalrl = null;
    HashMap<String,List<String>> gmaplist;
    
    public Formulafactory(String productcode,String dformulacode,int branchid,int companyid,HashMap<String,List<String>> gmaplist){
      this.branchid = branchid;
      this.companyid = companyid;
      this.productcode = productcode;
      this.dformulacode = dformulacode;
      this.gmaplist = gmaplist;
    }
    public void determineformulavalue() throws GenericsiftException{
        formularesult = 0.0;
        formulamessage = "No defined Validation rule";
        // System.out.println("determineformulavalue1");
        gethpvalrlfromdb();
        if (hpvalrl!=null) {
           // System.out.println("determineformulavalue2");
           formularesult = getformularesults(gmaplist); 
           //System.out.println("formularesult " + formularesult);
           formulamessage = "Validation rule defined";
        }
    }
    
    public double getResult() {
        return formularesult;
    }
    
    public String getMessage() {
        return formulamessage;
    }
    
    private Hpvalidationrules retrieveHpvalrule(String code,String dformlacode,int branchid,int companyid) throws GenericsiftException {
        Hpvalidationrules vDetails = new Hpvalidationrules();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM hpvalidationrules a where a.code = '" + dformlacode + "' and a.productcode = '" + code + "' and a.companyid  = " + companyid + " and a.branchid = " + branchid + " order by description asc");  
         while (rs.next()) {
             vDetails = new Hpvalidationrules();
             vDetails.setCode(rs.getString("code"));
             vDetails.setDescription(rs.getString("description")); 
             vDetails.setProductcode(rs.getString("productcode"));
             vDetails.setValidationtype(rs.getString("validationtype"));
             vDetails.setFormula(rs.getString("formula"));
             vDetails.setResultcond(rs.getString("resultcond"));
             vDetails.setId(rs.getInt("id"));
          }  
        } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
     } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        return vDetails;
    }
    
    public void gethpvalrlfromdb() throws GenericsiftException{
        hpvalrl = retrieveHpvalrule(productcode,dformulacode,branchid,companyid);
    } 
    
    private List<String> getformulaoperands(){
        List<String> doperands = new ArrayList<String>();
      //  doperands = evaluateandextractcodes(hpvalrl.getFormula());
        
        JEP parser = new JEP();
        parser.setAllowUndeclared(true);
        parser.parseExpression(hpvalrl.getFormula());
       //System.out.println("determineformulavalue4 " +hpvalrl.getFormula());
        SymbolTable symbolTable = parser.getSymbolTable();
        //System.out.println("determineformulavalue5" +  symbolTable.values().size());
        for (Object variableObj : symbolTable.values()) {
         Variable variable = (Variable) variableObj;// symbolTable.getVar(variableName.toString());
        // System.out.println("determineformulavalue6");
           if (!variable.isConstant()) {
               doperands.add(variable.getName());
             // System.out.println("variable.getName() " + variable.getName());
           }
         }     
            
        return doperands;
   
    }
    
    
    public double getoperandvalfrompropdb(String operandcode,List<String> placeholderreplacements) throws GenericsiftException{
        double amt = 0.0;
        String searchcode = "formula." + operandcode;
        String sqlstmt = "";
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            sqlstmt = (String) ctx.lookup("java:comp/env/"+searchcode);
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }
        
        if (sqlstmt.isEmpty()==false) {
           //System.out.println("sqlstmt " + sqlstmt);
            String arg1="";String arg2="";String arg3="";String arg4="";String arg5="";String arg6="";String arg7="";String arg8="";String arg9="";String arg10="";String arg11="";String arg12="";
            if (placeholderreplacements.size()==1) {arg1=placeholderreplacements.get(0);}
            if (placeholderreplacements.size()==2) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);}
            if (placeholderreplacements.size()==3) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);}
            if (placeholderreplacements.size()==4) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);}
            if (placeholderreplacements.size()==5) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);}
            if (placeholderreplacements.size()==6) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);arg6=placeholderreplacements.get(5);}
            if (placeholderreplacements.size()==7) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);arg6=placeholderreplacements.get(5);arg7=placeholderreplacements.get(6);}
            if (placeholderreplacements.size()==8) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);arg6=placeholderreplacements.get(5);arg7=placeholderreplacements.get(6);arg8=placeholderreplacements.get(7);}
            if (placeholderreplacements.size()==9) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);arg6=placeholderreplacements.get(5);arg7=placeholderreplacements.get(6);arg8=placeholderreplacements.get(7);arg9=placeholderreplacements.get(8);}
            if (placeholderreplacements.size()==10) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);arg6=placeholderreplacements.get(5);arg7=placeholderreplacements.get(6);arg8=placeholderreplacements.get(7);arg9=placeholderreplacements.get(8);arg10=placeholderreplacements.get(9);}
            if (placeholderreplacements.size()==11) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);arg6=placeholderreplacements.get(5);arg7=placeholderreplacements.get(6);arg8=placeholderreplacements.get(7);arg9=placeholderreplacements.get(8);arg10=placeholderreplacements.get(9);arg11=placeholderreplacements.get(10);}
            if (placeholderreplacements.size()==12) {arg1=placeholderreplacements.get(0);arg2=placeholderreplacements.get(1);arg3=placeholderreplacements.get(2);arg4=placeholderreplacements.get(3);arg5=placeholderreplacements.get(4);arg6=placeholderreplacements.get(5);arg7=placeholderreplacements.get(6);arg8=placeholderreplacements.get(7);arg9=placeholderreplacements.get(8);arg10=placeholderreplacements.get(9);arg11=placeholderreplacements.get(10);arg12=placeholderreplacements.get(11);}
            
            String nsqlstmt = String.format(sqlstmt,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12); 
          // System.out.println("nsqlstmt " + nsqlstmt);
          //String nsqlstmt = String.format(sqlstmt,placeholderreplacements); 
          GendataService dbobj = new GendataService();
          dbobj.inimkcon();
          ResultSet rs =  null;
          try {
          rs =  dbobj.retrieveRecset(nsqlstmt);  
          while (rs.next()) {
             amt = rs.getDouble("amount");
          }  
        } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
        } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
         }
        }
        //System.out.println("amt " + amt);
        return amt;
    } 
    
    public Hpvalidationrules getValidationrules(){
        return hpvalrl;
    }
   
    private double getformularesults(HashMap<String,List<String>> maplist) throws GenericsiftException {
      double damt = 0.0;
      String dnewformula = hpvalrl.getFormula();
      JEP myParser = new JEP();
      myParser.setAllowUndeclared(true);
      myParser.parseExpression(dnewformula);
      
     // System.out.println("determineformulavalue3");
      
      List<String> myoperands = getformulaoperands();
      for (String opercode : myoperands) {
          List<String> placeholderreplacements = new ArrayList();
          placeholderreplacements = maplist.get(opercode);
          // assign right values to arraylist above
          System.out.println(opercode + " opercode");
          
          myParser.addVariable(opercode, getoperandvalfrompropdb(opercode, placeholderreplacements));
           
      }
      if (dnewformula.isEmpty() == false) {
           //myParser.parseExpression(dnewformula); 
           damt = myParser.getValue();
          // System.out.println(damt + " damt");
      } 
      return damt;
    }
    
    
    public List<String> evaluateandextractcodes(String dformula){
       List<String> dcodes=new ArrayList<String>();
       String dRegex = "\\{.+?\\}";
       Pattern pattern = Pattern.compile(dRegex,Pattern.CASE_INSENSITIVE);
       Matcher matcher = pattern.matcher(dformula);
       while (matcher.find()) {
               // System.out.println("I found the text " + matcher.group() + " starting at " +
               //    "index " + matcher.start() + " and ending at index " + matcher.end() + "");
                dcodes.add(matcher.group());
       } 
       return dcodes;
    }
    
    public String replacevariableswthvalue(String dformula,String valuetobereplaced,String replacement){
       String dcodes="";
       Pattern pattern = Pattern.compile(valuetobereplaced,Pattern.LITERAL);
       Matcher matcher = pattern.matcher(dformula);
       dcodes = matcher.replaceAll(replacement);
       return dcodes;
    }
    
    
}
