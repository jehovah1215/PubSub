package PublishThread;

import Message.Message;
import Publisher.Publisher;

/**
 * Created by sushant.s on 23/08/20.
 */
public class PublishThread implements Runnable{
   Message msg;
   Publisher p;
    public PublishThread(Message msg,Publisher p) {
        this.msg=msg;
        this.p=p;
    }
    public void run() {
       p.publish(msg);

    }
}
