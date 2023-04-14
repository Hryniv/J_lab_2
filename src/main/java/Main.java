import java.math.BigDecimal;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        BigDecimal[][] array = new BigDecimal[][] {
                {new BigDecimal(1), new BigDecimal(2)},
                {new BigDecimal(3), new BigDecimal(4)}};
        MutableMatrix matrix = new MutableMatrix(array);

        Matrix matrix1 = new MutableMatrix(matrix);
        boolean b = matrix.equals(matrix1);
        System.out.println(matrix);
        BigDecimal[][] decimal = new BigDecimal[0][8];

    }
}
