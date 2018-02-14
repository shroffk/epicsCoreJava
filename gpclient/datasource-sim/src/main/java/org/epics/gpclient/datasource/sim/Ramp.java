/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.gpclient.datasource.sim;

import org.epics.util.stats.Range;
import org.epics.vtype.Alarm;
import org.epics.vtype.Display;
import org.epics.vtype.Time;
import org.epics.vtype.VDouble;


/**
 * Function to simulate a signal that increases constantly within a range
 * (saw-tooth shape). The warning
 * limits are set at 80% of the range and the alarm at 90% the range.
 * All values are going to have no alarm status, with the timestamp set at the
 * moment the sample was generated.
 *
 * @author carcassi
 */
public class Ramp extends SimFunction<VDouble> {

    private double min;
    private double max;
    private double currentValue;
    private double step;
    private Range range;
    private VDouble lastValue;

    /**
     * Creates a ramp shaped signal between min and max, updating a step amount
     * every interval seconds.
     *
     * @param min minimum value
     * @param max maximum value
     * @param step increment for each sample
     * @param interval interval between samples in seconds
     */
    public Ramp(Double min, Double max, Double step, Double interval) {
        super(interval, VDouble.class);
        if (interval <= 0.0) {
            throw new IllegalArgumentException("Interval must be greater than zero (was " + interval + ")");
        }
        this.min = min;
        this.max = max;
        if (step >=0) {
            this.currentValue = min - step;
        } else {
            this.currentValue = max - step;
        }
        this.step = step;
        range = Range.of(min, max);
        lastValue = VDouble.of(currentValue, Alarm.none(), Time.now(),
                Display.of(range, range.shrink(0.9), range.shrink(0.8), Range.undefined(),
                        "", Display.defaultNumberFormat()));
    }

    /**
     * Creates a ramp shaped signal between min and max, incrementing 1
     * every interval seconds.
     *
     * @param min minimum value
     * @param max maximum value
     * @param interval interval between samples in seconds
     */
    public Ramp(Double min, Double max, Double interval) {
        this(min, max, 1.0, interval);
    }
    
    /**
     * Creates a ramp shaped signal between -5 and +5, incrementing 1 every second.
     */
    public Ramp() {
        this (-5.0, 5.0, 1.0);
    }

    @Override
    VDouble nextValue() {
        currentValue = currentValue + step;
        if (currentValue > max) {
            currentValue = min;
        }
        if (currentValue < min) {
            currentValue = max;
        }

        return newValue(currentValue, lastValue);
    }
}
