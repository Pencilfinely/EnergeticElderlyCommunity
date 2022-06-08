/*
 * Created by JFormDesigner on Tue Jun 07 20:16:34 CST 2022
 */

package com.eec.view;

import java.awt.event.*;
import javax.swing.table.*;

import com.eec.dao.MemberDao;
import com.eec.dao.UserDao;
import com.eec.dao.impl.MemberDaoImpl;
import com.eec.dao.impl.UserDaoImpl;
import com.eec.entity.Member;
import com.eec.entity.User;
import com.eec.utils.Tools;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class SetServiceDialog extends JDialog {

    private static SetServiceDialog instance = null;
    private User livingManager = null;
    private List<Member> recordList;

    public static SetServiceDialog getInstance(){
        if(instance == null){
            instance = new SetServiceDialog(null);
        }

        return instance;
    }

    private SetServiceDialog(Window owner) {
        super(owner);
        initComponents();

        resetTable();
    }

    public void setLivingManager(User user){
        this.livingManager = user;
        resetTable();
    }

    public void resetTable(){
        MemberDao memberDao = MemberDaoImpl.getInstance();
        try {
            List<Member> list = memberDao.getAll();

            DefaultTableModel modelLeft = (DefaultTableModel)this.table1.getModel();
            DefaultTableModel modelRight = (DefaultTableModel)this.table2.getModel();

            modelLeft.fireTableDataChanged();
            modelRight.fireTableDataChanged();
            Vector<Vector> dataLeft = modelLeft.getDataVector();
            Vector<Vector> dataRight = modelRight.getDataVector();
            dataLeft.clear();
            dataRight.clear();
            for(Member member : list){
                if(member.getId() == 0 && member.getIC().equals("")){
                    continue;
                }

                Vector vector = new Vector();
                vector.add(member.getName());
                vector.add(member.getIC());

                if(livingManager != null && member.getLivingManagerUsername().equals(livingManager.getUsername()) &&
                        member.getLivingManagerPassword().equals(livingManager.getPassword())){
                    dataLeft.add(vector);
                }else if(member.getLivingManagerUsername().equals("") &&
                        member.getLivingManagerPassword().equals("")) {
                    dataRight.add(vector);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

//        JOptionPane.showMessageDialog(this,livingManager);

    }

    private void btnAdd(ActionEvent e) {

        int row = this.table2.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选中对象！");
            return;
        }
        MemberDaoImpl memberDao = MemberDaoImpl.getInstance();
        List<Member> list = null;

        try {
            list = memberDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (Member member : list) {
            if(member.getName().equals(this.table2.getValueAt(row, 0)) &&
                    member.getIC().equals(this.table2.getValueAt(row, 1))){
                member.setLivingManagerUsername(livingManager.getUsername());
                member.setLivingManagerPassword(livingManager.getPassword());
                break;
            }
        }

        try {
            memberDao.setAll(list);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        resetTable();
    }

    private void btnDel(ActionEvent e) {
        int row = this.table1.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选中对象！");
            return;
        }
        MemberDaoImpl memberDao = MemberDaoImpl.getInstance();
        List<Member> list = null;

        try {
            list = memberDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (Member member : list) {
            if(member.getName().equals(this.table1.getValueAt(row, 0)) &&
                    member.getIC().equals(this.table1.getValueAt(row, 1))){
                member.setLivingManagerUsername("");
                member.setLivingManagerPassword("");
                break;
            }
        }

        try {
            memberDao.setAll(list);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        resetTable();
    }

    private void ok(ActionEvent e) {
        this.setVisible(false);
        this.dispose();
    }

    private void btnReset(ActionEvent e) {
        resetTable();
    }

    private void btnQue(ActionEvent e) {

        MemberDao memberDao = MemberDaoImpl.getInstance();
        try {
            List<Member> list = memberDao.getAll();

            DefaultTableModel modelLeft = (DefaultTableModel)this.table1.getModel();
            DefaultTableModel modelRight = (DefaultTableModel)this.table2.getModel();

            modelLeft.fireTableDataChanged();
            modelRight.fireTableDataChanged();
            Vector<Vector> dataLeft = modelLeft.getDataVector();
            Vector<Vector> dataRight = modelRight.getDataVector();
            dataLeft.clear();
            dataRight.clear();
            for(Member member : list){
                if(member.getId() == 0 && member.getIC().equals("")){
                    continue;
                }
                if(member.getName().equals((String) this.txtQue.getText()) ||
                        member.getIC().equals((String) this.txtQue.getText())){
                    Vector vector = new Vector();
                    vector.add(member.getName());
                    vector.add(member.getIC());

                    if(livingManager != null && member.getLivingManagerUsername().equals(livingManager.getUsername()) &&
                            member.getLivingManagerPassword().equals(livingManager.getPassword())){
                        dataLeft.add(vector);
                    }else {
                        dataRight.add(vector);
                    }
                }

            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label3 = new JLabel();
        panel2 = new JPanel();
        label2 = new JLabel();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        panel3 = new JPanel();
        panel5 = new JPanel();
        btnAdd = new JButton();
        btnDel = new JButton();
        btnReset = new JButton();
        btnQue = new JButton();
        txtQue = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout());

                //======== panel1 ========
                {
                    panel1.setLayout(new BorderLayout());

                    //======== scrollPane1 ========
                    {

                        //---- table1 ----
                        table1.setPreferredScrollableViewportSize(new Dimension(250, 400));
                        table1.setModel(new DefaultTableModel(
                            new Object[][] {
                                {null, null},
                                {null, null},
                            },
                            new String[] {
                                "\u59d3\u540d", "\u8eab\u4efd\u8bc1"
                            }
                        ));
                        scrollPane1.setViewportView(table1);
                    }
                    panel1.add(scrollPane1, BorderLayout.CENTER);

                    //---- label3 ----
                    label3.setText("\u751f\u6d3b\u7ba1\u5bb6");
                    label3.setHorizontalAlignment(SwingConstants.CENTER);
                    label3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                    panel1.add(label3, BorderLayout.NORTH);
                }
                contentPanel.add(panel1, BorderLayout.WEST);

                //======== panel2 ========
                {
                    panel2.setLayout(new BorderLayout());

                    //---- label2 ----
                    label2.setText("\u4f1a\u5458");
                    label2.setHorizontalAlignment(SwingConstants.CENTER);
                    label2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                    panel2.add(label2, BorderLayout.NORTH);

                    //======== scrollPane2 ========
                    {

                        //---- table2 ----
                        table2.setPreferredScrollableViewportSize(new Dimension(250, 400));
                        table2.setModel(new DefaultTableModel(
                            new Object[][] {
                                {null, null},
                                {null, null},
                            },
                            new String[] {
                                "\u59d3\u540d", "\u8eab\u4efd\u8bc1"
                            }
                        ));
                        scrollPane2.setViewportView(table2);
                    }
                    panel2.add(scrollPane2, BorderLayout.CENTER);
                }
                contentPanel.add(panel2, BorderLayout.EAST);

                //======== panel3 ========
                {
                    panel3.setLayout(new BorderLayout());

                    //======== panel5 ========
                    {
                        panel5.setLayout(new GridLayout(5, 0));

                        //---- btnAdd ----
                        btnAdd.setText("\u2190");
                        btnAdd.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
                        btnAdd.addActionListener(e -> btnAdd(e));
                        panel5.add(btnAdd);

                        //---- btnDel ----
                        btnDel.setText("\u2192");
                        btnDel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
                        btnDel.addActionListener(e -> btnDel(e));
                        panel5.add(btnDel);

                        //---- btnReset ----
                        btnReset.setText("\u91cd\u7f6e");
                        btnReset.setHorizontalTextPosition(SwingConstants.CENTER);
                        btnReset.addActionListener(e -> btnReset(e));
                        panel5.add(btnReset);

                        //---- btnQue ----
                        btnQue.setText("\u67e5\u8be2");
                        btnQue.addActionListener(e -> btnQue(e));
                        panel5.add(btnQue);
                        panel5.add(txtQue);
                    }
                    panel3.add(panel5, BorderLayout.CENTER);
                }
                contentPanel.add(panel3, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(e -> ok(e));
                buttonBar.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label3;
    private JPanel panel2;
    private JLabel label2;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JPanel panel3;
    private JPanel panel5;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnReset;
    private JButton btnQue;
    private JTextField txtQue;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
