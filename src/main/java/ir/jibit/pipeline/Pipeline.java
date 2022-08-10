package ir.jibit.pipeline;


import ir.jibit.handler.Handler;
import ir.jibit.handler.HandlerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * Takes some handlers and store them in an LinkedList and executes handlers functions on an input object
 *
 * @param <I> The type of the input to the pipeline.
 * @param <O> The type of te output of pipeline.
 * @author Alireza Khodadoost
 */
@SuppressWarnings("ALL")
public final class Pipeline<I, O> {
    /**
     * A linkedlist which saving all the handlerWrappers for the pipeline.
     */
    private final LinkedList<HandlerWrapper> handlerWrappers;

    /**
     * A logger object from slf4j package for logging messages.
     */
    private final static Logger logger = LoggerFactory.getLogger(Pipeline.class);

    public Pipeline() {
        handlerWrappers = new LinkedList<>();
    }

    /**
     * Wrapps a handler and adds it to the list. Also sets the newhandlerWrapper as the nextHandlerWrapper field
     * in the previos one.
     *
     * @param handler provided handler by the client.
     */
    public void addHandler(Handler handler) {
        HandlerWrapper newHandlerWrapper = new HandlerWrapper(handler);
        if (!handlerWrappers.isEmpty()) {
            handlerWrappers.getLast().setNextHandlerWrapper(newHandlerWrapper);
        }
        handlerWrappers.add(newHandlerWrapper);
    }

    /**
     * Wrapps a handler and adds it to the list. Also changes nextHandlerWraper field of prvious handlerWrapper.
     *
     * @param handler Provided handler by the client.
     * @param index   The specified index for the handler.
     */
    public void addHandler(Handler handler, int index) {
        HandlerWrapper newHandlerWrapper = new HandlerWrapper(handler);
        newHandlerWrapper.setNextHandlerWrapper(handlerWrappers.get(index));
        if (index != 0) {
            handlerWrappers.get(index - 1).setNextHandlerWrapper(newHandlerWrapper);
        }
        handlerWrappers.add(index, newHandlerWrapper);
    }

    /**
     * Removes a handler from pipeline.
     *
     * @param handler The handler which clinet wanna remove.
     */
    public void deleteHandler(Handler handler) {
        for (int i = 0; i < handlerWrappers.size(); i++) {
            if (handlerWrappers.get(i).getCurrentHandler().equals(handler)) {
                if (i != 0) {
                    handlerWrappers.get(i - 1).setNextHandlerWrapper(handlerWrappers.get(i + 1));
                }
                handlerWrappers.remove(handlerWrappers.get(i));
            }
        }
    }

    /**
     * Removes a handler from pipeline by providing its name.
     *
     * @param handlerName Name of the handler which clinet wanna remove.
     */
    public void deleteHandler(String handlerName) {
        for (int i = 0; i < handlerWrappers.size(); i++) {
            if (handlerWrappers.get(i).getCurrentHandler().getHandlerName().equals(handlerName)) {
                if (i != 0) {
                    handlerWrappers.get(i - 1).setNextHandlerWrapper(handlerWrappers.get(i + 1));
                }
                handlerWrappers.remove(handlerWrappers.get(i));
            }
        }
    }

    /**
     * Passes input object to the first handlerWrapper and then they pass it one by one to each other for processing it.
     *
     * @param input Object for begining the pipeline.
     * @throws DataTypeMissMatchException If data type of input for some handler function does not match the required
     * data type for that method.
     */
    public O run(I input) {
        logger.info("pipeline is running!");
        O output = (O) handlerWrappers.getFirst().process(input);
        logger.info("pipeline is done!");
        return output;
    }
}
