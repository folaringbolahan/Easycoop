/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;
import com.sift.gl.dao.*;
import com.sift.gl.dao.Currentuserdisplaydao;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Company;
import com.sift.gl.model.Moduledetails;
import com.sift.gl.model.Modulemenudetails;
import com.sift.gl.model.Users;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotMembers;
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

@Component ("currrentvoterServicex")
//@Scope("session")
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
//@Scope(WebApplicationContext.SCOPE_SESSION)
public class CurrentvoterdisplayImpl {
    Currentvoterdisplaydao CurrentuserdisplayInterface;
     private VotMembers curruser;
    private VotAgm currusercompany;
    
    public CurrentvoterdisplayImpl(){
        CurrentuserdisplayInterface = new Currentvoterdisplaydao(); //) initial.lookup("payrollsalarylevelstep");
    }
    
    
   
    public void setdCurruser(VotMembers thecurrentuser){
            this.curruser = thecurrentuser;
    }
    
    public void setCurruser(VotMembers curruser) {
        this.curruser = curruser;
    }
    public VotMembers getCurruser(){
        return this.curruser;
    }
    public void setCurrusercompany(VotAgm currusercompany) {
        this.currusercompany = currusercompany;
    }
    public VotAgm getCurrusercompany(){
        return this.currusercompany;
    }
    public VotMembers getdCurruser(Integer userid){
            VotMembers thecurrentuser = new VotMembers();
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
    
    public VotAgm getAgmdetails(Integer id){
            VotAgm allrecs = new VotAgm();
            try{
             allrecs = CurrentuserdisplayInterface.retrieveAgmdet(id);
             currusercompany = allrecs;
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allrecs;
    }
}
