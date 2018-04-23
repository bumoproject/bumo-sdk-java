package io.bumo.sdk.core.utils.console;

/**
 * Command processor
 *
 * @author bumo
 */
public interface CommondProcessor{

    public void onEnter(String command, String[] args, CommandConsole console);

}
