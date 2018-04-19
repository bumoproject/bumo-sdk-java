package org.bumo.sdk.core.utils;

import org.bumo.sdk.core.utils.spring.StringUtils;

public abstract class PathUtils{

    public static final String PATH_SEPERATOR = "/";
    public static final char PATH_SEPERATOR_CHAR = '/';

    public static final String WINDOWS_PATH_SEPERATOR = "\\";

    public static final String SCHEMA_SEPERATOR = ":";

    /**
     * Standardized path
     * <p>
     * The standardization process includes：
     * 1、Clean up the "." of the string;
     * 2、Clears the ".." in the string and the corresponding upper level path in the path (For example, ab/../cd becomes CD after processing)
     * 2、Replace the separator "windows" with the standard separator "/"
     * 3、Replace a continuous repeating separator with a single separator
     * 4、Remove the delimiter of the end
     * 5、Remove the blank character in it
     * <p>
     * Note: the schema head separated with a colon ":" will be retained
     *
     * @param path
     * @return
     */
    public static String standardize(String path){
        path = StringUtils.trimAllWhitespace(path);
        path = StringUtils.cleanPath(path);
        path = cleanRepeatlySeperator(path);
        if (path.endsWith(PATH_SEPERATOR)) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * Converting the specified path to an absolute path；
     * <p>
     * Method will detect the specified path if it does not start with the path separator "/", nor does the colon ":" the beginning of the separated schema (for example, file:)
     * <p>
     * At the beginning, add the path separator "/"
     * <p>
     * Note: the method will not detect whether the path is standard or automatic
     *
     * @param path
     * @return
     */
    public static String absolute(String path){
        if (path.startsWith(PATH_SEPERATOR)) {
            return path;
        }
        if (path.indexOf(SCHEMA_SEPERATOR) >= 0) {
            return path;
        }
        return PATH_SEPERATOR + path;
    }

    /**
     * Clear the repeating separator in the path
     *
     * @param path
     * @return
     */
    public static String cleanRepeatlySeperator(String path){
        // Clear the repeating separator；
        String schema = "";
        String pathToProcess = path;
        int index = path.indexOf("://");
        if (index >= 0) {
            schema = path.substring(0, index + 3);
            for (index = index + 3; index < path.length(); index++) {
                if (path.charAt(index) != PATH_SEPERATOR_CHAR) {
                    break;
                }
            }
            pathToProcess = path.substring(index);
        }
        StringBuilder pathToUse = new StringBuilder();
        boolean hit = false;
        char ch;
        for (int i = 0; i < pathToProcess.length(); i++) {
            ch = pathToProcess.charAt(i);
            if (ch == PATH_SEPERATOR_CHAR) {
                if (hit) {
                    continue;
                } else {
                    hit = true;
                }
            } else {
                hit = false;
            }
            pathToUse.append(ch);
        }
        return schema + pathToUse;
    }

    /**
     * @param paths
     */
    public static String concatPaths(String... paths){
        if (paths == null || paths.length == 0) {
            return "";
        }
        StringBuilder path = new StringBuilder();
        for (String p : paths) {
            path.append(p);
            path.append(PATH_SEPERATOR_CHAR);
        }
        return standardize(path.toString());
    }
}
