package ir.jibit.handler;

/**
 * Represents an operation that accepts a single input argument and returns an object
 *
 * @param <I> the type of the input to the operation
 * @param <O> the type of te output of function
 */
@FunctionalInterface
public interface Handler<I, O> {
    O function(I input);
}
