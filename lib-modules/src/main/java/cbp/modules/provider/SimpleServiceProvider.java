package cbp.modules.provider;

/**
 * @author cbp
 * @date 2018/4/3 10:02
 */
public class SimpleServiceProvider<T> implements ServiceProvider<T> {
    private final T mInstance;

    public SimpleServiceProvider(T instance) throws NullPointerException {
        if (instance == null) {
            throw new NullPointerException("Instance cannot be null");
        }
        mInstance = instance;
    }

    @Override
    public T get() {
        return mInstance;
    }
}
