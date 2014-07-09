package liquibase.ext.ucd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaProcess {

    private JavaProcess() {}

    public static int exec( String className, String classpath, List<String> jvmArgs,  List<String> args ) throws IOException,
                                               InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        if ( classpath == null ) {
        	classpath = System.getProperty("java.class.path");
        }

        List<String> builderArgs = new ArrayList<>();
        builderArgs.add(javaBin);
        builderArgs.add("-cp");
        builderArgs.add(classpath);
        if ( jvmArgs != null ) {
        	builderArgs.addAll(jvmArgs);
        }
        builderArgs.add(className);
        if ( args != null ) {
        	builderArgs.addAll(args);
        }

        ProcessBuilder builder = new ProcessBuilder( builderArgs );

        Process process = builder.start();
        process.waitFor();
        return process.exitValue();
    }

}