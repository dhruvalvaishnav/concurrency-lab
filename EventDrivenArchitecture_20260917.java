```java
package eventdriven;

public interface EventHandler {
    void handle(Event event);
}

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

public class EventBus {
    public void publish(Event event) { }

    public void subscribe(String eventType, EventHandler handler) { }

    public void unsubscribe(String eventType, EventHandler handler) { }
}

public class Publisher {
    private final EventBus bus;

    public Publisher(EventBus bus) {
        this.bus = bus;
    }

    public void publish(String eventType, Object payload) {
        bus.publish(new Event(eventType, payload));
    }
}

public class Subscriber {
    private final EventBus bus;

    public Subscriber(EventBus bus) {
        this.bus = bus;
    }

    public void subscribe(String eventType, EventHandler handler)