package com.mryunqi.qimenbot.Controller;
import com.mryunqi.qimenbot.Util.GoCQReadLog;
import com.mryunqi.qimenbot.Util.YmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Configuration
@Component
public class Gocq {
    private static Logger log = LoggerFactory.getLogger(Gocq.class);
    private final Environment environment;

    public Gocq(Environment environment){
        this.environment = environment;
    }

    //@PostConstruct
    public static void Run_Go_cq() throws Exception {
        String rootPath = System.getProperty("user.dir");
        log.info("正在启动Go-Cqhttp客户端...");
        WriteYml(rootPath);
        Runtime runtime = Runtime.getRuntime();
        String command = "cmd /c start cmd /c go-cqhttp.exe";
        String path = rootPath + "\\data\\go-cqhttp";
        File exeFile = new File(path);
        try {
            Process GoCq = runtime.exec(command, null, exeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        //RunLog(rootPath);
    }

    /**
     * 从控制台获取密码
     * @return 返回从控制台获取的密码
     */
    public static String getPassword(){
        Console console = System.console();
        char[] passwordCharArray = console.readPassword();
        return new String(passwordCharArray);
    }

    private static void WriteYml(String rootPath) throws Exception {
        Scanner sc = new Scanner(System.in);
        String FileDir = rootPath+"\\data\\go-cqhttp\\";
        File yml = new File(FileDir + "config.yml");
        YmlUtil.setYmlFile(yml);
        Long dqq = 2733333333L;
        if((dqq).equals(YmlUtil.getByKey("account.uin"))){
            System.out.println("----->请输入登录QQ：");
            Long qq = sc.nextLong();
            //Long longnum = (long) qq;
            YmlUtil.saveOrUpdateByKey("account.uin",qq);
            System.out.println("----->是否输入登录密码【y or n】(输入n时使用扫码登录)：");
            String yn = sc.next();
            if (("y").equals(yn)){
                //String password = getPassword();
                //System.out.println("----->请输入登录密码：");
                String password = readPassword("----->请输入登录密码: ").replaceAll("\\s*|\r|\n|\t","");
                YmlUtil.saveOrUpdateByKey("account.password",password);
            }
        }
        log.warn("即将登录QQ-->"+YmlUtil.getByKey("account.uin"));
    }

    private static void RunLog(String rootPath) {
        String timeStr= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String FileDir = rootPath+"\\data\\go-cqhttp\\logs\\";
        File logFile = new File(FileDir+timeStr+".log");
        Thread rthread = new Thread(new GoCQReadLog(logFile));
        rthread.start();
    }

    //关闭gocq
    //@PreDestroy
    public void destory() {
        try {
            Runtime run = Runtime.getRuntime();
            String cmd = "cmd /k start taskkill /f /t /im go-cqhttp.exe";
            Process StopGo = run.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readPassword(String prompt) {
        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();

        StringBuilder password = new StringBuilder();
        InputStream inputStream = System.in;
        byte[] buffer = new byte[1];
        List<Byte> result = new LinkedList<>();

        try {
            while (inputStream.read(buffer) != -1) {
                if (buffer[0] == 10) {
                    // stop masking
                    et.stopMasking();
                    break;
                }
                result.add(buffer[0]);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        for (Byte b : result) {
            password.append((char) b.byteValue());
        }
        // return the password entered by the user
        return password.toString();
    }

    static class EraserThread implements Runnable {
        private volatile boolean stop;

        public EraserThread(String prompt) {
            System.out.print(prompt + " ");
        }

        public void run() {
            stop = true;
            while (stop) {
                System.out.print("\010*");
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        /**
         * Instruct the thread to stop masking
         */
        public void stopMasking() {
            this.stop = false;
        }
    }
}