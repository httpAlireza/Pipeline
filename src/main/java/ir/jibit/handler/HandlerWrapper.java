package ir.jibit.handler;

import ir.jibit.pipeline.DataTypeMissMatchException;
import ir.jibit.pipeline.Pipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapps a Handler and does the Necessary stuff for running a Handler.
 *
 * @author Alireza Khodadoost
 */

@SuppressWarnings("ALL")
public final class HandlerWrapper {

    /**
     * A pointer to the Handler which is going to be wrapped.
     */
    private Handler currentHandler;

    /**
     * A pointer to the next wrapper to pass the object to it.
     */
    private HandlerWrapper nextHandlerWrapper;

    /**
     * A logger object from slf4j package for logging messages.
     */
    private final static Logger logger = LoggerFactory.getLogger(Pipeline.class);

    public HandlerWrapper(Handler handler) {
        this.currentHandler = handler;
        nextHandlerWrapper = null;
    }

    public void setNextHandlerWrapper(HandlerWrapper nextHandlerWrapper) {
        this.nextHandlerWrapper = nextHandlerWrapper;
    }

    public Handler getCurrentHandler() {
        return currentHandler;
    }

    public HandlerWrapper getNextHandlerWrapper() {
        return nextHandlerWrapper;
    }

    /**
     * Takes an Object and applies the currentHandler function on it then passes it the next handler. Also logs the
     * Necessary stuff.
     *
     * @param input An Object to apply handler function on it.
     * @throws DataTypeMissMatchException If data type of input does not match required data type of method for the
     * input.
     */
    public Object process(Object input) {
        logger.info("handler \"" + currentHandler.getHandlerName() + "\" is processing...");
        Object object;
        try {
            if (nextHandlerWrapper == null) {
                object = currentHandler.function(input);
            } else {
                object = nextHandlerWrapper.process(currentHandler.function(input));
            }
        } catch (ClassCastException e) {
            DataTypeMissMatchException exception = new DataTypeMissMatchException("Provided data type for handler \""
                    + currentHandler.getHandlerName() +
                    "\" does not match with required data type!");
            logger.error("pipeline failed!", exception);
            throw exception;
        }

        try {
            return object;
        } catch (ClassCastException e) {
            DataTypeMissMatchException exception = new DataTypeMissMatchException("Data types of last handler output " +
                    "and pipelime output do not match!");
            logger.error("pipeline failed!", exception);
            throw exception;
        }
    }
}
