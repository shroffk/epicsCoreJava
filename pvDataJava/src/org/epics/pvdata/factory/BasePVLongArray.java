/**
 * Copyright - See the COPYRIGHT that is included with this distribution.
 * EPICS pvData is distributed subject to a Software License Agreement found
 * in file LICENSE that is included with this distribution.
 */
package org.epics.pvdata.factory;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.epics.pvdata.pv.DeserializableControl;
import org.epics.pvdata.pv.LongArrayData;
import org.epics.pvdata.pv.PVLongArray;
import org.epics.pvdata.pv.ScalarArray;
import org.epics.pvdata.pv.SerializableControl;


/**
 * Base class for implementing PVLongArray.
 * @author mrk
 *
 */
public class BasePVLongArray extends AbstractPVScalarArray implements PVLongArray
{
    protected long[] value;
    
    /**
     * Constructor.
     * @param array The introspection interface.
     */
    public BasePVLongArray(ScalarArray array)
    {
        super(array);
    }
    
    @Override
    protected void allocate(int newCapacity) {
    	value = new long[newCapacity];
    	capacity = newCapacity;
    }
    
    @Override
    protected Object getValue()
    {
    	return value;
    }
    
    @Override
    protected void setValue(Object array)
    {
    	value = (long[])array;
    }

    @Override
	protected int putToBuffer(ByteBuffer buffer, SerializableControl control, int offset, int length)
	{
		buffer.asLongBuffer().put(value, offset, length);
		buffer.position(buffer.position() + length*8);
		return length;
	}
	
    @Override
	protected int getFromBuffer(ByteBuffer buffer, DeserializableControl control, int offset, int length)
	{
		buffer.asLongBuffer().get(value, offset, length);
		buffer.position(buffer.position() + length*8);
		return length;
	}

    @Override
    public int get(int offset, int len, LongArrayData data) {
    	return internalGet(offset, len, data);
    }
    
    @Override
    public int put(int offset, int len, long[] from, int fromOffset) {
    	return internalPut(offset, len, from, fromOffset);
    }


    @Override
    public void shareData(long[] from) {
    	internalShareData(from);
    }

    @Override
    protected boolean valueEquals(Object obj)
    {
		PVLongArray b = (PVLongArray)obj;
	    LongArrayData arrayData = new LongArrayData();
    	// NOTE: this assumes entire array set to arrayData
	    b.get(0, b.getLength(), arrayData);
		return Arrays.equals(arrayData.data, value);
    }
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(value);
	}
}
