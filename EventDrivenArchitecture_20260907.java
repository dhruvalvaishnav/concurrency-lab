public interface Event {
}

public interface Subscriber<E extends Event> {
    void onEvent(E event);
}

public class EventBus {
    public <E extends Event> void subscribe(Class<E> eventType, Subscriber<E> subscriber) {
    }

    public <E extends Event> void unsubscribe(Class<E> eventType, Subscriber<E> subscriber) {
    }

    public void publish(Event event) {
