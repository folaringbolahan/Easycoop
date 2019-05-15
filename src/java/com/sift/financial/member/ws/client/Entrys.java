/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.ws.client;

import com.sift.financial.member.ws.client.Entry;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yomi-pc
 */
@XmlRootElement
@XmlSeeAlso({Entry.class,com.sift.financial.member.ws.client.Txnsheader.class})
//public class Entrys extends java.util.LinkedList<Entry> {
public class Entrys extends java.util.LinkedList<Entry> {
       private static final long serialVersionUID = 1L;
       private com.sift.financial.member.ws.client.Txnsheader txnsheader;
       private List<Entry> entrys;
   /* public Entrys() {
        //super();
    }*/
    /*public Entrys(Collection<? extends Entry> c) {
        super(c);
    }*/
    @XmlElement(name = "Txnsheader")
    public com.sift.financial.member.ws.client.Txnsheader getTxnsheader() {
        return this.txnsheader;
        //return this;
    }
    public void setTxnsheader(com.sift.financial.member.ws.client.Txnsheader txnsheader) {
        this.txnsheader = txnsheader;
    }
    @XmlElement(name = "Entry")
    public List<Entry> getEntrys() {
        return this.entrys;
    }
    public void setEntrys(List<Entry> entrys) {
        this.entrys = entrys;
    }
}