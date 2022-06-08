/*
 * Created by JFormDesigner on Sat Jun 04 00:29:18 CST 2022
 */

package com.eec.view;

import java.awt.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class CheckInManagerPanel extends JPanel {

    private static CheckInManagerPanel instance = null;

    public static CheckInManagerPanel getInstance(){
        if(instance == null){
            instance = new CheckInManagerPanel();
        }

        return instance;
    }

    private CheckInManagerPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        panel4 = new JPanel();
        label2 = new JLabel();
        textField1 = new JTextField();
        label3 = new JLabel();
        textField2 = new JTextField();
        label4 = new JLabel();
        textField3 = new JTextField();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("\u5165\u4f4f\u7ba1\u7406");
            label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            panel1.add(label1, BorderLayout.WEST);
        }
        add(panel1, BorderLayout.NORTH);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //======== panel3 ========
            {
                panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

                //---- button1 ----
                button1.setText("\u65b0\u589e");
                panel3.add(button1);

                //---- button2 ----
                button2.setText("\u4fee\u6539");
                panel3.add(button2);

                //---- button3 ----
                button3.setText("\u5220\u9664");
                panel3.add(button3);

                //---- button4 ----
                button4.setText("\u67e5\u8be2");
                panel3.add(button4);

                //---- button5 ----
                button5.setText("text");
                panel3.add(button5);
            }
            panel2.add(panel3, BorderLayout.NORTH);

            //======== panel4 ========
            {
                panel4.setLayout(new GridLayout(1, 3));

                //---- label2 ----
                label2.setText("text");
                panel4.add(label2);
                panel4.add(textField1);

                //---- label3 ----
                label3.setText("text");
                panel4.add(label3);
                panel4.add(textField2);

                //---- label4 ----
                label4.setText("text");
                panel4.add(label4);
                panel4.add(textField3);
            }
            panel2.add(panel4, BorderLayout.SOUTH);
        }
        add(panel2, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    private JPanel panel3;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JPanel panel4;
    private JLabel label2;
    private JTextField textField1;
    private JLabel label3;
    private JTextField textField2;
    private JLabel label4;
    private JTextField textField3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
