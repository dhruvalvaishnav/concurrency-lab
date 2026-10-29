```java
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;

public class CompletableFuturePipeline<T, R> {

    private final List<Function<?, ?>> stages = new ArrayList<>();

    public <S> CompletableFuturePipeline<T, S> addStage(Function<? super T, ? extends S> stage) {
        stages.add(stage);
        return (CompletableFuturePipeline<T, S>) this;
    }

    public CompletableFuture<R> execute(T input) {
        CompletableFuture<Object> future = CompletableFuture.completedFuture(input);
        for (Function<?, ?> stage : stages) {
            future = future.thenApply((Function<Object, Object>) stage);
        }
        return (CompletableFuture<R>) future;
    }

    public CompletableFuturePipeline<T, R> thenCompose(Function<? super R, ? extends CompletableFuture<?>> nextStage) {
        stages.add(nextStage);
        return this;
    }

    public CompletableFuturePipeline<T, R> exceptionally(Function<Throwable, ? extends R> handler) {
        stages.add(handler);
        return this;
    }

    public CompletableFuturePipeline<T, R> whenComplete(BiConsumer<? super R, ? super Throwable> action) {
        stages.add(action);
        return this;
    }
}
```