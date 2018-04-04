package cbp.message;

import android.support.v4.app.Fragment;

import cbp.launcher.BaseLauncherElement;
import cbp.message.fragment.MessageFragment;

/**
 * @author cbp
 * @date 2018/4/4 13:37
 */
public class LauncherActivityMessage extends BaseLauncherElement {
    @Override
    public Fragment getFragment() {
        return new MessageFragment();
    }
}
