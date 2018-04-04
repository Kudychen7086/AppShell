package cbp.clients;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import cbp.clients.api.ClientsApiService;
import cbp.clients.impl.ClientsApiServiceImpl;
import cbp.modules.Module;
import cbp.modules.navigator.Navigator;
import cbp.modules.provider.LazyServiceProvider;
import cbp.util.ModuleConstants;

/**
 * @author cbp
 * @date 2018/4/4 13:37
 */
public class ClientsModule extends Module {

    @Override
    protected void onCreate(Application application) {
        registerService(ClientsApiService.class, new LazyServiceProvider<ClientsApiService>() {
            @Override
            protected ClientsApiService create() {
                return new ClientsApiServiceImpl();
            }
        });
        // navigators.
        registerNavigator(ModuleConstants.MODULE_CLIENTS, new MessageNavigator());
    }

    private static class MessageNavigator implements Navigator {

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