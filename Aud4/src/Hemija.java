import java.util.concurrent.Semaphore;

public class Hemija {

    public static int NUM_RUN = 50;

    static Semaphore si;
    static Semaphore o;
    static Semaphore siHere;
    static Semaphore oHere;
    static Semaphore ready;

    public static void Init(){
        si = new Semaphore(1);
        o = new Semaphore(2);
        siHere = new Semaphore(0);
        oHere = new Semaphore(0);
        ready = new Semaphore(0);
    }

    public static class SI extends Thread{
        public void bond(){
            System.out.println("SI is bonding...");
        }

        public void execute() throws InterruptedException{
            si.acquire();
            siHere.release(2);
            oHere.acquire(2);
            ready.release(2);
            bond();
            si.release();
        }

        @Override
        public void run() {
            for(int i = 0;i<NUM_RUN;i++){
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class O extends Thread{
        public void bond(){
            System.out.println("O is bonding...");
        }

        public void execute() throws InterruptedException{
            o.acquire();
            siHere.acquire();
            oHere.release();
            ready.acquire();
            bond();
            o.release();
        }

        @Override
        public void run() {
            for(int i = 0;i<NUM_RUN;i++){
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
