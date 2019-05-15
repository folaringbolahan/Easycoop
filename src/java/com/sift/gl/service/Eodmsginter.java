/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import com.sift.gl.dao.*;
import com.sift.gl.AccountgroupException;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Periodend;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Eodmsginter {
   void sendMsgAlert(Periodend periodendmessage);
}
