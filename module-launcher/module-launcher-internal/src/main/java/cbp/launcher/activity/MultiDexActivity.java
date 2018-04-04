package cbp.launcher.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cbp.launcher.R;

/**
 * 子进程启动页, 负责异步加载 MultiDex. 完成后打开主进程欢迎页.
 */
public class MultiDexActivity extends Activity {

    private static final String FINISH_ACTION = "com.eastmoney.berlin.multidex.finish";

    public static void notifyMainProcessLaunched(Context context) {
        try {
            final Intent finishIntent = new Intent(FINISH_ACTION);
            finishIntent.setPackage(context.getPackageName());
            context.sendBroadcast(finishIntent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disablePendingTransition();

        registerReceiver(mFinishReceiver, new IntentFilter(FINISH_ACTION));

        setContentView(R.layout.activity_welcome_multidex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mFinishReceiver);
    }

    @Override
    public void onBackPressed() {
        // 不允许后退.
    }

    private void disablePendingTransition() {
//        overridePendingTransition(
//                com.eastmoney.android.util.R.anim.activity_async_multi_dex,
//                com.eastmoney.android.util.R.anim.activity_async_multi_dex);
    }

    private final BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isFinishing()) {
                finish();

                disablePendingTransition();
            }
        }
    };
}