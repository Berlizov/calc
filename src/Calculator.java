import java.util.Locale;

class Calculator {
    public static void main(String[] args) {
        new UI();
      // System.out.print(calculat("0+54+sqrt(56)+47"));
    }
    public static String calculat(String str) {
        if (str.length() == 0)
            return "";
        String[] oper = {"sqrt(", "reciproc("};
        Locale.setDefault(Locale.US);
        for (int j=0;j<oper.length;j++)
            while (true) {
                String anOper=oper[j];
                int i = str.indexOf(anOper);
                if (i < 0) break;

                int ai = getDoubleIndex(str, i + anOper.length() - 1);
                if ((j==0)&&(Double.parseDouble(str.substring(i + anOper.length(), ai)) < 0))
                    return "ERROR";
                if ((j==1)&&(Double.parseDouble(str.substring(i + anOper.length(), ai)) == 0))
                    return "ERROR";
                Double a = doMath(Double.parseDouble(str.substring(i + anOper.length(), ai)), anOper.charAt(0));
                str = str.substring(0, i) + String.format("%.40e",a) + str.substring(ai + 1, str.length());
            }
        int ai = 0;
        for (int i = 1; i < str.length(); i++)
            if (((str.charAt(i) == '*') || (str.charAt(i) == '/') || (str.charAt(i) == '+') || (str.charAt(i) == '-'))&&(str.charAt(i-1)!='e')) {
                System.out.print("\na=" + Double.parseDouble(str.substring(ai, i)));
                int bi = getDoubleIndex(str, i);
                if ((Double.parseDouble(str.substring(i + 1, bi)) == 0) && (str.charAt(i) == '/'))
                    return "ERROR";
                System.out.print("\nb=" + Double.parseDouble(str.substring(i + 1, bi)));
                Double a = doMath(Double.parseDouble(str.substring(ai, i)), Double.parseDouble(str.substring(i + 1, bi)), str.charAt(i));
                if(a==null)
                    return "OVERFLOW";
                System.out.print("\n" + str);
                str = String.format("%.40e", a) + str.substring(bi, str.length());
                System.out.print("\n!" + str);
                i=1;
            }
        return format(str);
    }
    static String format(String s){
        String str=String.format("%e", Double.parseDouble(s));
        s=String.format("%.18f", Double.parseDouble(s));
        int i=s.length();
        if (s.contains("."))
            for(i=s.length()-1;i>s.indexOf(".");i--)
                if((s.charAt(i)!='0')&&(s.charAt(i)!='.'))
                {i++;
                    break;}
        s = String.valueOf(s.substring(0,i));
        int t=16;
        if(s.charAt(0)=='-')
            t++;
        if(s.length()>t)
            s=s.substring(0,t);
        Double d=Math.abs(Double.parseDouble(str)-Double.parseDouble(s));
        if((d>0.001)||(Math.abs(Double.parseDouble(str))<0.001)&&(Double.parseDouble(str)!=0))
            s=str;
        return s;
    }
    private static int getDoubleIndex(String str, int index) {
        int g = 1;
        int i = index + 2;
        boolean t = false;
        for (; (i >= 0) && (i < str.length()); i += g)
            if (((str.charAt(i) < '0') || (str.charAt(i) > '9')) && (str.charAt(i) != '.') && (str.charAt(i) != '-'))
            {
                if(str.charAt(i) == 'e')
                {
                    i+=2*g;
                    continue;
                }
                if((i+g >= 0) && (i+g < str.length())&& (str.charAt(i+g) == 'e'))
                    continue;
                break;
            }
            else if ((str.charAt(i) == '-'))
                if (t)
                    break;
                else
                    t = true;
        return i;
    }
    private static Double doMath(Double first, Double second, char op) {
        double result;
        switch (op) {
            case '+':
                result=first + second;
                if ((first != 0 && second != 0) && result == 0 && !(!first.equals(second) && Math.abs(first) == Math.abs(second)))
                    return null;
                return result;
            case '-':
                result=first - second;
                if (result == 0.0 && !first.equals(second))
                    return null;
                return result;
            case '*':
                result=first * second;
                if (result == 0 && first != 0 && second != 0)
                    return null;
                return result;
            case '/':
                result=first / second;
                if ((first != 0 && second != 0) && result == 0 )
                    return null;
                return result;
            default:
                return 0.0;
        }
    }
    private static Double doMath(Double first, char op) {
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