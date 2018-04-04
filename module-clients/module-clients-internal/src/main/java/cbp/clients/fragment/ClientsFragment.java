package cbp.clients.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cbp.clients.R;

/**
 * @author cbp
 * @Description 示例页面（客户）
 * @date 2018/4/4 14:15
 */
public class ClientsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clients_layout, container, false);
    }
}
