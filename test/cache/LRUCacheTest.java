package cache;

import org.junit.Test;

public class LRUCacheTest {


    @Test
    public void test1() {

        LRUCacheEnhancer lruCacheEnhancer = new LRUCacheEnhancer(5) {
            @Override
            public Object getData(Object key) {
                return (Integer) key + 100;
            }
        };

        int[] dbs = new int[]{1, 3, 8, 2, 3, 7, 5, 4, 3, 1, 3, 5, 3, 4, 7, 2, 5, 3, 4, 1, 2, 3, 1, 9, 7, 3};
        for (int i = 0; i < dbs.length; i++) {
            Object data = lruCacheEnhancer.get(dbs[i]);
            System.out.println("key=" + dbs[i] + "   " + lruCacheEnhancer.toString());
        }
    }

    @Test
    public void test2() {
        LRUCache lruCache = new LRUCache(5);
        int[] dbs = new int[]{1, 3, 8, 2, 3, 7, 5, 4, 3, 1, 3, 5, 3, 4, 7, 2, 5, 3, 4, 1, 2, 3, 1, 9, 7, 3};
        for (int i = 0; i < dbs.length; i++) {
            Object data = lruCache.get(dbs[i]);
            if (data == null) {
                data = dbs[i] + 100;
                lruCache.add(dbs[i], data);
            }
            System.out.println("key=" + dbs[i] + "   " + lruCache.toString());
        }
    }
}