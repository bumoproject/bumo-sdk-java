package org.bumo.sdk.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

public class EmptyProperties extends Properties{

    private static final long serialVersionUID = 5941797426076447165L;

    public static Properties INSTANCE = new EmptyProperties();

    private EmptyProperties(){
    }

    @Override
    public String getProperty(String key){
        return null;
    }

    @Override
    public String getProperty(String key, String defaultValue){
        return defaultValue;
    }

    @Override
    public Enumeration<?> propertyNames(){
        return Collections.enumeration(Collections.emptyList());
        //		return Collections.emptyEnumeration();
    }

    @Override
    public int size(){
        return 0;
    }

    @Override
    public boolean isEmpty(){
        return true;
    }

    @Override
    public boolean containsKey(Object key){
        return false;
    }

    @Override
    public boolean containsValue(Object value){
        return false;
    }

    @Override
    public synchronized Object get(Object key){
        return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Set<Object> keySet(){
        return Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Collection<Object> values(){
        return Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Set<java.util.Map.Entry<Object, Object>> entrySet(){
        return Collections.EMPTY_SET;
    }

    //	@Override
    //    public Object getOrDefault(Object k, Object defaultValue) {
    //        return defaultValue;
    //    }
    //
    //    @Override
    //    public void forEach(BiConsumer<? super Object, ? super Object> action) {
    //        Objects.requireNonNull(action);
    //    }
    //
    //    @Override
    //    public void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> function) {
    //        Objects.requireNonNull(function);
    //    }
    //
    //    @Override
    //    public Object putIfAbsent(Object key, Object value) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public boolean remove(Object key, Object value) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public boolean replace(Object key, Object oldValue, Object newValue) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public Object replace(Object key, Object value) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public Object computeIfAbsent(Object key,
    //            Function<? super Object, ? extends Object> mappingFunction) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public Object computeIfPresent(Object key,
    //            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public Object compute(Object key,
    //            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public Object merge(Object key, Object value,
    //            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public Enumeration<Object> elements() {
    //    	return Collections.emptyEnumeration();
    //    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException{
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(Reader reader) throws IOException{
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException{
        throw new UnsupportedOperationException();
    }

    @Override
    public Object put(Object key, Object value){
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration<Object> keys(){
        return Collections.enumeration(Collections.emptyList());
        //    	return Collections.emptyEnumeration();
    }

    @Override
    public Object setProperty(String key, String value){
        throw new UnsupportedOperationException();
    }

}
