
package aganaktish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Einai h klassh pou ulopoiei to boolean modelo
 * @author Theo
 */
public class Boolean {
    /**
     * Kanei anazhthsh sth sullogh sumfwna me to boolean modelo - ksekinaei to boolean modelo
     * @param myString  to query
     * @return      Lista me ta id twn arxeiwn pou epistreftikan ws apotelesma
     */
    protected static ArrayList<String> searchBooleanQuery(String myString) {

        myString = myString.replace("(", " ( ");
        myString = myString.replace(")", " ) ");
        String[] myStringSplit = myString.split(" ");
        if (myStringSplit.length == 1) {
            myString = myString + " OR " + myString;
        } else if (myStringSplit.length == 2 && myString.contains("NOT")) {
            myString = " ( " + myString + " ) OR ( " + myString + " ) ";
        }
        //Dimiourgeia ierarxias
        Stack lifo = new Stack();
        StringTokenizer token = new StringTokenizer(myString);
        while (token.hasMoreTokens()) {
            String temp = token.nextToken();

            if (temp.equals(")")) {
                String temp1 = (String) lifo.pop();
                String[] tempArray = new String[3];
                int i = 0;
                do {
                    tempArray[i] = temp1;
                    temp1 = (String) lifo.pop();
                    i++;
                } while (!temp1.equals("("));
                String mytemp = tempArray[1] + "(" + tempArray[0] + "  " + tempArray[2] + ")";
                lifo.push(mytemp);
            } else {
                lifo.push(temp);
            }
            if (!token.hasMoreTokens()) {
                String temp1;
                String[] tempArray = new String[3];
                int i = 0;
                while (i < 3) {
                    temp1 = (String) lifo.pop();
                    tempArray[i] = temp1;
                    i++;
                }
                String mytemp = tempArray[1] + "(" + tempArray[0] + "  " + tempArray[2] + ")";
                lifo.push(mytemp);
            }
        }

        String query = (String) lifo.pop();
        Node root = createTree(query);
        ArrayList<String> docs = new ArrayList();

        Set mySet = Aganaktish.collectionCatalog.entrySet();
        Iterator itr = mySet.iterator();
        if (query.contains("NOT")) {
            while (itr.hasNext()) {
                Map.Entry me = (Map.Entry) itr.next();
                ArrayList<Pair> pairList = (ArrayList) me.getValue();
                for (Pair temp : pairList) {
                    String txtID = temp.getId();
                    if (!docs.contains(txtID)) {
                        docs.add(txtID);
                    }
                }
            }
        }


        Node.setTotaldocs(docs);
        ArrayList myList = root.evaluate(Aganaktish.collectionCatalog);


        return (myList);



    }
    /**
     * Dhmiourgei to logiko dedro anadromika
     * @param query To string pou periexei to query.
     * @return Epistrefei tin riza tou dedrou.
     */
    private static Node createTree(String query) {
        //(x or y) and (k or (not a)) test
        Node myNode = null;
        if (query.contains("(")) {
            String temp = query.substring(0, query.indexOf("("));
            if (temp.contains("AND") || temp.contains("OR") || temp.contains("NOT")) {
                String tempquery = query.substring(query.indexOf("(") + 1, query.lastIndexOf(")"));
                String[] tempArray = tempquery.split("  ");
                String[] child = new String[2];
                child[0] = "";
                child[1] = "";
                int k = 0;
                int i = 0;
                while (i < tempArray.length - 1) {
                    if (tempArray[i].contains("(")) {
                        child[k] = child[k].concat("  " + tempArray[i]);
                    } else {
                        if (tempArray[i + 1].contains(")")) {
                            child[k] = child[k].concat("  " + tempArray[i]);
                        } else {
                            child[k] = child[k].concat("  " + tempArray[i]);
                            k = 1;
                        }
                    }
                    i++;
                }
                child[k] = child[k].concat("  " + tempArray[i]);
                Node lchild = createTree(child[0]);
                Node rchild = createTree(child[1]);
                if (temp.contains("AND")) {
                    myNode = new Node(lchild, rchild, "AND");
                } else if (temp.contains("OR")) {
                    myNode = new Node(lchild, rchild, "OR");
                } else {
                    myNode = new Node(lchild, null, "NOT");
                }
            }
        } else {
            query = query.substring(2);
            myNode = new VariableNode(query, null, null, "variable");
        }
        return myNode;
    }
}
