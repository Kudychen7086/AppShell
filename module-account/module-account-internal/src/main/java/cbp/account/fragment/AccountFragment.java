package cbp.account.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cbp.account.R;

/**
 * @author cbp
 * @Description 示例页面（我的）
 * @date 2018/4/3 16:18
 */
public class AccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_layout, container, false);
    }
}
