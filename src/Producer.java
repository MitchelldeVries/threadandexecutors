import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class Producer implements Runnable {

    private char[] pattern;

    public Producer(char[] pattern) {
        this.pattern = pattern;
    }

    public void run() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("TEST.FOO");

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            ObjectMessage prefix =  session.createObjectMessage(calculatePrefix(this.pattern));
            // Tell the producer to send the message
            System.out.println("Producer: Sent message: " + prefix.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(prefix);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    private int[] calculatePrefix(char[] pattern) {
        int i = 1;
        int j = 0;
        int patternLength = pattern.length;
        int[] prefix = new int[patternLength];

        prefix[0] = 0;

        while (i < patternLength) {
            if (pattern[i] == pattern[j]) {
                j++;
                prefix[i] = j;
                i++;
            } else if (j > 0) {
                j = prefix[j - 1];
            } else {
                prefix[i] = 0;
                i++;
            }
        }
        return prefix;
    }
}
