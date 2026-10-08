```java
package com.example.eventdriven;

public interface Event {
    String getType();
    Object getPayload();
}

public interface EventHandler<E extends Event> {
    void handle(E event);
}

public class EventBus {
    public void register(String eventType, EventHandler<? extends Event> handler) {}
    public void unregister(String eventType, EventHandler<? extends Event> handler) {}
    public void publish(Event event) {}
}

public interface Publisher {
    void publish(String eventType, Object payload);
}

public interface Subscriber {
    void subscribe(String eventType, EventHandler<? extends Event> handler);
    void unsubscribe(String eventType, EventHandler<? extends Event> handler);
}
```