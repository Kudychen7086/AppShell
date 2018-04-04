package cbp.appshell;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import cbp.account.AccountModule;
import cbp.clients.ClientsModule;
import cbp.launcher.LauncherModule;
import cbp.launcher.activity.MultiDexActivity;
import cbp.message.MessageModule;
import cbp.modules.ModuleManager;
import cbp.util.ContextUtil;

/**
 * @author cbp
 * @Description 项目中请重命名此类
 * @date 2018/4/2 14:27
 */
public class MyApplication extends Application {
    private static final String PROCESS_DEX_OPTIMIZATION = ":multidex";

    /**
     * 实现Multi-dex必须实现的回调函数
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        final String processName = getCurrentProcessName(this);
        if (checkCurrentProcess(processName, PROCESS_DEX_OPTIMIZATION)) { // MultiDex 进程, 直接返回.
            return;
        }
        if (processName != null && processName.equals(getPackageName())) { // 主进程.
            startMultiDexActivityIfNeeded();
        }
        try {
            MultiDex.install(this);
            saveSourcesState();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 检测当前进程. 若当前进程为multidex子进程, 则不作任何操作.
        final String processName = getCurrentProcessName(this);
        if (checkCurrentProcess(processName, PROCESS_DEX_OPTIMIZATION)) { // MultiDex 进程, 直接返回.
            return;
        }
        //此时做各个模块加载动作
        beforeApplicationCreate();
        ContextUtil.init(this);
        // ---------------------------------------------------------------------------------------------
        // TODO:在以下做一些全局操作，比如初始化日志系统、初始化崩溃处理线程、初始化网络、监控Activity生命周期、注册推送等
        // ---------------------------------------------------------------------------------------------
    }

    // ---------------------------------------------------------------------------------------------
    // MultiDex
    // ---------------------------------------------------------------------------------------------
    private void startMultiDexActivityIfNeeded() {
        if (!isDexInstalled()) {
            final Intent intent = new Intent(this, MultiDexActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private boolean isDexInstalled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        final String currentSourcesState = getSharedPreferences("async_multi_dex", MODE_PRIVATE)
                .getString("sources_state", null);
        return TextUtils.equals(getSourcesState(), currentSourcesState);
    }

    private String getSourcesState() {
        final StringBuilder builder = new StringBuilder();
        final String sourceDir = getApplicationInfo().sourceDir;
        try {
            final File file = new File(sourceDir);
            builder.append(file.lastModified()).append('/').append(file.length());
        } catch (Throwable ignore) {
        }
        builder.append('_');
        try {
            final PackageInfo packageInfo = getPackageManager()
                    .getPackageInfo(getPackageName(), MODE_PRIVATE);
            if (packageInfo != null) {
                builder.append(packageInfo.versionName);
            }
        } catch (PackageManager.NameNotFoundException ignore) {
            // 忽略.
        }
        return builder.toString();
    }

    @SuppressLint("ApplySharedPref")
    private void saveSourcesState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        getSharedPreferences("async_multi_dex", MODE_PRIVATE).edit()
                .putString("sources_state", getSourcesState()).commit();
    }

    private static boolean checkCurrentProcess(String processName, String process) {
        return processName != null && processName.endsWith(process);
    }

    private static String getCurrentProcessName(Context context) {
        String processName = null;
        try {
            final int pid = android.os.Process.myPid();
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                final List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
                if (runningProcesses != null) {
                    for (ActivityManager.RunningAppProcessInfo info : runningProcesses) {
                        if (info.pid == pid) {
                            processName = info.processName;
                            break;
                        }
                    }
                }
            }
        } catch (Exception ignore) {
        }
        return processName;
    }

    private void beforeApplicationCreate() {
        ModuleManager.dispatchApplicationCreated(this, Arrays.asList(
                new LauncherModule(),
                new ClientsModule(),
                new MessageModule(),
                new AccountModule()));
    }
}
