package cbp.account;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import cbp.account.api.AccountApiService;
import cbp.account.impl.AccountApiServiceImpl;
import cbp.modules.Module;
import cbp.modules.navigator.Navigator;
import cbp.modules.provider.LazyServiceProvider;
import cbp.util.ModuleConstants;

/**
 * @author cbp
 * @date 2018/4/3 16:35
 */
public class AccountModule extends Module {

    @Override
    protected void onCreate(Application application) {
        registerService(AccountApiService.class, new LazyServiceProvider<AccountApiService>() {
            @Override
            protected AccountApiService create() {
                return new AccountApiServiceImpl();
            }
        });
        // navigators.
        registerNavigator(ModuleConstants.MODULE_ACCOUNT, new AccountNavigator());
    }

    private static class AccountNavigator implements Navigator {

        @Override
        public boolean onStart(Context context, String target, Bundle data) {
            switch (target) {
                //TODO:添加自定义的一些case，做跳转处理，此处用字符串login作为示例，具体根据业务需求去实现
                case "login":
                    //跳转到登录页
                    return true;
            }
            return true;
        }
    }
}