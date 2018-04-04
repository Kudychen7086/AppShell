package cbp.launcher.impl;


import cbp.launcher.ILauncherElement;
import cbp.launcher.LauncherActivityHome;
import cbp.launcher.api.LauncherApiService;

/**
 * @author cbp
 * @date 2018/4/3 11:10
 */
public class LauncherApiServiceImpl implements LauncherApiService {
    @Override
    public ILauncherElement getLauncherHome() {
        return new LauncherActivityHome();
    }
}
