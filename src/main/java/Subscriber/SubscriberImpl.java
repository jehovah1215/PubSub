package Subscriber;

import PubSubService.PubSubService;

import java.util.List;

/**
 * Created by sushant.s on 23/08/20.
 */
public class SubscriberImpl extends Subscriber {



    @Override
    public void addSubscriber(String topic) {
        PubSubService.getServiceInstance().putSubscriberByKey(topic,this);
    }

    @Override
    public void unSubscribe(String topic) {
        PubSubService.getServiceInstance().removeSubscriberFromTopic(topic,this);
    }

    @Override
    public void getMessagesForSubscriberOfTopic(String topic, int offset) {
        List<String> messages = PubSubService.getServiceInstance().getMessageByTopic(topic,offset);
        if(messages==null||messages.isEmpty())
        {
            System.out.print("No more messages");
            return;
        }

        for(String str : messages) {
            System.out.println("messages  from topic - "+ topic + " are " + str);
        }

    }

    @Override
    public void getMessageStream(String topic) throws InterruptedException {
        while (true) {

            List<String> messages = PubSubService.getServiceInstance().getMessageByTopic(topic,0);
            if(messages==null||messages.isEmpty())
            {
                System.out.print("No more messages");
                return;
            }

            for(String str : messages) {
                System.out.println("messages  from topic - "+ topic + " are " + messages);
            }
            Thread.sleep(1000);
        }
    }
}
