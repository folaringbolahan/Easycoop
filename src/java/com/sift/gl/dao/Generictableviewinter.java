/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccounttxnsException;
import com.sift.gl.GenericsiftException;
import com.sift.gl.model.Accounttxnsdetail;
import com.sift.gl.model.Generictableviewbean;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Generictableviewinter {
    public List<List<String>> buildtablebody(String vSqlStmt) throws GenericsiftException;
    public void writeListToExcel(Generictableviewbean dlist, HttpServletResponse response,String filename) throws GenericsiftException;
}
