import exception.UnsupportedMatrixOperationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ImmutableMatrixTest {
    @Test
    public void createEmptyMatrix_ok() {
        Matrix matrix = new ImmutableMatrix();

        assertArrayEquals(new int[]{0, 0}, matrix.size());
        assertEquals("Matrix is empty", matrix.toString());
        assertEquals(0, matrix.hashCode());
        assertTrue(matrix.isEmpty());
    }

    @Test
    public void createMatrix_nullMatrix_notOk() {
        Matrix matrix = null;
        assertThrows(NullPointerException.class, () -> {
            new ImmutableMatrix(matrix);
        });
    }

    @Test
    public void createMatrix_ok() {
        ImmutableMatrix matrix = new ImmutableMatrix(2, 2);

        assertArrayEquals(new int[]{2, 2}, matrix.size());
    }

    @Test
    public void createMatrix_array_ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        ImmutableMatrix matrix = new ImmutableMatrix(array);

        assertArrayEquals(array, matrix.toArray());
        assertEquals("1  2  \n" +
                        "3  4  ",
                matrix.toString());
    }
    @Test
    public void createMatrix_matrix_ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        ImmutableMatrix matrix = new ImmutableMatrix(array);

        ImmutableMatrix matrix1 = new ImmutableMatrix(matrix);
        assertEquals(matrix, matrix1);
        assertEquals(matrix.hashCode(), matrix1.hashCode());
    }

    @Test
    public void setByRandomElement() {
        ImmutableMatrix matrix = new ImmutableMatrix(2, 2);
        assertThrows(UnsupportedMatrixOperationException.class, ()
                -> matrix.setByRandomElements(9));
    }

    @Test
    public void setElement_notOk() {
        ImmutableMatrix matrix = new ImmutableMatrix(2, 2);
        assertThrows(UnsupportedMatrixOperationException.class, ()
                -> matrix.setElement(1, 1, new BigDecimal(10)));
    }

    @Test
    public void getElement() {
        BigDecimal[][] array = new BigDecimal[1][1];
        BigDecimal expected = new BigDecimal(4);
        array[0][0] = expected;
        ImmutableMatrix matrix = new ImmutableMatrix(array);
        BigDecimal actual = matrix.getElement(0,0);
        assertEquals(expected, actual);

        actual = new BigDecimal(5);
        array[0][0] = actual;
        assertNotEquals(actual, matrix.getElement(0,0));

    }

    @Test
    public void setRow_notOk() {
        Matrix matrix = new ImmutableMatrix(3, 2);

        BigDecimal element1 = new BigDecimal(5);
        BigDecimal element2 = new BigDecimal(7);
        BigDecimal[] decimals = new BigDecimal[]{element1, element2};

        assertThrows(UnsupportedMatrixOperationException.class, () -> {
            matrix.setRow(1, decimals);
        });
    }

    @Test
    public void getRow_ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        ImmutableMatrix matrix = new ImmutableMatrix(array);

        BigDecimal[] expected = array[0];
        BigDecimal[] actual = matrix.getRow(0);
        assertArrayEquals(expected, actual);

        actual[0] = new BigDecimal(5);
        assertFalse(Arrays.equals(actual, matrix.getRow(0)));
    }

    @Test
    public void setColumn_notOk() {
        ImmutableMatrix matrix = new ImmutableMatrix(3, 2);

        BigDecimal element1 = new BigDecimal(5);
        BigDecimal element2 = new BigDecimal(7);
        BigDecimal element3 = new BigDecimal(9);
        BigDecimal[] decimals = new BigDecimal[]{element1, element2, element3};

        assertThrows(UnsupportedMatrixOperationException.class, () -> {
            matrix.setColumn(1, decimals);
        });
    }

    @Test
    public void getColumn_ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        ImmutableMatrix matrix = new ImmutableMatrix(array);

        BigDecimal[] expected = new BigDecimal[] {array[0][0], array[1][0]};
        BigDecimal[] actual = matrix.getColumn(0);
        assertArrayEquals(expected, actual);

        actual[0] = new BigDecimal(5);

        assertFalse(Arrays.equals(actual, matrix.getColumn(0)));
        assertArrayEquals(expected, matrix.getColumn(0));
    }

    @Test
    public void multiplication_ok() {
        BigDecimal[][] array = new BigDecimal[][]{
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        ImmutableMatrix matrix = new ImmutableMatrix(array);

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

        //зміна елементу у мутабельній матриці
        array2[0][0] = new BigDecimal(0);
        Matrix actualMatrix2 = matrix.multiplication(matrix1);

        assertNotEquals(expectedMatrix, actualMatrix2);
    }

}