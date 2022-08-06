package ir.jibit.pipeline;


import ir.jibit.handler.Handler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Takes some handlers and store them in an LinkedList and executes handlers functions on an input object
 *
 * @param <I> the type of the input to the pipeline
 * @param <O> the type of te output of pipeline
 * @author Alireza Khodadoost
 */
@SuppressWarnings("ALL")
public class Pipeline<I, O> {
    /**
     * A linkedlist which saving all the handlers of the pipeline.
     */
    private final LinkedList<Handler> handlers;

    public Pipeline() {
        handlers = new LinkedList<>();
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
        Object obj = input;
        for (Handler handler : handlers) {
            try {
                obj = handler.function(obj);
            } catch (ClassCastException e) {
                throw new DataTypeMissMatchException("Provided data type for handler \"" + handler.getHandlerName() +
                        "\" does not match with required data type!");
            }
        }
        try {
            return (O) obj;
        } catch (ClassCastException e) {
            throw new DataTypeMissMatchException("Data types of last handler output and pipelime output do not match!");
        }
    }
}
