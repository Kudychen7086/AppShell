package cbp.modules.provider;

/**
 * @author cbp
 * @date 2018/4/3 10:02
 */
public interface ServiceProvider<T> {
    T get();
}