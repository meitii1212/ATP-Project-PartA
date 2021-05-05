package algorithms.mazeGenerators;

import java.util.Objects;

public class Position {
    private int row;
    private int column;

    /**
     * this class represents a position in the maze by format of {row,col}
     * @param row  number of the rows in the maze
     * @param column  number of the columns in the maze
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRowIndex() {
        return row;
    }

    public void setRowIndex(int row) {
        this.row = row;
    }

    public int getColumnIndex() {
        return column;
    }

    public void setColumnIndex(int column) {
        this.column = column;
    }

    @Override

    public String toString(){
        return "{"+row+","+column+"}" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

}
