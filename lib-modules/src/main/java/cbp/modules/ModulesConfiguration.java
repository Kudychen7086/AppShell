package cbp.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cbp.modules.navigator.Navigator;
import cbp.modules.provider.ServiceProvider;

/**
 * @author cbp
 * @date 2018/4/3 10:02
 */
class ModulesConfiguration {

    // ---------------------------------------------------------------------------------------------
    // Service
    // ---------------------------------------------------------------------------------------------

    private final Map<Class<?>, ServiceProvider<?>> mServiceProviders = new HashMap<>();

    <T> void registerService(Class<? super T> service, ServiceProvider<T> provider)
            throws NullPointerException, IllegalStateException {
        if (mServiceProviders.containsKey(service)) {
            throw new IllegalStateException("Service " + service.getName() + " already registered");
        }

        mServiceProviders.put(service, provider);
    }

    @SuppressWarnings("unchecked")
    <T> T getService(Class<T> service) {
        final ServiceProvider<T> provider = (ServiceProvider<T>) mServiceProviders.get(service);

        return provider != null ? provider.get() : null;
    }

    // ---------------------------------------------------------------------------------------------
    // Navigator
    // ---------------------------------------------------------------------------------------------

    private final Map<String, List<Navigator>> mNavigators = new HashMap<>();

    void registerNavigator(String module, Navigator navigator) {
        List<Navigator> navigators = mNavigators.get(module);
        if (navigators == null) {
            mNavigators.put(module, navigators = new ArrayList<>(1));
        }
        navigators.add(navigator);
    }

    List<Navigator> getNavigators(String module) {
        return mNavigators.get(module);
    }
}