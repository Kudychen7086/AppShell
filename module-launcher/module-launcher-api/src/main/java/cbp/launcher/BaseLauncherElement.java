package cbp.launcher;

import android.content.Intent;
import android.net.Uri;

/**
 * @author cbp
 * @Description 只针对ILauncherElement做了默认实现
 * @date 2018/4/3 11:10
 */
public abstract class BaseLauncherElement implements ILauncherElement {
    public ILauncherContainer mContainer;

    @Override
    public void onActivityCreated() {
    }

    @Override
    public void onActivityDestroy() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void setActive(boolean isActive) {

    }

    @Override
    public boolean handleHomeUrl(String url) {
        return false;
    }

    public ILauncherContainer getContainer() {
        return mContainer;
    }

    public void setContainer(ILauncherContainer container) {
        this.mContainer = container;
    }

    @Override
    public Uri parseIntentOnCreate(Intent intent) {
        return null;
    }

    @Override
    public Uri parseIntentOnResume(Intent intent) {
        return null;
    }
}
