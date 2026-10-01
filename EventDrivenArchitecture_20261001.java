```java
public interface Event {}

public interface Subscriber<E extends Event> {
    void onEvent(E event);
}

public interface Publisher<E extends Event> {
    void publish(E event);
}

public class EventBus {
    public <E extends Event> void register(Class<E> eventType, Subscriber<E> subscriber) {}
    public <E extends Event> void unregister(Class<E> eventType, Subscriber<E> subscriber) {}
    public <E extends Event> void publish(E event) {}
}

public class SampleEvent implements Event {
    private final String data;
    public SampleEvent(String data) { this.data = data; }
    public String getData() { return data; }
}

public class SampleSubscriber implements Subscriber<SampleEvent> {
    @Override
    public void onEvent(SampleEvent event) {}
}

public class SamplePublisher implements Publisher<SampleEvent> {
    private final EventBus bus;
    public SamplePublisher(EventBus bus) { this.bus = bus; }
    @Override
    public void publish(SampleEvent event) { bus.publish(event); }
}
```