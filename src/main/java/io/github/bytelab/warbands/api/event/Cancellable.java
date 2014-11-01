package io.github.bytelab.warbands.api.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancel);

}
