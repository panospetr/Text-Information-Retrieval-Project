package aganaktish;

import java.util.*;
/**Klash h opoia ylopoiei ta fylla tou boolean dedrou kata thn anazhthsh me boolean montelo
 * 
 * @author Panos
 */
public class VariableNode extends Node {

    private String var;         // h lexh sthn opoia antistoixei o komvos
    
    /**Constructor, arxikopoihsh twn pediwn
     * 
     * @param var
     * @param lchild
     * @param rchild
     * @param type 
     */
    protected VariableNode(String var, Node lchild, Node rchild, String type) {
        super(lchild, rchild, type);
        this.var = var;
    }
    /**
     * 
     * @return 
     */
    protected String getVar() {
        return var;
    }
    /**
     * 
     * @param var 
     */
    protected void setVar(String var) {
        this.var = var;
    }
    /**Synarthsh h opoia kaleite apo ton patera tou komvou autou kai kanei anazhthsh gia ta egrafa sta 
     * opoia periexete h metavlhth pou antistoixei ston komvo auta (private String var)
     * 
     * @param catalog       O antestramenos katalogos prokeimenou na ginei eukola h anazhthsh
     * @return              Lista me ta arxeia sta opoia periexete h metavlhth/lexh tou komvou .
     */ 
    @Override
    protected ArrayList evaluate(TreeMap catalog) {
        ArrayList myList = (ArrayList) catalog.get(var);
        ArrayList<String> myIdList = new ArrayList();
        if (myList == null) {
            return new ArrayList();
        }

        for (int i = 0; i < myList.size(); i++) {
            Pair temp = (Pair) myList.get(i);
            myIdList.add(temp.getId());
        }
        return myIdList;
    }
    /**
     * 
     */
    @Override
    protected void showMyself() {
        System.out.println("My Value:" + var);
    }
}
