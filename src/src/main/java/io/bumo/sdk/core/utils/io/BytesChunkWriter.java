package io.bumo.sdk.core.utils.io;

import java.io.*;

/**
 * Byte write
 * <p>
 * BytesChunkWriter The magic byte (Magic bytes) is defined as the beginning of the byte block stream, and is written before all contents
 * <p>
 * Before writing every byte block, we first write the block length as a 4 byte binary array, and then follow the contents of the block
 * <p>
 * After completing all the writes, write the 4 byte binary format of -1 as the end identification
 * <p>
 * 注：BytesChunkReader It's not thread safe
 *
 * @author bumo
 */
public class BytesChunkWriter implements Closeable{

    private static byte[] END_BYTES = BytesUtils.toBytes(-1);

    private byte[] magicBytes;

    private OutputStream out;

    private boolean enclose = false;

    /**
     * Create a byteschunkwriter instance
     *
     * @param magicString The magic character; as the starting mark of a byte block stream
     * @param out         Streams to be written
     * @throws IOException
     */
    public BytesChunkWriter(String magicString, OutputStream out) throws IOException{
        try {
            this.out = out;
            this.magicBytes = magicString.getBytes("UTF-8");
            writeMagic();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Create a BytesChunkWriter instance
     *
     * @param magicBytes The magic character; as the starting mark of a byte block stream
     * @param out
     * @throws IOException
     */
    public BytesChunkWriter(byte[] magicBytes, OutputStream out) throws IOException{
        this.magicBytes = magicBytes;
        this.out = out;
        writeMagic();
    }

    private void writeMagic() throws IOException{
        if (magicBytes.length == 0) {
            throw new IllegalArgumentException("The magicBytes is empty!");
        }
        out.write(magicBytes);
    }

    /**
     * Write a byte block
     * <p>
     * Before writing byte blocks, write 4 byte block length header and write contents
     *
     * @param bytes
     * @throws IOException
     */
    public void write(byte[] bytes) throws IOException{
        checkEncosed();
        byte[] lenHeader = BytesUtils.toBytes(bytes.length);
        out.write(lenHeader);
        out.write(bytes);
    }

    /**
     * Read the specified length block from the specified stream and write the stream
     * <p>
     * If the length of the read is less than the specified value, the IllegalArgumentException exception will be thrown
     *
     * @param len Block length
     * @param in  To read the stream of data
     * @throws IOException
     */
    public void write(int len, InputStream in) throws IOException{
        if (len < 1) {
            throw new IllegalArgumentException("The len must be positive!");
        }
        byte[] lenHeader = BytesUtils.toBytes(len);
        out.write(lenHeader);
        int wrLen = BytesUtils.copy(in, out, len);
        if (wrLen < len) {
            throw new IllegalArgumentException("The length of the input stream is less than the specified len of chunk!");
        }
    }

    public void flush() throws IOException{
        out.flush();
    }

    private void encloseChunk() throws IOException{
        if (enclose) {
            return;
        }
        out.write(END_BYTES);
        enclose = true;
    }

    private void checkEncosed(){
        if (enclose) {
            throw new IllegalStateException("This BytesChunkWriter instance is enclosed!");
        }
    }

    /**
     * End and close the flow
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException{
        if (!enclose) {
            encloseChunk();
            flush();
        }
        out.close();
    }

    public static byte[] compact(byte[] magicBytes, byte[]... dataBytes) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BytesChunkWriter writer = new BytesChunkWriter(magicBytes, out);
        try {
            if (dataBytes != null) {
                for (byte[] bs : dataBytes) {
                    writer.write(bs);
                }
            }
            writer.flush();
        } finally {
            writer.close();
        }
        return out.toByteArray();
    }
}
