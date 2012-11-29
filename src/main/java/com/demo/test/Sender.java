package com.demo.test;

import com.tibco.tibjms.Tibjms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;

public class Sender {
    private JmsTemplate jmsTemplate;

    public void send(String message) {
        jmsTemplate.send(new LoanMessageCreator(message));
    }

    // has state, do not reuse
    private static final class LoanMessageCreator implements MessageCreator {
        private final String str;

        private LoanMessageCreator(String str) {
            this.str = str;
        }

        @Override
        public Message createMessage(Session session) throws JMSException {
            TextMessage result = session.createTextMessage(str);
            result.setBooleanProperty(Tibjms.JMS_TIBCO_COMPRESS, true);

            return result;
        }
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
