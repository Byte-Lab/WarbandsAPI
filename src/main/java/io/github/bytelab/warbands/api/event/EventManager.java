package io.github.bytelab.warbands.api.event;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class EventManager {

    private HandlerEntryList[] handlers;

    protected EventManager() {
        handlers = new HandlerEntryList[Order.values().length];

        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new HandlerEntryList();
        }
    }

    public void register(JavaPlugin registrant, Object obj) {

        Class<?> handlerClass = obj.getClass();

        for (Method method : handlerClass.getMethods()) {
            if (
              method.isAccessible()
                && method.isAnnotationPresent(EventHandler.class)
                && method.getParameterTypes().length == 1
                && Event.class.isAssignableFrom(method.getParameterTypes()[0])
              ) {
                EventHandler annotation = method.getAnnotation(EventHandler.class);

                Class c = method.getParameterTypes()[0];

                while (Event.class.isAssignableFrom(c)) {
                    handlers[annotation.order().getOrder()].add(new HandlerEntry(c, obj, method, registrant, annotation.subscribe()));

                    c = c.getSuperclass();
                }
            }
        }


    }

    public void unregister(JavaPlugin registrant, Object obj) {
        for (HandlerEntryList entryList : handlers) {
            for (HandlerEntry entry : entryList) {
                if (entry.getHandler() == obj && entry.getRegistrant() == registrant) { entryList.remove(entry); }
            }
        }
    }

    public <T extends Event> T dispatch(T event) {
        Class eventClass = event.getClass();

        for (int i = 0, handlersLength = handlers.length; i < handlersLength; i++) {
            for (HandlerEntry entry : handlers[i]) {
                if (entry.getEvent() == eventClass) {
                    if (event instanceof Cancellable) {
                        if ((((Cancellable) event).isCancelled() && contains(
                          entry.getSubscribe(),
                          Subscribe.CANCELLED
                        )) || (!((Cancellable) event).isCancelled())) { dispatchToEntry(entry, event); }
                    } else {
                        dispatchToEntry(entry, event);
                    }
                }
            }
        }


        return event;
    }

    private void dispatchToEntry(HandlerEntry entry, Event event) {
        try {
            entry.getSubscriber().invoke(entry.getHandler(), event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            unregister(entry.getRegistrant(), entry.getHandler());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static <T> boolean contains(T[] arr, T obj) {
        for (T anArr : arr) { if (anArr == obj) { return true; } }

        return false;
    }

    protected class HandlerEntry {

        private final Class<? extends Event> event;

        private final Object handler;

        private final Method subscriber;

        private final JavaPlugin registrant;

        private final Subscribe[] subscribe;

        private HandlerEntry(Class<? extends Event> event, Object handler, Method subscriber, JavaPlugin registrant, Subscribe[] subscribe) {
            this.event = event;
            this.handler = handler;
            this.subscriber = subscriber;
            this.registrant = registrant;
            this.subscribe = subscribe;
        }

        public Object getHandler() {
            return handler;
        }

        public Method getSubscriber() {
            return subscriber;
        }

        public JavaPlugin getRegistrant() {
            return registrant;
        }

        public Class<? extends Event> getEvent() {
            return event;
        }

        public Subscribe[] getSubscribe() {
            return subscribe;
        }
    }

    protected class HandlerEntryList extends ArrayList<HandlerEntry> {}
}
