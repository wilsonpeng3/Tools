package thread;

public class BaseClassTest {
    public Object object = null;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        /*SubClassA a = new SubClassA();
        SubClassA a1 = new SubClassA();
        SubClassB b = new SubClassB();
        Field a_aa = a.getClass().getSuperclass().getDeclaredField("aa");
        a_aa.setAccessible(true);
        Field b_aa = a.getClass().getSuperclass().getDeclaredField("aa");
        b_aa.setAccessible(true);
        System.out.println("before change a_aa,a_aa=" + a_aa.get(a));  //5
        System.out.println("before change a_aa,b_aa=" + b_aa.get(b));//5
        a_aa.set(a, 100);
        System.out.println("after change a_aa,a_aa=" + a_aa.get(a));//100
        System.out.println("after change a_aa,b_aa=" + b_aa.get(b));//5
        BaseClassTest.class.notifyAll();*/
        BaseClassTest b = new BaseClassTest();
        synchronized (b.object) {
            System.out.println("asdfasd");
        }
    }
}
