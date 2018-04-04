package cbp.launcher.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cbp.account.api.AccountApiService;
import cbp.clients.api.ClientsApiService;
import cbp.launcher.ILauncherContainer;
import cbp.launcher.ILauncherElement;
import cbp.launcher.R;
import cbp.launcher.api.LauncherApiService;
import cbp.message.api.MessageApiService;
import cbp.modules.ModuleManager;

public class MainActivity extends AppCompatActivity implements ILauncherContainer {

    private static final String TAG = "MainActivity";
    private List<ILauncherElement> launcherElements = new ArrayList<>();

    private int[] buttonIds = new int[]{R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4};
    private Fragment[] fragments = new Fragment[buttonIds.length];
    public RelativeLayout[] buttons = new RelativeLayout[buttonIds.length];
    private int lastIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDexActivity.notifyMainProcessLaunched(this);
        setContentView(R.layout.activity_main);

        initLauncherElements();
        initViews();
        int index = 0;
        //----------------------------------------------------------
        //TODO:index默认为0，项目根据实际业务可能会对index进行修改
        //----------------------------------------------------------
        buttons[index].performClick();
    }

    private void initLauncherElements() {
        launcherElements.add(ModuleManager.getService(LauncherApiService.class).getLauncherHome());
        launcherElements.add(ModuleManager.getService(ClientsApiService.class).getLauncherClients());
        launcherElements.add(ModuleManager.getService(MessageApiService.class).getLauncherMessage());
        launcherElements.add(ModuleManager.getService(AccountApiService.class).getLauncherAccount());
        for (ILauncherElement element : launcherElements) {
            element.setContainer(this);
        }
    }

    private void initViews() {
        for (int i = 0; i < buttons.length; i++) {
            final int j = i;
            buttons[i] = findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lastIndex == j) {
                        //TODO:根据实际业务可能会做一些自己的操作
                        return;
                    }
                    for (RelativeLayout button : buttons) {
                        button.setSelected(false);
                    }
                    buttons[j].setSelected(true);
                    switchFragment(j);
                }
            });
        }
    }

    private void switchFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (lastIndex != -1) {
            if (getFragment(lastIndex) != null) {
                transaction.hide(getFragment(lastIndex));
            }
        }
        if (!getFragment(index).isAdded()) { // 先判断是否被add过
            transaction.add(R.id.main_container, getFragment(index)).commitAllowingStateLoss(); //add下一个到Activity中
        } else {
            transaction.show(getFragment(index)).commitAllowingStateLoss(); //显示下一个
        }
        lastIndex = index;
        getSupportFragmentManager().executePendingTransactions();
    }

    private Fragment getFragment(int i) {
        if (fragments[i] == null) {
            fragments[i] = launcherElements.get(i).getFragment();
        }
        return fragments[i];
    }

    @Override
    public void showElement(ILauncherElement element) {
        if (element == null) return;
        int index = launcherElements.indexOf(element);
        if (index >= 0 && index < buttons.length) {
            buttons[index].performClick();//点击下面的BOTTOM，切换Fragment
        }
    }
}
