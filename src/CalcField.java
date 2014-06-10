import javax.swing.*;
import java.awt.*;
import java.util.Locale;

class CalcField extends JTextField {
    private boolean memory;
    private String additionalString;
    private boolean error=false;
    public CalcField() {
        super("0");
        Locale.setDefault(Locale.US);
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
        if ((((super.getText().charAt(0)!='-')&&(super.getText().length() == 16)))||(((super.getText().charAt(0)=='-')&&(super.getText().length() == 17))))
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
        g2d.drawString(additionalString, getWidth() - (g2d.getFontMetrics().stringWidth(additionalString)) - 4, 12);
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
    public void setMainString(String s){
        if (s.equals("ERROR") || s.equals("OVERFLOW")|| s.toUpperCase().contains("INFINITY"))
        {
            error=true;
            setText("ERROR");
            return;
        }
        setText(Calculator.format(s));
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
        setAdditionalString(getAdditionalString().substring(0, getLastOperIndexAdditionalString()));
    }
    public void clearLastOperationAdditionalString(int i) {
        setAdditionalString(getAdditionalString().substring(0, getLastOperIndexAdditionalString()+i));
    }
    int getLastOperIndexAdditionalString() {
        String s = getAdditionalString();
        int len=s.length()-1;
        int i = len;
        for (; i > 0; i--)
            if (((s.charAt(i) == '+') || (s.charAt(i) == '/') || (s.charAt(i) == '*') || (s.charAt(i) == '-'))) {
                if((s.charAt(i-1) == 'e'))
                    continue;
                break;
            }
            else if((i+4<len)&&(s.substring(i,i+4).equals("sqrt")))
            {
                i--;
                break;
            }
            else if((i+8<len)&&(s.substring(i,i+8).equals("reciproc")))
            {
                i--;
                break;
            }
        System.out.print(s.substring(i));
        return i;
    }
    public boolean hasError(){
        return error;
    }
    public void delError(){
        error=false;
    }
    public String getLastOperationAdditionalString() {
        return getAdditionalString().substring(getLastOperIndexAdditionalString(), getAdditionalString().length());
    }
}