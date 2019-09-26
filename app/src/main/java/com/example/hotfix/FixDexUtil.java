package com.example.hotfix;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author zhangjun
 * @date 2019-9-26
 */
public class FixDexUtil {

    private static HashSet<File> loadedDex = new HashSet<File>();

    static{
        loadedDex.clear();
    }

    /**
     *  遍历所有的dex文件存储到成员变量loadedDex中，用于后续合并
     * @param context
     */
   public static void loadFixedDex(Context context){

       if(context==null)return;
        //私有目录dex文件路径
       File fileDir  = context.getDir("odex", Context.MODE_PRIVATE);
       File[] files = fileDir.listFiles();
       for (File file : files) {
           LogUtil.log("loadFixedDex>>>"+file.getName());
           if(file.getName().startsWith("classes") && file.getName().endsWith("dex")&&!"classes.dex".equals(file.getName())){
                //找到了要修复的dex
               loadedDex.add(file);
           }
       }

       //合并之前到dex
       doDexInject(context,fileDir);
   }

    private static void doDexInject(Context context, File filesDir) {
        //dex文件需要被写入的目录
        String optimizeDir = filesDir.getAbsolutePath()+File.separator+"opt_dex";
        File fileOpt = new File(optimizeDir);
        if(!fileOpt.exists()){
            fileOpt.mkdirs();
        }
        //1.获得加载应用程序dex的PathClassLoader
        PathClassLoader systemClassLoader = (PathClassLoader) context.getClassLoader();
        for (File dexFile : loadedDex) {
            //DexClassLoader构造参数
            //String dexPath,dex路径
            // String optimizedDirectory,
            // String librarySearchPath, so路径
            // ClassLoader parent
            DexClassLoader dexClassLoader = new DexClassLoader(
                    dexFile.getAbsolutePath(),
                    optimizeDir,
                    null,
                    systemClassLoader
                    );

            //3.合并dex
            try {
                Object dexObj = ReflectUtil.getPathList(dexClassLoader);
                Object pathObj = ReflectUtil.getPathList(systemClassLoader);
                Object fixDexElements = ReflectUtil.getDexElements(dexObj);
                Object pathDexElements = ReflectUtil.getDexElements(pathObj);
                //合并两个数组
                Object newDexElements = ReflectUtil.combineArray(fixDexElements,pathDexElements);
                LogUtil.log("combineArray>>>");
                //重新赋值给PathClassLoader 中的exElements数组

                Object pathList = ReflectUtil.getPathList(systemClassLoader);

                ReflectUtil.setField(pathList,pathList.getClass(),"dexElements",newDexElements);
                LogUtil.log("修复完成>>>");
                LogUtil.log("修复完成>>>"+pathList+"   ");
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.log("doDexInject Exception>>>"+e.toString());
            }

        }

    }
}
