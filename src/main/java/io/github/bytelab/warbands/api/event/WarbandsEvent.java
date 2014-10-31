package io.github.bytelab.warbands.api.event;

/**
 * @author Tristan
 */
public abstract class WarbandsEvent {

    private boolean canceled;

    private boolean cancelable;

    protected WarbandsEvent(boolean cancelable) {
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
            throw new WarbandsEventCancellationException("Attempted to cancel uncancellable event " + this + ".");
        }
    }

}
