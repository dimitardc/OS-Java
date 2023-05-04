import java.io.*;

public class Aud1_2 {

    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream("pom.txt");                //citame
        OutputStream  out = new FileOutputStream("file.txt");           //zapishuvame

//        copyStream(in,out);
//        correctReading(in);
//        System.out.println(readTextFile("pom.txt"));
//        stdinRead();
//        TextFileCopy("pom.txt","file.txt");
//        readFileWithLineNumbering("pom.txt");
//        dataReadText("pom.txt");
//        readWriteRandomAccessFile("pom.txt");
        redirect();
    }

    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        try {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        }
        finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }

    public static void badReading(InputStream in) throws IOException {
        try {
            byte[] buffer = new byte[100];
            in.read(buffer);
            //doSomethingWithTheBytes(buffer)
        }
        finally {
            if (in != null) in.close();
        }
    }

    public static void correctReading(InputStream in) throws IOException {
        try {
            byte[] buffer = new byte[100];//0 1 2 3 5 4 5 6 _ _ _ _ _ _ ... _ _ _

            int readLen = 0;

            int leftToBeRead = 100;

            int offset = 0;

            while ((readLen = in.read(buffer, offset, leftToBeRead)) != -1) {
                offset += readLen;
                leftToBeRead -= readLen;
            }
            doSomethingWithTheReadData(buffer, offset);
        }
        finally {
            if (in != null) in.close();
        }
    }

    private static void doSomethingWithTheReadData(byte[] buffer, int offset) {
        System.out.println(new String(buffer));
    }


    public static String readTextFile(String path) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        StringBuilder sb = new StringBuilder();
        String pom = "";
        String line = bf.readLine();
        while (line != null){
            sb.append(line).append("\n");
            pom += line +"\n";
            line = bf.readLine();
        }
        bf.close();
        return  pom;
    }

    public static void stdinRead() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a new line: ");

        String s = bf.readLine();

        while(s  != null && s.length() != 0){
            System.out.println(s);
            System.out.println("Enter a new line: ");
            s = bf.readLine();
        }

        bf.close();
    }


    public static void TextFileCopy(String from, String to) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(from));
        BufferedWriter out = new BufferedWriter(new FileWriter(to));

        try{
            String line;
            while((line = in.readLine()) != null){
                out.write(line + "\n");

            }

        }
        finally {
            if(in != null) in.close();
            if(out != null) out.close();
        }
    }

    public static void readFileWithLineNumbering(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("file.txt")));
//        try{
//            String line;
//            int count = 1;
//            while((line = in.readLine()) != null){
//                out.println(count+": "+line);
//                count++;
//            }
//        }
        BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));
        try{
            String line = in.readLine();
            int count = 1;
            while(line != null){
                out.write(count+": "+line + "\n");
                line = in.readLine();
                count++;
            }
        }
        finally {
            if(in != null) in.close();
            if(out != null) out.close();

        }
    }

    public static void dataReadText(String path) throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        try{
            for(int i = 0; i < 5; i++)
                out.writeDouble(1.23*i);
            out.writeUTF("This is the end of the file");


        }
        finally {
            if(out != null) out.close();
        }
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));

        for(int i = 0; i < 5; i++)
            System.out.println(in.readDouble());
        System.out.println(in.readUTF());

        if(in != null) in.close();

    }
    public static void display(String path) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(path, "r");
        try{
            for(int i =0; i < 10; i++)
                System.out.println("Value "+i+": "+rf.readDouble());
            System.out.println(rf.readUTF());
        }
        finally {
            if(rf != null) rf.close();
        }
    }

    public static void readWriteRandomAccessFile(String path) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(path, "rw");
        try{
            for(int i =0; i < 10; i++)
                rf.writeDouble(i * 1.23);
            rf.writeUTF("This is the end of the file");

            display(path);

        }
        finally {
            if(rf != null) rf.close();
        }
        rf = new RandomAccessFile(path, "rw");
        try{
            rf.seek(5 * 8);
            System.out.println(rf.readDouble());
            rf.writeDouble(9.999);
            display(path);
        }
        finally {
            if(rf != null) rf.close();

        }
    }

    public static void redirect() throws IOException {
        InputStream consoleIn = System.in;
        PrintStream console = System.out;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("pom.txt"));
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("file.txt")));

        try{
            System.setIn(in);
            System.setOut(out);
            System.setErr(out);
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            String line;
            while((line = bf.readLine()) != null){
                out.println(line);
            }

        }
        finally {
            if(in != null) in.close();
            if(out != null) out.close();
            System.setIn(consoleIn);
            System.setOut(console);

        }
    }
}
