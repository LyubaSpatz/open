package numbers;

/**
 * Created by Lyuba on 05.10.2016.
 *
 * the lines of the playing field
 */
public class Line implements Num {

    private String x = null;
    private String y = null;
    private String z = null;

    public String[] getArrayOfNumbers() {
        String[] angleArray = {x, y, z};
        return angleArray;
    }

    public Line(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
