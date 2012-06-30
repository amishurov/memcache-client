import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedClient {
    static String address;
    static String key;
    static String method;
    static Object value;

    private static void loadProperties() {
        String host = System.getProperty("host", "127.0.0.1");
        String port = System.getProperty("port", "11211");

        key = System.getProperty("key", null);
        address = host + ":" + port;
        method = System.getProperty("method", "get");
    }

    public static void main(String[] args) {
        loadProperties();

        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(new String[]{address});
        pool.initialize();

        MemCachedClient client = new MemCachedClient();
        client.setSanitizeKeys(false);

        if (method.equals("get")) {
            value = client.get(key);
        } else if (method.equals("flush_all")) {
            value = client.flushAll();
        } else if (method.equals("stats")) {
            value = client.stats();
        } else {
            System.out.println("Method is unknown. Implement it if you want.");
        }

        if (value != null) {
            System.out.println(value.toString());
        } else {
            System.out.println("Null value!");
        }
    }
}