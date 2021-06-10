package it.distributedsystems.model.logging;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.ejb.ActivationConfigProperty;

import org.apache.log4j.Logger;

@MessageDriven(
        mappedName = "LogReceiverBean",
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = "queue/LoggingQueue")
        }
)
public class LogReceiverBean implements MessageListener {

    private static Logger logger = Logger.getLogger(LogReceiverBean.class.getName());

    public LogReceiverBean() {

    }

    public void onMessage(Message message) {
        try {
            logger.info(((ObjectMessage) message).getObject().toString());
        } catch (JMSException e) {
            logger.error("Error reading message", e);
        }
    }
}
