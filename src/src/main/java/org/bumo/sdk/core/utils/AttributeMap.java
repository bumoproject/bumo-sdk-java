package org.bumo.sdk.core.utils;

import java.util.Set;

/**
 * AttributeMap Define the general access interface of string key-value attribute table
 *
 * @author bumo
 */
public interface AttributeMap{

    /**
     * List of attribute names
     *
     * @return
     */
    public Set<String> getAttributeNames();

    /**
     * Does it contain the attributes of the specified name
     *
     * @param name
     * @return
     */
    public boolean containAttribute(String name);

    /**
     * Return the attribute value of the specified name
     *
     * @param name
     * @return
     */
    public String getAttribute(String name);

}
