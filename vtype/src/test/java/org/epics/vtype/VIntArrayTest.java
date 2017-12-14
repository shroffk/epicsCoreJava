/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

import org.epics.util.array.ArrayInt;
import org.epics.util.array.ListInt;
import org.epics.util.array.ListInt;

/**
 *
 * @author carcassi
 */
public class VIntArrayTest extends FeatureTestVNumberArray<ListInt, VIntArray> {

    @Override
    ListInt getData() {
        return ArrayInt.of(0,1,2,3,4,5,6,7,8,9);
    }

    @Override
    VIntArray of(ListInt data, Alarm alarm, Time time, Display display) {
        return VIntArray.of(data, alarm, time, display);
    }

    @Override
    VIntArray of(ListInt data, ListInt sizes, Alarm alarm, Time time, Display display) {
        return VIntArray.of(data, sizes, alarm, time, display);
    }

    @Override
    String getToString() {
        return "VIntArray[[0, 1, 2, 3, 4, 5, 6, 7, 8, 9], size [5, 2], MINOR(DB) - LOW, 2012-12-05T14:57:21.521786982Z]";
    }
    
}
