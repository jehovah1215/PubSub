import Message.Message;
import PubSubService.PubSubService;
import PublishThread.PublishThread;
import Publisher.PublisherImpl;
import Subscriber.Subscriber;
import Subscriber.SubscriberImpl;
import Publisher.Publisher;
import SubscriberThread.SubscriberThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by sushant.s on 23/08/20.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        PubSubService.getServiceInstance().init();
        PubSubService.getServiceInstance().createTopic("abc");
        PubSubService.getServiceInstance().createTopic("xyz");
        Publisher p = new PublisherImpl();
        Subscriber s1 = new SubscriberImpl();
        Subscriber s2 = new SubscriberImpl();

        s1.addSubscriber("abc");
        s1.addSubscriber("abc");
        s2.addSubscriber("xyz");
        ExecutorService publishExecutor =  Executors.newFixedThreadPool(3);
        for(int i=0;i<100;i++){
            PublishThread pb = new PublishThread(new Message(i%2==0?"abc":"xyz","msg"+i),p);
            publishExecutor.execute(pb);
        }
        ExecutorService subExecutor =  Executors.newFixedThreadPool(2);
        SubscriberThread sb = new SubscriberThread("abc",0,s1);
        SubscriberThread sb1 = new SubscriberThread("xyz",2,s2);
        subExecutor.execute(sb);
        subExecutor.execute(sb1);


    }
}
