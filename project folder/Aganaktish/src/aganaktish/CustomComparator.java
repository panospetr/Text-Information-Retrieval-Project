package aganaktish;

import java.util.Comparator;
/**H klash auth ylopoiei enan Comparator wste na einai dynath h sygrish twn klasewn PairDouble
 * apo thn klash Collections ths java, ws pros to pedio weight tous.
 * 
 * @author Panos
 */
public class CustomComparator implements Comparator<PairDouble> {
    /**Sygrinei 2 antikeimena PairDouble ws pros to pedio weight .
     * 
     * @param o1    1o antikeimeno pros sygrish 
     * @param o2    2o antikeimeno pros sygrish
     * @return      int -1, 1 , 0 analoga me to poio einai megalytero mikrotero h iso.
     */
    @Override
    public int compare(PairDouble o1, PairDouble o2) {

        double weight1 = o1.getWeight();
        double weight2 = o2.getWeight();

        if (weight1 > weight2) {
            return -1;
        } else if (weight1 < weight2) {
            return 1;
        } else {
            return 0;
        }

    }
}
