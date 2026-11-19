```java
public interface Event {
    String getType();
    Object getPayload();
}

public interface EventHandler {
    void handle(Event event);
}

public class EventBus {
    public void register(String eventType, EventHandler handler) {}
    public void unregister(String eventType, EventHandler handler) {}
    public void publish(Event event) {}
}

public abstract class Publisher {
    protected EventBus eventBus;
    public Publisher(EventBus eventBus) { this.eventBus = eventBus; }
    public abstract void publishEvent(String type, Object payload);
}

public abstract class Subscriber {
    protected EventBus eventBus;
    public Subscriber(EventBus eventBus) { this.eventBus = eventBus; }
    public abstract void subscribe(String eventType);
    public abstract void unsubscribe(String eventType);
}
```