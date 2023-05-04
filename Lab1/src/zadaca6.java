import java.io.*;

public class zadaca6 {

    public static void main(String[] args) throws IOException {
        File f1 = new File("C:\\Users\\dimit\\Desktop\\TESTING\\OS 2021\\Lab1\\src\\izvor.txt");
        File f2 = new File("C:\\Users\\dimit\\Desktop\\TESTING\\OS 2021\\Lab1\\src\\destinacija.txt");
        BufferedReader in = new BufferedReader(new FileReader(f1));
        BufferedWriter out = new BufferedWriter(new FileWriter(f2));
        try{
            String line = in.readLine();
            while(line!=null){
                int counter = 0;
                for(int i = 0;i<line.length();i++){
                    if(line.charAt(i) == 'a' || line.charAt(i) == 'e' || line.charAt(i) == 'i'
                        || line.charAt(i) == 'u' || line.charAt(i) == 'o'){
                        counter++;
                    }
                }
                out.write(counter+"\n");
                line = in.readLine();
            }
        }
        finally {
            if(in != null) in.close();
            if(out != null) out.close();
        }
    }


}
