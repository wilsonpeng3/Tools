/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package rocky.cache;

import rocky.comm.Node;

import java.util.concurrent.ConcurrentHashMap;

public abstract class LRUCache {

    private int originalCapacity = 5;
    private int capacity = originalCapacity;
    private Node head = new Node(null, null);  //头节点，它的下一个节点就是最近最久未被使用的
    private Node tail = head;    //初始时，末尾节点也是头节点
    private ConcurrentHashMap<Object, Object> container = new ConcurrentHashMap<>();  //采用LRU算法对这个map里的实例进行维护

    /**
     * @param capacity 如果小于等于1，默认修改为5
     */
    public LRUCache(int capacity) {
        this.originalCapacity = capacity <= 1 ? 5 : capacity;
        this.capacity = originalCapacity;
    }

    public synchronized Object get(Object key) {
        if (container.containsKey(key)) {  //找到了
            Node preNode = findNode(head, key);
            Node currNode = preNode.getNextNode();
            Node nextNode = currNode.getNextNode();

            if (nextNode != null) {//说明currNode不是尾节点
                tail.setNextNode(currNode);
                preNode.setNextNode(nextNode);
                currNode.setNextNode(null);
                tail = currNode;
            }
            return container.get(key);
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
                container.remove(firstNode.getData());
            }

            Object newDriver = getData(key);
            container.put(key, newDriver);
            return newDriver;
        }
    }

    public synchronized void clear() {
        container.clear();
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
        StringBuffer sb = new StringBuffer();
        while (firstNode != null) {
            sb.append(firstNode.getData() + "\t");
            firstNode = firstNode.getNextNode();
        }
        sb.append("\n");
        return sb.toString();
    }
}
