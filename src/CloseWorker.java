
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hakimhassani97
 */
public class CloseWorker extends Thread {
    Close c;int debut;int fin ;
    ArrayList<Integer> generateur;
    ArrayList<Integer> fermeture;
    CloseWorker(Close close,int debut,int fin,ArrayList<Integer> g,ArrayList<Integer> f){
        c = close;
        generateur=g;
        fermeture=f;
        this.debut = debut;
        this.fin = fin;
    }
    @Override
    public void run() {
        for(int i=debut;i<fin;i++){
            boolean b=true;
            for(int j=0;b && j<c.transactions.length;j++){
                if(c.estUn(generateur,j)){
                    ArrayList<Integer> l=new ArrayList<>();
                    l.add(i+1);
                    if(!c.estUn(l,j)){
                        b=false;
                    }
                }
            }
            synchronized(fermeture){
                if(b) fermeture.add(i+1);
            }
        }
    }
    
}
