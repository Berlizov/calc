import javax.swing.*;
import java.awt.*;

public class CalcField extends JTextField{

    boolean memory;
    String additionalString;

    public CalcField() {
        super("0");
        setHorizontalAlignment(JTextField.RIGHT);
        setPreferredSize(new Dimension(100, 50));
        setMinimumSize(getPreferredSize());
        setDisabledTextColor(Color.BLACK);
        setEnabled(false);
        memory = false;
        additionalString="";
        setFont(getFont());
    }
    public void appendMainText(String str){
        setText(this.getText()+str);
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        setFont(new Font(getFont().getFontName(), Font.PLAIN, getHeight()-30));
        super.paint(g);
        g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 12));

        g2d.drawString(additionalString, (int)(getWidth()-(g2d.getFontMetrics().stringWidth(additionalString))-4), 12);
        if(memory) {
            g2d.setColor(Color.red);
            g2d.drawString("M", 12, getHeight() - 12);
        }
    }


    public boolean isMemory() {
        return memory;
    }

    public void setMemory(boolean memory) {
        this.memory = memory;
    }
    public String getAdditionalString() {
        return additionalString;
    }

    public void setAdditionalString(String additionalString) {
        this.additionalString = additionalString;
    }
}
