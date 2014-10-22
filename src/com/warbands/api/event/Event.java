package com.warbands.api.event;

/**
 * @author Tristan
 */
public abstract class Event {

    private boolean canceled;

    private boolean cancelable;

    protected Event(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isCancelable() {
        return this.cancelable;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        if (this.isCancelable()) {
            this.canceled = canceled;
        } else {
            throw new EventCancellationException("Attempted to cancel uncancellable event " + this + ".");
        }
    }

}
