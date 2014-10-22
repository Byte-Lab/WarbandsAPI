package com.warbands.api.event;

/**
 * @author Tristan
 */
public class EventCancellationException extends RuntimeException {

    public EventCancellationException(String message) {
        super(message);
    }
}
