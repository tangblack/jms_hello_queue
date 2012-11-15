package example.jms.queue;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueSenderExample extends AbstarctQueueExample
{
	public void run() throws NamingException, JMSException
	{
		InitialContext ctx = getInitialContext();
		Queue queue = getQueue(ctx);
		QueueConnectionFactory connFactory = getQueueConnectionFactory(ctx);
		QueueConnection queueConn = getQueueConnection(connFactory);
		QueueSession queueSession = getQueueSession(queueConn);
		
		/* create a queue sender */
		QueueSender queueSender = queueSession.createSender(queue);
		queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		/* create a simple message to say "Hello" */
		TextMessage message = queueSession.createTextMessage("Hello");

		/* send the message */
		queueSender.send(message);

		/* print what we did */
		System.out.println("sent: " + message.getText());

		/* close the queue connection */
		queueSession.close();
		queueConn.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		new QueueSenderExample().run();
	}
}
