package ir.jibit.pipeline;


import ir.jibit.handler.Handler;

import java.util.ArrayList;

/**
 * Takes some handlers and store them in an ArrayList and executes handlers functions on an input object
 *
 * @param <I> the type of the input to the pipeline
 * @param <O> the type of te output of pipeline
 */
@SuppressWarnings("ALL")
public class Pipeline<I, O> {
    private final ArrayList<Handler> handlers;

    public Pipeline() {
        handlers = new ArrayList<>();
    }

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public void addHandler(Handler handler, int index) {
        handlers.add(index, handler);
    }

    public void deleteHandler(Handler handler) {
        handlers.remove(handler);
    }

    /**
     * passes input object to the handlers one by one to do dunctions on it
     *
     * @param input object for begining the pipeline
     * @throws DataTypeMissMatchException if data type of input and first handler input or output of a handler and input
     *                                    of next handler don't match
     */
    public O start(I input) {
        Object obj = input;

        for (int i = 0; i < handlers.size(); i++) {
            try {
                obj = handlers.get(i).function(obj);
            } catch (ClassCastException e) {
                if (i == 0) {
                    throw new DataTypeMissMatchException("Data types of input object and first handler input " +
                            "do not match!");
                } else {
                    throw new DataTypeMissMatchException("Data types of output of handler at index " + (i - 1) +
                            " and input of handler at index" + i + " do not match!");
                }
            }
        }
        try {
            return (O) obj;
        } catch (ClassCastException e) {
            throw new DataTypeMissMatchException("Data types of last handler output and pipelime output do not match!");
        }
    }
}
