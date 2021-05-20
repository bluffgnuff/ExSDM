package it.distributedsystems.model.logging;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.*;

public class OperationLogger {

    @Resource(name="InVmConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name ="java:/queue/LoggingQueue")
    private Destination queue;

    public OperationLogger() {	}

    @AroundInvoke
    public Object logOperation(InvocationContext invocationContext)
            throws Exception{
        //Creazione connessione e sessione JMS
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);
        connection.start();

        //Esecuzione logica di business
        Object result = invocationContext.proceed();

        //invio messaggio
        ObjectMessage message = session.createObjectMessage(
                "SUCCESSFULLY INVOKED "+invocationContext.getClass().getSimpleName() +
                        "."+invocationContext.getMethod().getName());
        producer.send(message);

        //Chiusura sessione e connessione JMS
        session.close();
        connection.close();

        return result;
    }
}
