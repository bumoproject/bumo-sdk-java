package io.bumo.sdk.core.utils.io;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import io.bumo.sdk.core.utils.IllegalDataException;

/**
 * Byte block read
 * <p>
 * Execute the read logic corresponding to the writing process of {@link BytesChunkWriter}, and read the contents in blocks
 * <p>
 * When creating BytesChunkReader instances, the magic byte is checked first. If the mode byte check fails, the IllegalDataException will be thrown
 * <p>
 * 注：BytesChunkReader It's not thread safe
 *
 * @author bumo
 */
public class BytesChunkReader implements Closeable{

    private byte[] magicBytes;

    private InputStream in;

    /**
     * Create BytesChunkReader instance；
     * <p>
     * If the mode byte check fails, the IllegalDataException will be thrown
     *
     * @param magicString Magic characters; will be encoded in UTF-8 code, binary code to participate in the check
     * @param in          Input stream
     * @throws IOException
     */
    public BytesChunkReader(String magicString, InputStream in) throws IOException{
        this.magicBytes = magicString.getBytes("UTF-8");
        this.in = in;

        checkMagic();
    }

    /**
     * Create BytesChunkReader instance；
     * <p>
     * If the mode byte check fails, the IllegalDataException will be thrown
     *
     * @param magicBytes Magic bytes
     * @param in         Input stream
     * @throws IOException
     */
    public BytesChunkReader(byte[] magicBytes, InputStream in) throws IOException{
        this.magicBytes = magicBytes;
        this.in = in;

        checkMagic();
    }

    private void checkMagic() throws IOException{
        byte[] buff = new byte[magicBytes.length];
        int len = in.read(buff);
        if (len <= 0) {
            throw new IllegalDataException("No data to read!");
        }
        if (len < magicBytes.length) {
            throw new IllegalDataException("Mismatch magic bytes!");
        }
        if (!BytesUtils.compare(magicBytes, buff)) {
            throw new IllegalDataException("Mismatch magic bytes!");
        }
    }

    /**
     * Read the next block of data
     *
     * @return Data block; if there is no data block readable, it returns to null and closes the input stream
     * @throws IOException
     */
    public byte[] read() throws IOException{
        int len = readNextLengthHeader();
        if (len < 0) {
            // No chunk;
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int reallyLen = BytesUtils.copy(in, out, len);
        if (reallyLen < len) {
            throw new IllegalDataException(
                    "No enough data as the length header indicated to read from the input stream !");
        }
        return out.toByteArray();// ByteArrayOutputStream is not necessary to
        // close;
    }

    /**
     * Read the next block of data
     *
     * @param out Output stream
     * @return Data block; if there is no data block readable, it returns to -1 and closes the input stream
     * @throws IOException
     */
    public int read(OutputStream out) throws IOException{
        int len = readNextLengthHeader();
        if (len < 0) {
            // No chunk;
            return -1;
        }
        int cpLen = BytesUtils.copy(in, out, len);
        if (cpLen < len) {
            throw new IllegalDataException(
                    "No enough data as the length header indicated to read from the input stream!");
        }
        return len;
    }

    /**
     * Return the next length head
     * <p>
     * If it's at the end, it returns to -1 and closes the input stream
     *
     * @return
     * @throws IOException
     */
    private int readNextLengthHeader() throws IOException{
        int len = BytesUtils.readInt(in);
        if (len <= 0) {
            // No chunk;
            close();
            return -1;
        }
        return len;
    }

    @Override
    public void close() throws IOException{
        in.close();
    }

    public static byte[][] extract(byte[] magicBytes, byte[] compactedBytes) throws IOException{
        ByteArrayInputStream in = new ByteArrayInputStream(compactedBytes);
        BytesChunkReader reader = new BytesChunkReader(magicBytes, in);
        List<byte[]> dataBytesList;
        try {
            dataBytesList = new ArrayList<>();
            byte[] dataBytes = null;
            while ((dataBytes = reader.read()) != null) {
                dataBytesList.add(dataBytes);
            }
        } finally {
            reader.close();
        }

        return dataBytesList.toArray(new byte[dataBytesList.size()][]);
    }
}
