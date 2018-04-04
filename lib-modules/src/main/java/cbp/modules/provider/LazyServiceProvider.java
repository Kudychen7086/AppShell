package cbp.modules.provider;

/**
 * @author cbp
 * @date 2018/4/3 10:02
 */
public abstract class LazyServiceProvider<T> implements ServiceProvider<T> {
    private T mInstance;

    public static <T> LazyServiceProvider<T> wrap(ServiceProvider<T> provider)
            throws NullPointerException {
        return new Wrapper<>(provider);
    }

    @Override
    public T get() throws NullPointerException {
        T instance = mInstance;
        if (instance == null) {
            synchronized (this) {
                instance = mInstance;
                if (instance == null) {
                    instance = create();
                    if (instance == null) {
                        throw new NullPointerException("Instance cannot be null");
                    }
                    mInstance = instance;
                }
            }
        }
        return instance;
    }

    protected abstract T create();

    private static class Wrapper<T> extends LazyServiceProvider<T> {
        private final ServiceProvider<T> mProvider;

        Wrapper(ServiceProvider<T> provider) throws NullPointerException {
            if (provider == null) {
                throw new NullPointerException("Provider cannot be null");
            }
            mProvider = provider;
        }

        @Override
        protected T create() {
            return mProvider.get();
        }
    }
}