import numbers.Corner;
import numbers.Line;
import numbers.Num;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lyuba on 04.10.2016.
 */
public class Model {

    /*
    * the array of the lines
    * */
    private static final List<Num> lines = new ArrayList<>();
    private static final List<Line> diagonals = new ArrayList<>();

    public boolean isOver(Map<String, String> fields) {

        int count = 0;
        for (Map.Entry<String, String > pair : fields.entrySet()) {
            if (pair.getValue().equals("1"))
                break;
            count++;
        }
        if (count == 9)
            return true;

        List<String> line = getFullLine(fields);
        if (line!=null) {
            return true;
        }
        else {
            return false;
        }
    }


    public String getBotStep(Map<String, String> fields, String gamerSign, String botSign) {

        String step = canEnd(fields, botSign);
        if (step!=null){ //bot can win
            return step;
        }

        step = canEnd(fields, gamerSign);
        if (step!=null){ //bot can loose
            return step;
        }

        if (fields.get("5").equals("1")) { // get free center
            return "5";
        }


        if (fields.get("5").equals(botSign)) { //protect diagonal
            int countTotal = 0;
            int countGamer = 0;
            for (Map.Entry<String, String> pair : fields.entrySet()) {
                if (!pair.getValue().equals("1")) {
                    countTotal++;
                    if (pair.getValue().equals(gamerSign)) {
                        countGamer++;
                    }
                }
            }
            if (countTotal == 3 && countGamer == 2) {
                String protectionStep = getDiagonalProtection(fields);
                if (protectionStep!=null) {
                    return getDiagonalProtection(fields);
                }
            }
        }

        String[] cornerNumber = Corner.getInstance().getArrayOfNumbers(); // get free corner
        for (String corner : cornerNumber) {
            if (fields.get(corner).equals("1")) {
                return corner;
            }
        }

        ArrayList<String> emptyFields = new ArrayList<>();
        for (Map.Entry<String, String > pair : fields.entrySet()) {
            if (pair.getValue().equals("1")) {
                emptyFields.add(pair.getKey());
            }
        }
        if (!emptyFields.isEmpty()) {
            Random random = new Random();
            return emptyFields.get(random.nextInt(emptyFields.size()));
        }
        return null;
    }


    /*
    * get the winning line
    * */
    public List<String> getEndList(Map<String, String> fields) {
        List<String> line = getFullLine(fields);
        if (line!=null) {
            return line;
        }
        else {
            return new ArrayList<>();
        }
    }

    private List<String> getFullLine(Map<String, String> fields) {
        List<String> fullLine = new ArrayList<>();
        for (Num num : lines) {
            String[] line = num.getArrayOfNumbers();
            int count = 0;
            for (String number : line) {

                String value = fields.get(number);
                if (!value.equals("1")) {
                    if (fullLine.isEmpty()) {
                        fullLine.add(value);
                    }
                    fullLine.add(number);
                    if (!fullLine.get(0).equals(value)) {
                        break;
                    }
                    count++;
                }

            }
            if (count==3) {
                return fullLine;
            }
            else {
                fullLine.clear();
            }
        }
        return null;
    }

    private String getDiagonalProtection(Map<String, String> fields) {

        for (Line num : diagonals) {
            String[] diagonal = num.getArrayOfNumbers();
            int count = 0;
            for (String number : diagonal) {

                String value = fields.get(number);
                if (!value.equals("1")) {

                    count++;
                }
                if (count==3) {
                    return Integer.toString(Integer.parseInt(num.getZ())-1);
                }

            }


        }
        return null;
    }

    private String canEnd(Map<String, String> fields, String sign) {

        for (Num num : lines) {
            String[] line = num.getArrayOfNumbers();
            int count = 0;
            String step = null;
            for (String number : line) {

                String value = fields.get(number);
                if (sign.equals(value)) {
                    count++;
                }
                else if (value.equals("1")){
                    step = number;
                }

            }
            if (count==2 && step!=null) {
                return step;
            }

        }
        return null;
    }


    static {

        Num line1 = new Line("1", "2", "3");
        lines.add(line1);

        Num line2 = new Line("4", "5", "6");
        lines.add(line2);

        Num line3 = new Line("7", "8", "9");
        lines.add(line3);

        Num line4 = new Line("1", "4", "7");
        lines.add(line4);

        Num line5 = new Line("2", "5", "8");
        lines.add(line5);

        Num line6 = new Line("3", "6", "9");
        lines.add(line6);

        Num line7 = new Line("1", "5", "9");
        lines.add(line7);
        diagonals.add((Line)line7);

        Num line8 = new Line("3", "5", "7");
        lines.add(line8);
        diagonals.add((Line)line8);
    }
}
