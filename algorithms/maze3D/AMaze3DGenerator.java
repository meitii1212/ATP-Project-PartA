package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    public long measureAlgorithmTimeMillis(int depth, int row, int column){

        long first_time = System.currentTimeMillis();
        this.generate(depth,row,column);
        long last_time = System.currentTimeMillis();
        return last_time- first_time;
    }
}
