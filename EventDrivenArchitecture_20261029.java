```java
public interface Event {
    String getType();
}

public interface Subscriber {
    void handleEvent(Event event);
}

public class EventBus {
    public void subscribe(String eventType, Subscriber subscriber) {}
    public void unsubscribe(String eventType, Subscriber subscriber) {}
    public void publish(Event event) {}
}

public class Publisher {
    private final EventBus eventBus;
    public Publisher(EventBus eventBus) { this.eventBus = eventBus; }
    public void publishEvent(Event event) { eventBus.publish(event); }
}
```