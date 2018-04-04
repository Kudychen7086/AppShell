package cbp.modules.navigator;

import android.content.Context;
import android.os.Bundle;

/**
 * @author cbp
 * @date 2018/4/3 10:02
 */
public interface Navigator {
    String DEFAULT_TARGET = "";
    Bundle DEFAULT_DATA = Bundle.EMPTY;

    /**
     * 处理页面跳转.
     *
     * @param target 页面. 未指定时, 值为 {@link #DEFAULT_TARGET}.
     * @param data   页面参数. 未指定时, 值为 {@link #DEFAULT_DATA}.
     * @return 返回是否跳转成功.
     */
    boolean onStart(Context context, String target, Bundle data);
}
