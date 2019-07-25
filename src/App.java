
import java.util.ArrayList;

/**
 *
 * @author hakimhassani97
 */
public class App {
    public static void main(String[] args){
        Close c=new Close();
        System.out.println("***************************START***************************");
        //String file="example.dat";
        //String file="T10I4D100K.dat";
        //String file="T40I10D100K.dat";
        //String file="4k6k.dat";
        String file="chess.dat";
        double minSup=0.719;
        long startTimestamp = System.currentTimeMillis();
        c.loadFile(file);
        long endTimestamp = System.currentTimeMillis();
        System.out.println(c.nbLines(file)+" lignes traités en "+(endTimestamp-startTimestamp)+" ms");
        //c.close(50);
        System.out.println("****************************END***************************");
        //generateur 1
        //double maxSup=0;
        startTimestamp = System.currentTimeMillis();
        c.generateurs=new ArrayList<>();
        c.fermetures=new ArrayList<>();
        for(int i=1;i<=c.nbAttributes;i++){
            ArrayList<Integer> l=new ArrayList<>();
            l.add(i);
            //l.add(i+1);l.add(i+2);
            //double support=c.support(l);
            //if(maxSup<support) maxSup=support;
            //System.out.println("supp "+l+"="+c.support(l));
            //if(c.support(l)>0) System.out.println("ferm "+l+"="+c.getFermeture(l));
            //if(c.support(l)>0.019) System.out.println("ferm "+l+"="+c.getFermetureThreaded(l));
            //if(c.support(l)>=minSup) c.getFermetureThreaded(l);
            if(c.support(l)>=minSup) System.out.println("ferm "+l+"="+c.getFermetureThreaded(l));
            //c.getFermeture(l);
        }
        System.out.println("nb els :"+c.fermetures.size());
        endTimestamp = System.currentTimeMillis();
        System.out.println("1-GENERATEURS traités en "+(endTimestamp-startTimestamp)+" ms");
        //combinaisons possibles des 2 generateurs
//        String[] s=c.CombinaisonsBin(27,2);
//        for (String item : s) {
//            System.out.println(item);
//        }
        ArrayList<Integer> combDe=c.combinaisonsDe(c.generateurs);
        ArrayList<String> s=c.CombinaisonsBinThreaded(combDe.size(),2);
        ArrayList<ArrayList<Integer>> newGenerateurs=new ArrayList<>();
        for (String item : s) {
            ArrayList<Integer> l=new ArrayList<>();
            for(int i=0;i<item.length();i++){
                if(item.charAt(i)=='1') l.add(combDe.get(i));
            }
            if(c.support(l)>=minSup && !c.inclusDansFermeture(l, c.fermetures)) newGenerateurs.add(l);
        }
        for(ArrayList<Integer> i:newGenerateurs){
            System.out.println(i);
        }
        //generateur 2
        System.out.println("2-GENERATEURS");
        startTimestamp = System.currentTimeMillis();
        c.generateurs=new ArrayList<>();
        c.fermetures=new ArrayList<>();
        for(ArrayList<Integer> l:newGenerateurs){
            if(c.support(l)>=minSup) System.out.println("ferm "+l+"="+c.getFermetureThreaded(l));
        }
        endTimestamp = System.currentTimeMillis();
        System.out.println("2-GENERATEURS traités en "+(endTimestamp-startTimestamp)+" ms");
        //combinaisons de 3 
        combDe=c.combinaisonsDe(c.generateurs);
        s=c.CombinaisonsBinThreaded(combDe.size(),3);
        newGenerateurs=new ArrayList<>();
        for (String item : s) {
            ArrayList<Integer> l=new ArrayList<>();
            for(int i=0;i<item.length();i++){
                if(item.charAt(i)=='1') l.add(combDe.get(i));
            }
            if(c.support(l)>=minSup && !c.inclusDansFermeture(l, c.fermetures)) newGenerateurs.add(l);
        }
        //generateur 3
        System.out.println("3-GENERATEURS");
        startTimestamp = System.currentTimeMillis();
        c.generateurs=new ArrayList<>();
        c.fermetures=new ArrayList<>();
        for(ArrayList<Integer> l:newGenerateurs){
            if(c.support(l)>=minSup) System.out.println("ferm "+l+"="+c.getFermetureThreaded(l));
        }
        endTimestamp = System.currentTimeMillis();
        System.out.println("3-GENERATEURS traités en "+(endTimestamp-startTimestamp)+" ms");
        //combinaisons de 4
        combDe=c.combinaisonsDe(c.generateurs);
        s=c.CombinaisonsBinThreaded(combDe.size(),4);
        newGenerateurs=new ArrayList<>();
        for (String item : s) {
            ArrayList<Integer> l=new ArrayList<>();
            for(int i=0;i<item.length();i++){
                if(item.charAt(i)=='1') l.add(combDe.get(i));
            }
            if(c.support(l)>=minSup && !c.inclusDansFermeture(l, c.fermetures)) newGenerateurs.add(l);
        }
        //generateur 4
        System.out.println("4-GENERATEURS");
        startTimestamp = System.currentTimeMillis();
        c.generateurs=new ArrayList<>();
        c.fermetures=new ArrayList<>();
        for(ArrayList<Integer> l:newGenerateurs){
            if(c.support(l)>=minSup) System.out.println("ferm "+l+"="+c.getFermetureThreaded(l));
        }
        endTimestamp = System.currentTimeMillis();
        System.out.println("4-GENERATEURS traités en "+(endTimestamp-startTimestamp)+" ms");
        //System.out.println("max supp="+maxSup);
//        int nbNuls=0;
//        for(int i=1;i<=c.nbAttributes;i++){
//            ArrayList<Integer> l=new ArrayList<>();
//            l.add(i);
//            if(c.support(l)==0){
//                nbNuls++;
//                System.out.println(" attribut "+i+" =0");
//            }
//        }
//        System.out.println("nombre de nuls="+nbNuls);
    }
}
