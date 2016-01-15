
package aganaktish;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**Einai h klassh pou ulopoiei to Vector modelo
 * 
 * @author Panos
 */
public class Vector {
    
    
    /**
     * Kanei anazhthsh sthn sullogh sumfona me to vector modelo -arxizei to vector modelo .
     * @param method To onoma ths methodou pou tha xrhshmopoihthei
     * @param query To query.
     * @return    Lista zeugwn (id,weight) pou epistreftikan ws apotelesma
     */
    protected static ArrayList<PairDouble> initializeVector(String method, String query) {
        String[] words = query.split(" ");
        TreeMap queryMap = calculateQueryWeights(words);
        ArrayList<PairDouble> pairList = new ArrayList();
        double currentWeight;
        ArrayList<String> docs = new ArrayList();
        Set mySet = Aganaktish.collectionCatalog.entrySet();
        Iterator itr = mySet.iterator();
        while (itr.hasNext()) {
            Map.Entry me = (Map.Entry) itr.next();
            ArrayList<Pair> tempPairList = (ArrayList) me.getValue();
            for (Pair temp : tempPairList) {
                String txtID = temp.getId();
                if (!docs.contains(txtID)) {
                    docs.add(txtID);
                }
            }
        }
        DecimalFormat df = new DecimalFormat("#.00000");


        for (String temp : docs) {
            double tempWeight = calculateDocVectors(temp, queryMap, method);
            currentWeight=Double.parseDouble(df.format(tempWeight));
            PairDouble myPairDouble = new PairDouble(temp, currentWeight);
            pairList.add(myPairDouble);
        }

        Collections.sort(pairList, new CustomComparator());

        return pairList;


    }
    /**
     * Ypologizei to varos ths kathe lekshs tou query.
     * @param words To query.
     * @return  Epistrefei ena TreeMap me kleidi tin lexh k value to varos ths
     */
    private static TreeMap calculateQueryWeights(String[] words) {


        TreeMap queryMap = new TreeMap();
        int weight;
        for (String temp : words) {
            if (queryMap.containsKey(temp)) {
                weight = (Integer) queryMap.get(temp);
                queryMap.put(temp, weight + 1);
            } else {
                queryMap.put(temp, 1);
            }
        }
        Set set2 = queryMap.entrySet();
        // Get an iterator
        Iterator iter2 = set2.iterator();
        while (iter2.hasNext()) {
            Map.Entry me = (Map.Entry) iter2.next();
            String key = (String) me.getKey();
            ArrayList<Pair> myList = new ArrayList();
            if (Aganaktish.collectionCatalog.containsKey(key)) {
                myList = (ArrayList) Aganaktish.collectionCatalog.get(key);
            }
            if (myList.isEmpty()) {
                queryMap.put(key, 0);
            } else {
                //DIAFORETIKI SYNARTISI
                weight = (Integer) queryMap.get(key);
                double tempno = Math.log(Aganaktish.docNum / myList.size());
                queryMap.put(key, weight * tempno);
            }
        }

        return queryMap;


    }
    /**
     * Ypologizei ta dianismata sxetikothtas egrafwn - query.
     * @param id    To egrafo ypo eksetash
     * @param queryMap  To apotelesma ths     TreeMap calculateQueryWeights(String[] words)
     * @param method    H methodos ypologismou pou exei epilexthei (Jaccard, Dice, Cosine)
     * @return      To varos tou egrafou pou eksetazoume
     */
    private static double calculateDocVectors(String id, TreeMap queryMap, String method) {

        double tf, idf;
        TreeMap tempMap = new TreeMap();
        Set set2 = Aganaktish.collectionCatalog.entrySet();
        Iterator iter2 = set2.iterator();
        double Ld = 0;
        while (iter2.hasNext()) {
            Map.Entry me = (Map.Entry) iter2.next();
            ArrayList<Pair> value = (ArrayList) me.getValue();
            for (Pair temp : value) {
                if (temp.getId().equals(id)) {
                    tf = temp.getCounter();
                    idf = Math.log(Aganaktish.docNum / value.size());
                    double temp_weight = tf * idf;
                    tempMap.put(me.getKey(), temp_weight);   // Ypologismos varous lexhs.
                    Ld = Ld + Math.pow(temp_weight, 2);
                    break;
                }
            }
        }
        Set set3 = queryMap.entrySet();
        Iterator iter3 = set3.iterator();
        double sum = 0;
        double Lq = 0;
        while (iter3.hasNext()) {
            Map.Entry me = (Map.Entry) iter3.next();
            String wordInQuery = (String) me.getKey();
            double tempDouble = Double.parseDouble(me.getValue().toString());
            Lq = Lq + Math.pow(tempDouble, 2);
            if (tempMap.containsKey(wordInQuery)) {
                //ArrayList mylist = (ArrayList) collectionCatalog.get(wordInQuery);
                sum = sum + (Double) queryMap.get(wordInQuery) * (Double) tempMap.get(wordInQuery);
            }
        }
        double result = 0;
        if (method.equals("Cosine Method")) {
            Ld = Math.sqrt(Ld);
            Lq = Math.sqrt(Lq);
            double paronomastis = Ld * Lq;
            if (paronomastis == 0) {
                return 0;
            }
            result = sum / paronomastis;
        } else if (method.equals("Dice Method")) {
            sum = 2 * sum;
            double paronomastis = Math.pow(Math.sqrt(Lq), 2) + Math.pow(Math.sqrt(Ld), 2);
            result = sum / paronomastis;
        } else if (method.equals("Jaccard Method")) {
            double paronomastis = Math.pow(Math.sqrt(Lq), 2) + Math.pow(Math.sqrt(Ld), 2) - sum;
            result = sum / paronomastis;
        }
        return result;
    }
}
