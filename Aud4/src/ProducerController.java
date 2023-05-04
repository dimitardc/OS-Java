import javax.crypto.SealedObject;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class ProducerController {

    public static int NUM_RUN = 50;
    static Semaphore accessBuffer;
    static Semaphore lock;              //pristap to numChecks
    static Semaphore canCheck;          //10 checks max

    public static void init() {
        accessBuffer = new Semaphore(1);
        lock = new Semaphore(1);
        canCheck = new Semaphore(10);               //10 controllers mozat da pravat proverka

    }

    public class Buffer {
        public int numChecks = 0;

        public void Produce() {
            System.out.println("is producing...");
        }

        public void Check() {
            System.out.println("is checking...");
        }
    }

    public static class Producer extends Thread {
        private final Buffer buffer;

        public Producer(Buffer buffer) {
            this.buffer = buffer;
        }

        public void execute() throws InterruptedException {
            accessBuffer.acquire();
            this.buffer.Produce();
            accessBuffer.release();

        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Controller extends Thread {
        private final Buffer buffer;

        public Controller(Buffer buffer) {
            this.buffer = buffer;
        }

        public void execute() throws InterruptedException {
            lock.acquire();
            if (this.buffer.numChecks == 0) {
                accessBuffer.acquire();
            }
            this.buffer.numChecks++;
            lock.release();

            canCheck.acquire();     //za proverka dali imame pomalku od 10 checks
            this.buffer.Check();
            lock.acquire();
            this.buffer.numChecks--;
            canCheck.release();
            if(this.buffer.numChecks == 0){
                accessBuffer.release();
            }
            lock.release();
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
