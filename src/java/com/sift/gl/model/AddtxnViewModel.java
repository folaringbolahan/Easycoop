/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import java.util.LinkedList;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

/**
 *
 * @author yomi-pc
 */
public class AddtxnViewModel {
        
	private String msg;
        @Command @NotifyChange("msg")
         public void add(){
          //txnList.add(item); //add an item
         msg = "Added Transaction"; //update message
         }
        //other
  
}
