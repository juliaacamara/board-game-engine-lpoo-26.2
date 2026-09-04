import java.util.Objects;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        return this.row == other.row && this.column == other.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.column);
    }

    @Override
    public String toString() {
        return "(" + this.row + ", " + this.column + ")";
    }
}
