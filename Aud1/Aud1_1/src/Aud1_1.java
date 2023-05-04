import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Aud1_1 {
//    private final static  String usage = "Usage: MakeDirectories path1 ..." + "\nUsage: MakeDirectories -d path1 ..." + "\nUsage: MakeDirectories -r path1 path2";

//    private static void usage(){
//        System.out.println(usage);
//        System.exit(1);
//    }

    private static void fileData(File f){
        System.out.println("Absolute path: " + f.getAbsolutePath() +"\n Can read: " + f.canRead() + "\n Can write: " + f.canWrite() +
                "\n getName: " + f.getName() + "\n length: " + f.length() + "\n lastModified: " + f.lastModified());

        if(f.isFile())
            System.out.println("it's a file");
        else if(f.isDirectory())
            System.out.println("it's a directory");
    }


    public static void main(String[] args) throws IOException {
        File file = new File(".");

        if(args.length < 1) {
            System.out.println("Error, write arguments!");
            return;
        }
        if(args[0].equals("-r")) {
            if (args.length != 3) {
                System.out.println("Error, write arguments!");
                return;
            }
            File old = new File(args[1]);
            File rname = new File(args[2]);
            old.renameTo(rname);
            fileData(old);
            fileData(rname);
            return;
        }

        int count = 0;
        boolean del  = false;
        if(args[0].equals("-d")){
            count++;
            del = true;
        }

        for(; count < args.length; count++){
            File f = new File(args[count]);
            if(f.exists()){
                System.out.println(f+" exists");
                if(del){
                    System.out.println("deleting..."+f);
                    f.delete();
                }
            }
            else{
                if(!del){
                    f.mkdirs();
                    System.out.println("Created " + f);
                }
            }
            fileData(f);

        }


        //        String [] list;
//        if(args.length == 0){
//            list = file.list();
//        }
//        else {
//            list = file.list(new DirFilter(args[0]));
//        }
//
//        for( int i  = 0; i < list.length; i++ )
//            System.out.println(list[i]);
        

//        listFile(file.getAbsolutePath(), ".txt\t");
    }

    private static class DirFilter implements FilenameFilter {
        String arg;
        public DirFilter(String arg) {
            this.arg = arg;
        }

        @Override
        public boolean accept(File dir, String name) {
            String f = new File(name).getName();
            return f.indexOf(arg) != -1;
        }
    }


    public static void listFile(String absolutePath, String prefix){
        File file = new File(absolutePath);

        if(file.exists()){
            File subfiles[] = file.listFiles();
            for(File f: subfiles){
                System.out.println(prefix + getPermission(f) +"\t" + f.getName() );
                if(f.isDirectory())
                    listFile(f.getAbsolutePath(),prefix + "\t");
            }
        }
    }

    private static String getPermission(File f) {

        return String.format("%s%s%s",f.canRead() ? "r" : "-", f.canWrite() ? "w" : "-", f.canExecute() ? "x" : "-");
    }
}
