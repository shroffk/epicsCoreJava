/*
 * Copyright (c) 2004 by Cosylab
 *
 * The full license specifying the redistribution, modification, usage and other
 * rights and obligations is included with the distribution of this project in
 * the file "LICENSE-CAJ". If the license is not included visit Cosylab web site,
 * <http://www.cosylab.com>.
 *
 * THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND, NOT EVEN THE
 * IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR OF THIS SOFTWARE, ASSUMES
 * _NO_ RESPONSIBILITY FOR ANY CONSEQUENCE RESULTING FROM THE USE, MODIFICATION,
 * OR REDISTRIBUTION OF THIS SOFTWARE.
 */

package org.epics.pvaccess.util.test;

import junit.framework.TestCase;

import org.epics.pvaccess.util.HexDump;

/**
 * @author <a href="mailto:matej.sekoranjaATcosylab.com">Matej Sekoranja</a>
 * @version $Id$
 */
public class HexDumpTest extends TestCase {

    public HexDumpTest(String methodName) {
        super(methodName);
    }

    /*
     * Class under test for void hexDump(String, byte[])
     */
    public void testHexDump()
    {
        final byte[] TO_DUMP = "Matej Sekoranja\0\1\2\3\4\5\6\254\255\256".getBytes();
        HexDump.hexDump("test", TO_DUMP);

        HexDump.hexDump("only name", TO_DUMP, 15);
        HexDump.hexDump("16 bytes test", TO_DUMP, 16);
    }

}
