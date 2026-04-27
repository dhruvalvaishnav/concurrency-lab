```java
package eventdriven;

public class Event {
    private final String type;
    private final Object payload;

    public Event(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }
}
```

```java
package eventdriven;

public interface Subscriber {
    void onEvent(Event event);
}
```

```java
package eventdriven;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private final List<Subscriber> subscribers = new CopyOnWriteArrayList<>();

    public void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unregister(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void publish(Event event) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onEvent(event);
        }
    }
}
```

```java
package eventdriven;

public class Publisher {
    private final EventBus eventBus;

    public Publisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void publish(String type, Object payload) {
        eventBus.publish(new Event(type, payload));
    }
}
```

```java
package eventdriven;

public class SampleSubscriber implements Subscriber {
    @Override
    public void onEvent(Event event) {
        // handle event
    }
}
```