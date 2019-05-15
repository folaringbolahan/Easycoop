/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileMeta;

import com.sift.gl.model.Users;
import com.sift.votes.dao.VotMembersDao;
import com.sift.votes.model.VotDAOFactory;
import com.sift.votes.model.VotFileMeta;
import com.sift.votes.model.VotFileUpload;
import com.sift.votes.model.VotMembers;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import org.apache.commons.io.FilenameUtils;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Nelson Akpos
 */
@Service("votMemberService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotMemberServiceImpl implements VotMemberService {
        private String uploadUri;
        @Autowired
 private VotMembersDao votMemberDao;
       
         
     @Autowired
    private VotFileUploadService votFileUploadService;
     
     LinkedList<VotFileMeta> files = new LinkedList<VotFileMeta>();
        private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotMemberServiceImpl.class);
         VotFileMeta fileMeta = null;
    
        public String performBulkSave2(MultipartHttpServletRequest request, Users user) throws PersistentException {
         System.out.println("i am in the perform bulk save method ");
        //PersistentTransaction t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
        String pattern = "yyyy-MM-dd";
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uploadUri = (String) ctx.lookup("java:comp/env/uploadpath");
        } catch (NamingException ex) {
            _logger.error("upload directory error", ex);
        }
        //String rootPath = System.getProperty("catalina.home");
        String rootPath = uploadUri;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date trx_date = null;
        Iterator<String> itr = request.getFileNames();
        System.out.println("file name from itr "+ request.getFileNames());
        MultipartFile mpf = null;
      //  String productId = request.getParameter("productId");
        int noOfRecords = Integer.valueOf(request.getParameter("noOfRecords"));
        int agmid = Integer.valueOf(request.getParameter("description"));
        System.out.println("The number of records are "+ noOfRecords);
      
        String referenceNumber = String.valueOf(System.currentTimeMillis());
        FilenameUtils fileUTIL = new FilenameUtils();

        String ext = null;
        String baseName = null;
        String serverFileName = null;

        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = dir.getAbsolutePath() + File.separator;


        while (itr.hasNext()) {

           
            mpf = request.getFile(itr.next());
           
            ext = fileUTIL.getExtension(mpf.getOriginalFilename());
            baseName = fileUTIL.getBaseName(mpf.getOriginalFilename());

            serverFileName = rootPath + "tmpfiles" + File.separator + baseName + "_" + referenceNumber + "." + ext;
            System.out.println("the server file name is "+serverFileName);
            System.out.println("original file name "+ mpf.getOriginalFilename());
            System.out.println("the useid id is "+user.getUserId());

            //create the file on server

            VotFileUpload votfileUpload = new VotFileUpload();
            votfileUpload.setBranchId(agmid);
            votfileUpload.setCompanyId(user.getCompanyid());
           votfileUpload.setFileName(mpf.getOriginalFilename());
            votfileUpload.setLocation(serverFileName);
            votfileUpload.setReferenceNumber(referenceNumber);
            //fileUpload.setProductId(Integer.valueOf(productId));
           votfileUpload.setUserId(user.getUserId());
           votfileUpload.setProcessedDate(new Date());
            votfileUpload.setTotalRecords(noOfRecords);
            //fileUpload.setFileSum(totalSum);
           // System.out.println("totalSum in SavongsServiceImpl :: " +  totalSum);
           
            //votfileUpload.setFileSum(totalSum);
            //fileUpload.setProcessedSum(processedSum);
            if (mpf.getOriginalFilename().equals("")==false) {
             votFileUploadService.save(votfileUpload);
            }
           //.save(votfileUpload);
            

            System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());
            System.out.println("The file size  "+ mpf.getSize() );




            try {
                if (files.size() >= 10) {
                    files.pop();
                }


                fileMeta = new VotFileMeta();
                fileMeta.setFileName(mpf.getOriginalFilename());
                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());
                fileMeta.setBytes(mpf.getBytes());
                // to set the refrenceNumber
                fileMeta.setReferenceNumber(referenceNumber);
              System.out.println("file size from file meta " + fileMeta.getFileSize() );
                File serverFile = new File(serverFileName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(mpf.getBytes());
                stream.close();

                
            } catch (FileNotFoundException e) {
               
                e.printStackTrace();
            } catch (IOException e) {
               
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception ex) {
               
                ex.printStackTrace();
            }
            //2.4 add to files
            files.add(fileMeta);
            System.out.println("files added ");

        }


       // return files;
        return referenceNumber;
    }
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)  
    public void save(VotMembers votmembers){
  
    votMemberDao.save(votmembers);
    }
    public List<VotMembers > listApprovedAgmMembers(int agmid){
         return votMemberDao.listApprovedAgmMembers(agmid);
    }
    public boolean getMemberValidity(String email,int agmid){
       return votMemberDao.getMemberValidity(email,agmid);
    }
    public List<VotMembers > listAgmMemberDetailsByAgmid(int agmid){
       return votMemberDao.listAgmMemberDetailsByAgmid(agmid);
    }
    public  VotMembers getVotMemberDetails(int id){
       return votMemberDao.getVotMemberDetails(id);
    }


}
