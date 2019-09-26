package com.example.hotfix;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSec(View view) {
        startActivity(new Intent(this,SecActivity.class));
    }

    /**
     * 修复原理
     * 使用DexClassLoader：能够加载未安装的jar/apk/dex
     * DexClassLoader ->DexPathlist->dexElements[]
     * 将修复bug的dex包与dexElement[] 合并成一个新的dex，并把fix.dex 放到 数组的前面
     * 通过反射将 原来的dexElements[]用 合并后的的dexElements[] 替换掉，这样就可不会加载旧的带有bug的class文件。
     * @param view
     */
    public void fixBug(View view) {
        //修复bug
        //1.修复包 classes2.dex
        String dexName = "classes2.dex";
        File sourceFile = new File(Environment.getExternalStorageDirectory(),dexName);

        //DexClassLoader指定的应用程序目录
        File targetFile = new File(getDir("odex", Context.MODE_PRIVATE).getAbsolutePath()+File.separator+dexName);
        if(targetFile.exists()){
            targetFile.delete();
        }
        //拷贝到私有路径
        FileUtil.copyFile(sourceFile,targetFile);

        FixDexUtil.loadFixedDex(this);

    }
}
