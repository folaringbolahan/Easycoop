<%@page import="java.io.*"%>
<%@page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>

<%

     //File f = new File ("c:/apps/tomcatwebapps/NewCCP/uploads/" + request.getParameter("filename") );
     File f = new File (request.getParameter("filename") );
     response.setContentType ("application/vnd.ms-excel"); 
     response.setHeader ("Content-Disposition", "attachment; filename=" + request.getParameter("filename")); 
     
     String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length()); 
     InputStream in = new FileInputStream(f); 
     ServletOutputStream outs = response.getOutputStream();

     int bit = 256; 
     int i = 0; 
     try { 
        while ((bit) >= 0) {
            bit = in.read(); outs.write(bit); 
         } 
     } catch (IOException ioe) {
         ioe.printStackTrace(System.out); 
     } 
     
     outs.flush(); 
     outs.close(); 
     in.close(); 
%>
<%--
  File filespec = null;
  String filename=(String)request.getParameter("fname");
  String path=request.getContextPath("/uploads/");
  
  String message=""; 

  String filespecTmp = filename.replace(".jsp",""); 

  if(filespecTmp == null || filespecTmp.equals("") || filespecTmp.equalsIgnoreCase("(NONE)")){
     filespec = null;
  }else{          
     filespec = new File(path + "/" + filespecTmp);
  } 

  boolean checkFile=filespec.exists();

  if(checkFile==false){
     message="File Does Not Exist";
  }

  if(filespec != null){
    filename = filespec.getName();          
  }

  //response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-Disposition","attachment; filename="+filename);
  response.setHeader("Content-Description", filename);
  out.clearBuffer();
  FileInputStream reader = new FileInputStream(filespec);
  int c;

  while (( c=reader.read()) != -1){
	out.print((char)c);
  }

  reader.close();
  out.flush();
  --%>