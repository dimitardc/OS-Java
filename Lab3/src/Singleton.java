public class Singleton {

    private static volatile Singleton singleton;

    private Singleton() {

    }

    public static Singleton getInstance() {
        // TODO: 3/29/20 Synchronize this
        singleton = new Singleton();

        return singleton;
    }

    public static void main(String[] args) {
        // TODO: 3/29/20 Simulate the scenario when multiple threads call the method getInstance
    }

}