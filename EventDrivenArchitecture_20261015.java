```java
package eventdriven;

public interface Event {
    String getType();
}

public interface EventHandler<E extends Event> {
    void handle(E event);
}

public class EventBus {
    public <E extends Event> void subscribe(String eventType, EventHandler<E> handler) {}
    public <E extends Event> void unsubscribe(String eventType, EventHandler<E> handler) {}
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
    public <E extends Event> void subscribe(String eventType, EventHandler<E> handler) {
        eventBus.subscribe(eventType, handler);
    }
    public <E extends Event> void unsubscribe(String eventType, EventHandler<E> handler) {
        eventBus.unsubscribe(eventType, handler);
    }
}
```