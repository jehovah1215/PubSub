package SubscriberThread;

import Subscriber.Subscriber;
import Subscriber.SubscriberImpl;


/**
 * Created by sushant.s on 23/08/20.
 */
public class SubscriberThread implements Runnable {
    int offset;
    String topic;
    Subscriber subscriber;
    public SubscriberThread(String topic,int offset, Subscriber s){
        this.offset=offset;
        this.topic=topic;
        this.subscriber = s;
    }



    public void run() {
        subscriber.getMessagesForSubscriberOfTopic(topic,offset);

    }
}
