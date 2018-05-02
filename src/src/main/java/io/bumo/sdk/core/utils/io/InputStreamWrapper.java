package io.bumo.sdk.core.utils.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream wrapper
 *
 * @author bumo
 */
public class InputStreamWrapper extends InputStream{

    private InputStream in;

    public InputStreamWrapper(InputStream in){
        this.in = in;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException{
        return in.read(b, off, len);
    }

    @Override
    public int read() throws IOException{
        return in.read();
    }

    @Override
    public int available() throws IOException{
        return in.available();
    }

    @Override
    public void close() throws IOException{
        in.close();
    }

    @Override
    public long skip(long n) throws IOException{
        return in.skip(n);
    }

    @Override
    public boolean markSupported(){
        return in.markSupported();
    }

    @Override
    public synchronized void mark(int readlimit){
        in.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException{
        in.reset();
    }

    @Override
    public int read(byte[] b) throws IOException{
        return in.read(b);
    }

}
