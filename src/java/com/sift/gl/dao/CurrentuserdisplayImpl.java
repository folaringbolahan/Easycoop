/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.dao.Currentuserdisplaydao;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Company;
import com.sift.gl.model.Moduledetails;
import com.sift.gl.model.Modulemenudetails;
import com.sift.gl.model.Users;
import java.util.*;
import javax.naming.InitialContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

@Component ("currrentuserServicex")
//@Scope("session")
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
//@Scope(WebApplicationContext.SCOPE_SESSION)
public class CurrentuserdisplayImpl {
    Currentuserdisplaydao CurrentuserdisplayInterface;
    Accountgrpclassdao AccountgroupclassInterface;
    Accountgrprepdao AccountgrouprepInterface;
    private List<Moduledetails> currmodules;
    private List<Modulemenudetails> currmodulemenus;
    private Users curruser;
    private Company currusercompany;
    
    public CurrentuserdisplayImpl(){
        CurrentuserdisplayInterface = new Currentuserdisplaydao(); //) initial.lookup("payrollsalarylevelstep");
    }
    
    public List<Modulemenudetails> getModulemenus(String role,Integer branch,Integer company){
            List allrecs = new ArrayList();
            try{
             allrecs = CurrentuserdisplayInterface.retrieveModulemenus(role,branch,company);
             currmodulemenus = allrecs;
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecs;
    }
    public List<Moduledetails> getModules(String role,Integer branch,Integer company){
            List dlist = new ArrayList();
            try{
             dlist = CurrentuserdisplayInterface.retrieveModules(role,branch,company);
             currmodules = dlist;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dlist;
    }
    public void setdCurruser(Users thecurrentuser){
            this.curruser = thecurrentuser;
    }
    public List<Modulemenudetails> getCurrmodulemenus(){
            return this.currmodulemenus;
    }
    public List<Moduledetails> getCurrmodules(){
            return this.currmodules;
    }
    public void setCurrmodules(List<Moduledetails> currmodules) {
        this.currmodules = currmodules;
    }
    public void setCurrmodulemenus(List<Modulemenudetails> currmodulemenus) {
        this.currmodulemenus = currmodulemenus;
    }
    public void setCurruser(Users curruser) {
        this.curruser = curruser;
    }
    public Users getCurruser(){
        return this.curruser;
    }
    public void setCurrusercompany(Company currusercompany) {
        this.currusercompany = currusercompany;
    }
    public Company getCurrusercompany(){
        return this.currusercompany;
    }
    public Users getdCurruser(String userid){
            Users thecurrentuser = new Users();
            try
            {
             thecurrentuser = CurrentuserdisplayInterface.retrieveUserdet(userid);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }  
             this.curruser = thecurrentuser;
            return thecurrentuser;
    }
    
    public Company getCompanydetails(Integer branch,Integer company){
            Company allrecs = new Company();
            try{
             allrecs = CurrentuserdisplayInterface.retrieveCompanydet(branch,company);
             currusercompany = allrecs;
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecs;
    }
}
