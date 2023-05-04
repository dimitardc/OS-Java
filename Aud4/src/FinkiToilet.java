import java.util.concurrent.Semaphore;

public class FinkiToilet {

    public static class Toilet {

        public void vlezi() {
            System.out.println("Vleguva...");
        }

        public void izlezi() {
            System.out.println("Izleguva...");
        }
    }

    static Semaphore toiletSem;
    static Semaphore mLock;
    static Semaphore wLock;

    static int numM;
    static int numW;

    public static void init() {
        toiletSem = new Semaphore(1);
        mLock = new Semaphore(1);
        wLock = new Semaphore(1);
        numM = 0;
        numW = 0;
    }

    public static class Man extends Thread{

        private Toilet toilet;

        public Man(Toilet toilet) {
            this.toilet = toilet;
        }

        public void enter() throws InterruptedException {
            mLock.acquire();
            if (numM==0) {
                toiletSem.acquire();
            }
            numM++;
            this.toilet.vlezi();
            mLock.release();


        }

        public void exit() throws InterruptedException {
            mLock.acquire();
            this.toilet.izlezi();
            numM--;
            if (numM==0) {
                toiletSem.release();
            }
            mLock.release();
        }

        @Override
        public void run() {
            super.run();
        }
    }

    public static class Woman extends Thread{

        private Toilet toilet;

        public Woman(Toilet toilet) {
            this.toilet = toilet;
        }

        public void enter() throws InterruptedException {
            wLock.acquire();
            if (numW ==0) {
                toiletSem.acquire();
            }
            numW++;
            this.toilet.vlezi();
            wLock.release();
        }

        public void exit() throws InterruptedException {
            wLock.acquire();
            this.toilet.izlezi();
            numW--;
            if (numW ==0) {
                toiletSem.release();
            }
            wLock.release();
        }

        @Override
        public void run() {
            super.run();
        }
    }
}
