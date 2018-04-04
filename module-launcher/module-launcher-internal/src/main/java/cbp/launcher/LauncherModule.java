package cbp.launcher;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import cbp.launcher.api.LauncherApiService;
import cbp.launcher.impl.LauncherApiServiceImpl;
import cbp.modules.Module;
import cbp.modules.navigator.Navigator;
import cbp.modules.provider.LazyServiceProvider;
import cbp.util.ModuleConstants;

/**
 * @author cbp
 * @date 2018/4/3 11:10
 */
public class LauncherModule extends Module {
    @Override
    protected void onCreate(Application application) {
        // services.
        registerService(LauncherApiService.class, new LazyServiceProvider<LauncherApiService>() {

            @Override
            protected LauncherApiService create() {
                return new LauncherApiServiceImpl();
            }
        });

        // navigators.
        registerNavigator(ModuleConstants.MODULE_LAUNCHER, new LauncherNavigator());
    }

    private static class LauncherNavigator implements Navigator {

        @Override
        public boolean onStart(Context context, String target, Bundle data) {
            switch (target) {
                //TODO:添加自定义的一些case，做跳转处理，此处用字符串home、systemSettings作为示例，具体根据业务需求去实现
                case "home":
                    //跳转到首页
                    return true;
                case "systemSettings":
                    //跳转到系统设置
                    return true;
            }
            return true;
        }
    }
}
