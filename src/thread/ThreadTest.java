package thread;

public class ThreadTest {
    volatile static Thread father;

    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("TTTTTTTTTTTTTTTT");
        father = new Thread(group, new Runnable() {
            Thread[] threads = new Thread[100];

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    System.out.println("new thread: " + i);
                    f1(10000);
                    /*threads[i].start();
                    System.out.println(threads[i].getName() + " started");*/
                }
                for (int i = 10; i < 100; i++) {

                    try {
                        synchronized (father) {
                            father.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /*threads[i].start();
                    System.out.println(threads[i].getName() + " started");*/
                }

                /*for (int i = 0; i < 100 && Thread.currentThread().equals(father); i++) {
                    threads[i].start();
                    System.out.println(threads[i].getName() + " started");
                }*/
            }
        });
        father.start();
        f1(000050);
        father.interrupt();
        /*try {
            System.out.println("father thread start to sleep....");
            *//*synchronized (father) {
                father.wait();
            }*//*
        } catch (InterruptedException e) {

        }*/
        System.out.println("thread father interrupt,main exit");
    }


    public static void f(int n) {
        String tmp = "";
        for (int i = 0; i < n; i++) {
            tmp += i;
            System.out.println(Thread.currentThread().getName() + "\t\t" + i);
        }
    }

    public static void f1(int n) {
        String tmp = "";
        for (int i = 0; i < n; i++) {
            tmp += i;
            //System.out.println(Thread.currentThread().getName() + "\t\t" + i);
        }
    }
}
