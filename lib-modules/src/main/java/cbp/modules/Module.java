package cbp.modules;

import android.app.Application;

import cbp.modules.navigator.Navigator;
import cbp.modules.provider.ServiceProvider;
import cbp.modules.provider.SimpleServiceProvider;

/**
 * @author cbp
 * @date 2018/4/3 10:02
 */
public abstract class Module {
    private ModulesConfiguration mTempConfiguration;

    void dispatchApplicationCreated(Application application, ModulesConfiguration configuration) {
        mTempConfiguration = configuration;
        try {
            onCreate(application);
        } finally {
            mTempConfiguration = null;
        }
    }

    void dispatchModulesCreated(Application application) {
        onPostCreate(application);
    }

    /**
     * 应用启动时回调.
     */
    protected abstract void onCreate(Application application);

    /**
     * 所有模块初始化完成后回调.
     */
    protected void onPostCreate(Application application) {
        // do nothing.
    }

    // ---------------------------------------------------------------------------------------------
    // Service
    // ---------------------------------------------------------------------------------------------

    protected <T> void registerService(Class<? super T> service, T instance)
            throws NullPointerException, IllegalStateException {
        registerService(service, new SimpleServiceProvider<>(instance));
    }

    @SuppressWarnings("WeakerAccess")
    protected <T> void registerService(Class<? super T> service, ServiceProvider<T> provider)
            throws NullPointerException, IllegalStateException {
        assertOnCreate("registerService()");

        mTempConfiguration.registerService(service, provider);
    }

    // ---------------------------------------------------------------------------------------------
    // Navigator
    // ---------------------------------------------------------------------------------------------

    protected void registerNavigator(String module, Navigator navigator) {
        assertOnCreate("registerNavigator()");

        mTempConfiguration.registerNavigator(module, navigator);
    }

    // ---------------------------------------------------------------------------------------------
    // Tools
    // ---------------------------------------------------------------------------------------------

    private void assertOnCreate(String action) throws IllegalStateException {
        if (mTempConfiguration == null) {
            throw new IllegalStateException(action + " can only be called in onCreate()");
        }
    }
}