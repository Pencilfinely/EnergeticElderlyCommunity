/*
 * Created by JFormDesigner on Fri Jun 03 22:51:34 CST 2022
 */

package com.eec.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author unknown
 */
public class RoomManagerPanel extends JPanel {

    private static RoomManagerPanel instance = null;

    public static RoomManagerPanel getInstance(){
        if(instance == null){
            instance = new RoomManagerPanel();
        }

        return instance;
    }

    private RoomManagerPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

            //---- label1 ----
            label1.setText("\u623f\u95f4\u7ba1\u7406");
            label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            panel1.add(label1);
        }
        add(panel1, BorderLayout.NORTH);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());
        }
        add(panel2, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
