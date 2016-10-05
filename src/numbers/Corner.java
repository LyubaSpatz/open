package numbers;

/**
 * Created by Lyuba on 05.10.2016.
 *
 * the corners of the playing field
 */
public class Corner implements Num {
    private static Corner ourInstance = new Corner();
    private String a = null;
    private String b = null;
    private String c = null;
    private String d = null;

    public static Corner getInstance() {
        return ourInstance;
    }

    private Corner() {
        this.a = "1";
        this.b = "3";
        this.c = "7";
        this.d = "9";
    }


    public String[] getArrayOfNumbers() {
        String[] angleArray = {a, b, c, d};
        return angleArray;
    }
}
