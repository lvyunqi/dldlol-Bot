package com.mryunqi.qimenbot.Util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
public class GoCQReadLog implements Runnable {
    private File logFile = null;
    private long lastTimeFileSize = 0; // 上次文件大小
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public GoCQReadLog(File logFile) {
        this.logFile = logFile;
        lastTimeFileSize = logFile.length();
    }

    /**
     * 实时输出日志信息
     */
    public void run() {
        while (true) {
            try {
                RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
                randomFile.seek(lastTimeFileSize);
                String tmp = null;
                while ((tmp = randomFile.readLine()) != null) {
                    System.out.println(new String(tmp.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
                lastTimeFileSize = randomFile.length();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
