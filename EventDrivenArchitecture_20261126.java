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
    private final EventBus bus;
    public Publisher(EventBus bus) { this.bus = bus; }
    public void publish(Event event) { bus.publish(event); }
}

public class Subscriber {
    private final EventBus bus;
    public Subscriber(EventBus bus) { this.bus = bus; }
    public <E extends Event> void subscribe(String eventType, EventHandler<E> handler) {
        bus.subscribe(eventType, handler);
    }
    public <E extends Event> void unsubscribe(String eventType, EventHandler<E> handler) {
        bus.unsubscribe(eventType, handler);
    }
}
```