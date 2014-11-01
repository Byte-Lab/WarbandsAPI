package io.github.bytelab.warbands.api.event;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface EventHandler {

    Order order() default Order.DEFAULT;

    Subscribe[] subscribe() default {};

}
