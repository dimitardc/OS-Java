import java.util.ArrayList;
import java.util.List;

public class Library {

    public static void main(String[] args) throws InterruptedException {
        List<Member> members = new ArrayList<>();
        SemaphorLib library = new SemaphorLib(10);
        for(int i = 0 ; i<10 ; i++){
            Member member = new Member("M"+i, library);
            members.add(member);
        }
        for(Member m : members){
            m.start();
        }
        for(Member m : members){
            m.join(2000);
        }
        System.out.println("nice!!");
    }

}

class Member extends Thread{
    private String name;
    private SemaphorLib library;

    public Member(String name, SemaphorLib library){
        this.name = name;
        this.library = library;
    }

    @Override
    public void run() {
        for(int i = 0;i<3;i++){
            System.out.println("Member"+i+" returns book");
            try {
                library.returnBook("Book"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0;i<2;i++){
            System.out.println("Member"+i+" borrows book");
            try {
                library.borrowBook();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
