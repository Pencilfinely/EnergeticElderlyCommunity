/*
 * Created by JFormDesigner on Fri Jun 03 23:50:29 CST 2022
 */

package com.eec.view;

import java.beans.*;
import com.eec.dao.MemberDao;
import com.eec.dao.UserDao;
import com.eec.dao.impl.MemberDaoImpl;
import com.eec.dao.impl.UserDaoImpl;
import com.eec.entity.Member;
import com.eec.utils.TimeHelper;
import com.eec.utils.Tools;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author unknown
 */
public class MembershipManagerPanel extends JPanel {

    private static MembershipManagerPanel instance = null;

    public static MembershipManagerPanel getInstance(){
        if(instance == null){
            instance = new MembershipManagerPanel();
        }

        return instance;
    }

    private MembershipManagerPanel() {
        initComponents();

        resetTable();
    }

    public void resetTable(){
        MemberDao memberDao = MemberDaoImpl.getInstance();
        try {
            List<Member> list = memberDao.getAll();

            DefaultTableModel model = (DefaultTableModel)this.table1.getModel();

            model.fireTableDataChanged();
            Vector<Vector> data = model.getDataVector();
            data.clear();
            for(Member member : list){
                if(member.getId() == 0 && member.getIC().equals("")){
                    continue;
                }
                Vector vector = new Vector();
                vector.add(member.getId());
                vector.add(member.getName());
                try {
                    vector.add(TimeHelper.ageHelper(member.getBirth()));
                } catch (ParseException e) {
//                    JOptionPane.showMessageDialog(this,"出生日期设置不正确（年月日组成的连续数字，位数不足用0补齐）");
                    vector.add("出生日期设置错误（年月日组成的连续数字，位数不足用0补齐）");
                }
//                vector.add(member.getBirth());
                vector.add(member.getGender());
                vector.add(member.getPhone());
                vector.add(member.getIC());
                vector.add(member.getAdress());
                vector.add(member.getType());
                vector.add(member.getContact());
                vector.add(member.getContactPhone());

                data.add(vector);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void btnAdd(ActionEvent e) {
        MemberDao memberDao = MemberDaoImpl.getInstance();
        String gender = "";
        if(this.radioButtonMale.isSelected()){
            gender = "男";
        }else if(this.radioButtonFemale.isSelected()){
            gender = "女";
        }

        try {
            for(int i = 0; i < this.table1.getRowCount(); i++){
                if(this.txtIC.getText().equals(this.table1.getValueAt(i,5))){
                    JOptionPane.showMessageDialog(this,"已在表中！");
                    return;
                }
            }
            memberDao.add(this.txtName.getText(),this.txtBirthDate.getText(),gender,
                    this.txtPhoneNum.getText(),this.txtIC.getText(),this.txtAddress.getText(),
                    this.txtType.getText(),this.txtContact.getText(),this.txtContactPhoneNum.getText(),"","");
            JOptionPane.showMessageDialog(this,"添加成功！");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"添加失败！");
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
        int n = JOptionPane.showConfirmDialog(this,"是否删除？","提示",JOptionPane.YES_NO_OPTION);

        if(n == JOptionPane.YES_OPTION){

            MemberDaoImpl memberDao = MemberDaoImpl.getInstance();
            List<Member> list = null;
//            ((DefaultTableModel)this.table1.getModel()).removeRow(row);

            try {
                list = memberDao.getAll();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"文件同步失败！");
                throw new RuntimeException(ex);
            }

            for (Member member : list) {
                if(member.getIC().equals(this.table1.getValueAt(row,5))){

                    try {
                        memberDao.delete(member);
                        JOptionPane.showMessageDialog(this,"删除成功！");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,"删除失败！");
                        throw new RuntimeException(ex);
                    }

                    break;
                }
            }

        }

        resetTable();
    }

    private void btnEdt(ActionEvent e) {
        int row = this.table1.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选中对象！");
            return;
        }
        int n = JOptionPane.showConfirmDialog(this,"是否修改？","提示",JOptionPane.YES_NO_OPTION);

        if(n == JOptionPane.YES_OPTION){

            MemberDaoImpl memberDao = MemberDaoImpl.getInstance();
            List<Member> list = null;

            try {
                list = memberDao.getAll();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"文件同步失败！");
                throw new RuntimeException(ex);
            }

            String gender = "";
            if(this.radioButtonMale.isSelected()){
                gender = "男";
            }else if(this.radioButtonFemale.isSelected()){
                gender = "女";
            }

            for (Member member : list) {
                if(member.getIC().equals(this.table1.getValueAt(row,5))){
                    member.setName(this.txtName.getText());
                    member.setBirth(this.txtBirthDate.getText());
                    member.setGender(gender);
                    member.setPhone(this.txtPhoneNum.getText());
                    member.setIC(this.txtIC.getText());
                    member.setAdress(this.txtAddress.getText());
                    member.setType(this.txtType.getText());
                    member.setContact(this.txtContact.getText());
                    member.setContactPhone(this.txtContactPhoneNum.getText());
                    try {
                        memberDao.setAll(list);
                        JOptionPane.showMessageDialog(this,"修改成功！");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,"修改失败！");
                        throw new RuntimeException(ex);
                    }
                    break;
                }
            }
        }

        resetTable();
    }

    private void table1MousePressed(MouseEvent e) {

        int row = this.table1.getSelectedRow();
        MemberDaoImpl memberDao = MemberDaoImpl.getInstance();
        List<Member> list = null;

        try {
            list = memberDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (Member member : list) {
            if(member.getId() == (int)this.table1.getValueAt(row, 0) && member.getName().equals((String) this.table1.getValueAt(row, 1))){
                this.txtName.setText(member.getName());
                this.txtBirthDate.setText(member.getBirth());
                if(member.getGender().equals("男")){
                    this.radioButtonMale.setSelected(true);
                }else if(member.getGender().equals("女")){
                    this.radioButtonFemale.setSelected(true);
                }
                this.txtPhoneNum.setText(member.getPhone());
                this.txtIC.setText(member.getIC());
                this.txtAddress.setText(member.getAdress());
                this.txtType.setText(member.getType());
                this.txtContact.setText(member.getContact());
                this.txtContactPhoneNum.setText(member.getContactPhone());
                break;
            }
        }

    }

    private void btnQue(ActionEvent e) {
        this.btnReset.setVisible(true);

        MemberDao memberDao = MemberDaoImpl.getInstance();
        try {
            List<Member> list = memberDao.getAll();

            DefaultTableModel model = (DefaultTableModel)this.table1.getModel();

            String gender = "";

            model.fireTableDataChanged();
            Vector<Vector> data = model.getDataVector();
            data.clear();
            for(Member member : list){
                if(member.getId() == 0 && member.getIC().equals("")){
                    continue;
                }
                if(this.radioButtonMale.isSelected()){
                    gender = "男";
                }else if(this.radioButtonFemale.isSelected()){
                    gender = "女";
                }
                if((member.getName().equals((String) this.txtName.getText()) || ((String)this.txtName.getText()).equals("")) &&
                        (member.getBirth().equals((String) this.txtBirthDate.getText()) || ((String)this.txtBirthDate.getText()).equals("")) &&
                        (member.getGender().equals(gender) || gender.equals("")) &&
                        (member.getPhone().equals((String) this.txtPhoneNum.getText()) || ((String)this.txtPhoneNum.getText()).equals("")) &&
                        (member.getIC().equals((String) this.txtIC.getText()) || ((String)this.txtIC.getText()).equals("")) &&
                        (member.getAdress().equals((String) this.txtAddress.getText()) || ((String)this.txtAddress.getText()).equals("")) &&
                        (member.getType().equals((String) this.txtType.getText()) || ((String)this.txtType.getText()).equals("")) &&
                        (member.getContact().equals((String) this.txtContact.getText()) || ((String)this.txtContact.getText()).equals("")) &&
                        (member.getContactPhone().equals((String) this.txtContactPhoneNum.getText()) || ((String)this.txtContactPhoneNum.getText()).equals(""))
                ){
                    Vector vector = new Vector();
                    vector.add(member.getId());
                    vector.add(member.getName());
                    try {
                        vector.add(TimeHelper.ageHelper(member.getBirth()));
                    } catch (ParseException s) {
//                    JOptionPane.showMessageDialog(this,"出生日期设置不正确（年月日组成的连续数字，位数不足用0补齐）");
                        vector.add("出生日期设置错误（年月日组成的连续数字，位数不足用0补齐）");
                    }
//                vector.add(member.getBirth());
                    vector.add(member.getGender());
                    vector.add(member.getPhone());
                    vector.add(member.getIC());
                    vector.add(member.getAdress());
                    vector.add(member.getType());
                    vector.add(member.getContact());
                    vector.add(member.getContactPhone());

                    data.add(vector);
                }

            }


        } catch (IOException b) {
            b.printStackTrace();
        }

    }

    private void btnReset(ActionEvent e) {
        resetTable();
        this.btnReset.setVisible(false);
    }

    private void btnRefresh(ActionEvent e) {
        this.txtName.setText("");
        this.txtBirthDate.setText("");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.radioButtonMale);
        buttonGroup.add(this.radioButtonFemale);
        buttonGroup.clearSelection();
        this.txtPhoneNum.setText("");
        this.txtIC.setText("");
        this.txtAddress.setText("");
        this.txtType.setText("");
        this.txtContact.setText("");
        this.txtContactPhoneNum.setText("");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        panel5 = new JPanel();
        label2 = new JLabel();
        txtName = new JTextField();
        label5 = new JLabel();
        txtPhoneNum = new JTextField();
        label6 = new JLabel();
        txtIC = new JTextField();
        label3 = new JLabel();
        panel6 = new JPanel();
        radioButtonMale = new JRadioButton();
        radioButtonFemale = new JRadioButton();
        label9 = new JLabel();
        txtAddress = new JTextField();
        label7 = new JLabel();
        txtType = new JTextField();
        label4 = new JLabel();
        txtBirthDate = new JTextField();
        label10 = new JLabel();
        txtContact = new JTextField();
        label8 = new JLabel();
        txtContactPhoneNum = new JTextField();
        panel7 = new JPanel();
        panel8 = new JPanel();
        btnAdd = new JButton();
        btnDel = new JButton();
        btnEdt = new JButton();
        btnQue = new JButton();
        btnReset = new JButton();
        scrollPane2 = new JScrollPane();
        table1 = new JTable();
        panel1 = new JPanel();
        btnRefresh = new JButton();

        //======== this ========
        setLayout(new BorderLayout());

        //---- label1 ----
        label1.setText("\u4f1a\u5458\u7ba1\u7406");
        label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        add(label1, BorderLayout.NORTH);

        //======== panel5 ========
        {
            panel5.setLayout(new GridLayout(3, 3));

            //---- label2 ----
            label2.setText("\u59d3\u540d");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label2);
            panel5.add(txtName);

            //---- label5 ----
            label5.setText("\u8054\u7cfb\u7535\u8bdd");
            label5.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label5);
            panel5.add(txtPhoneNum);

            //---- label6 ----
            label6.setText("\u8eab\u4efd\u8bc1");
            label6.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label6);
            panel5.add(txtIC);

            //---- label3 ----
            label3.setText("\u6027\u522b");
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label3);

            //======== panel6 ========
            {
                panel6.setLayout(new GridLayout());

                //---- radioButtonMale ----
                radioButtonMale.setText("\u7537");
                radioButtonMale.setHorizontalAlignment(SwingConstants.CENTER);
                panel6.add(radioButtonMale);

                //---- radioButtonFemale ----
                radioButtonFemale.setText("\u5973");
                radioButtonFemale.setHorizontalAlignment(SwingConstants.CENTER);
                panel6.add(radioButtonFemale);
            }
            panel5.add(panel6);

            //---- label9 ----
            label9.setText("\u5c45\u4f4f\u5730\u5740");
            label9.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label9);
            panel5.add(txtAddress);

            //---- label7 ----
            label7.setText("\u4f1a\u5458\u7c7b\u578b");
            label7.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label7);
            panel5.add(txtType);

            //---- label4 ----
            label4.setText("\u51fa\u751f\u65e5\u671f");
            label4.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label4);
            panel5.add(txtBirthDate);

            //---- label10 ----
            label10.setText("\u8054\u7cfb\u4eba");
            label10.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label10);
            panel5.add(txtContact);

            //---- label8 ----
            label8.setText("\u8054\u7cfb\u4eba\u7535\u8bdd");
            label8.setHorizontalAlignment(SwingConstants.CENTER);
            panel5.add(label8);
            panel5.add(txtContactPhoneNum);
        }
        add(panel5, BorderLayout.SOUTH);

        //======== panel7 ========
        {
            panel7.setLayout(new BorderLayout());

            //======== panel8 ========
            {
                panel8.setLayout(new BoxLayout(panel8, BoxLayout.X_AXIS));

                //---- btnAdd ----
                btnAdd.setText("\u65b0\u589e");
                btnAdd.addActionListener(e -> btnAdd(e));
                panel8.add(btnAdd);

                //---- btnDel ----
                btnDel.setText("\u5220\u9664");
                btnDel.addActionListener(e -> btnDel(e));
                panel8.add(btnDel);

                //---- btnEdt ----
                btnEdt.setText("\u4fee\u6539");
                btnEdt.addActionListener(e -> btnEdt(e));
                panel8.add(btnEdt);

                //---- btnQue ----
                btnQue.setText("\u67e5\u8be2");
                btnQue.addActionListener(e -> btnQue(e));
                panel8.add(btnQue);

                //---- btnReset ----
                btnReset.setText("\u91cd\u7f6e");
                btnReset.setVisible(false);
                btnReset.addActionListener(e -> btnReset(e));
                panel8.add(btnReset);
            }
            panel7.add(panel8, BorderLayout.NORTH);

            //======== scrollPane2 ========
            {

                //---- table1 ----
                table1.setModel(new DefaultTableModel(
                    new Object[][] {
                        {null, null, null, null, null, null, null, null, null, null},
                    },
                    new String[] {
                        "ID", "\u59d3\u540d", "\u5e74\u9f84", "\u6027\u522b", "\u8054\u7cfb\u7535\u8bdd", "\u8eab\u4efd\u8bc1", "\u5c45\u4f4f\u5730\u5740", "\u4f1a\u5458\u7c7b\u578b", "\u8054\u7cfb\u4eba", "\u8054\u7cfb\u4eba\u7535\u8bdd"
                    }
                ));
                table1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        table1MousePressed(e);
                    }
                });
                scrollPane2.setViewportView(table1);
            }
            panel7.add(scrollPane2, BorderLayout.CENTER);

            //======== panel1 ========
            {
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

                //---- btnRefresh ----
                btnRefresh.setText("\u6e05\u7a7a");
                btnRefresh.addActionListener(e -> btnRefresh(e));
                panel1.add(btnRefresh);
            }
            panel7.add(panel1, BorderLayout.SOUTH);
        }
        add(panel7, BorderLayout.CENTER);

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButtonMale);
        buttonGroup1.add(radioButtonFemale);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JPanel panel5;
    private JLabel label2;
    private JTextField txtName;
    private JLabel label5;
    private JTextField txtPhoneNum;
    private JLabel label6;
    private JTextField txtIC;
    private JLabel label3;
    private JPanel panel6;
    private JRadioButton radioButtonMale;
    private JRadioButton radioButtonFemale;
    private JLabel label9;
    private JTextField txtAddress;
    private JLabel label7;
    private JTextField txtType;
    private JLabel label4;
    private JTextField txtBirthDate;
    private JLabel label10;
    private JTextField txtContact;
    private JLabel label8;
    private JTextField txtContactPhoneNum;
    private JPanel panel7;
    private JPanel panel8;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnEdt;
    private JButton btnQue;
    private JButton btnReset;
    private JScrollPane scrollPane2;
    private JTable table1;
    private JPanel panel1;
    private JButton btnRefresh;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
