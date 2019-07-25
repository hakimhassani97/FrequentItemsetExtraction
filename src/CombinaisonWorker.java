
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
public class CombinaisonWorker extends Thread {
    Close c;int debut;int fin ;
    int nbElements;
    ArrayList<String> s;
    int max;
    CombinaisonWorker(Close close,int d,int f,int nbEl,ArrayList<String> liste,int m){
        c = close;
        s=liste;
        max=m;
        nbElements=nbEl;
        this.debut=d;
        this.fin=f;
    }
    @Override
    public void run() {
        String ss="";
        for(int i=debut;i<fin;i++){
            ss=Integer.toBinaryString(i);
            if(c.nbUns(ss)==nbElements){
                synchronized(s){
                    s.add(ss);
                    //fill with zeros
                    int vides=max-s.get(s.size()-1).length();
                    for(int j=0;j<vides;j++){
                        s.set(s.size()-1,"0"+s.get(s.size()-1));
                    }
                }
            }
        }
    }
    
}
