package cbp.launcher;

import android.support.v4.app.Fragment;

import cbp.launcher.fragment.HomeFragment;

/**
 * @author cbp
 * @date 2018/4/3 11:10
 */
public class LauncherActivityHome extends BaseLauncherElement {
    private Fragment mHomeFragment;

    @Override
    public Fragment getFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        return mHomeFragment;
    }
}
