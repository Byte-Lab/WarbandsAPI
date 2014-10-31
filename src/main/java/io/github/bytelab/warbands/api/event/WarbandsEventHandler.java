package io.github.bytelab.warbands.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Tristan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WarbandsEventHandler {

    public WarbandsEventBus.EventPriority priority() default WarbandsEventBus.EventPriority.NORMAL;

    public WarbandsEventBus.EventSubscribe[] subscribe() default {};

}
