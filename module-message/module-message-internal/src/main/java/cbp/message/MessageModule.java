package cbp.message;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import cbp.message.api.MessageApiService;
import cbp.message.impl.MessageApiServiceImpl;
import cbp.modules.Module;
import cbp.modules.navigator.Navigator;
import cbp.modules.provider.LazyServiceProvider;
import cbp.util.ModuleConstants;

/**
 * @author cbp
 * @date 2018/4/4 13:37
 */
public class MessageModule extends Module {

    @Override
    protected void onCreate(Application application) {
        registerService(MessageApiService.class, new LazyServiceProvider<MessageApiService>() {
            @Override
            protected MessageApiService create() {
                return new MessageApiServiceImpl();
            }
        });
        // navigators.
        registerNavigator(ModuleConstants.MODULE_MESSAGE, new MessageNavigator());
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