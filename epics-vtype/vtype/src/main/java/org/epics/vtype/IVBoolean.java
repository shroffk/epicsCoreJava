/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

/**
 * Immutable {@code VBoolean} implementation.
 *
 * @author carcassi
 */
final class IVBoolean extends VBoolean {
    
    private final Boolean value;
    private final Alarm alarm;
    private final Time time;

    IVBoolean(Boolean value, Alarm alarm, Time time) {
        VType.argumentNotNull("value", value);
        VType.argumentNotNull("alarm", alarm);
        VType.argumentNotNull("time", time);
        this.value = value;
        this.alarm = alarm;
        this.time = time;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public Alarm getAlarm() {
        return alarm;
    }

    @Override
    public Time getTime() {
        return time;
    }

}
