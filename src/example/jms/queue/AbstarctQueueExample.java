package example.jms.queue;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public abstract class AbstarctQueueExample
{
	/* 
	 * Note
	 *  -Need to set up which role(default: guest) can access this queue.
	 *  -Need to add a user belong to this role(ex: jms=guest). 
	 */
	public static final String USER = "jms"; 					//TODO Change user name!!
	public static final String PASSWORD = "jmsjboss"; 			//TODO Change password!!
	public static final String QUEUE_NAME = "jms/queue/test";
	public static final String CONNECTION_FACTORY_NAME = "jms/RemoteConnectionFactory";
	
	/** get the initial context */
	public InitialContext getInitialContext() throws NamingException
	{
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "remote://localhost:4447");
		env.put(Context.SECURITY_PRINCIPAL, USER); 			// user
		env.put(Context.SECURITY_CREDENTIALS, PASSWORD); 	// password
		return new InitialContext(env);
	}
	
	/** lookup the queue object */
	public Queue getQueue(InitialContext ctx) throws NamingException
	{
		return (Queue) ctx.lookup(QUEUE_NAME);
	}
	
	/** lookup the queue connection factory */
	public QueueConnectionFactory getQueueConnectionFactory(InitialContext ctx) throws NamingException
	{
		return (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY_NAME);
	}
	
	/** create a queue connection */
	public QueueConnection getQueueConnection(QueueConnectionFactory connFactory) throws JMSException
	{
		return connFactory.createQueueConnection(USER, PASSWORD); // user/password
	}
	
	/** 
	 * create a queue session 
	 */
	public QueueSession getQueueSession(QueueConnection queueConn) throws JMSException
	{
		/*
		 * public static final int AUTO_ACKNOWLEDGE = 1;
		 * ���۰ʽT�{�A�Ȥ�ݵo�e�M�����������ݭn���B�~���u�@�C
		 * 
		 * public static final int CLIENT_ACKNOWLEDGE = 2;
		 * ���Ȥ�ݽT�{�C�Ȥ�ݱ����������A�����ե�javax.jms.Message��acknowledge��k�Cjms�A�Ⱦ��~�|�R�������C
		 * 
		 * public static final int DUPS_OK_ACKNOWLEDGE = 3;
		 * ���\�ƥ����T�{�Ҧ��C�@�����������ε{�Ǫ���k�եαq�B�z�����B��^
		 * �A�|�ܹ�H�N�|�T�{�����������F�ӥB���\���ƽT�{�C�b�ݭn�Ҽ{�귽�ϥήɡA�o�ؼҦ��D�`���ġC
		 */
		return queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	}
}
