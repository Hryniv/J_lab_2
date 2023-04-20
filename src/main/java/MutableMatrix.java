import exception.MatrixException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

public class MutableMatrix implements Matrix {
    private int numOfRows;
    private int numOfCols;
    private BigDecimal[][] array;

    MutableMatrix() {
        this(0, 0);
    }

    MutableMatrix(int rows, int cols) {
        if (rows < 0 || cols < 0) {
            throw new MatrixException("Invalid index");
        }
        this.numOfRows = rows == 0 || cols == 0 ? 0 : rows;
        this.numOfCols = numOfRows == 0 ? 0 : cols;
        this.array = new BigDecimal[rows][cols];
    }

    MutableMatrix(BigDecimal[][] array) {
        if (array == null) {
            throw new MatrixException("Can't copy null matrix");
        }
        this.numOfRows = array.length == 0 || array[0].length == 0 ? 0 : array.length;
        this.numOfCols = numOfRows == 0 ? 0 : array[0].length;
        this.array = array;
    }

    MutableMatrix(Matrix matrix) {
        this(matrix == null ? null : matrix.toArray());
    }

    @Override
    public int getNumOfRows() {
        return numOfRows;
    }

    @Override
    public int getNumOfCols() {
        return numOfCols;
    }

    @Override
    public int[] size() {
        return new int[] {numOfRows, numOfCols};
    }

    @Override
    public BigDecimal[][] toArray() {
        return array;
    }

    private boolean isIndexInvalid(int index, int bound) {
        return index < 0 || index >= bound;
    }

    @Override
    public void setByRandomElements(int bound) {
        for(int i = 0; i < numOfRows; i++)
        {
            for (int j = 0; j < numOfCols; j++)
            {
                this.array[i][j] = BigDecimal.valueOf((new Random().nextInt(bound)));
            }
        }
    }

    @Override
    public void setElement(int row, int col, BigDecimal element) {
        if (element == null) {
            throw new MatrixException("Element can not be null");
        }
        if (isEmpty()) {
            throw new RuntimeException("Can't set element in empty matrix");
        }
        if (isIndexInvalid(row, numOfRows)) {
            throw new MatrixException("Invalid index. Number of rows in matrix = " + numOfRows
            + ", Row index of element = " + row);
        }
        if (isIndexInvalid(col, numOfCols)) {
            throw new MatrixException("Invalid index. Number of columns in matrix = " + numOfCols
                    + ", Column index of element = " + col);
        }
        array[row][col] = element;
    }

    @Override
    public BigDecimal getElement(int index_row, int index_column) {
        if (isEmpty()) {
            throw new MatrixException("Can't get element from empty matrix");
        }
        if (isIndexInvalid(index_row, numOfRows) || isIndexInvalid(index_column, numOfCols)) {
            throw new MatrixException("Invalid index. "
                    + "Row: max index = " + (numOfRows - 1) + ", input index = " + index_row
                    + "Column: max index = " + (numOfCols - 1) + ", input index = " + index_column);
        }
        return array[index_row][index_column];
    }

    @Override
    public void setRow(int index, BigDecimal[] row) {
        if (row == null) {
            throw new MatrixException("Inputted row can not be null");
        }
        if (isEmpty()) {
            throw new RuntimeException("Can't set row in empty matrix");
        }
        if (isIndexInvalid(index, numOfRows)) {
            throw new MatrixException("Invalid index");
        }
        if (numOfCols != row.length) {
            throw new MatrixException("Incorrect length of row to set. "
                    + "Length of matrix row = " + numOfCols
                    + ", length of inputted row = " + row.length);
        }
        for(int j = 0; j < numOfCols; j++) {
            this.setElement(index, j, row[j]);
        }
    }

    @Override
    public BigDecimal[] getRow(int index_row) {
        if (isEmpty()) {
            throw new RuntimeException("Can't get row from empty matrix");
        }
        if (isIndexInvalid(index_row, numOfRows)) {
            throw new MatrixException("Invalid Index");
        }

        return array[index_row];
    }

    @Override
    public void setColumn(int colIndex, BigDecimal[] column) {
        if (column == null) {
            throw new MatrixException("Column can not be null");
        }
        if (isEmpty()) {
            throw new RuntimeException("Can't set column in empty matrix");
        }
        if(isIndexInvalid(colIndex, numOfCols))   {
            throw new MatrixException("Invalid index");
        }
        if (numOfRows != column.length) {
            throw new MatrixException("Incorrect length of column to set. "
                    + "Length of matrix column = " + numOfRows
                    + ", length of inputted column = " + column.length);
        }
        for (int i = 0; i < numOfRows; i++) {
            setElement(i, colIndex, column[i]);
        }
    }

    @Override
    public BigDecimal[] getColumn(int index_column) {
        if (isEmpty()) {
            throw new RuntimeException("Can't get column from empty matrix");
        }
        if (isIndexInvalid(index_column, numOfCols)) {
            throw new MatrixException("Invalid Index. Column max index = " + (numOfCols - 1)
            + ", inputted index = " + index_column);
        }
        BigDecimal[] column = new BigDecimal[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            column[i] = array[i][index_column];
        }
        return column;
    }

    @Override
    public int hashCode() {
        if (isEmpty())
        {
            return 0;
        }
        int result = 1;
        for (BigDecimal[] row : array)
        {
            for (BigDecimal element : row)
            {
                result = 31 * result + element.hashCode();
            }
        }
        result = 31 * result + numOfRows;
        result = 31 * result + numOfCols;

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Matrix)) {
            return false;
        }
        Matrix other = (Matrix) o;
        if (numOfRows != other.getNumOfRows() || numOfCols != other.getNumOfCols()) {
            return false;
        }
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                if (!Objects.equals(array[i][j], other.toArray()[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static MutableMatrix crateRandomColumnMatrix(int numOfRows,  int bound) {
        MutableMatrix matrix = new MutableMatrix(numOfRows, 1);
        matrix.setByRandomElements(bound);
        return matrix;
    }

    @Override
    public boolean isEmpty() {
        return numOfCols == 0 || numOfRows == 0;
    }

    private boolean isArrayHaveNullElements(BigDecimal[][] array) {
        for (BigDecimal[] row: array) {
            for (BigDecimal element: row) {
                if (element == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Matrix multiplication(Matrix matrix) {
        if (matrix == null) {
            throw new MatrixException("Can't multiply null matrix");
        }

        if (isEmpty() || matrix.isEmpty()) {
            throw new MatrixException("Can't multiply empty matrix.");
        }

        if (numOfCols != matrix.getNumOfRows()) {
            throw new MatrixException("Incorrect dimension. First matrix: num of columns = " + numOfCols
            + ". Second matrix: num of rows = " + matrix.getNumOfRows());
        }

        if (isArrayHaveNullElements(array) || isArrayHaveNullElements(matrix.toArray())) {
            throw new MatrixException("Matrix has null element");
        }

        BigDecimal[][] secondArray = matrix.toArray();
        BigDecimal[][] result = new BigDecimal[numOfRows][matrix.getNumOfCols()];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < matrix.getNumOfCols(); j++) {
                result[i][j] = BigDecimal.ZERO;
                for (int c = 0; c < numOfCols; c++) {
                    result[i][j] = result[i][j].add(array[i][c].multiply(secondArray[c][j]));
                }
            }
        }
        return new MutableMatrix(result);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Matrix is empty";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numOfRows; i++) {
            builder.append("\n");
            for (int j = 0; j < numOfCols; j++) {
                builder.append(array[i][j]).append("  ");
            }
        }
        return builder.substring(1).toString();
    }
}


