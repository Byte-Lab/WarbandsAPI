package io.github.bytelab.warbands.api.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tristan
 */
public class WarbandsEventBus {

    private static Logger logger = LogManager.getLogger("Warbands EventBus");

    private static Map<Class<?>, ConcurrentHashMap<Method, Object>> systemListeners = new ConcurrentHashMap<Class<?>, ConcurrentHashMap<Method, Object>>();

    private static Map<Class<?>, ConcurrentHashMap<Method, Object>> highPriorityListeners = new ConcurrentHashMap<Class<?>, ConcurrentHashMap<Method, Object>>();

    private static Map<Class<?>, ConcurrentHashMap<Method, Object>> normalPriorityListeners = new ConcurrentHashMap<Class<?>, ConcurrentHashMap<Method, Object>>();

    private static Map<Class<?>, ConcurrentHashMap<Method, Object>> lowPriorityListeners = new ConcurrentHashMap<Class<?>, ConcurrentHashMap<Method, Object>>();

    private static int threadInitNumber;

    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }

    public static void register(Object object) {

        for (Method method : object.getClass().getMethods()) {
            if (
                method.isAnnotationPresent(WarbandsEventHandler.class)
                    && method.getParameterTypes().length == 1
                    && WarbandsEvent.class.isAssignableFrom(method.getParameterTypes()[0])
                ) {
                switch (method.getAnnotation(WarbandsEventHandler.class).priority()) {
                    case SYSTEM:
                        registerToList(method, object, systemListeners);
                        break;
                    case HIGH:
                        registerToList(method, object, highPriorityListeners);
                        break;
                    case NORMAL:
                        registerToList(method, object, normalPriorityListeners);
                        break;
                    case LOW:
                        registerToList(method, object, lowPriorityListeners);
                        break;
                    default:
                        logger.error(
                            "Attempted to register method \"{0}\" with unknown priority \"{1}\". This should be impossible!",
                            method,
                            method.getAnnotation(WarbandsEventHandler.class).priority()
                        );
                }
            }
        }

    }

    public static <T extends WarbandsEvent> T dispatch(T event) {
        List<Class> classes = new ArrayList<Class>();
        Class currentClass = event.getClass();

        while (currentClass != WarbandsEvent.class.getSuperclass()) {
            classes.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }

        EventDispatcher<T> dispatcher = new EventDispatcher<T>(event, classes);

        new Thread(dispatcher, "EventDispatcherThread (" + nextThreadNum() + ")").run();

        return dispatcher.getEvent();

    }

    private static void registerToList(Method method, Object object, Map<Class<?>, ConcurrentHashMap<Method, Object>> classListMap) {
        if (!classListMap.containsKey(method.getParameterTypes()[0])) {
            classListMap.put(method.getParameterTypes()[0], new ConcurrentHashMap<Method, Object>());
        }
        if (!classListMap.get(method.getParameterTypes()[0]).containsKey(method)) {
            classListMap.get(method.getParameterTypes()[0]).put(method, object);
        }
    }

    private static void dispatchToList(WarbandsEvent event, List<Class> eventClasses, Map<Class<?>, ConcurrentHashMap<Method, Object>> classListMap) {

        for (Class eventClass : eventClasses) {
            if (classListMap.containsKey(eventClass)) {
                for (Method method : classListMap.get(eventClass).keySet()) {
                    if (!event.isCanceled() || Arrays.asList(method.getAnnotation(WarbandsEventHandler.class).subscribe()).contains(EventSubscribe.CANCELLED)) {
                        try {
                            method.invoke(classListMap.get(eventClass).get(method), event);
                        } catch (IllegalAccessException e) {
                            logger.error("Encountered a handled exception:");
                            e.printStackTrace();
                            logger.error("This usually indicates that there is something wrong with an EventHandler");
                        } catch (InvocationTargetException e) {
                            logger.error("Encountered a handled exception:");
                            e.getCause().printStackTrace();
                            logger.error("This usually indicates that there is something wrong with an EventHandler");
                        }
                    }
                }
            }
        }

    }

    public static enum EventPriority {SYSTEM, NORMAL, HIGH, LOW}

    public static enum EventSubscribe {CANCELLED}

    public static class EventDispatcher<T extends WarbandsEvent> implements Runnable {

        private T event;

        private List<Class> classes;

        public EventDispatcher(T event, List<Class> classes) {
            this.event = event;
            this.classes = classes;
        }

        public void dispatch() {
            dispatchToList(event, classes, systemListeners);
            dispatchToList(event, classes, highPriorityListeners);
            dispatchToList(event, classes, normalPriorityListeners);
            dispatchToList(event, classes, lowPriorityListeners);
        }

        @Override
        public void run() {
            this.dispatch();
        }

        public T getEvent() {
            return event;
        }
    }

}
