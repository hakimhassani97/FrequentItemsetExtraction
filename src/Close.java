import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hakimhassani97
 */
public class Close {
    //ArrayList<int[]> transactions;
    Transaction[] transactions;
    int nbAttributes=0;
    //int nbLines=5;
    //int nbLines=100000;
    Close(){
        //transactions=new ArrayList();
        //transactions=new Transaction[nbLines];
    }
    /********Setup and Data functions********/
    public int[] stoi(String s[]){//Array of Strings to Array of Int
        int[] t=new int[s.length];
        for(int i=0;i<s.length;i++)
            t[i]=Integer.parseInt(s[i]);
        return t;
    }
    public int[] splitToBinary(String line){//split into binary encoding
        String[] stransaction=line.split("[ ]");
        int[] transaction=stoi(stransaction);
        int[] trans=new int[nbAttributes];
        for(int i=0;i<trans.length;i++){
            for(int j=0;j<transaction.length;j++){
                if(transaction[j]==i+1)
                    trans[i]=1;
            }
            if(trans[i]!=1) trans[i]=0;
        }
        return trans;
    }
    public int[] split(String line){
        String[] stransaction=line.split("[ ]");
        int[] transaction=stoi(stransaction);
        return transaction;
    }
    public void show(int[] transaction){
        for(int i=0;i<transaction.length;i++)
            System.out.print(transaction[i]+" ");
        System.out.println("");
    }
    public String showMatrix(){
        String s="";
        File out=new File("output.dat");
        FileWriter fout=null;
        try {
            fout=new FileWriter(out);
        } catch (IOException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Transaction t:transactions){
            for(int i=0;i<t.attributes.length;i++){
                s+=t.attributes[i]+" ";
                try {
                    fout.write(s);
                    s="";
                } catch (IOException ex) {
                    Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.print(t.attributes[i]+" ");
            }
            s+="\n";
            System.out.println("");
        }
        try {
            fout.close();
        } catch (IOException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    public void showArray(int[] t){
        for(int i=0;i<t.length;i++) System.out.print(t[i]+" ");
        System.out.println("");
    }
    int max(int[] t){
        int max=0;
        for(int i=0;i<t.length;i++){
            if(max<t[i]) max=t[i];
        }
        return max;
    }
    public int nbLines(String fileName){
        int i=0;
        File f=new File(fileName);
        BufferedReader b;
        try {
            b = new BufferedReader(new FileReader(f));
            String line=b.readLine();
            while(line!=null){
                int m=max(split(line));
                if(m>nbAttributes) nbAttributes=m;
                i++;
                line=b.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    public void loadFile(String fileName){
        transactions=new Transaction[nbLines(fileName)];
        try {
            File f=new File(fileName);
            BufferedReader b=new BufferedReader(new FileReader(f));
            String line=b.readLine();
            int i=0;
            while(line!=null){
                //transactions.add(split(line));
                transactions[i]=new Transaction();
                transactions[i].attributes=splitToBinary(line);
                i++;
                line=b.readLine();
            }
            //String res=showMatrix();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /******** ensemble functions********/
    int nbDigits(int n){
        int nb=0;
        if(n==0) return 1;
        while(n>0){
            n=n/10;
            nb++;
        }
        return nb;
    }
    int nbUns(String s){
        int nb=0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='1') nb++;
        }
        return nb;
    }
    public ArrayList<String> CombinaisonsBinThreaded(int taille,int nbElements){
        ArrayList<String> s=new ArrayList<>();
        int nbThreads=5;
        CombinaisonWorker[] c=new CombinaisonWorker[nbThreads];
        int pas=(int)Math.pow(2, taille)/nbThreads;
        int d=0,f=pas;
        for(int i=0;i<c.length;i++){
            c[i]=new CombinaisonWorker(this, d,f,nbElements,s,taille);
            d+=pas;
            f+=pas;
        }
        //start threads
        for(int i=0;i<c.length;i++){
            c[i].start();
        }
        try {
            for(int i=0;i<c.length;i++){
                c[i].join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    //toutes les combinaisnons de taille nbElements d'un ensemble de taille=taille
    public String[] CombinaisonsBin(int taille,int nbElements){
        String ss="";
        int nb=0,max=0;
        for(int i=0;i<Math.pow(2, taille);i++){
            ss=Integer.toBinaryString(i);
            if(max<ss.length()) max=ss.length();
            if(nbUns(ss)==nbElements) nb++;
        }
        String[] s=new String[nb];
        int cpt=0;
        for(int i=0;i<Math.pow(2, taille);i++){
            ss=Integer.toBinaryString(i);
            if(nbUns(ss)==nbElements){
                s[cpt]=ss;
                //fill with zeros
                int vides=max-s[cpt].length();
                for(int j=0;j<vides;j++){
                    s[cpt]="0"+s[cpt];
                }
                cpt++;
            }
        }
        return s;
    }
    boolean inclus(ArrayList<Integer> ens1,ArrayList<Integer> ens2){
        boolean exist;
        for(int i:ens1){
            exist=false;
            for(int j:ens2){
                if(i==j) exist=true;
            }
            if(!exist) return false;
        }
        return true;
    }
    boolean memeEns(ArrayList<Integer> ens1,ArrayList<Integer> ens2){
        return inclus(ens1,ens2) && inclus(ens2,ens1);
    }
    boolean inclusDansFermeture(ArrayList<Integer> ens,ArrayList<ArrayList<Integer>> fermeture){
        for(ArrayList<Integer> i:fermeture){
            if(inclus(ens, i)) return true; 
        }
        return false;
    }
    /********Close Algo functions********/
    int[] listToArray(ArrayList<Integer> l){
        int[] a=new int[l.size()];
        for(int i=0;i<a.length;i++)
            a[i]=l.get(i);
        return a;
    }
    ArrayList<Integer> arrayToList(int[] a){
        ArrayList<Integer> l=new ArrayList<>();
        for(int i=0;i<a.length;i++)
            l.add(a[i]);
        return l;
    }
    public double support(ArrayList<Integer> attributes){
        double supp=0;
        for(int i=0;i<transactions.length;i++){
            if(estUn(attributes,i)){
                supp++;
            }
        }
        return supp/transactions.length;
    }
    ArrayList<Integer> combinaisonsDe(ArrayList<ArrayList<Integer>> fermeture){
        ArrayList<Integer> l=new ArrayList<>();
        for(ArrayList<Integer> i:fermeture){
            for(Integer j:i){
                if(!l.contains((int) j)) l.add(j);
            }
        }
        return l;
    }
    ArrayList<ArrayList<Integer>> generateurs=new ArrayList<>();
    ArrayList<ArrayList<Integer>> fermetures=new ArrayList<>();
    public ArrayList<Integer> getFermetureThreaded(ArrayList<Integer> generateur){
        ArrayList<Integer> fermeture=new ArrayList<>();
        int nbThreads=9;
        //init threads
        CloseWorker[] c=new CloseWorker[nbThreads];
        int d=0,f=nbAttributes/nbThreads;
        for(int i=0;i<c.length;i++){
            c[i]=new CloseWorker(this, d,f, generateur, fermeture);
            d+=nbAttributes/nbThreads;
            f+=nbAttributes/nbThreads;
        }
        //start threads
        for(int i=0;i<c.length;i++){
            c[i].start();
        }
        try {
            for(int i=0;i<c.length;i++){
                c[i].join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
        }
        generateurs.add(generateur);
        fermetures.add(fermeture);
        return fermeture;
    }
    public ArrayList<Integer> getFermeture(ArrayList<Integer> generateur){
        ArrayList<Integer> fermeture=new ArrayList<>();
        for(int i=0;i<nbAttributes;i++){
            boolean b=true;
            for(int j=0;b && j<transactions.length;j++){
                if(estUn(generateur,j)){
                    ArrayList<Integer> l=new ArrayList<>();
                    l.add(i+1);
                    if(!estUn(l,j)){
                        b=false;
                    }
                }
            }
            if(b) fermeture.add(i+1);
        }
        return fermeture;
    }
    public boolean estUn(ArrayList<Integer> fermeture,int ligne){//fermeture=liste d'attributs a verifier dans la ligne
        for(int i=0;i<fermeture.size();i++){
            if(transactions[ligne].attributes[fermeture.get(i)-1]!=1) return false;
        }
        return true;
    }
    public void close(double minSup){
        int gen=2;
        //generateur 1
        double startTimestamp = System.currentTimeMillis();
        this.generateurs=new ArrayList<>();
        this.fermetures=new ArrayList<>();
        for(int i=1;i<=this.nbAttributes;i++){
            ArrayList<Integer> l=new ArrayList<>();
            l.add(i);
            if(this.support(l)>=minSup) System.out.println("ferm "+l+"="+this.getFermetureThreaded(l));
        }
        System.out.println("nb els :"+this.fermetures.size());
        double endTimestamp = System.currentTimeMillis();
        System.out.println("1-GENERATEURS traités en "+(endTimestamp-startTimestamp)+" ms");
        //combinaisons de n
        ArrayList<Integer> combDe=this.combinaisonsDe(this.generateurs);
        ArrayList<String> s=this.CombinaisonsBinThreaded(combDe.size(),2);
        ArrayList<ArrayList<Integer>> newGenerateurs=new ArrayList<>();
        for (String item : s) {
            ArrayList<Integer> l=new ArrayList<>();
            for(int i=0;i<item.length();i++){
                if(item.charAt(i)=='1') l.add(combDe.get(i));
            }
            if(this.support(l)>=minSup && !this.inclusDansFermeture(l, this.fermetures)) newGenerateurs.add(l);
        }
        while(newGenerateurs.size()>0){
            //generateur n
            System.out.println(gen+"-GENERATEURS");
            startTimestamp = System.currentTimeMillis();
            this.generateurs=new ArrayList<>();
            this.fermetures=new ArrayList<>();
            for(ArrayList<Integer> l:newGenerateurs){
                if(this.support(l)>=minSup) System.out.println("ferm "+l+"="+this.getFermetureThreaded(l));
            }
            System.out.println("nb els :"+this.fermetures.size());
            endTimestamp = System.currentTimeMillis();
            System.out.println(gen+"-GENERATEURS traités en "+(endTimestamp-startTimestamp)+" ms");
            gen++;
            //combinaisons de n+1
            combDe=this.combinaisonsDe(this.generateurs);
            s=this.CombinaisonsBinThreaded(combDe.size(),gen);
            newGenerateurs=new ArrayList<>();
            for (String item : s) {
                ArrayList<Integer> l=new ArrayList<>();
                for(int i=0;i<item.length();i++){
                    if(item.charAt(i)=='1') l.add(combDe.get(i));
                }
                if(this.support(l)>=minSup && !this.inclusDansFermeture(l, this.fermetures)) newGenerateurs.add(l);
            }
        }
    }
}
