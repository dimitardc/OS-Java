import java.util.concurrent.Semaphore;

public class UpisiFinki {


    static Semaphore slobodnoUpisnoMesto;
    static Semaphore enter;
    static Semaphore here;
    static Semaphore done;


    public static void init() {
        slobodnoUpisnoMesto = new Semaphore(4);
        enter = new Semaphore(0);
        here = new Semaphore(0);
        done = new Semaphore(0);
    }

    public static class Clen extends Thread{

        public void init() {
        }

        public void execute() throws InterruptedException {
            slobodnoUpisnoMesto.acquire();
            int i = 10;

            while(i>0) {
                //TODO: zapishuvaj novi studenti
                enter.release();
                here.acquire();
                zapisi();
                done.release();
                i--;
            }
            slobodnoUpisnoMesto.release();
        }

        public void zapisi() {
            System.out.println("Zapisuvam student...");
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Student extends Thread{

        public void execute() throws InterruptedException {
            enter.acquire();
            ostaviDokumenti();
            here.release();
            done.acquire();
        }

        public void ostaviDokumenti() {
            System.out.println("Ostavam dokumenti...");
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
