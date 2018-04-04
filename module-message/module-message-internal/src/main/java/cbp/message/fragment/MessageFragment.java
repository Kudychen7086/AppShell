package cbp.message.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cbp.message.R;

/**
 * @author cbp
 * @Description 示例页面（消息）
 * @date 2018/4/4 13:37
 */
public class MessageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_layout, container, false);
    }
}
