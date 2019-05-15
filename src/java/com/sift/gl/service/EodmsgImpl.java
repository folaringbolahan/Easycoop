/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;
import com.sift.gl.dao.*;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.model.Accountsegmentdetlist;
import com.sift.gl.model.Periodend;
import java.io.Serializable;
import java.util.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.JmsTemplate;


/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class EodmsgImpl implements Eodmsginter
{
    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMsgAlert(final Periodend periodendmessage) {
      jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session)
               throws JMSException {
                    return session.createObjectMessage((Serializable)periodendmessage);
               }
      }
     );
    }
}
