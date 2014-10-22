package com.warbands.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Tristan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    public EventBus.EventPriority priority() default EventBus.EventPriority.NORMAL;

    public EventBus.EventSubscribe[] subscribe() default {};

}
