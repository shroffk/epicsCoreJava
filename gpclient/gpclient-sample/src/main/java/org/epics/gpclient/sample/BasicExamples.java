/**
 * Copyright information and license terms for this software can be
 * found in the file LICENSE.TXT included with the distribution.
 */
package org.epics.gpclient.sample;

import org.epics.gpclient.GPClient;
import org.epics.gpclient.PVEvent;
import org.epics.gpclient.PVReader;
import org.epics.vtype.VType;
import static org.epics.gpclient.GPClient.*;
import org.epics.gpclient.PV;

/**
 *
 * @author carcassi
 */
public class BasicExamples {

    public static void b1_readLatestValue() throws Exception {
        PVReader<VType> pv = GPClient.read("sim://delayedConnectionChannel(3, \"init\")")
                .addListener((PVEvent event, PVReader<VType> pvReader) -> {
                    System.out.println(event + " " + pvReader.isConnected() + " " + pvReader.getValue());
                })
                .start();

        Thread.sleep(10000);

        GPClient.defaultInstance().getDefaultDataSource().close();
    }

    public static void b1_readAndWriteLoc() throws Exception {
        PV<VType, Object> pv = GPClient.readAndWrite(channel("loc://a(\"init\")"))
                .addListener((PVEvent event, PV<VType, Object> pvReader) -> {
                    System.out.println(event + " " + pvReader.isConnected() + " " + pvReader.isWriteConnected() + " " + pvReader.getValue());
                    if (event.isType(PVEvent.Type.EXCEPTION)) {
                        event.getException().printStackTrace();
                    }
                })
                .start();

        Thread.sleep(1000);
        
        pv.write("New Value");

        Thread.sleep(1000);

        GPClient.defaultInstance().getDefaultDataSource().close();
    }

    public static void main(String[] args) throws Exception {
        b1_readLatestValue();
    }
}
