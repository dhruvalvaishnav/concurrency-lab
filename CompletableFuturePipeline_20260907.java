```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CompletableFuturePipeline<T, R> {
    private final List<Function<?, ?>> stages = new ArrayList<>();

    public <S> CompletableFuturePipeline<T, S> addStage(Function<? super T, ? extends S> stage) {
        stages.add(stage);
        return (CompletableFuturePipeline<T, S>) this;
    }

    public CompletableFuture<R> run(T input) {
        CompletableFuture<Object> future = CompletableFuture.completedFuture(input);
        for (Function<?, ?> stage : stages) {
            future = future.thenApply((Function<Object, Object>) stage);
        }
        return (CompletableFuture<R>) future;
    }

    public CompletableFuturePipeline<T, R> thenAccept(java.util.function.Consumer<? super R> action) {
        CompletableFuture<R> future = run(null);
        future.thenAccept(action);
        return this;
    }

    public CompletableFuturePipeline<T, R> exceptionally(java.util.function.Function<Throwable, ? extends R> fn) {
        CompletableFuture<R> future = run(null);
        future.exceptionally(fn);
        return this;
    }
}
```