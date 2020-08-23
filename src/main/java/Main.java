import Message.Message;
import PubSubService.PubSubService;
import Publisher.PublisherImpl;
import Subscriber.Subscriber;
import Subscriber.SubscriberImpl;
import Publisher.IPublisher;


/**
 * Created by sushant.s on 23/08/20.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Subscriber s1 = new SubscriberImpl();
        IPublisher p1 = new PublisherImpl();
       PubSubService.getServiceInstance().init();
        PubSubService.getServiceInstance().createTopic("abc");
        s1.addSubscriber("abc");
        for(int i= 0 ;i < 5 ;i++) {
            Message message = new Message("abc", "kya " + i);
            p1.publish(message);
        }
        Thread.sleep(1000);
        s1.getMessagesForSubscriberOfTopic("abc", 0);
        Thread.sleep(1000);
        p1.publish(new Message("abc", "bhaiiiii"));
        Thread.sleep(1000);
        s1.getMessagesForSubscriberOfTopic("abc", 0);


    }
}
