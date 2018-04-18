package org.bumo.sdk.core.utils.io;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bumo.sdk.core.utils.IllegalDataException;

/**
 * Binary tool class
 *
 * @author bumo
 */
public class BytesUtils{

    public static final int MAX_BUFFER_SIZE = 100 * 1024 * 1024;
    public static final int BUFFER_SIZE = 1024;

    private BytesUtils(){
    }

    public static boolean compare(byte[] bytes1, byte[] bytes2){
        if (bytes1.length != bytes2.length) {
            return false;
        }
        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Read all the contents of the input stream to the byte array to return
     * <p>
     * If the length of the input stream exceeds the value defined by MAX_BUFFER_SIZE, IllegalArgumentException is thrown
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] copyToBytes(InputStream in) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        long size = 0;
        while ((len = in.read(buffer)) > 0) {
            size += len;
            if (size > MAX_BUFFER_SIZE) {
                throw new IllegalArgumentException(
                        "The size of the InputStream exceed the max buffer size [" + MAX_BUFFER_SIZE + "]!");
            }
            out.write(buffer, 0, len);
        }
        return out.toByteArray();
    }

    /**
     * Copy the input stream to the output stream；
     *
     * @param in      Input stream
     * @param out     Output stream
     * @param maxSize Maximum byte size
     * @return Returns the number of bytes that are actually copied
     * @throws IOException
     */
    public static int copy(InputStream in, OutputStream out, int maxSize) throws IOException{
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        int left = maxSize;
        int readLen = buffer.length;
        while (left > 0) {
            readLen = Math.min(left, buffer.length);
            len = in.read(buffer, 0, readLen);
            if (len > 0) {
                out.write(buffer, 0, len);
                left = left - len;
            } else {
                break;
            }
        }
        return maxSize - left;
    }

    /**
     * Turn the int value into a binary array of 4 bytes
     *
     * @param value
     * @return The binary array after transformation is high and low
     */
    public static byte[] toBytes(int value){
        byte[] bytes = new byte[4];
        toBytes(value, bytes, 0);
        return bytes;
    }

    /**
     * Turn the int value into a binary array of 4 bytes
     *
     * @param value The int integer to be converted
     * @param bytes To save the binary array of transformation results, the conversion results will be written from high to low in order to write 4 elements from array 0
     */
    public static void toBytes(int value, byte[] bytes){
        toBytes(value, bytes, 0);
    }

    /**
     * Turn the int value into a binary array of 4 bytes
     *
     * @param value  The int integer to be converted
     * @param bytes  To save the binary array of transformation results, the transformation results will be written from high to low in order to write 4 elements from the offset specified location
     * @param offset Write the starting position of the transformation result
     */
    public static void toBytes(int value, byte[] bytes, int offset){
        bytes[offset] = (byte) ((value >> 24) & 0x00FF);
        bytes[offset + 1] = (byte) ((value >> 16) & 0x00FF);
        bytes[offset + 2] = (byte) ((value >> 8) & 0x00FF);
        bytes[offset + 3] = (byte) (value & 0x00FF);
    }

    /**
     * From the high to low order, convert the 4 byte from the location 0 to the int integer
     *
     * @param bytes Binary array to be converted
     * @return Converted int integers
     */
    public static int toInt(byte[] bytes){
        int value = 0;
        value = (value | (bytes[0] & 0xFF)) << 8;
        value = (value | (bytes[1] & 0xFF)) << 8;
        value = (value | (bytes[2] & 0xFF)) << 8;
        value = value | (bytes[3] & 0xFF);

        return value;
    }

    /**
     * From the high to low order, convert the 4 byte from the specified binary array to the int integer from the location specified by the offset parameter
     *
     * @param bytes  Binary array to be converted
     * @param offset 
     * @return To read the starting position of the data
     */
    public static int toInt(byte[] bytes, int offset){
        int value = 0;
        value = (value | (bytes[offset] & 0xFF)) << 8;
        value = (value | (bytes[offset + 1] & 0xFF)) << 8;
        value = (value | (bytes[offset + 2] & 0xFF)) << 8;
        value = value | (bytes[offset + 3] & 0xFF);

        return value;
    }

    /**
     * Read 4 bytes from the specified input stream, from pre to post, from high to low, to int integer
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static int readInt(InputStream in) throws IOException{
        byte[] buf = new byte[4];
        if (in.read(buf) < 4) {
            throw new IllegalDataException("No enough data to read as integer from the specified input stream!");
        }
        return toInt(buf);
    }
}
