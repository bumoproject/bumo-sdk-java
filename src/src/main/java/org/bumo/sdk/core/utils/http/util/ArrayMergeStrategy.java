package org.bumo.sdk.core.utils.http.util;

/**
 * The merging strategy of arrays
 *
 * @author haiq
 */
public enum ArrayMergeStrategy{

    /**
     * Replacement strategy: replace the original array with new array
     */
    REPLACE,

    /**
     * Add strategy: add the elements of the new array to the original array elements
     */
    APPEND,

    /**
     * Deep merge strategy: for the same location element, if the original value is replaced, if it is the JSON object, it is recursively merged; the elements of the new array are added to the original array
     */
    DEEP_MERGE

}
