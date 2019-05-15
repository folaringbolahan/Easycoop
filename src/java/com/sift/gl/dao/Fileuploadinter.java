/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.FileuploadException;
import com.sift.gl.NotificationService;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Fileuploadinter {
  public void addFile(FileUpload fileUpload) throws FileuploadException;
  public void addFilecontents(String batchid,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone) throws FileuploadException;
  public void addtxnFilecontents(String batchid,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone,String mailsubject,NotificationService notificationService) throws FileuploadException;
  public void removeFile(FileUpload fileUpload) throws FileuploadException;
  public void addtxnFile(FileUpload fileUpload,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone,String acctno, Date txndate) throws FileuploadException;
  public List<FileUploadBean> getAccttxns4apprv(int companyid,int branchid,String userid) throws FileuploadException;
  public List<FileUploadBean> getAccttxns4view(int companyid,int branchid,String userid) throws FileuploadException;
  public void rejectFile(String batchid,int companyid,int branchid) throws FileuploadException;
  public List<FileUploadBean> getJournaltxns4apprv(int companyid,int branchid,String userid) throws FileuploadException;
  public void rejectPostingjrnl(String batchid,int companyid,int branchid,String userid, String timeZonestr) throws FileuploadException;
}
