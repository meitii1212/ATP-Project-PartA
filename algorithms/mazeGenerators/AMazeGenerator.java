package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

        public long measureAlgorithmTimeMillis (int rows, int columns){
        long first_time = System.currentTimeMillis();
        this.generate(rows, columns);
        long last_time = System.currentTimeMillis();
        return last_time- first_time;
    }

}
