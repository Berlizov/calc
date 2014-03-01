import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JFrame implements ActionListener {
    final CalcField cf;
    boolean func = false;

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
        cf.setMemory(true);
        add(cf, c);

        String[][] str = {{"MC", "MR", "MS", "M+", "M-"},
                {"←", "CE", "C", "±", "√"},
                {"7", "8", "9", "/", "%"},
                {"4", "5", "6", "*", "1/x"},
                {"1", "2", "3", "-", "="},
                {"0", "", ",", "+", ""}};
        for (int i = 1; i < 7; i++)
            for (int j = 0; j < 5; j++) {
                if (!str[i - 1][j].equals("")) {
                    c.gridwidth = 1;
                    c.gridheight = 1;
                    if (str[i - 1][j].equals("0"))
                        c.gridwidth = 2;
                    if (str[i - 1][j].equals("="))
                        c.gridheight = 2;


                    c.gridx = j;
                    c.gridy = i;
                    JButton b = new JButton(str[i - 1][j]);
                    b.addActionListener(this);
                    add(b, c);
                }
            }
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if ((button.getText().charAt(0) >= '0') && (button.getText().charAt(0) <= '9')) {
            if (func) {
                cf.clearMainString();
                func = false;
            }
            if (cf.getText().equals("0"))
                cf.setMainString(button.getText());
            else
                cf.appendMainString(button.getText());
        }else if (button.getText().equals(","))
            cf.appendMainString(".");
        else if (button.getText().equals("←")) {
            cf.deleteLast();
        }else if (button.getText().equals("CE"))
            cf.clearMainString();
        else if (button.getText().equals("C")) {
            cf.clearMainString();
            cf.clearAdditionalString();
        }else if (button.getText().equals("±")) {
            String str = cf.getMainString();
            if (str.charAt(0) == '-')
                cf.setMainString(str.substring(1, str.length()));
            else
                cf.setMainString("-" + str);
            func = false;
        }else if (button.getText().equals("=")) {
            func = true;
            cf.appendAdditionalString(cf.getMainString());
            cf.setMainString(Calculator.calculat(cf.getAdditionalString()));
            cf.clearAdditionalString();
        }else {
            func = true;
            cf.appendAdditionalString(cf.getMainString() + button.getText());
        }
    }
}
