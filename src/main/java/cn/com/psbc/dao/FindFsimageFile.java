package cn.com.psbc.dao;

import java.io.File;

public class FindFsimageFile {
    public File findFiles(String baseDirName) {
        File tempFile = null;
        File baseDir = new File(baseDirName);        // 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {    // 判断目录是否存在
            System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName = null;

        File[] files = baseDir.listFiles();
        if (files.length == 0) {//该文件夹下没有文件，为空文件夹
            System.out.println("该文件夹下没有文件，为空文件夹");
            return null;
        }
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            tempName = tempFile.getName();
            //System.out.println(tempName);
            if (tempName.contains("fsimage_000")) {
                //System.out.println(tempFile.getAbsoluteFile().toString());
                return tempFile.getAbsoluteFile();
            }
        }
        return tempFile.getAbsoluteFile();
    }
}


