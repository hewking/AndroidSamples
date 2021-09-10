package com.hewking.develop.test;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description:  * github:https://github.com/Jay-Goo/JacocoTestHelper
 * @Author: jianhao
 * @Date: 2021-09-10 14:28
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
public class JacocoHelper {

    private static final String TAG = JacocoHelper.class.getName();
    //ec文件的路径
    private static String DEFAULT_COVERAGE_FILE_PATH = "/hibox/coverage.ec";

    private static boolean ISDEBUG = false;

    /**
     * 初始化
     * @param projectPath  '项目路径' + '/app/build/outputs/code-coverage/'
     * @param isDebug 是否打开log
     */
    public static void init(String projectPath, boolean isDebug){
        ISDEBUG = isDebug;
    }

    /**
     * 生成ec文件
     *
     * @param isNew 是否重新创建ec文件
     */
    public static File generateEcFile(Context context, boolean isNew) {
        OutputStream out = null;
        File mCoverageFilePath = new File(context.getExternalCacheDir(), DEFAULT_COVERAGE_FILE_PATH);
        Log.d(TAG, "JacocoHelper_generateEcFile: path: " + mCoverageFilePath);
        try {
            if (isNew && mCoverageFilePath.exists()) {
                Log.d(TAG, "JacocoHelper_generateEcFile: 清除旧的ec文件");
                mCoverageFilePath.delete();
            }
            if (!mCoverageFilePath.exists()) {
                File dir = mCoverageFilePath.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                mCoverageFilePath.createNewFile();
            }
            out = new FileOutputStream(mCoverageFilePath.getPath(), true);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);
            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            Log.d(TAG, "JacocoHelper_generateEcFile: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    Log.d(TAG, "JacocoHelper_generateEcFile: "+mCoverageFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mCoverageFilePath;
    }

}
