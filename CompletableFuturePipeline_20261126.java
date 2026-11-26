```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class CompletableFuturePipeline<I, O> {

    private final List<Function<?, ?>> stages = new ArrayList<>();
    private Executor executor;

    public CompletableFuturePipeline() {}

    public CompletableFuturePipeline<I, O> withExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public <T> CompletableFuturePipeline<I, T> addStage(Function<? super I, ? extends T> stage) {
        stages.add(stage);
        return (CompletableFuturePipeline<I, T>) this;
    }

    public CompletableFuture<O> execute(I input) {
        CompletableFuture<Object> future = CompletableFuture.completedFuture(input);
        for (Function<?, ?> stage : stages) {
            future = future.thenApplyAsync((Function<Object, Object>) stage, executor);
        }
        return (CompletableFuture<O>) future;
    }

    public CompletableFuture<O> execute(I input, Function<Throwable, O> errorHandler) {
        CompletableFuture<O> future = execute(input);
        return future.exceptionally(errorHandler);
    }
}
```