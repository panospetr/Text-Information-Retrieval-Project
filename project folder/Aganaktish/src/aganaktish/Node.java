package aganaktish;

import java.util.*;
/**H klash auth ylopoiei enan komvo tou logikou dedrou to opoio einai aparaithto gia ena boolean query.
 * 
 *
 * 
 * @author Panos
 */
public class Node {
    
    
    private Node lchild;        //aristero paidi tou dedrou
    private Node rchild;        //dexi pedi tou dedrou
    private String type;        //Typos dedrou, OR NOT AND
    private static ArrayList<String> totaldocs;         //ta synolika documents pou periexontai ston katalogo sylloghs

    protected static void setTotaldocs(ArrayList totaldocs) {
        Node.totaldocs = totaldocs;
    }
    /**Constructor, arxikopoihsh twn pediwn
     * 
     * @param lchild
     * @param rchild
     * @param type 
     */

    protected Node(Node lchild, Node rchild, String type) {
        this.lchild = lchild;
        this.rchild = rchild;
        this.type = type;
    }

    protected void setLchild(Node lchild) {
        this.lchild = lchild;
    }

    protected void setRchild(Node rchild) {
        this.rchild = rchild;
    }

    protected void setType(String type) {
        this.type = type;
    }

    protected Node getLchild() {
        return lchild;
    }

    protected Node getRchild() {
        return rchild;
    }

    protected String getType() {
        return type;
    }

    protected ArrayList getTotaldocs() {
        return totaldocs;
    }

    protected void setList(ArrayList list) {
        this.totaldocs = list;
    }

    
    /**Synarthsh pou kalei ton eauto ths sta paidia tou komvou kai ektelei katallhles praxeis analoga me ton 
     * typo tou komvou tis sta apotelesmata pou lamvanei apo ta paidia ths.
     * 
     * @param catalog       O antestramenos katalogos o opoios xrhshmopoieitai sto boolean query search.
     * @return              Epistrefei mia lista me egrafa ws apotelesma tis mexri twra anazhthshs
     */
    protected ArrayList evaluate(TreeMap catalog) {

        ArrayList<String> newList = new ArrayList();
        if (type.equals("OR")) {
            ArrayList rList = this.rchild.evaluate(catalog);
            ArrayList lList = this.lchild.evaluate(catalog);

            Iterator iter = rList.iterator();

            while (iter.hasNext()) {
                String temp = iter.next().toString();
                if (!lList.contains(temp)) {
                    lList.add(temp);
                }

            }
            newList = lList;
        } else if (type.equals("AND")) {
            ArrayList rList = this.rchild.evaluate(catalog);
            ArrayList lList = this.lchild.evaluate(catalog);

            Iterator iter = rList.iterator();
            while (iter.hasNext()) {
                String temp = iter.next().toString();
                if (lList.contains(temp)) {
                    newList.add(temp);
                }


            }


        } else if (type.equals("NOT")) {
            ArrayList lList = this.lchild.evaluate(catalog);
            newList = totaldocs;
            Iterator iter = lList.iterator();

            while (iter.hasNext()) {
                String temp = iter.next().toString();
                if (newList.contains(temp)) {
                    newList.remove(temp);
                }


            }

        }
        return newList;
    }

    protected void showMyself() {
        System.out.println("My Type:" + type);
        if (type.equals("NOT")) {
            lchild.showMyself();
        } else {
            rchild.showMyself();
            lchild.showMyself();
        }
    }
}
