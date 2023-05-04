import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphorLib {

    List<String> books = new ArrayList<>();
    int capacity;
    Semaphore coordinator = new Semaphore(1);
    Semaphore returnBookSemaphore = new Semaphore(10);
    Semaphore borrowBookSemaphore = new Semaphore(10);
    public SemaphorLib(int capacity) {
        this.capacity = capacity;
    }

    //koga vrakjame kniga
    public void returnBook(String book) throws InterruptedException {
        returnBookSemaphore.acquire();

        coordinator.acquire();
        while (books.size() == capacity) {
            coordinator.release();
            Thread.sleep(1000);
            coordinator.acquire();
        }
        books.add(book);
        coordinator.release();
        borrowBookSemaphore.release();
    }

    //koga vadime kniga
    public String borrowBook() throws InterruptedException {
        borrowBookSemaphore.acquire();

        coordinator.acquire();
        String book = "";
        while (books.size() == 0) {
            coordinator.release();
            Thread.sleep(1000);
            coordinator.acquire();
        }
        book = books.remove(0);
        coordinator.release();
        returnBookSemaphore.release();
        return book;
    }
}
