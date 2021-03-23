import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {

    @Test
    public void test1(){
        // 创建数据库连接池对象
        JedisPool jedisPool = new JedisPool("192.168.1.7", 6379);
        System.out.println(jedisPool);
        // 获取Jedis对象
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis);
        // 根据key取值
        String value = jedis.get("name");
        System.out.println(value);
    }
}
