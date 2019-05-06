package DataLayer.DBConnectionPool;

public interface ResourcePool<T> {

    T get();


    void release(T t);


    void terminate();

}
