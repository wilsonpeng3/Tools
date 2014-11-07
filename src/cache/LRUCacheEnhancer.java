package cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 1、支持多线程环境
 * 2、完成了从缓存进行判断的工作
 */
public abstract class LRUCacheEnhancer {

    /**
     * 用于链表
     */
    public final class Node {
        private Object data;
        private Node nextNode;

        public Node(Object data, Node nextNode) {
            this.data = data;
            this.nextNode = nextNode;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }
    }

    private int originalCapacity = 5;
    private int capacity = originalCapacity;
    private Node head = new Node(null, null);  //头节点，它的下一个节点就是最近最久未被使用的
    private Node tail = head;    //初始时，末尾节点也是头节点
    private ConcurrentHashMap<Object, Object> cache = new ConcurrentHashMap<Object, Object>();  //采用LRU算法对这个map里的实例进行维护

    /**
     * @param capacity 如果小于等于1，默认修改为5
     */
    public LRUCacheEnhancer(int capacity) {
        this.originalCapacity = capacity <= 1 ? 5 : capacity;
        this.capacity = originalCapacity;
    }

    public synchronized Object get(Object key) {
        if (cache.containsKey(key)) {  //找到了
            Node preNode = findNode(head, key);
            Node currNode = preNode.getNextNode();
            Node nextNode = currNode.getNextNode();

            if (nextNode != null) {//说明currNode不是尾节点
                tail.setNextNode(currNode);
                preNode.setNextNode(nextNode);
                currNode.setNextNode(null);
                tail = currNode;
            }
            return cache.get(key);
        } else {   //如果没有找到。
            Node newNode = new Node(key, null);
            tail.setNextNode(newNode);
            tail = newNode;

            if (capacity > 0) {  //还有空间
                capacity--;
            } else {  //空间已经使用完了
                Node firstNode = head.getNextNode();
                head.setNextNode(firstNode.getNextNode());
                firstNode.setNextNode(null);
                cache.remove(firstNode.getData());
            }

            Object data = getData(key);
            cache.put(key, data);
            return data;
        }
    }

    public synchronized void clear() {
        cache.clear();
        head = new Node(null, null);
        tail = head;
        capacity = originalCapacity;
    }

    public abstract Object getData(Object key);

    private void deleteNode(Node head, Object key) {
        Node preNode = findNode(head, key);
        Node currNode = preNode.getNextNode();
        preNode.setNextNode(currNode.getNextNode());
        currNode.setNextNode(null);
        if (currNode == tail) { //如果要删除的是尾节点
            tail = preNode;
        }
    }

    private Node findNode(Node head, Object key) {
        Node curr = head;
        while (!key.equals(curr.getNextNode().getData())) {
            curr = curr.getNextNode();
        }
        return curr;
    }


    @Override
    public String toString() {
        Node firstNode = this.head.getNextNode();
        StringBuffer keys = new StringBuffer();
        keys.append("keys=");
        StringBuffer datas = new StringBuffer();
        datas.append("datas=");
        while (firstNode != null) {
            keys.append(firstNode.getData() + "   ");
            datas.append(cache.get(firstNode.getData()) + "   ");
            firstNode = firstNode.getNextNode();
        }
        return keys.toString() + datas.toString();
    }
}
