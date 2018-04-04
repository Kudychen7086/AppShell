package cbp.account.impl;

import cbp.account.LauncherActivityAccount;
import cbp.account.api.AccountApiService;
import cbp.launcher.ILauncherElement;

/**
 * @author cbp
 * @date 2018/4/3 16:18
 */
public class AccountApiServiceImpl implements AccountApiService {
    @Override
    public ILauncherElement getLauncherAccount() {
        return new LauncherActivityAccount();
    }
}
