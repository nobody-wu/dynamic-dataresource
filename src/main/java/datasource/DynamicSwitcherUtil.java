package datasource;

public class DynamicSwitcherUtil {

    private static ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();

    public static String getDataSourceKey()
    {
        return dataSourceKey.get();
    }

    public static void setDataSourceKey(String key)
    {
        dataSourceKey.set(key);
    }

    public static void removeDataSourceKey()
    {
        dataSourceKey.remove();
    }
}
