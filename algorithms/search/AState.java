package algorithms.search;

import java.util.Objects;

public abstract class AState {


    protected AState parent = null;
    protected double cost = 0 ; //cost of step initializes to zero.
    protected Object origin; //refers to the original origin object.- dependency injection


    //not difaultive cconstructor
    public AState(Object origin1) throws Exception {
        NullArgCheck(origin1);
        origin = origin1;
    }

    // ABSTRUCT FUNCTIONS:

    public abstract String toString();
    public abstract boolean equals(Object o);
    public abstract int hashCode();


    //COST COMPERATOR
    public int CompareCost(AState s2){

        if(this.getCost() > s2.getCost()){
            return 1;
        }
        if(this.getCost() < s2.getCost()){
            return -1;
        }
        return 0;
    }


    //GETTERS AND SETTERS
    public AState getParent() {
        return parent;
    }

    public void setParent(AState parent) throws Exception {
        this.parent = parent;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) throws Exception {
        NullArgCheck(cost);
        this.cost = cost;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin1) throws Exception {

        NullArgCheck(origin1);
        origin = origin1;
    }

    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }

}
