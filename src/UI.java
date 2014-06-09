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
    private boolean add = true;
    private String last_oper = "+0";
    private String memory = "0";
    String[][] B_Str = {{"MC", "MR", "MS", "M+", "M-"},
            {"←", "CE", "C", "±", "√"},
            {"7", "8", "9", "/", "%"},
            {"4", "5", "6", "*", "1/x"},
            {"1", "2", "3", "-", "="},
            {"0", "", ".", "+", ""}};
    JButton[][] b;

    public UI() {
        super("Calculator");
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
        if((c=="Enter")||(c=="Equals"))
            c="=";
        else if((c=="Minus"))
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
    public void action(String string) {

        if ((string.length() == 1) && (string.charAt(0) >= '0') && (string.charAt(0) <= '9')) {
            if (!add) {
                cf.clearMainString();
                cf.clearAdditionalString();
                add = true;
            }
            func11 = false;
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
            cf.setMemory(Double.valueOf(cf.getMainString())!=0);
            memory = cf.getMainString();
        } else if (string.equals("M+")) {
            cf.setMemory(true);
            memory = String.valueOf(Double.parseDouble(memory) + Double.parseDouble(cf.getMainString()));
        } else if (string.equals("M-")) {
            cf.setMemory(true);
            memory = String.valueOf(Double.parseDouble(memory) - Double.parseDouble(cf.getMainString()));
        } else if (string.equals("←")) {
            cf.deleteLast();
        } else if (string.charAt(0) == 'C') {
            cf.clearMainString();
            if (string.length() == 1){
                cf.clearAdditionalString();
                last_oper = "+0";
                func = false;
                func11 = false;
                add = true;
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
                cf.setAdditionalString("0+");
            cf.setMainString(String.valueOf(Double.parseDouble(Calculator.calculat(cf.getAdditionalString().substring(0, cf.getAdditionalString().length() - 1))) / 100 * Double.parseDouble(cf.getMainString())));
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
    }

    void calc(String oper) {
        if (!add)
            cf.clearLastOperationAdditionalString();
        String s = oper + "(" + cf.getMainString() + ")";
        cf.appendAdditionalString(s);
        cf.setMainString(Calculator.calculat(s));
        func = true;
        func11 = true;
        add = false;
    }
}