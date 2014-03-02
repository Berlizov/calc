import java.util.Formatter;
import java.util.Locale;

public class Calculator {
    public static void main(String[] args) {
        new UI();  // new Calculator();
        //System.out.print("****" + calculat("0+5+5+5+5+5+5+5"));
    }

    public static String calculat(String str) {
        if (str.length() == 0)
            str = "0";
        String[] oper = {"sqrt(", "reciproc("};
        for (int j = 0; j < oper.length; j++)
            while (true) {
                int i = str.indexOf(oper[j]);
                if (i < 0) break;
                int ai = getDoubleIndex(str, i + oper[j].length() - 1, false);
                Double a = doMath(Double.parseDouble(str.substring(i + oper[j].length(), ai)), oper[j].charAt(0));
                str = str.substring(0, i) + String.valueOf(a) + str.substring(ai + 1, str.length());
            }
        for (int i = 1; i < str.length(); i++)
            if ((str.charAt(i) == '*') || (str.charAt(i) == '/') || (str.charAt(i) == '+') || (str.charAt(i) == '-')) {
                int ai = getDoubleIndex(str, i, true);
                System.out.print("\na=" + Double.parseDouble(str.substring(ai, i)));
                int bi = getDoubleIndex(str, i, false);
                System.out.print("\nb=" + Double.parseDouble(str.substring(i + 1, bi)));
                Double a = doMath(Double.parseDouble(str.substring(ai, i)), Double.parseDouble(str.substring(i + 1, bi)), str.charAt(i));
                System.out.print("\n" + str);
                str = str.substring(0, ai) +  new Formatter(Locale.US).format("%.16f",a) + str.substring(bi, str.length());
                System.out.print("\n!" + str);
                i=1;
            }
        return str;
    }

    public static int getDoubleIndex(String str, int index, boolean invert) {
        int g = 1;
        int i = index + 2;
        boolean t = false;
        if (invert) {
            g = -1;
            i = index - 1;
        }
        for (; (i >= 0) && (i < str.length()); i += g)
            if (((str.charAt(i) < '0') || (str.charAt(i) > '9')) && (str.charAt(i) != '.') && (str.charAt(i) != '-'))
                break;
            else if ((str.charAt(i) == '-'))
                if (t)
                    break;
                else
                    t = true;
        if (!invert)
            return i;
        return i + 1;
    }

    public static Double doMath(Double first, Double second, char op) {
        switch (op) {
            case '+':
                return first + second;
            case '-':
                return first - second;
            case '*':
                return first * second;
            case '/':
                return first / second;
            default:
                return 0.0;
        }
    }

    public static Double doMath(Double first, char op) {
        switch (op) {
            case 's':
                return Math.sqrt(first);
            case 'r':
                return 1 / first;
            default:
                return 0.0;
        }
    }
}