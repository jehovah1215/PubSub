package Subscriber;

/**
 * Created by sushant.s on 23/08/20.
 */
import Message.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Subscriber {
    private List<Message> subscriberMessages = new ArrayList<Message>();

    //Add subscriber with PubSubService for a topic
    public abstract void addSubscriber(String topic);

    //Unsubscribe subscriber with PubSubService for a topic
    public abstract void unSubscribe(String topic);

    //Request specifically for messages related to topic from PubSubService
    public abstract void getMessagesForSubscriberOfTopic(String topic,int offset);

    public abstract void getMessageStream(String topic) throws InterruptedException;
}
