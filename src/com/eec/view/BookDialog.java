/*
 * Created by JFormDesigner on Wed Jun 08 23:04:21 CST 2022
 */

package com.eec.view;

import java.awt.event.*;

import com.eec.dao.BusDao;
import com.eec.dao.MemberDao;
import com.eec.dao.impl.BusDaoImpl;
import com.eec.dao.impl.MemberDaoImpl;
import com.eec.entity.Bus;
import com.eec.entity.Member;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class BookDialog extends JDialog {

    private static BookDialog instance = null;
    private Bus bus;

    public static BookDialog getInstance(Bus bus){
        if(instance == null){
            instance = new BookDialog(null);
        }
        instance.setBus(bus);

        return instance;
    }

    public void setBus(Bus bus){
        this.bus = bus;
    }

    private BookDialog(Window owner) {
        super(owner);
        initComponents();
    }

    private void ok(ActionEvent e) {
        MemberDao memberDao = MemberDaoImpl.getInstance();
        BusDao busDao = BusDaoImpl.getInstance();
        List<Member> list = null;
        List<Bus> busList = null;
        boolean fnd = false;

        try {
            list = memberDao.getAll();
            busList = busDao.getAll();

            for (Member m : list) {
                if(m.getIC().equals("")){
                    continue;
                }
                if(m.getName().equals(this.txtName.getText()) && m.getIC().equals(this.txtIC.getText())){
//                    m.setBusCode(this.bus.getBusCode());
                    List<String> buses = m.getBusList();
                    boolean has = false;
                    for (String s : buses) {
                        if(s.equals(this.bus.getBusCode())){
                            has = true;
                        }
                    }
                    if(!has){
                        m.addBus(this.bus.getBusCode());
                    }else {
                        JOptionPane.showMessageDialog(this,"此会员已预约该班车！");
                        return;
                    }

                    fnd = true;
                    break;
                }
            }

            if(fnd){
                for (Bus b : busList) {
                    if(b.getBusCode().equals(this.bus.getBusCode())){
                        int cnt = 0;
                        for (Member m : list) {
                            if(m.getIC().equals("")){
                                continue;
                            }
//                            if(m.getBusCode().equals(this.bus.getBusCode())){
//                                cnt++;
//                            }
                            List<String> buses = m.getBusList();
                            int i = 0;
                            for(String s : buses){
                                if (s.equals(this.bus.getBusCode())){
                                    cnt++;
                                }
                                i++;
                            }
                        }
                        b.setBookingCnt(cnt);
                        break;
                    }
                }
            }else {
                JOptionPane.showMessageDialog(this,"未找到此会员！");
            }


            memberDao.setAll(list);
            busDao.setAll(busList);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
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
        label1 = new JLabel();
        panel1 = new JPanel();
        label2 = new JLabel();
        txtName = new JTextField();
        label3 = new JLabel();
        txtIC = new JTextField();
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
                contentPanel.setLayout(new BorderLayout());

                //---- label1 ----
                label1.setText("\u73ed\u8f66\u9884\u7ea6\u767b\u8bb0");
                label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(label1, BorderLayout.NORTH);

                //======== panel1 ========
                {
                    panel1.setLayout(new GridLayout(2, 2));

                    //---- label2 ----
                    label2.setText("\u59d3\u540d");
                    label2.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label2);
                    panel1.add(txtName);

                    //---- label3 ----
                    label3.setText("\u8eab\u4efd\u8bc1");
                    label3.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label3);
                    panel1.add(txtIC);
                }
                contentPanel.add(panel1, BorderLayout.CENTER);
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
    private JLabel label1;
    private JPanel panel1;
    private JLabel label2;
    private JTextField txtName;
    private JLabel label3;
    private JTextField txtIC;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
