package Publisher;

import Message.Message;
import PubSubService.PubSubService;

/**
 * Created by sushant.s on 23/08/20.
 */
public class PublisherImpl implements Publisher {

    public void publish(Message m) {
        PubSubService.getServiceInstance().pushMessageByTopic(m);

    }
}
