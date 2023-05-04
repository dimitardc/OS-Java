import java.util.ArrayList;
import java.util.List;

public class SyncLib {

    List<String> books = new ArrayList<>();
    int capacity;

    public SyncLib(int capacity){
        this.capacity = capacity;
    }

    //koga frakjame kniga
    public synchronized void returnBook(String book) throws InterruptedException {
        while(books.size() == capacity){
            wait();
        }
        books.add(book);
        notifyAll();
    }

    //koga vadime kniga
    public synchronized String borrowBook() throws InterruptedException {
        String book = "";
        while(books.size()==0){
            wait();
        }
        book = books.remove(0);
        notifyAll();
        return book;
    }
}
