package cbp.clients;

import android.support.v4.app.Fragment;

import cbp.clients.fragment.ClientsFragment;
import cbp.launcher.BaseLauncherElement;

/**
 * @author cbp
 * @date 2018/4/4 14:15
 */
public class LauncherActivityClients extends BaseLauncherElement {
    @Override
    public Fragment getFragment() {
        return new ClientsFragment();
    }
}
