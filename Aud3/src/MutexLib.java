import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexLib {

    List<String> books = new ArrayList<>();
    int capacity;
    public static Lock lock = new ReentrantLock();

    public MutexLib(int capacity){
        this.capacity = capacity;
    }

    //koga frakjame kniga
    public void returnBook(String book) throws InterruptedException {
        while(true) {
            lock.lock();
            if (books.size() < capacity) {
                books.add(book);
                lock.unlock();
                break;
            }
            lock.unlock();
        }
    }

    //koga vadime kniga
    public String borrowBook() throws InterruptedException {
        String book = "";
        while(true) {
            lock.lock();
            if (books.size() > 0) {
                book = books.remove(0);
                lock.unlock();
                break;
            }
            lock.unlock();
        }
        return book;
    }
}
