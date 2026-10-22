```java
public interface Event {
    String getType();
    Object getPayload();
}

public interface EventHandler {
    void handle(Event event);
}

public class EventBus {
    public void subscribe(String eventType, EventHandler handler) {}
    public void unsubscribe(String eventType, EventHandler handler) {}
    public void publish(Event event) {}
}

public class Publisher {
    private final EventBus eventBus;
    public Publisher(EventBus eventBus) { this.eventBus = eventBus; }
    public void publish(Event event) { eventBus.publish(event); }
}

public class Subscriber {
    private final EventBus eventBus;
    public Subscriber(EventBus eventBus) { this.eventBus = eventBus; }
    public void subscribe(String eventType, EventHandler handler) { eventBus.subscribe(eventType, handler); }
    public void unsubscribe(String eventType, EventHandler handler) { eventBus.unsubscribe(eventType, handler); }
}
```