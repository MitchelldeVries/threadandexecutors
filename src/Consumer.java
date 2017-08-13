import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class Consumer implements Runnable, ExceptionListener {
    private char[] text;
    private char[] pattern;

    public Consumer(char[] text, char[] pattern) {
        this.text = text;
        this.pattern = pattern;
    }

    public void run() {
        try {

            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("TEST.FOO");

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive(1000);

            if (message instanceof ObjectMessage) {
                ObjectMessage textMessage = (ObjectMessage) message;
                int[] text = (int[]) textMessage.getObject();
                System.out.println("Consumer: Received: " + Arrays.toString(text));
                System.out.println("Consumer: Number of occurrences:" + search(text));
            } else {
                System.out.println("Consumer: Received: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }

    public int search(int[] prefix) {
        int occurrences = 0;
        int i = 0;
        int j = 0;
        int textLength = text.length;
        int patternLength = pattern.length;

        while (i < textLength) {
            if (text[i] == pattern[j]) {
                if (j == patternLength - 1) {
                    i++;
                    occurrences++;
                } else {
                    i++;
                    j++;
                }
            } else if (j > 0) {
                j = prefix[j - 1];
            } else {
                i++;
            }
        }
        return occurrences;
    }
}
