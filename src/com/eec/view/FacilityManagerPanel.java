/*
 * Created by JFormDesigner on Sun Jun 05 16:11:27 CST 2022
 */

package com.eec.view;

import java.awt.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class FacilityManagerPanel extends JPanel {

    private static FacilityManagerPanel instance = null;

    public static FacilityManagerPanel getInstance(){
        if(instance == null){
            instance = new FacilityManagerPanel();
        }

        return instance;
    }

    private FacilityManagerPanel() {
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
            label1.setText("\u573a\u9986\u8bbe\u65bd\u7ba1\u7406");
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
