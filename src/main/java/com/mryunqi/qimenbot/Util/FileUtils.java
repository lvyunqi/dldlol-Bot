package com.mryunqi.qimenbot.Util;

import java.io.*;

public class FileUtils {
    private static final String rootPath = System.getProperty("user.dir");
    public static String is_Directory(String path){
        String Path = rootPath.concat(path);
        File file = new File(Path);
        if  (!file .exists()  && !file .isDirectory()){
            Boolean result = file.mkdirs();
            return "[文件夹]".concat(path).concat(String.valueOf(result));
        }else{
            return "[文件夹]".concat(path).concat("已存在");
        }
    }

    public static Boolean is_file(String filePath){
        String Path = rootPath.concat(filePath);
        File file = new File(Path);
        return file.exists();
    }

}