package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{

    //inherit all the functions of the AState class with dependency injection of Position
    public MazeState(Position pos_arg) {
        super(pos_arg);
        this.origin = (Position) this.origin; //casting the object to exact type???
        
    }


    //implementation of the abstruct functions:

    public String toString(){
       return ((Position)this.getOrigin()).toString();
    }
    public boolean equals(Object o){
        Position p = (Position) o;
        return ((Position)(this.getOrigin())).equals(p);
    }
    public int hashCode(){
       return ((Position)this.getOrigin()).hashCode();
    }


}
