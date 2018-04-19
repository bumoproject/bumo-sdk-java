package org.bumo.sdk.core.utils.hash;

public interface Hashing{

    public static final Hashing MURMUR3_HASH = new Hashing(){

        @Override
        public int hash32(CharSequence id){
            return MurmurHash3.murmurhash3_x86_32(id, 0, id.length(), 1024);
        }
    };

    public int hash32(CharSequence id);


}
