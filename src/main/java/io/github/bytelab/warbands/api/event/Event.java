/*
 * Copyright (C) 2014 Bytelab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.bytelab.warbands.api.event;

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
