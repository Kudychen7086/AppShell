package cbp.launcher.api;

import cbp.launcher.ILauncherElement;

/**
 * @author cbp
 * @Description 项目中根据实际需求扩展，此处为了示例只定义了获取主容器中的一个元素
 * @date 2018/4/3 11:10
 */
public interface LauncherApiService {
    ILauncherElement getLauncherHome();
}
