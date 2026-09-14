```java
package eventdriven;

public class Event {
    private final String type;
    private final Object payload;

    public Event(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() { return type; }
    public Object getPayload() { return payload; }
}
```

```java
package eventdriven;

public interface EventHandler {
    void handle(Event event);
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

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final ConcurrentHashMap<String, Set<Subscriber>> subscribers = new ConcurrentHashMap<>();

    private EventBus() {}

    public static EventBus getInstance() { return INSTANCE; }

    public void subscribe(String eventType, Subscriber subscriber) {
        subscribers.computeIfAbsent(eventType, k -> ConcurrentHashMap.newKeySet()).add(subscriber);
    }

    public void unsubscribe(String eventType, Subscriber subscriber) {
        Set<Subscriber> set = subscribers.get(eventType);
        if (set != null) {
            set.remove(subscriber);
        }
   