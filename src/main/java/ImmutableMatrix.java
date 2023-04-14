import exception.MatrixException;
import exception.UnsupportedMatrixOperationException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public final class ImmutableMatrix implements Matrix {
    private final int numOfRows;
    private final int numOfCols;
    private final BigDecimal[][] array;

    public ImmutableMatrix() {
        this(0, 0);
    }

    public ImmutableMatrix(int rows, int cols) {
        if (rows < 0 || cols < 0) {
            throw new MatrixException("Invalid index");
        }
        this.numOfRows = rows == 0 || cols == 0 ? 0 : rows;
        this.numOfCols = this.numOfRows == 0 ? 0 : cols;
        this.array = new BigDecimal[numOfRows][numOfCols];
    }

    public ImmutableMatrix(int rows, int cols, int bound) {
        if (rows < 0 || cols < 0) {
            throw new MatrixException("Invalid index");
        }
        this.numOfRows = rows == 0 || cols == 0 ? 0 : rows;
        this.numOfCols = this.numOfRows == 0 ? 0 : cols;

        BigDecimal[][] array = new BigDecimal[numOfRows][numOfCols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = new BigDecimal(bound).multiply(new BigDecimal(new Random().nextInt()));
            }
        }
        this.array = array;
    }

    public ImmutableMatrix(BigDecimal[][] array) {
        if (array == null) {
            throw new MatrixException("Can't copy null array");
        }
        this.numOfRows = array.length == 0 || array[0].length == 0 ? 0 : array.length;
        this.numOfCols = numOfRows == 0 ? 0 : array[0].length;
        this.array = null; //deep2DArrayCopy(array, numOfRows, numOfCols);
    }

    public ImmutableMatrix(Matrix matrix) {
        if (matrix == null) {
            throw new MatrixException("Can't copy null matrix");
        }
        this.numOfRows = matrix.getNumOfRows();
        this.numOfCols = matrix.getNumOfCols();
        this.array = null; //deep2DArrayCopy(matrix.toArray(), numOfRows, numOfCols);
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
    public void setByRandomElements(int bound) {
        throw new UnsupportedMatrixOperationException("Can't modify immutable.");
    }

    @Override
    public Matrix multiplication(Matrix matrix) {
        return null;
    }

    @Override
    public void setElement(int i, int j, BigDecimal element) {
        throw new UnsupportedMatrixOperationException("Can't modify immutable.");
    }

    @Override
    public BigDecimal getElement(int index_row, int index_column) {
        return new BigDecimal(array[index_row][index_column].toString());
    }

    @Override
    public void setRow(int rowIndex, BigDecimal[] row) {
        throw new UnsupportedMatrixOperationException("Can't modify immutable.");
    }

    private boolean isIndexInvalid(int index, int bound) {
        return index < 0 || index >= bound;
    }

    private BigDecimal[] deepArrayCopy(BigDecimal[] array) {
        BigDecimal[] decimals = new BigDecimal[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                decimals[i] = null;
                continue;
            }
            decimals[i] = new BigDecimal(array[i].toString());
        }
        return decimals;
    }


    @Override
    public BigDecimal[] getRow(int index_row) {
        if (isEmpty()) {
            throw new MatrixException("Can't get row from empty matrix");
        }
        if (isIndexInvalid(index_row, numOfRows)) {
            throw new MatrixException("Invalid index: max index: " + (numOfRows - 1)
                                                        + "input index: " + index_row);
        }
        return deepArrayCopy(array[index_row]);
    }

    @Override
    public void setColumn(int colIndex, BigDecimal[] column) {
        throw new UnsupportedMatrixOperationException("Can't modify immutable.");
    }

    @Override
    public BigDecimal[] getColumn(int index_column) {
        if (isEmpty()) {
            throw new RuntimeException("Can't get column from empty matrix");
        }
        if (isIndexInvalid(index_column, numOfCols)) {
            throw new MatrixException("Invalid index: Column max index = " + (numOfCols - 1)
                    + ", inputted index = " + index_column);
        }
        BigDecimal[] column = new BigDecimal[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            if (array[i][index_column] == null) {
                column[i] = null;
                continue;
            }
            column[i] = new BigDecimal(array[i][index_column].toString());
        }
        return column;
    }

    private BigDecimal[][] deep2DArrayCopy(BigDecimal[][] array, int rows, int cols) {
        BigDecimal[][] decimals = new BigDecimal[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                decimals[i][j] = array[i][j] == null ? null :
                        new BigDecimal(array[i][j].toString());
            }
        }
        return decimals;
    }


    @Override
    public BigDecimal[][] toArray() {
        return deep2DArrayCopy(array, numOfRows, numOfCols);
    }

    @Override
    public String size() {
        return String.format("%sx%s", numOfRows, numOfCols);
    }

    @Override
    public boolean isEmpty() {
        return numOfCols == 0 || numOfRows == 0;
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
    public String toString() {
        return "ImmutableMatrix{" +
                "numOfRows=" + numOfRows +
                ", numOfCols=" + numOfCols +
                ", array=" + Arrays.toString(array) +
                '}';
    }
}
