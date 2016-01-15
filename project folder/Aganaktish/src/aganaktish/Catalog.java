package aganaktish;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
/**
 * 
 * @author Theo
 */
public class Catalog {
   
    /**
     * Dhmiourgei ton Katalago apo oles tis lekseis twn arxeiwn.
     * @param catalog
     * @param path TO path sto opoio vriskontai ta arxeia.
     * @return Epistrefei ena Treemap pou periexei ton antrestameno katalogo.
     * @throws IOException
     * @throws NullPointerException 
     */
    public static TreeMap createCatalog(TreeMap<String, ArrayList<Pair>> catalog, String path) throws IOException, NullPointerException {
        String tempLine = "";
        BufferedReader in;
        String contained = "";
        String id = path.substring((path.lastIndexOf("\\") + 1), path.lastIndexOf(".txt"));
        File myFile = new File(path);
        in = new BufferedReader(new FileReader(path));
        tempLine = in.readLine();
        while (tempLine != null) {
            contained = contained + tempLine;
            tempLine = in.readLine();
        }
        in.close();
        contained = contained.replace(".", " ");
        contained = contained.replace("(", " ");
        contained = contained.replace(")", " ");
        contained = contained.replace(",", " ");
        contained = contained.replace("?", " ");
        contained = contained.replace(":", " ");
        contained = contained.replace("\"", " ");
        contained = contained.replace(" '", " ");
        contained = contained.replace("' ", " ");
        contained = contained.replace(";", " ");
        contained = " " + contained + " ";

        StringTokenizer token = new StringTokenizer(contained);
        while (token.hasMoreTokens()) {
            String temp = token.nextToken();
            ArrayList<Pair> templist = new ArrayList();
            if (catalog.containsKey(temp)) {
                templist = (ArrayList) catalog.get(temp);
                boolean found = false;
                Pair myPair = null;
                int counterPos = 0;
                for (Pair arrayBlock : templist) {
                    if (arrayBlock.getId().equals(id)) {
                        counterPos = templist.indexOf(arrayBlock);
                        int tempInt = arrayBlock.getCounter();
                        tempInt = tempInt + 1;
                        arrayBlock.setCounter(tempInt);
                        myPair = arrayBlock;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    myPair = new Pair(id, 1);
                    templist.add(myPair);
                } else {
                    templist.set(counterPos, myPair);
                }
            } else {
                catalog.put(temp, templist);
                Pair myPair = new Pair(id, 1);
                templist.add(myPair);
            }

            catalog.put(temp, templist);
        }
        return catalog;
    }
    /** Kleinei to katalogo tis epilegmenhs sylloghs k analoga me to an exei ginei allagh se auton ton xanaapothikeuei
     * 
     * @throws IOException
     * @throws NullPointerException
     * @throws ClassNotFoundException 
     */
    public static void closeCatalog() throws IOException, NullPointerException, ClassNotFoundException {
        if (Aganaktish.newCatalog != 0) {
            saveCatalog();
            Aganaktish.newCatalog = 0;
        }
        Aganaktish.collectionCatalog = null;
    }
    /**Apothikeuei tis allages pou eginan ston katalogo
     * 
     * @throws IOException
     * @throws NullPointerException
     * @throws ClassNotFoundException 
     */
    public static void saveCatalog() throws IOException, NullPointerException, ClassNotFoundException {
        File myFile = new File(Aganaktish.collectionPath + "\\catalog");
        if (!myFile.exists()) {
            myFile.mkdir();
        }
        FileOutputStream fos = new FileOutputStream(Aganaktish.collectionPath + "\\catalog\\catalog.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(Aganaktish.collectionCatalog);
        oos.flush();
        oos.close();
    }
    /**Apothikeuei tis allages pou eginan ston katalogo otan autos dhmiourgeitai gia prwth fora
     * 
     * @param path  to monopati sto opoio tha ginei h apopthikeush
     * @throws IOException
     * @throws NullPointerException
     * @throws ClassNotFoundException 
     */
    public static void saveCatalog(String path) throws IOException, NullPointerException, ClassNotFoundException {
        File myFile = new File(path + "\\catalog");
        if (!myFile.exists()) {
            myFile.mkdir();
        }
        FileOutputStream fos = new FileOutputStream(path + "\\catalog\\catalog.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(Aganaktish.collectionCatalog);
        oos.flush();
        oos.close();
    }
    /**
     * Fortwnei sto programma ta stoixeia tou katalogou ths sylloghs pou exei epilexthei.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static void loadCatalog() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(Aganaktish.collectionPath + "\\catalog\\catalog.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Aganaktish.collectionCatalog = (TreeMap) ois.readObject();
    }
    /**
     * Eisagwgh kainouriou arxeiou pros epeksergasia kai kataxwrhsh sto hdh uparxonta katalogo.
     * @param path To path tou arxeiou pou einai na prostethei ston katalogo.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void InsertIntoCatalog(String path) throws FileNotFoundException, IOException {
        String tempLine = "";
        BufferedReader in;
        String contained = "";
        String id = path.substring((path.lastIndexOf("\\") + 1), path.lastIndexOf(".txt"));
        in = new BufferedReader(new FileReader(path));
        tempLine = in.readLine();
        while (tempLine != null) {
            contained = contained + tempLine;
            tempLine = in.readLine();
        }
        in.close();
        contained = contained.replace(".", " ");
        contained = contained.replace("(", " ");
        contained = contained.replace(")", " ");
        contained = contained.replace(",", " ");
        contained = contained.replace("?", " ");
        contained = contained.replace(":", " ");
        contained = contained.replace("\"", " ");
        contained = contained.replace(" '", " ");
        contained = contained.replace("' ", " ");
        contained = contained.replace(";", " ");
        contained = " " + contained + " ";

        StringTokenizer token = new StringTokenizer(contained);
        while (token.hasMoreTokens()) {
            String temp = token.nextToken();
            ArrayList<Pair> templist = new ArrayList();
            if (Aganaktish.collectionCatalog.containsKey(temp)) {
                templist = (ArrayList) Aganaktish.collectionCatalog.get(temp);
                boolean found = false;
                Pair myPair = null;
                int counterPos = 0;
                for (Pair arrayBlock : templist) {
                    if (arrayBlock.getId().equals(id)) {
                        counterPos = templist.indexOf(arrayBlock);
                        int tempInt = arrayBlock.getCounter();
                        tempInt = tempInt + 1;
                        arrayBlock.setCounter(tempInt);
                        myPair = arrayBlock;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    myPair = new Pair(id, 1);
                    templist.add(myPair);
                } else {
                    templist.set(counterPos, myPair);
                }
            } else {
                Aganaktish.collectionCatalog.put(temp, templist);
                Pair myPair = new Pair(id, 1);
                templist.add(myPair);
            }

            Aganaktish.collectionCatalog.put(temp, templist);
        }
    }
    /**
     * Diagrafh arxeiou apo ton katalogo.
     * @param path To path tou arxeiou pou einai pros diagrafh
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void deleteFromCatalog(String path) throws FileNotFoundException, IOException {
        String tempLine = "";
        BufferedReader in;
        String contained = "";
        String id = path.substring((path.lastIndexOf("\\") + 1), path.lastIndexOf(".txt"));
        in = new BufferedReader(new FileReader(path));
        tempLine = in.readLine();
        while (tempLine != null) {
            contained = contained + tempLine;
            tempLine = in.readLine();
        }
        in.close();
        contained = contained.replace(".", " ");
        contained = contained.replace("(", " ");
        contained = contained.replace(")", " ");
        contained = contained.replace(",", " ");
        contained = contained.replace("?", " ");
        contained = contained.replace(":", " ");
        contained = contained.replace("\"", " ");
        contained = contained.replace(" '", " ");
        contained = contained.replace("' ", " ");
        contained = contained.replace(";", " ");
        contained = " " + contained + " ";

        StringTokenizer token = new StringTokenizer(contained);
        while (token.hasMoreTokens()) {
            String temp = token.nextToken();
                ArrayList<Pair> templist = (ArrayList) Aganaktish.collectionCatalog.get(temp);
                Pair delPair=null;
                for(Pair tempPair:templist){
                    if(tempPair.getId().equals(id)){
                        delPair=tempPair;
                        break;
                    }
                }
                templist.remove(delPair);
                Aganaktish.collectionCatalog.put(temp, templist);
        }
    }
    
    /**
     * Entopismos kai apaloifh twn leksewn pou emfanizontai se panw apo n% twn egrafwn - To n orizetai apo ton xrhsth.
     * @param percentage Pososto twn arxeiwn pou prepei na emfanizetai h leksh gia na diagrafei.
     */
    public static void removeCommon(int percentage){
        
        
        Set mySet= Aganaktish.collectionCatalog.entrySet();
        Iterator itr= mySet.iterator();
        int doc=Aganaktish.docNum;
        ArrayList<String> removable=new ArrayList();
        while(itr.hasNext())
        {
            Map.Entry me=(Map.Entry) itr.next();
            ArrayList<PairDouble> tempList= (ArrayList)me.getValue();
            if(100*tempList.size()/doc > percentage)
            {
                removable.add((String)me.getKey());
            }
           
            
        }
        if(removable.size()>=1)
        {
                for(String temp:removable)
                {
                    Aganaktish.collectionCatalog.remove(temp);
                }
           Aganaktish.newCatalog=1;
        }
    }
}
