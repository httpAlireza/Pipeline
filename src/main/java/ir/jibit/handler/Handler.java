package ir.jibit.handler;

/**
 * An abstract class witch has only one functioning method witch takes a single argument and returns the result.
 *
 * @param <I> the type of the input to the operation
 * @param <O> the type of te output of function
 * @author Alireza khodadoust
 */

public abstract class Handler<I, O> {
    /**
     * Saves a string as id for the handler.
     */
    private String handlerId;

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        if (handlerId == null || handlerId.isBlank()) {
            throw new IllegalArgumentException("handler id can not be null or blank!");
        }
        this.handlerId = handlerId;
    }
    /** The abstract method witch should be overridden for the purpose.
     * @param input the object witch enters to the handler.
     * @return an object of O type.
     */
    public abstract O function(I input);
}
