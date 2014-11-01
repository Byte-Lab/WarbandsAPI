package io.github.bytelab.warbands.api.event;

/**
 * @author Tristan
 */
public abstract class EventBase implements Event {

    @Override
    public boolean isCancellable() {
        return this instanceof Cancellable;
    }
}
