/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import java.util.List;


//public class Entrys extends java.util.LinkedList<Entry> {
public class Generictableviewbean extends java.util.LinkedList<String> {
       private static final long serialVersionUID = 1L;
       private List<String> header;
       private List<List<String>> body;
       private String pagetitle;
    public String getPagetitle() {
        return this.pagetitle;
    }
    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }
    public List<String> getHeader() {
        return this.header;
    }
    public void setHeader(List<String> header) {
        this.header = header;
    }
    public List<List<String>> getBody() {
        return this.body;
    }
    public void setBody(List<List<String>> body) {
        this.body = body;
    }
}