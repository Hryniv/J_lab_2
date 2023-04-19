import exception.UnsupportedMatrixOperationException;

import java.math.BigDecimal;
import java.util.Objects;

public final class ImmutableMatrix extends MutableMatrix implements Matrix {
    private final Matrix matrix;

    public ImmutableMatrix(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException();
        }
        this.matrix = matrix;
    }

    public ImmutableMatrix() {
        this(new MutableMatrix(0, 0));
    }

    public ImmutableMatrix(int rows, int cols) {
        this(new MutableMatrix(rows, cols));
    }

    public ImmutableMatrix(BigDecimal[][] array) {
        this(new MutableMatrix(array));
    }

    @Override
    public int getNumOfRows() {
        return matrix.getNumOfRows();
    }

    @Override
    public int getNumOfCols() {
        return matrix.getNumOfCols();
    }

    @Override
    public Matrix multiplication(Matrix matrix) {
        Matrix matrix1 = this.matrix.multiplication(matrix);
        BigDecimal[][] ar = deepArrayCopy(matrix1.toArray());
        return new ImmutableMatrix(ar);
    }

    @Override
    public void setByRandomElements(int bound) {
        throw new UnsupportedMatrixOperationException("");
    }

    @Override
    public void setElement(int i, int j, BigDecimal element) {
        throw new UnsupportedMatrixOperationException("");
    }

    @Override
    public void setRow(int rowIndex, BigDecimal[] row) {
        throw new UnsupportedMatrixOperationException("");

    }

    @Override
    public void setColumn(int colIndex, BigDecimal[] column) {
        throw new UnsupportedMatrixOperationException("");
    }

    @Override
    public BigDecimal getElement(int index_row, int index_column) {
        return new BigDecimal(matrix.getElement(index_row, index_column).toString());
    }

    @Override
    public BigDecimal[] getRow(int index_row) {
        return deepArrayCopy(matrix.getRow(index_row));
    }

    @Override
    public BigDecimal[] getColumn(int index_column) {
        return deepArrayCopy(matrix.getColumn(index_column));
    }

    @Override
    public BigDecimal[][] toArray() {
        return deepArrayCopy(matrix.toArray());
    }

    @Override
    public String size() {
        return matrix.size();
    }

    @Override
    public boolean isEmpty() {
        return matrix.isEmpty();
    }

    private BigDecimal[][] deepArrayCopy(BigDecimal[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        BigDecimal[][] decimals = new BigDecimal[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                decimals[i][j] = array[i][j] == null ? null :
                        new BigDecimal(array[i][j].toString());
            }
        }
        return decimals;
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
    public boolean equals(Object o) {
        return matrix.equals(o);
    }

    @Override
    public int hashCode() {
        return matrix.hashCode();
    }
}
