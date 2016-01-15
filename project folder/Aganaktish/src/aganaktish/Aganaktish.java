
package aganaktish;

import java.io.*;
import java.util.*;
import java.lang.Math.*;
/**
 * 
 * @author Theo
 */
public class Aganaktish {

    protected static ArrayList<String> Sylloges;
    protected static String collectionPath;
    protected static TreeMap collectionCatalog;
    protected static int docNum;
    protected static int newCatalog = 0;

    public static void main(String[] args) throws IOException, NullPointerException, ClassNotFoundException {

         CollectionClass.loadCollections();
         MainWindow myWindow = new MainWindow();
         myWindow.setVisible(true);

    }
    /**
         * Ypologizei ta statistika,
         * Dahladh to recall kai to precision sugkrinontas ta dedomena apotelesmata me ta dika mas apotelesmata.
         * @param ourMap Einai ta dika mas apotelesmata
         * @param hisMap Einai ta dedomena apotelesmata.
         * @return 
         */

    
       public static TreeMap<Integer,double[]> calculateStats(TreeMap ourMap, TreeMap hisMap){
           
           
           TreeMap<Integer,double[]> myMap= new TreeMap();
           for (int i=1;i<=hisMap.size();i++)
           {
               ArrayList<PairDouble> ourList= (ArrayList)ourMap.get(i);
               ArrayList<String> hisList= (ArrayList)hisMap.get(i);
               double counter=0;
               
               for(String temp:hisList)
                   for(PairDouble ourTemp: ourList)
                       if( ourTemp.getId().equals(temp))
                           counter++;
               
               double recall, precision ;
               
               precision=counter/ourList.size();
               recall = counter/hisList.size();
               double[] tempArray= new double[2];
               tempArray[0]= recall;
               tempArray[1]= precision;
               myMap.put(i,tempArray);
               
                
               
               
           }
           return myMap;
        
        
    }
    /**Trexei ena ena ta queries apo to txt pou dinete sta orismata
     * 
     * @param path Einai to path to opoio deixnei sto txt me ta queries twn sullogwn med kai cran.
     * @param method Einai h methodos me thn opoia tha treksei to vector modelo (Cosine,dice,jaccard).
     * @param k Einai ta top k pou tha epistrepsei to vector modelo.
     * @return Epistrefei ena TreeMap me key ta ID twn queries kai value mia ArrayList twn apotelesmatwn pou vrethhkan.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static TreeMap<Integer, ArrayList<PairDouble>> runqueries(String path, String method, int k) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        TreeMap<Integer, ArrayList<PairDouble>> myMap = new TreeMap();
        String filename = ".I ", tempStr, j="", query = "";

        tempStr = in.readLine();
        do {
            if (tempStr.contains(filename)) {
                j=tempStr.substring(tempStr.lastIndexOf(" ")+1);
                in.readLine();

            }
           
            tempStr = in.readLine();
            while ((tempStr != null) && (!tempStr.contains(filename))) {


                query = query +" "+ tempStr;
                tempStr = in.readLine();
            }
            
            ArrayList<PairDouble> tempList = Vector.initializeVector(method, query);

            ArrayList<PairDouble> topList = new ArrayList();
            if (k == 0) {
                for (PairDouble temp : tempList) {
                    if (temp.getWeight() != 0) {
                        topList.add(temp);
                    }
                }
            } else {
                int m = 0;
                for (PairDouble temp : tempList) {
                    if (m < k) {
                        topList.add(temp);
                    }
                    m++;
                }
            }
            Collections.sort(topList,new CustomComparator());
            myMap.put(Integer.parseInt(j), topList);
            if(tempStr!=null){
                j=tempStr.substring(tempStr.lastIndexOf(" ")+1);
                tempStr = in.readLine();
            }
            query = "";
            
        } while (tempStr != null);
        in.close();
        return myMap;


    }
    /**Fortwnei sto programma ta apoteslesmata ton dedomenwn query
     * 
     * @param path Einai to path sto opoio vrisketai to txt pou periexei ta dedomena apotelesmata.
     * @return Epistrefei ena TreeMap me key ta ID twn queries kai value mia ArrayList me ID twn dedomenwn apotelesmatwn twn txt
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static TreeMap<Integer, ArrayList<String>> getRelevant(String path) throws FileNotFoundException, IOException {
        TreeMap<Integer, ArrayList<String>> myMap = new TreeMap();
        File myFile = new File(path);
        BufferedReader in = new BufferedReader(new FileReader(path));
        String tempLine;
        ArrayList<String> tempList = new ArrayList();
        int i = 1;
        String[] splite;
        tempLine=in.readLine();
        while (tempLine != null) {            
            splite=tempLine.split(" ");
            if(Integer.parseInt(splite[0])==i)
            {
                tempList.add(splite[1]);
            }
            else{
                myMap.put(i, tempList);
                i++;
                tempList=new ArrayList();
                tempList.add(splite[1]);
                
            }
            tempLine=in.readLine();
        } 
        myMap.put(i,tempList);
        in.close();
        return myMap;
    }
    /**
     * 
     * @return Epistrefei ena pinaka String me ta onomata twn txt pou exei h sullogh.
     */
    public static String[] getTxt() {
        File myFile = new File(collectionPath + "\\txt\\");
        String[] txt = myFile.list();
        for (int i = 0; i < txt.length; i++) {
            txt[i] = txt[i].substring(0, txt[i].lastIndexOf(".txt"));
        }
        return txt;
    }
}
