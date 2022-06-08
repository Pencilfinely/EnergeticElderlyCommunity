/*
 * Created by JFormDesigner on Mon Jun 06 15:25:55 CST 2022
 */

package com.eec.view;

import java.awt.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class VoidPanel extends JPanel {

    private static VoidPanel instance = null;

    public static VoidPanel getInstance(){
        if(instance == null){
            instance = new VoidPanel();
        }

        return instance;
    }

    private VoidPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

        //======== this ========
        setLayout(new BorderLayout());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
