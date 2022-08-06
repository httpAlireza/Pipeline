package ir.jibit.pipeline;


import ir.jibit.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * Takes some handlers and store them in an LinkedList and executes handlers functions on an input object
 *
 * @param <I> the type of the input to the pipeline
 * @param <O> the type of te output of pipeline
 * @author Alireza Khodadoost
 */
@SuppressWarnings("ALL")
public final class Pipeline<I, O> {
    /**
     * A linkedlist which saving all the handlers of the pipeline.
     */
    private final LinkedList<Handler> handlers;

    /**
     * A logger object from slf4j package for logging messages.
     */
    private final Logger logger;

    public Pipeline() {
        handlers = new LinkedList<>();
        logger = LoggerFactory.getLogger(Pipeline.class);
    }

    /**
     * Adds a handler to the pipeline.
     *
     * @param handler provided handler by the client.
     */
    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    /**
     * Adds a handler to the pipeline in the specified position.
     *
     * @param handler Provided handler by the client.
     * @param index   The specified index for the handler.
     */
    public void addHandler(Handler handler, int index) {
        handlers.add(index, handler);
    }

    /**
     * Removes a handler from pipeline.
     *
     * @param handler The handler which clinet wanna remove.
     */
    public void deleteHandler(Handler handler) {
        handlers.remove(handler);
    }

    /**
     * Removes a handler from pipeline by providing its name.
     *
     * @param handlerName Name of the handler which clinet wanna remove.
     */
    public void deleteHandler(String handlerName) {
        for (Handler handler : handlers) {
            if (handler.getHandlerName().equals(handlerName)) {
                handlers.remove(handler);
            }
        }
    }

    /**
     * passes input object to the handlers one by one to do dunctions on it
     *
     * @param input object for begining the pipeline
     * @throws DataTypeMissMatchException if data type of input and first handler input or output of a handler and input
     *                                    of next handler don't match
     */
    public O run(I input) {
        logger.info("pipeline is running!");
        Object obj = input;
        for (Handler handler : handlers) {
            try {
                logger.info("haldler \"" + handler.getHandlerName() + "\" is processing...");
                obj = handler.function(obj);
            } catch (ClassCastException e) {
                DataTypeMissMatchException exception = new DataTypeMissMatchException("Provided data type for handler \""
                        + handler.getHandlerName() +
                        "\" does not match with required data type!");
                logger.error("pipeline failed!", exception);
                throw exception;
            }
        }
        try {
            O object = (O) obj;
            logger.info("pipeline is done!");
            return object;
        } catch (ClassCastException e) {
            DataTypeMissMatchException exception = new DataTypeMissMatchException("Data types of last handler output " +
                    "and pipelime output do not match!");
            logger.error("pipeline failed!", exception);
            throw exception;
        }
    }
}
