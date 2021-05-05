package algorithms.maze3D;

import algorithms.search.AState;

public class Maze3DState extends AState {

    //inherit all the functions of the AState class with dependency injection of Position
    //CONSTRUCTOR
    public Maze3DState(Position3D pos3d_arg) throws Exception {
        super(pos3d_arg);
        this.origin = (Position3D) this.origin;
    }

    //implementation of the abstruct functions:
    @Override
    public String toString() {

            return ((Position3D)this.getOrigin()).toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze3DState p = (Maze3DState) o;
        return ((Position3D)(this.getOrigin())).equals(p.getOrigin());
    }

    @Override
    public int hashCode() {
        return ((Position3D)this.getOrigin()).hashCode();

    }
}
