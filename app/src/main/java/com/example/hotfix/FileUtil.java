package com.example.hotfix;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangjun
 * @date 2019-9-26
 */
public class FileUtil {

    /**
     * 拷贝文件
     * @param sourceFile
     * @param targetFile
     */
    public static void copyFile(File sourceFile, File targetFile){

        InputStream is = null;
        FileOutputStream os = null;
        try {
            LogUtil.log("fixBug: " +targetFile.getAbsolutePath()+"  "+sourceFile.getAbsolutePath());
            is = new FileInputStream(sourceFile);
            os = new FileOutputStream(targetFile.getAbsolutePath());
            LogUtil.log("fixBug: " +targetFile.getAbsolutePath()+"  "+sourceFile.getAbsolutePath());
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            LogUtil.log("复制成功");
            if (targetFile.exists()) {
                LogUtil.log("dex overwrite");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
