```java
package eventdriven;

public interface Event {
    String getType();
}

public interface EventHandler<T extends Event> {
    void handle(T event);
}

public class EventBus {
    public <T extends Event> void subscribe(String eventType, EventHandler<T> handler) {}
    public <T extends Event> void unsubscribe(String eventType, EventHandler<T> handler) {}
    public void publish(Event event) {}
}

public class Publisher {
    private final EventBus bus;
    public Publisher(EventBus bus) { this.bus = bus; }
    public void publish(Event event) { bus.publish(event); }
}

public class Subscriber {
    private final EventBus bus;
    public Subscriber(EventBus bus) { this.bus = bus; }
    public <T extends Event> void subscribe(String eventType, EventHandler<T> handler) {
        bus.subscribe(eventType, handler);
    }
    public <T extends Event> void unsubscribe(String eventType, EventHandler<T> handler) {
        bus.unsubscribe(eventType, handler);
    }
}
```