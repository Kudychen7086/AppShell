package cbp.launcher;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * @author cbp
 * @Description App中的装载元素，管理Activity及Fragment部分生命周期，提供页面所需的部分功能，可根据实际需求扩展
 * @date 2018/4/3 11:10
 */
public interface ILauncherElement {
    void onActivityCreated();

    void onActivityDestroy();

    //返回键处理
    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    //切换fragment时会调用,为兼容之前行情的逻辑
    void setActive(boolean isActive);

    //首页customUrl处理，返回可以处理的fragment位置
    boolean handleHomeUrl(String url);

    Fragment getFragment();

    void setContainer(ILauncherContainer container);

    Uri parseIntentOnCreate(Intent intent);

    Uri parseIntentOnResume(Intent intent);
}
