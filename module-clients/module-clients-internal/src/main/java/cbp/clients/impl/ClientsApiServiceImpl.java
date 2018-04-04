package cbp.clients.impl;

import cbp.clients.LauncherActivityClients;
import cbp.clients.api.ClientsApiService;
import cbp.launcher.ILauncherElement;

/**
 * @author cbp
 * @date 2018/4/4 13:37
 */
public class ClientsApiServiceImpl implements ClientsApiService {

    @Override
    public ILauncherElement getLauncherClients() {
        return new LauncherActivityClients();
    }
}

