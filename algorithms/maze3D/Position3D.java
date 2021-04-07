package algorithms.maze3D;
import java.util.Objects;

public class Position3D {
    private int DepthIndex;
    private int RowIndex;
    private int ColumnIndex;


    public Position3D(int depthindex, int rowindex, int columnindex) {
        this.DepthIndex = depthindex;
        this.RowIndex = rowindex;
        this.ColumnIndex = columnindex;
    }


    @Override

    public String toString(){
        return "{"+DepthIndex+","+RowIndex+","+ColumnIndex+"}" ;
    }

    public int getDepthIndex() {
        return DepthIndex;
    }

    public void setDepthIndex(int depthIndex) {
        DepthIndex = depthIndex;
    }

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int rowIndex) {
        RowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return ColumnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        ColumnIndex = columnIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position3D that = (Position3D) o;
        return DepthIndex == that.DepthIndex && RowIndex == that.RowIndex && ColumnIndex == that.ColumnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(DepthIndex, RowIndex, ColumnIndex);
    }
}
