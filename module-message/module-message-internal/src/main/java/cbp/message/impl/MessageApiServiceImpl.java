package cbp.message.impl;

import cbp.message.LauncherActivityMessage;
import cbp.message.api.MessageApiService;
import cbp.launcher.ILauncherElement;

/**
 * @author cbp
 * @date 2018/4/4 13:37
 */
public class MessageApiServiceImpl implements MessageApiService {

    @Override
    public ILauncherElement getLauncherMessage() {
        return new LauncherActivityMessage();
    }
}

