import exception.MatrixException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MutableMatrixTest {

    @Test
    public void createEmptyMatrix_ok() {
        Matrix mutableMatrix = new MutableMatrix();

        assertArrayEquals(new int[]{0, 0}, mutableMatrix.size());
        assertEquals("Matrix is empty", mutableMatrix.toString());
        assertEquals(0, mutableMatrix.hashCode());
    }

    @Test
    public void createMatrix_ok() {
        MutableMatrix mutableMatrix = new MutableMatrix(2, 2);

        assertArrayEquals(new int[]{2, 2}, mutableMatrix.size());
    }

    @Test
    public void createMatrix_array_ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        MutableMatrix matrix = new MutableMatrix(array);

        assertArrayEquals(array, matrix.toArray());
        assertEquals("1  2  \n" +
                                "3  4  ",
                matrix.toString());
    }

    @Test
    public void createMatrix_matrix_Ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        MutableMatrix matrix = new MutableMatrix(array);

        Matrix matrix1 = new MutableMatrix(matrix);
        assertEquals(matrix, matrix1);
        assertEquals(matrix.hashCode(), matrix1.hashCode());
    }

    @Test
    public void setElement_nullElement_notOk() {
        MutableMatrix mutableMatrix = new MutableMatrix(2, 2);
        assertThrows(MatrixException.class, ()
                -> mutableMatrix.setElement(1, 1, null));
    }

    @Test
    public void setElement_IncorrectIndexes_notOk() {
        MutableMatrix mutableMatrix = new MutableMatrix(2, 2);

        assertThrows(MatrixException.class, () -> {
            mutableMatrix.setElement(-1, 1, new BigDecimal(5));
        });

        System.out.println(1);

        assertThrows(MatrixException.class, () -> {
            mutableMatrix.setElement(1, -1, new BigDecimal(5));
        });

        System.out.println(2);

        assertThrows(MatrixException.class, () -> {
            mutableMatrix.setElement(2, 1, new BigDecimal(5));
        });
    }

    @Test
    public void setElement_correctElement_ok() {
        MutableMatrix mutableMatrix = new MutableMatrix(2, 2);
        BigDecimal element_1_1 = new BigDecimal(5);
        mutableMatrix.setElement(1, 1, element_1_1);
        assertEquals(element_1_1, mutableMatrix.getElement(1, 1));
    }

    @Test
    public void getElement_incorrectIndexes_notOk() {
        MutableMatrix mutableMatrix = new MutableMatrix(2, 2);
        assertThrows(MatrixException.class, () -> {
            mutableMatrix.getElement(2, 1);
        });
    }

    @Test
    public void getElement_correctIndexes_ok() {
        BigDecimal[][] array = new BigDecimal[1][1];
        BigDecimal expected = new BigDecimal(4);
        array[0][0] = expected;
        MutableMatrix mutableMatrix = new MutableMatrix(array);
        assertEquals(expected
                , mutableMatrix.getElement(0, 0));
    }

    @Test
    public void setByRandomElements_ok() {
        Matrix matrix = new MutableMatrix();
        matrix.setByRandomElements(9);
        assertArrayEquals(new int[]{0, 0}, matrix.size());

        MutableMatrix matrix1 = new MutableMatrix(2, 2);
        matrix1.setByRandomElements(9);
        assertArrayEquals(new int[] {2, 2}, matrix1.size());
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (matrix1.toArray()[i][j] == null)
                    fail("Method should set all elements");
            }
        }
    }

    @Test
    public void setRow_incorrectRowIndex_notOk() {
        Matrix matrix = new MutableMatrix(3, 2);

        BigDecimal element1 = new BigDecimal(5);
        BigDecimal element2 = new BigDecimal(7);
        BigDecimal[] decimals = new BigDecimal[]{element1, element2};

        assertThrows(MatrixException.class, () -> {
            matrix.setRow(7, decimals);
        });
    }

    @Test
    public void setRow_incorrectRowLength_notOk() {
        Matrix matrix = new MutableMatrix(3, 2);

        BigDecimal element1 = new BigDecimal(5);
        BigDecimal element2 = new BigDecimal(7);
        BigDecimal element3 = new BigDecimal(9);
        BigDecimal[] decimals = new BigDecimal[]{element1, element2, element3};

        assertThrows(MatrixException.class, () -> {
            matrix.setRow(1, decimals);
        });
    }

    @Test
    public void setRow_correctRow_ok() {
        Matrix matrix = new MutableMatrix(3, 2);

        BigDecimal element1 = new BigDecimal(5);
        BigDecimal element2 = new BigDecimal(7);
        BigDecimal[] decimals = new BigDecimal[]{element1, element2};

        matrix.setRow(2, decimals);
        assertArrayEquals(decimals, matrix.getRow(2));
    }

    @Test
    public void getRow_incorrectIndex_notOk() {
        Matrix matrix = new MutableMatrix(3, 2);

        assertThrows(MatrixException.class, () -> {
            matrix.getRow(10);
        });

        assertThrows(MatrixException.class, () -> {
            matrix.getRow(-1);
        });
    }


    @Test
    public void setColumn_incorrectColumnIndex_notOk() {
        Matrix matrix = new MutableMatrix(3, 2);
        BigDecimal[] decimals = new BigDecimal[2];

        assertThrows(MatrixException.class, () -> {
            matrix.setColumn(7, decimals);
        });
    }

    @Test
    public void setColumn_incorrectColumnLength_notOk() {
        Matrix matrix = new MutableMatrix(3, 2);
        BigDecimal[] decimals = new BigDecimal[4];

        assertThrows(MatrixException.class, () -> {
            matrix.setColumn(1, decimals);
        });
    }

    @Test
    public void setColumn_correctColumn_ok() {
        Matrix matrix = new MutableMatrix(3, 2);

        BigDecimal element1 = new BigDecimal(5);
        BigDecimal element2 = new BigDecimal(7);
        BigDecimal element3 = new BigDecimal(9);
        BigDecimal[] decimals = new BigDecimal[]{element1, element2, element3};

        matrix.setColumn(1, decimals);
        assertArrayEquals(decimals, matrix.getColumn(1));
    }

    @Test
    public void getColumn_incorrectIndex_notOk() {
        Matrix matrix = new MutableMatrix(3, 2);

        assertThrows(MatrixException.class, () -> {
            matrix.getColumn(10);
        });

        assertThrows(MatrixException.class, () -> {
            matrix.getColumn(-1);
        });
    }
    @Test
    public void createRandomColumnMatrix_ok() {
        Matrix matrix = MutableMatrix.crateRandomColumnMatrix(3, 9);
        assertArrayEquals(new int[]{3, 1}, matrix.size());
    }

    @Test
    public void multiplication_correctMatrix_ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        MutableMatrix matrix = new MutableMatrix(array);

        BigDecimal[][] array2 = new BigDecimal[][]{
                {new BigDecimal(7), new BigDecimal(1)},
                {new BigDecimal(1), new BigDecimal(1)}};
        MutableMatrix matrix1 = new MutableMatrix(array2);

        Matrix actualMatrix = matrix.multiplication(matrix1);

        Matrix expectedMatrix = new MutableMatrix(2, 2);
        expectedMatrix.setElement(0, 0, new BigDecimal(9));
        expectedMatrix.setElement(0, 1, new BigDecimal(3));
        expectedMatrix.setElement(1, 0, new BigDecimal(25));
        expectedMatrix.setElement(1, 1, new BigDecimal(7));

        assertEquals(expectedMatrix, actualMatrix);
    }

    @Test
    public void multiplication_nullMatrix_notOk() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        MutableMatrix matrix = new MutableMatrix(array);

        assertThrows(MatrixException.class, () -> {
            matrix.multiplication(null);
        });
    }

    @Test
    public void multiplication_EmptyMatrix_notOk() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        MutableMatrix matrix = new MutableMatrix(array);

        MutableMatrix matrix1 = new MutableMatrix();
        assertThrows(MatrixException.class, () -> {
            matrix.multiplication(matrix1);
        });
    }

    @Test
    public void multiplication_MatrixWithIncorrectSize_notOk() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        MutableMatrix matrix = new MutableMatrix(array);

        MutableMatrix matrix1 = new MutableMatrix(3, 2);
        assertThrows(MatrixException.class, () -> {
            matrix.multiplication(matrix1);
        });
    }

}
