public class zadaca1 {

//    public static class Thread1 extends Thread{
//        @Override
//        public void run() {
//            System.out.println("A");
//            System.out.println("B");
//        }
//    }
//
//    public static class Thread2 extends Thread{
//        @Override
//        public void run() {
//            System.out.println("1");
//            System.out.println("2");
//        }
//    }

    public static class ThreadAB implements Runnable{
        String[] str;

        public ThreadAB(String... str){
            this.str = str;
        }

        @Override
        public void run() {
            for(String s : str){
                System.out.println(s);
            }
        }
    }

    public static void main(String[] args) {
        //new Thread1().start();
        //new Thread2().start();
        Thread threadAlphabet = new Thread(new ThreadAB("A", "B", "C", "D", "E", "F", "G", "H"));
        Thread threadNumbers = new Thread(new ThreadAB("1", "2", "3", "4", "5", "6", "7"));
        threadAlphabet.start();
        threadNumbers.start();
    }
}
