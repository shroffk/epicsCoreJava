/**
 * Copyright - See the COPYRIGHT that is included with this distribution.
 * EPICS JavaIOC is distributed subject to a Software License Agreement found
 * in file LICENSE that is included with this distribution.
 */
package org.epics.pvData.factory;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.epics.pvData.pv.Array;
import org.epics.pvData.pv.IntArrayData;
import org.epics.pvData.pv.MessageType;
import org.epics.pvData.pv.PVIntArray;
import org.epics.pvData.pv.PVStructure;


/**
 * Base class for implementing PVIntArray.
 * @author mrk
 *
 */
public class BasePVIntArray extends AbstractPVArray implements PVIntArray
{
    protected int[] value;
    protected IntArrayData intArrayData = new IntArrayData();
    
    /**
     * Constructor.
     * @param parent The parent.
     * @param array The Introspection interface.
     */
    public BasePVIntArray(PVStructure parent,Array array)
    {
        super(parent,array);
        value = new int[capacity];
    }
    /* (non-Javadoc)
     * @see org.epics.pvData.factory.AbstractPVField#toString(int)
     */
    public String toString(int indentLevel) {
        return convert.getString(this, indentLevel)
        + super.toString(indentLevel);
    }
    /* (non-Javadoc)
     * @see org.epics.pvData.factory.AbstractPVArray#setCapacity(int)
     */
    public void setCapacity(int len) {
        if(!capacityMutable) {
            super.message("not capacityMutable", MessageType.error);
            return;
        }
        if(length>len) length = len;
        int[]newarray = new int[len];
        if(length>0) System.arraycopy(value,0,newarray,0,length);
        value = newarray;
        capacity = len;
    }
    /* (non-Javadoc)
     * @see org.epics.pvData.pv.PVIntArray#get(int, int, org.epics.pvData.pv.IntArrayData)
     */
    public int get(int offset, int len, IntArrayData data) {
        int n = len;
        if(offset+len > length) n = length - offset;
        data.data = value;
        data.offset = offset;
        return n;
    }
    /* (non-Javadoc)
     * @see org.epics.pvData.pv.PVIntArray#put(int, int, int[], int)
     */
    public int put(int offset, int len, int[]from, int fromOffset) {
        if(!super.isMutable()) {
            super.message("not isMutable", MessageType.error);
            return 0;
        }
        if(offset+len > length) {
            int newlength = offset + len;
            if(newlength>capacity) {
                setCapacity(newlength);
                newlength = capacity;
                len = newlength - offset;
                if(len<=0) return 0;
            }
            length = newlength;
        }
        System.arraycopy(from,fromOffset,value,offset,len);
        return len;
    }
	/* (non-Javadoc)
	 * @see org.epics.pvData.pv.Serializable#serialize(java.nio.ByteBuffer)
	 */
	public void serialize(ByteBuffer buffer) {
		writeSize(length, buffer);
		for (int i = 0; i < length; i++)
			buffer.putInt(value[i]);
	}
	/* (non-Javadoc)
	 * @see org.epics.pvData.pv.Serializable#deserialize(java.nio.ByteBuffer)
	 */
	public void deserialize(ByteBuffer buffer) {
		final int size = readSize(buffer);
		if (size >= 0) {
			// prepare array, if necessary
			if (size > capacity)
				setCapacity(size);
			// retrieve value from the buffer
			for (int i = 0; i < size; i++)
				value[i] = buffer.getInt();
			// set new length
			length = size;
		}
		// TODO null arrays (size == -1) not supported
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO anything else?
		if (obj instanceof PVIntArray) {
			PVIntArray b = (PVIntArray)obj;
			IntArrayData bad = new IntArrayData();
			b.get(0, b.getLength(), bad);
			return Arrays.equals(bad.data, value);
		}
		else
			return false;
	}
}
