
package com.cn.hebiao;

import java.util.List;
import java.util.Map.Entry;

/**
 * @author hebiao
 * @version $Id:RealSubject.java, v0.1 2018/9/6 11:30 hebiao Exp $$
 */
public class RealSubject extends AbstractSubject {

  public RealSubject(String topic) {
    super(topic);
  }

  @Override
  public void notifyObservers(Object args) {
    for (Entry<String, List<Iobervser>> iobervsers : topicIobervers.entrySet()) {
      if (iobervsers.getKey().equals(this.topic)) {
        List<Iobervser> list = iobervsers.getValue();
        for (Iobervser iobervser : list) {
          iobervser.update(topic);
        }
      }
    }
  }


  public static void main(String[] args) {
    RealSubject topic1 = new RealSubject("book");
    RealSubject topic2 = new RealSubject("music");
    Iobervser ob1 = new Observer();
    Iobervser ob2 = new Observer();
    topic1.subscribe(ob1);
    topic2.subscribe(ob2);
    topic1.notifyObservers("book");
    topic2.notifyObservers("music");
  }
}
