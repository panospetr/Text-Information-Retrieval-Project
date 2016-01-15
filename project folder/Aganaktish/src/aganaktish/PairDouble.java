
package aganaktish;
/**Ylopoiei tous komvous tis listas me vary gia kathe egrafw pou antistoixeitai se kathe lexh.
 * 
 * @author Panos
 */
public class PairDouble {
    
    private String id;
    private double weight;
    
    
    protected PairDouble(String id, double weight){
        
        this.id=id;
        this.weight=weight;
    }
    
    protected void setId(String id) {
        this.id = id;
    }

    protected void setWeight(double weight) {
        this.weight = weight;
    }

    protected String getId() {
        return id;
    }

    protected double getWeight() {
        return weight;
    }
    
    protected void showMe(){
        System.out.println(this.id + " -- "+ this.weight );
    }
    
}
