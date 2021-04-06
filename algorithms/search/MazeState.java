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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeState p = (MazeState) o;
        return ((Position)(this.getOrigin())).equals(p.getOrigin());
    }
    public int hashCode(){
       return ((Position)this.getOrigin()).hashCode();
    }


}
