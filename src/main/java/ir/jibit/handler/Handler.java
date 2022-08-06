package ir.jibit.handler;

/**
 * An abstract class which has only one functioning method which takes a single argument and returns the result.
 *
 * @param <I> the type of the input to the operation
 * @param <O> the type of te output of function
 * @author Alireza khodadoust
 */

public abstract class Handler<I, O> {
    /**
     * Saves a string as the name of the handler.
     */
    private String handlerName;

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        if (handlerName == null || handlerName.isBlank()) {
            throw new IllegalArgumentException("handler id can not be null or blank!");
        }
        this.handlerName = handlerName;
    }

    /**
     * The abstract method which should be overridden for the purpose.
     *
     * @param input the object which enters to the handler.
     * @return an object of O type.
     */
    public abstract O function(I input);
}
