package com.jxx.jms;


import com.unilife.commons.jms.JmsSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:*.xml")
public class jmsSender {

    @Lazy
    @Autowired(required = false)
    public JmsTemplate jmsTemplate;
    @Test
    public void sendMsg (){
        MessageCreator msg = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("test");
                return textMessage;
            }
        };
        jmsTemplate.convertAndSend("test",msg);

    }
}
