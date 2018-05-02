package io.bumo.sdk.core.utils.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bumo.sdk.core.utils.spring.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandConsole{

    private Map<String, CommondProcessor> processors = new ConcurrentHashMap<>();

    private CommondProcessor defaultProcessor;

    private boolean monitoring = false;

    private BufferedReader in;

    private PrintStream out;

    private String prompt;

    public PrintStream out(){
        return out;
    }

    /**
     * @param input  Command input stream
     * @param output Result output stream
     * @param prompt Prompt
     */
    public CommandConsole(InputStream input, PrintStream output, String prompt){
        try {
            this.in = new BufferedReader(new InputStreamReader(input, "GBK"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        this.out = output;
        this.prompt = prompt;

        // Defining the default command processor
        defaultProcessor = new CommondProcessor(){
            @Override
            public void onEnter(String command, String[] args, CommandConsole console){
                console.out().println("Unsupported command [" + command + "]!");
            }
        };
    }

    public void setDefaultCommandProcessor(CommondProcessor processor){
        if (processor == null) {
            throw new IllegalArgumentException("Null argument!");
        }
        this.defaultProcessor = processor;
    }

    /**
     * Register command processor
     * <p>
     * A command can only have one processor. Repeated registration of multiple processors will cause exceptions
     *
     * @param command   Command text; automatically ignore case
     * @param processor
     */
    public synchronized void register(String command, CommondProcessor processor){
        if (StringUtils.containsWhitespace(command)) {
            throw new IllegalArgumentException("Can't register command with white space character!");
        }
        if (processors.containsKey(command)) {
            throw new IllegalStateException("The command[" + command + "] has been registered!");
        }
        processors.put(command.toLowerCase(), processor);
    }

    /**
     * Start monitoring commands
     * <p>
     * Method will block the current thread
     */
    public synchronized void open(){
        monitoring = true;
        while (monitoring) {
            // Output hint
            out.println();
            out.print(prompt);

            // Read the command
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            if (command == null) {
                continue;
            }
            String[] cmdArgs = StringUtils.tokenizeToStringArray(command, " ");
            if (cmdArgs.length == 0) {
                continue;
            }
            String cmd = cmdArgs[0];
            String[] args;
            if (cmdArgs.length > 1) {
                args = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length);
            } else {
                args = new String[0];
            }
            CommondProcessor processor = processors.get(cmd.toLowerCase());
            try {
                if (processor != null) {
                    processor.onEnter(cmd, args, this);
                } else {
                    defaultProcessor.onEnter(cmd, args, this);
                }
            } catch (Exception e) {
                out.println("Error!!--" + e.getMessage());
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Stop command console
     */
    public synchronized void close(){
        monitoring = false;
    }

    private Logger logger = LoggerFactory.getLogger(CommandConsole.class);
}
