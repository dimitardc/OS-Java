import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Execution into the main class");
        Incrementor incrementor1 = new Incrementor();
        Incrementor incrementor2 = new Incrementor();

        ThreadClass t1 = new ThreadClass("T1", incrementor1);
        ThreadClass t2 = new ThreadClass("T2", incrementor2);

        //t1.run();                    ne se kreira nova nishka

        t1.start();                 // se kreira nova nishka
        t2.start();

        t1.join();
        t2.join();

//        if(t1.isAlive() && t2.isAlive()){
//            System.out.println("Thread is still alive.");
//            t1.interrupt();
//            t2.interrupt();
//        }
        System.out.println(incrementor1.getCount());
        System.out.println(incrementor2.getCount());

    }
}

class ThreadClass extends Thread {
    private String name;
    private Incrementor incrementor;

    public ThreadClass(String name, Incrementor incrementor) {
        this.name = name;
        this.incrementor = incrementor;
    }

    public void run() {
//        System.out.println("Execution into the thread class");
        for (int i = 0; i < 20; i++)
//            System.out.println(name +": "+i);
        {
            incrementor.safeMutexIncrement();

        }
    }
}

class Incrementor {
    private static int count = 0;
    private static Lock lock = new ReentrantLock();
    private static Semaphore semaphore = new Semaphore(2);

    public static void unsafeIncrement() throws InterruptedException {
        count++;

        //read count -> T1(0), T2(0), T1(1), T2(1)
        //increment count +1 -> T2(1), T1(1)
        //write count -> T1: WRITE -> 1, T2: WRITE -> 1

//       Thread.sleep(1);
    }

    public static void safeMutexIncrement() {
        lock.lock();
        count++;
        lock.unlock();
    }

    synchronized static void safeSynchronizedIncrement() {
        count++;
    }

    static void safeSynchronizedClassIncrement() {

        synchronized (Incrementor.class) {
            count++;
        }

    }

    static void safeSemaphoreIncrement() throws InterruptedException {
        semaphore.acquire();
        count++;
        semaphore.release();
    }


    synchronized public int getCount() {
        return count;
    }
}
