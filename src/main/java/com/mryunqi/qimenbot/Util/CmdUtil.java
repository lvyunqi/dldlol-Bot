package com.mryunqi.qimenbot.Util;

import java.io.*;

public class CmdUtil {
    public static String Run(String command, String input) throws IOException, InterruptedException {
        // create process
        String[] argss = {"cmd", "/c", command};
        ProcessBuilder pb = new ProcessBuilder(argss);
        Process process = pb.start();
        // create write reader
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // write input
        writer.write(input + "\n");
        writer.flush();
        // read output
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        // wait for process to finish
        int returnValue = process.waitFor();
        // close writer reader
        reader.close();
        writer.close();
        System.out.println("Exit with value " + returnValue);
        return String.valueOf(returnValue);
    }
}
