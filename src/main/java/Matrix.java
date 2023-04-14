import exception.MatrixException;

import java.math.BigDecimal;

public interface Matrix {
    int getNumOfRows();
    int getNumOfCols();

    Matrix multiplication(Matrix matrix);

    void setByRandomElements(int bound);
    void setElement(int i, int j, BigDecimal element);
    void setRow(int rowIndex, BigDecimal[] row);;
    void setColumn(int colIndex, BigDecimal[] column);

    BigDecimal getElement(int index_row, int index_column);
    BigDecimal[] getRow(int index_row);
    BigDecimal[] getColumn(int index_column);

    BigDecimal[][] toArray();
    String size();
    boolean isEmpty();

    String toString();
    boolean equals(Object o);
    int hashCode();
}
