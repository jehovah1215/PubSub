package PubSubService;

/**
 * Created by sushant.s on 23/08/20.
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import Message.Message;
import Subscriber.Subscriber;

public class PubSubService {
    private static PubSubService service_Instance;
    private static volatile ConcurrentHashMap<String, Set<Subscriber>> subscribersTopicMap;
    private static volatile ConcurrentHashMap<String, CopyOnWriteArrayList<String>> messageMap;
    private static volatile Map<String, Integer> topicOffset;

    private PubSubService() {

    }

    public static PubSubService getServiceInstance() {
        if (service_Instance == null) {
            synchronized (PubSubService.class) {
                if (service_Instance == null) {
                    service_Instance = new PubSubService();


                }
            }
        }

        return service_Instance;
    }

    public void init() {
        this.subscribersTopicMap = new ConcurrentHashMap<String, Set<Subscriber>>();
        this.messageMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<String>>();
        this.topicOffset = new HashMap<String, Integer>();

    }

    public Set<Subscriber> getSubscriberListBykey(String key) {
        synchronized (key) {
            return this.subscribersTopicMap.get(key);
        }
    }

    public void putSubscriberByKey(String key, Subscriber s) {
        synchronized (key) {
            if (this.getSubscriberListBykey(key) == null) {
                this.subscribersTopicMap.put(key, new HashSet<Subscriber>());
            }
            this.getSubscriberListBykey(key).add(s);
        }
    }

    public void removeSubscriberFromTopic(String key,Subscriber s) {
        synchronized (key){
                this.getSubscriberListBykey(key).remove(s);
        }
    }

    public CopyOnWriteArrayList<String> getMessageListByTopic(String key) {
        synchronized (key) {
            return this.messageMap.get(key);
        }
    }

    public int getOffsetByTopic(String s) {
        synchronized (s) {
            return this.topicOffset.get(s);
        }
    }

    public void updateOffset(String key, int offset) {
        synchronized (key) {
            this.topicOffset.put(key, offset);
        }
    }

    /**
     * @param key
     * @param offset -------   this should be thread safe as it can be modified by other threads for specific topic
     * @return
     */
    public List<String> getMessageByTopic(String key, int offset) {
        synchronized (key) {
            CopyOnWriteArrayList<String> messageQueue = this.getMessageListByTopic(key);
            if (messageQueue == null)
                return new ArrayList<String>();
            if (offset >= messageQueue.size() || messageQueue.isEmpty())
                return new ArrayList<String>();
            if (offset == 0) {
                offset = this.getOffsetByTopic(key);
            }
            this.updateOffset(key, messageQueue.size());
            return messageQueue.subList(offset, messageQueue.size());
        }

    }

    /**
     * map size and queue is assumed to be infine although we can have the upper limit
     *
     * @param msg
     */
    public void pushMessageByTopic(Message msg) {
        synchronized (msg.getTopic()) {
            if (this.getMessageListByTopic(msg.getTopic()) == null) {
                this.messageMap.put(msg.getTopic(), new CopyOnWriteArrayList<String>());
            }
            this.getMessageListByTopic(msg.getTopic()).add(msg.getPayload());
        }

    }

    public void createTopic(String topic) {
        synchronized (topic) {
            this.subscribersTopicMap.put(topic, new HashSet<Subscriber>());
            this.topicOffset.put(topic, 0);
            this.messageMap.put(topic, new CopyOnWriteArrayList<String>());

        }
    }
}