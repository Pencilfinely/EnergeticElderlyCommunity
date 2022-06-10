/*
 * Created by JFormDesigner on Wed Jun 08 22:23:40 CST 2022
 */

package com.eec.view;

import java.awt.event.*;
import com.eec.dao.BusDao;
import com.eec.dao.impl.BusDaoImpl;
import com.eec.entity.Bus;
import com.eec.utils.TimeHelper;
import com.eec.utils.Tools;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class SetEndBookingTimeDialog extends JDialog {

    private static SetEndBookingTimeDialog instance = null;
    private Bus bus;

    public static SetEndBookingTimeDialog getInstance(Bus bus){
        if(instance == null){
            instance = new SetEndBookingTimeDialog(null);
        }
        instance.setBus(bus);
        instance.reset();

        return instance;
    }

    public void setBus(Bus bus){
        this.bus = bus;
    }

    public void reset(){
        this.txtEndBookingTime.setText(this.bus.getEndBookingTime());
    }

    private SetEndBookingTimeDialog(Window owner) {
        super(owner);
        initComponents();
    }

    private void ok(ActionEvent e) {
        try {
            TimeHelper.timeEnough(this.txtEndBookingTime.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this,"格式不正确！");
            return;
        }

        BusDao busDao = BusDaoImpl.getInstance();

        try {
            List<Bus> list = busDao.getAll();
            for (Bus b : list) {
                if(b.getBusCode().equals(this.bus.getBusCode())){
                    b.setEndBookingTime(this.txtEndBookingTime.getText());
                    break;
                }
            }
            busDao.setAll(list);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        BusManagerPanel.getInstance().resetTable();
        this.setVisible(false);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        label2 = new JLabel();
        txtEndBookingTime = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

                //======== panel1 ========
                {
                    panel1.setLayout(new BorderLayout());

                    //---- label1 ----
                    label1.setText("\u8bbe\u7f6e\u622a\u6b62\u65f6\u95f4");
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                    panel1.add(label1, BorderLayout.NORTH);

                    //======== panel2 ========
                    {
                        panel2.setLayout(new BorderLayout());

                        //---- label2 ----
                        label2.setText("\u683c\u5f0f\u4f8b\uff1a11:50 09:01");
                        label2.setHorizontalAlignment(SwingConstants.CENTER);
                        panel2.add(label2, BorderLayout.NORTH);
                        panel2.add(txtEndBookingTime, BorderLayout.CENTER);
                    }
                    panel1.add(panel2, BorderLayout.CENTER);
                }
                contentPanel.add(panel1);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(e -> ok(e));
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    private JLabel label2;
    private JTextField txtEndBookingTime;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
