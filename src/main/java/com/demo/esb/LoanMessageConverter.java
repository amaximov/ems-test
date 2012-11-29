package com.demo.esb;

import com.demo.model.Loan;
import com.tibco.tibjms.Tibjms;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class LoanMessageConverter implements MessageConverter {
    private SimpleMessageConverter converter;

    private static final String COMPRESS_PROPERTY = Tibjms.JMS_TIBCO_COMPRESS; // could be property-configured if needed

    // could delegate to another converter that only does the compression flag (so a chain of converters on the way out)
    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        Message result = converter.toMessage(object, session);
        result.setBooleanProperty(COMPRESS_PROPERTY, true);

        return result;
    }

    @Override
    public Loan fromMessage(Message message) throws JMSException, MessageConversionException {
        Object extracted = converter.fromMessage(message);
        if (!(extracted instanceof String)) {
            throw new IllegalArgumentException("unsupported message type: " + extracted.getClass());
        }

        return parseMessage((String) extracted);
    }

    // in reality use spring-provided Unmarshaller implementation, that converts automatically, once plugged in
    private Loan parseMessage(String message) {
        Loan result = new Loan();

        String[] lines = message.split(";");
        for (String line : lines) {
            if (line.startsWith("id=")) {
                result.setId(line.substring(3));
            } else if (line.startsWith("value=")) {
                result.setValue(Double.parseDouble(line.substring(6)));
            }
        }

        return result;
    }

    public void setConverter(SimpleMessageConverter converter) {
        this.converter = converter;
    }
}
