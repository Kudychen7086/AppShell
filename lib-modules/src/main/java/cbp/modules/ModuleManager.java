package cbp.modules;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.util.List;
import java.util.ServiceLoader;

import cbp.modules.navigator.Navigator;

/**
 * @author cbp
 * @date 2018/4/3 10:02
 */
public class ModuleManager {
    private static final ModulesConfiguration CONFIGURATION = new ModulesConfiguration();

    private ModuleManager() {
        // hide.
    }

    public static void dispatchApplicationCreated(Application application) {
        final ServiceLoader<Module> modules = ServiceLoader.load(Module.class, Module.class.getClassLoader());
        dispatchApplicationCreated(application, modules);
    }

    @SuppressWarnings("WeakerAccess")
    public static void dispatchApplicationCreated(Application application, Iterable<Module> modules) {
        for (Module module : modules) {
            module.dispatchApplicationCreated(application, CONFIGURATION);
        }
        for (Module module : modules) {
            module.dispatchModulesCreated(application);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Service
    // ---------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> service) {
        return CONFIGURATION.getService(service);
    }

    // ---------------------------------------------------------------------------------------------
    // Navigator
    // ---------------------------------------------------------------------------------------------

    public static boolean startForResult(Activity activity, String module, int requestCode) {
        return start(new ActivityWrapper(activity, requestCode), module);
    }

    @SuppressWarnings("unused")
    public static boolean startForResult(Activity activity, String module, String target,
                                         int requestCode) {
        return start(new ActivityWrapper(activity, requestCode), module, target);
    }

    @SuppressWarnings("unused")
    public static boolean startForResult(Activity activity, String module, Bundle data,
                                         int requestCode) {
        return start(new ActivityWrapper(activity, requestCode), module, data);
    }

    @SuppressWarnings("unused")
    public static boolean startForResult(Activity activity, String module, String target,
                                         Bundle data, int requestCode) {
        return start(new ActivityWrapper(activity, requestCode), module, target, data);
    }

    public static boolean start(Context context, String module) {
        return start(context, module, Navigator.DEFAULT_TARGET, Navigator.DEFAULT_DATA);
    }

    public static boolean start(Context context, String module, String target) {
        return start(context, module, target, Navigator.DEFAULT_DATA);
    }

    public static boolean start(Context context, String module, Bundle data) {
        return start(context, module, Navigator.DEFAULT_TARGET, data);
    }

    public static boolean start(Context context, String module, String target, Bundle data) {
        final List<Navigator> navigators = CONFIGURATION.getNavigators(module);
        if (navigators == null) {
            return false;
        }
        if (target == null) {
            target = Navigator.DEFAULT_TARGET;
        }
        if (data == null) {
            data = Navigator.DEFAULT_DATA;
        }
        for (Navigator navigator : navigators) {
            if (navigator.onStart(context, target, data)) {
                return true;
            }
        }
        return false;
    }

    private static class ActivityWrapper extends ContextWrapper {
        private final Activity mActivity;
        private final int mRequestCode;

        public ActivityWrapper(Activity activity, int requestCode) {
            super(activity);
            mActivity = activity;
            mRequestCode = requestCode;
        }

        @Override
        public void startActivity(Intent intent) {
            normalizeIntent(intent);
            mActivity.startActivityForResult(intent, mRequestCode);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void startActivity(Intent intent, Bundle options) {
            normalizeIntent(intent);
            mActivity.startActivityForResult(intent, mRequestCode, options);
        }

        private static void normalizeIntent(Intent intent) {
            if (intent == null) {
                return;
            }
            /*
             * Activity.startActivityForResult() 方法不支持 {@link Intent#FLAG_ACTIVITY_NEW_TASK}.
             */
            intent.setFlags(intent.getFlags() & (~Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}