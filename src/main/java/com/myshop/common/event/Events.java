package com.myshop.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Events {
    private static ThreadLocal<List<EventHandler<?>>> handlers =
            new ThreadLocal<>();
    private static ThreadLocal<List<EventHandler<?>>> asyncHandlers =
            new ThreadLocal<>();
    private static ThreadLocal<Boolean> publishing =
            new ThreadLocal<Boolean>() {
                @Override
                protected Boolean initialValue() {
                    return Boolean.FALSE;
                }
            };

    private static ExecutorService executor;

    public static void init(ExecutorService executor) {
        Events.executor = executor;
    }

    public static void close() {
        if (executor != null) {
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void raise(Object event) {
        if (publishing.get()) return;

        try {
            publishing.set(Boolean.TRUE);

            List<EventHandler<?>> asyncEvtHandlers = asyncHandlers.get();
            if (asyncEvtHandlers != null) {
                for (EventHandler handler : asyncEvtHandlers) {
                    if (handler.canHandle(event)) {
                        executor.submit(() -> handler.handle(event));
                    }
                }
            }
            List<EventHandler<?>> eventHandlers = handlers.get();
            if (eventHandlers == null) return;
            for (EventHandler handler : eventHandlers) {
                if (handler.canHandle(event)) {
                    handler.handle(event);
                }
            }
        } finally {
            publishing.set(Boolean.FALSE);
        }
    }

    public static void handle(EventHandler<?> handler) {
        if (publishing.get()) return;

        List<EventHandler<?>> eventHandlers = handlers.get();
        if (eventHandlers == null) {
            eventHandlers = new ArrayList<>();
            handlers.set(eventHandlers);
        }
        eventHandlers.add(handler);
    }

    public static void handleAsync(EventHandler<?> handler) {
        if (publishing.get()) return;

        List<EventHandler<?>> eventHandlers = asyncHandlers.get();
        if (eventHandlers == null) {
            eventHandlers = new ArrayList<>();
            asyncHandlers.set(eventHandlers);
        }
        eventHandlers.add(handler);
    }

    public static void reset() {
        if (!publishing.get()) {
            handlers.remove();
            asyncHandlers.remove();
        }
    }
}
