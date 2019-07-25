
/**
 *
 * @author hakimhassani97
 */
public class AppClose {
    public static void main(String[] args){
        Close c=new Close();
        System.out.println("***************************START***************************");
        //String file="example.dat";
        //String file="T10I4D100K.dat";
        //String file="T40I10D100K.dat";
        //String file="4k6k.dat";
        String file="chess.dat";
        double minSup=0.719;
        c.loadFile(file);
        c.close(minSup);
    }
}
