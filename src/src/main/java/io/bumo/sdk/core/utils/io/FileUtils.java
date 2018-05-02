package io.bumo.sdk.core.utils.io;

import java.io.*;

/**
 * @author bumo
 */
public class FileUtils{

    /**
     * Returns the first non blank lines (i.e. contains a non empty string)
     *
     * @param file
     * @param charset Character set
     * @return Returns the first non blank lines; the empty string returns the result does not automatically intercept two
     * @throws IOException
     */
    public static String getFirstLineText(File file, String charset) throws IOException{
        FileInputStream in = new FileInputStream(file);
        try {
            InputStreamReader reader = new InputStreamReader(in, charset);
            return getFirstLine(reader);
        } finally {
            in.close();
        }
    }

    public static String getFirstLine(Reader reader) throws IOException{
        BufferedReader bfr = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        try {
            String line = null;
            while ((line = bfr.readLine()) != null) {
                if (line.trim().length() > 0) {
                    return line;
                }
            }
            return null;
        } finally {
            bfr.close();
        }
    }

    /**
     * Save the specified text to the specified file
     *
     * @param file    Files to be saved
     * @param charset character set
     * @param text    Text content
     * @throws IOException
     */
    public static void saveText(File file, String charset, String text) throws IOException{
        FileOutputStream out = new FileOutputStream(file, false);
        try {
            OutputStreamWriter writer = new OutputStreamWriter(out, charset);
            try {
                writer.write(text);
            } finally {
                writer.close();
            }
        } finally {
            out.close();
        }
    }

    public static String readText(String file, String charset) throws IOException{
        return readText(new File(file), charset);
    }

    public static String readText(File file, String charset) throws IOException{
        FileInputStream in = new FileInputStream(file);
        try {
            return readText(in, charset);
        } finally {
            in.close();
        }
    }


    public static String readText(InputStream in, String charset) throws IOException{
        InputStreamReader reader = new InputStreamReader(in, charset);
        try {
            StringBuilder content = new StringBuilder();
            char[] buffer = new char[64];
            int len = 0;
            while ((len = reader.read(buffer)) > 0) {
                content.append(buffer, 0, len);
            }
            return content.toString();
        } finally {
            reader.close();
        }
    }

}
