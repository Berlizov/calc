import javax.swing.*;
import java.awt.*;

public class CalcField extends JTextField {
    private boolean memory;
    private String additionalString;

    public CalcField() {
        super("0");
        setHorizontalAlignment(JTextField.RIGHT);
        setPreferredSize(new Dimension(100, 50));
        setMinimumSize(getPreferredSize());
        setDisabledTextColor(Color.BLACK);
        setEnabled(false);
        memory = false;
        additionalString = "";
        setFont(getFont());
    }

    public void appendMainString(String str) {
        if (super.getText().length() == 16)
            return;
        if (!((this.getText().indexOf(".") > 0) && (str.equals("."))))
            super.setText(this.getText() + str);
    }

    public void appendAdditionalString(String str) {
        setAdditionalString(getAdditionalString() + str);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        setFont(new Font(getFont().getFontName(), Font.PLAIN, getHeight() - 30));
        super.paint(g);
        g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 12));
        g2d.drawString(additionalString, (int) (getWidth() - (g2d.getFontMetrics().stringWidth(additionalString)) - 4), 12);
        if (memory) {
            g2d.setColor(Color.red);
            g2d.drawString("M", 12, getHeight() - 12);
        }
    }

    public void deleteLast() {
        String str = getText();
        if (str.length() < 2)
            clearMainString();
        else if ((str.charAt(0) == '-')&&((str.length() == 2)||(str.length() == 3)&& (str.charAt(1) == '0')))
            clearMainString();
        else
            setText(str.substring(0, str.length() - 1));
        if ((str.length() == 2) && ((str.charAt(0) == '-') && (str.charAt(1) == '0')))
            clearMainString();
    }
    public void setMemory(boolean memory) {
        this.memory = memory;
    }

    public String getMainString() {
        return getText();
    }

    public void setMainString(String s) {
        int i=s.length();
        if (s.indexOf(".") >= 0)
            for(i=s.length()-1;i>s.indexOf(".");i--)
                if((s.charAt(i)!='0')&&(s.charAt(i)!='.'))
                {i++;
                    break;}
        s = String.valueOf(s.substring(0,i));
        if(s.length()>16)
            s=s.substring(0,16);
        setText(s);
    }

    public void clearMainString() {
        setText("0");
    }

    public String getAdditionalString() {
        return additionalString;
    }

    public void setAdditionalString(String additionalString) {
        this.additionalString = additionalString;
    }

    public void clearAdditionalString() {
        setAdditionalString("");
    }

    public void clearLastOperationAdditionalString() {
        setAdditionalString(getAdditionalString().substring(0, getLastOperIndexAdditionalString() ));
    }

    public int getLastOperIndexAdditionalString() {
        String s = getAdditionalString();
        int i = s.length()-1;
        boolean t = false;
        for (; i > 0; i--)
            if ((s.charAt(i) == '+') || (s.charAt(i) == '/') || (s.charAt(i) == '*')) {
                t = false;
                break;
            } else if (s.charAt(i) == '-')
                if (t) {
                    t = false;
                    break;
                } else
                    t = true;
        if (t)
            i ++;
        System.out.print(getAdditionalString().substring(i, getAdditionalString().length()));
        return i;
    }

    public String getLastOperationAdditionalString() {
        return getAdditionalString().substring(getLastOperIndexAdditionalString(), getAdditionalString().length());
    }
}