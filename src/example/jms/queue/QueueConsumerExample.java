package example.jms.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueConsumerExample extends AbstarctQueueExample
{
	public void run() throws NamingException, JMSException
	{
		InitialContext ctx = getInitialContext();
		Queue queue = getQueue(ctx);
		QueueConnectionFactory connFactory = getQueueConnectionFactory(ctx);
		QueueConnection queueConn = getQueueConnection(connFactory);
		QueueSession queueSession = getQueueSession(queueConn);
		
		/* create a queue consumer (asynchronous style) */
		MessageConsumer msgConsumer = queueSession.createConsumer(queue);
		TextListener textListener = new TextListener();
		msgConsumer.setMessageListener(textListener);
		
		/* print what we did */
		System.out.println("start!");
		
		/* wait the message */
		queueConn.start();
		while (true)
		{
			
		}
		
		/* close the queue connection */
//		queueSession.close();
//		queueConn.close();
	}
	
	
	class TextListener implements MessageListener
	{
		/**
		 * Casts the message to a TextMessage and displays its text. A non-text
		 * message is interpreted as the end of the message stream, and the message
		 * listener sets its monitor state to all done processing messages.
		 * 
		 * @param message the incoming message
		 */
		public void onMessage(Message message)
		{
			if (message instanceof TextMessage)
			{
				TextMessage msg = (TextMessage) message;

				try
				{
					System.out.println("Reading message: " + msg.getText());
				}
				catch (JMSException e)
				{
					System.out.println("Exception in onMessage(): " + e.toString());
				}
			}
			else
			{
				throw new RuntimeException("TextListener just supports TextMessage now!");
			}
		}
	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) throws NamingException, JMSException
	{
		new QueueConsumerExample().run();
	}
}
