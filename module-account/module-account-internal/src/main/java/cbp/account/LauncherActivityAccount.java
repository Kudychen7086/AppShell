package cbp.account;

import android.support.v4.app.Fragment;

import cbp.account.fragment.AccountFragment;
import cbp.launcher.BaseLauncherElement;

/**
 * @author cbp
 * @date 2018/4/3 16:35
 */
public class LauncherActivityAccount extends BaseLauncherElement {
    @Override
    public Fragment getFragment() {
        return new AccountFragment();
    }
}
