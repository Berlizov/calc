import sun.org.mozilla.javascript.internal.ast.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class UI extends JFrame implements ActionListener, KeyListener {
    private final CalcField cf;
    private boolean func = false;
    private boolean func11 = false;
    private boolean func1234 = false;
    private boolean add = true;
    private String last_oper = "+0";
    private String memory = "0";
    private final String[][] B_Str = {{"MC", "MR", "MS", "M+", "M-"},
            {"←", "CE", "C", "±", "√"},
            {"7", "8", "9", "/", "%"},
            {"4", "5", "6", "*", "1/x"},
            {"1", "2", "3", "-", "="},
            {"0", "", ".", "+", ""}};
    private String lastValue="0";
    private boolean block=false;
    public UI() {
        super("Calculator");
        System.out.print(String.format("%.20f",(0.3+0.235)/147));
        setMinimumSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        cf = new CalcField();
        add(cf, c);
        JButton[][] b=new JButton[10][10];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++) {
                if (!B_Str [i][j].equals("")) {
                    c.gridwidth = 1;
                    c.gridheight = 1;
                    if (B_Str [i][j].equals("0"))
                        c.gridwidth = 2;
                    if (B_Str [i][j].equals("="))
                        c.gridheight = 2;
                    c.gridx = j;
                    c.gridy = i+1;
                    b[i][j] = new JButton(B_Str[i][j]);
                    b[i][j].addActionListener(this);
                    add(b[i][j], c);
                    b[i][j].addKeyListener(this);
                }
            }
        setVisible(true);
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
        String c=KeyEvent.getKeyText(e.getKeyCode());
        System.out.print(c);
        if((c.equals("Enter"))||(c.equals("Equals")))
            c="=";
        else if((c.equals("Minus")))
            c="-";
        else
            c=String.format("%c",c.charAt(c.length()-1));
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++) {
                if((!B_Str [i][j].equals(""))&&(B_Str[i][j].equals(c)))
                {
                    System.out.print(c);
                    action(B_Str[i][j]);
                }
            }
    }
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        action(((JButton) e.getSource()).getText());
    }
    void action(String string) {
        if(!cf.hasError())
        {
        if ((string.length() == 1) && (string.charAt(0) >= '0') && (string.charAt(0) <= '9')) {
            if (!add||func1234) {
                cf.clearMainString();
                cf.clearAdditionalString();
                add = true;
            }
            func11 = false;
            func1234=false;
            if (func) {
                cf.clearMainString();
                func = false;
            }
            if (cf.getText().equals("0"))
                cf.setMainString(string);
            else
                cf.appendMainString(string);
        } else if (string.equals(".")) {
            if (func) {
                cf.clearMainString();
                func = false;
            }
            cf.appendMainString(".");
        } else if (string.equals("MC")) {
            cf.setMemory(false);
            memory = "0";
        } else if (string.equals("MR")) {
            cf.setMainString(memory);
        } else if (string.equals("MS")) {
            memory = cf.getMainString();
            checkMem();
        } else if (string.equals("M+")) {
            memory = String.valueOf(Double.parseDouble(memory) + Double.parseDouble(cf.getMainString()));
            checkMem();
        } else if (string.equals("M-")) {
            memory = String.valueOf(Double.parseDouble(memory) - Double.parseDouble(cf.getMainString()));
            checkMem();
        } else if (string.equals("←")) {
            if((!cf.getMainString().toUpperCase().contains("E"))&&(!func))
                cf.deleteLast();
        } else if (string.charAt(0) == 'C') {
            cf.clearMainString();
            cf.delError();
            if (string.length() == 1){
                cf.clearAdditionalString();
                last_oper = "+0";
                func = false;
                func11 = false;
                add = true;
                lastValue="0";
            }
        } else if (string.equals("±")) {
            String str = cf.getMainString();
            if (str.charAt(0) == '-')
                cf.setText(str.substring(1, str.length()));
            else if ((str.length()>1)||(Double.parseDouble(str) != 0))
                cf.setText("-" + str);
            func = false;
        } else if (string.equals("1/x")) {
            calc("reciproc");
        } else if (string.equals("%")) {
            String tstr=cf.getAdditionalString();
            if(tstr.length()==0)
            {
                if(lastValue.equals("0"))
                {
                    cf.setAdditionalString("0+");
                    cf.clearMainString();
                }
                else
                    cf.setMainString((Calculator.calculat(lastValue+"/100*"+Double.parseDouble(cf.getMainString()))));
            }
            else
            {
                int tttttt=1;
                if(cf.getAdditionalString().charAt( cf.getAdditionalString().length()-1)==')')
                    tttttt=0;
                cf.setMainString(Calculator.calculat(cf.getAdditionalString().substring(0, cf.getAdditionalString().length()-tttttt) + "/100*" + Double.parseDouble(cf.getMainString())));
            }
        } else if (string.equals("√")) {
            calc("sqrt");
        } else if (string.equals("=")) {
            if (add)
                cf.appendAdditionalString(cf.getMainString());
            add = true;
            func = true;
            func11 = true;
            if (!cf.getAdditionalString().equals(Calculator.calculat(cf.getAdditionalString())))
                last_oper = cf.getLastOperationAdditionalString();
            else
                cf.setAdditionalString(cf.getMainString() + last_oper);
            cf.setMainString(Calculator.calculat(cf.getAdditionalString()));
            lastValue=cf.getMainString();
            cf.clearAdditionalString();
        } else {
            if (add)
                cf.appendAdditionalString(cf.getMainString());
            add = true;
            if ((!func11)&&(func)) {
                cf.clearLastOperationAdditionalString();
                cf.appendAdditionalString(string);
            } else {
                cf.appendAdditionalString(string);
                cf.setMainString(Calculator.calculat(cf.getAdditionalString().substring(0, cf.getAdditionalString().length() - 1)));
            }
            func11=false;
            func = true;
        }
            func1234=false;
        }
        else{
            if (string.charAt(0) == 'C') {
                cf.clearMainString();
                cf.delError();
                last_oper = "+0";
                    cf.clearAdditionalString();
                    func = false;
                    func11 = false;
                    add = true;
                    lastValue="0";
            }
        }
    }
    void checkMem(){
        func1234=true;
        if(Double.parseDouble(memory)==0)
            cf.setMemory(false);
        else
            cf.setMemory(true);
    }
    void calc(String oper) {
        int i=1;
        String string=cf.getAdditionalString();
        int t=0;
        for(int j=0;j<string.length();j++)
        {
            if(!string.substring(j).contains(oper))
                break;
            j+=string.substring(j).indexOf(oper);
            t++;
        }
        if((string.length()>0)&&(string.charAt(0)==oper.charAt(0))&&(t<2))
            i=0;
        if (!add)
            cf.clearLastOperationAdditionalString(i);
        String s = oper + "(" + cf.getMainString() + ")";
        cf.appendAdditionalString(s);
        cf.setMainString(Calculator.calculat(s));
        func = true;
        func11 = true;
        add = false;
    }
}