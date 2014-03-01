public class Calculator {
    public Calculator() {
        System.out.print("\n"+calculat("0+0.3+0.235/reciproc(147)"));
    }

    public static void main(String[] args) {
        // new UI();
        new Calculator();

    }

    public double calculat(String str){
        str=parse(str);
        return Double.parseDouble(str);
    }
    public String parse(String str){
        String[] oper={"sqrt(","reciproc("};
        for (int j =0;j<oper.length;j++)
        while(true)
        {
            int i=str.indexOf(oper[j]);
            if(i<0)break;
            int ai = getDoubleIndex(str,i+oper[j].length(),false);
            Double a=doMath(Double.parseDouble(str.substring(i+oper[j].length(),ai)),oper[j].charAt(0));
            str=str.substring(0,i)+String.valueOf(a)+str.substring(ai+1,str.length());
        }
        for(int i=0;i<str.length();i++)
        if((str.charAt(i)=='*')||(str.charAt(i)=='/')||(str.charAt(i)=='+')||(str.charAt(i)=='-'))
        {
            int ai = getDoubleIndex(str,i,true);
            int bi = getDoubleIndex(str,i,false);
            Double a = doMath(Double.parseDouble(str.substring(ai,i)),Double.parseDouble(str.substring(i+1,bi)),str.charAt(i));
            System.out.print("\n"+str);
            str=str.substring(0,ai)+String.valueOf(a)+str.substring(bi,str.length());
        }
        return str;
    }
    public int getDoubleIndex(String str, int index, boolean invert){
        int g=1;
        int i=index+1;
        if(invert){
            g=-1;
            i=index-1;
        }
        for(;(i>=0)&&(i<str.length());i+=g)
            if(((str.charAt(i)<'0')||(str.charAt(i)>'9'))&&(str.charAt(i)!='.'))
                break;
        if(!invert)
            return i;
        return i+1;
    }
    public Double doMath(Double first, Double second, char op ){
        switch(op){
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
    public Double doMath(Double first, char op ){
        switch(op){
            case 's':
                return Math.sqrt(first);
            case 'r':
                return 1/first;
            default:
                return 0.0;
        }
    }
}
