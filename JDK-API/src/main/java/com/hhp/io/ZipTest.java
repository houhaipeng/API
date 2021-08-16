package com.hhp.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class ZipTest {

    @Test
    public void test1() throws FileNotFoundException {
        //造文件
        File file1 = new File("Docker.md");
        File file2 = new File("Docker_copy3.md");
        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        File zipFile = new File("C:\\Users\\admin\\Desktop\\Docker-zip.zip");
        //造流
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        //2.1
        try {
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                //2.2
                BufferedInputStream bis = new BufferedInputStream(fis);
                //3 读取和写入
                try {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    byte[] buffer = new byte[5];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        System.out.println(len);
                        zos.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
