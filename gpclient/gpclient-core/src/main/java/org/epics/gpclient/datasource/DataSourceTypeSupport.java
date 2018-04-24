/**
 * Copyright information and license terms for this software can be
 * found in the file LICENSE.TXT included with the distribution.
 */
package org.epics.gpclient.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.epics.gpclient.ReadCollector;

/**
 * The type support for a datasource. This optional class is provided to help
 * create a more flexible type support in a datasource, so that support
 * for individual types is done through runtime configuration. It provides
 * default implementation for matching typeAdapters from the desired cache
 * and connection payload.
 *
 * @author carcassi
 */
public class DataSourceTypeSupport {
    
    /**
     * Given a collection of type datapters, finds the one that can store
     * data in the cache given the channel information described in the
     * connection payload. If there isn't a unique match, an exception
     * is thrown.
     * 
     * @param <C> type of connection payload
     * @param <T> datasource specific type adapter type
     * @param typeAdapters a collection of type adapters
     * @param cache the cache where to store the data
     * @param connection the connection payload
     * @return 0 if the type was not matched
     */
    protected <C, T extends DataSourceTypeAdapter<? super C,?>> T find(Collection<T> typeAdapters, ReadCollector<?, ?> cache, C connection) {
        List<T> matchedConverters = new ArrayList<T>();
        for (T converter : typeAdapters) {
            if (converter.match(cache, connection)) {
                matchedConverters.add(converter);
            }
        }
        
        if (matchedConverters.size() != 1) {
            throw new IllegalStateException(formatMessage(cache, connection, matchedConverters));
        }
        
        return matchedConverters.get(0);
    }
    
    /**
     * Formats the error message in case of not unique match. This
     * allows data sources to give more specific error messages.
     * 
     * @param cache the cache used for the match
     * @param connection the connection payload used for the match
     * @param matchedConverters the matched converters; will either be 0 (no match)
     * or more than 1 (non unique match)
     * @return the message to be passed with the exception
     */
    protected String formatMessage(ReadCollector<?, ?> cache, Object connection, List<? extends DataSourceTypeAdapter<?, ?>> matchedConverters) {
        if (matchedConverters.isEmpty()) {
            return "DataSource misconfiguration: no match found to convert payload to type. ("
                    + cache.getType() + " - " + connection + ")";
        } else {
            return "DataSource misconfiguration: multiple matches found to convert payload to type. ("
                    + cache.getType() + " - " + connection + ": " + matchedConverters + ")";
        }
    }
}
