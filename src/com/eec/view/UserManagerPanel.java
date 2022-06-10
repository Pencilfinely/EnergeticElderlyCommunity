/*
 * Created by JFormDesigner on Sat Jun 04 00:00:48 CST 2022
 */

package com.eec.view;

import java.awt.event.*;
import com.eec.dao.MemberDao;
import com.eec.dao.UserDao;
import com.eec.dao.impl.MemberDaoImpl;
import com.eec.dao.impl.UserDaoImpl;
import com.eec.entity.Member;
import com.eec.entity.User;
import com.eec.utils.TimeHelper;
import com.eec.utils.Tools;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author unknown
 */
public class UserManagerPanel extends JPanel {

    private static UserManagerPanel instance = null;

    public static UserManagerPanel getInstance(){
        if(instance == null){
            instance = new UserManagerPanel();
        }

        return instance;
    }

    private UserManagerPanel() {
        initComponents();

        resetTable();
    }

    public void resetTable(){
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            List<User> list = userDao.getAll();

            DefaultTableModel model = (DefaultTableModel)this.table1.getModel();

            model.fireTableDataChanged();
            Vector<Vector> data = model.getDataVector();
            data.clear();
            for(User user : list){
                if(!user.getPermission().equals("管理员")){
                    Vector vector = new Vector();
                    vector.add(user.getId());
                    vector.add(user.getUsername());
                    vector.add(user.getPassword());
                    vector.add(user.getName());
                    vector.add(user.getGender());
                    try {
                        vector.add(TimeHelper.ageHelper(user.getBirth()));
                    } catch (ParseException e) {
                        vector.add("出生日期设置错误（年月日组成的连续数字，位数不足用0补齐）");
                    }
                    vector.add(user.getPhone());
                    vector.add(user.getPermission());

                    data.add(vector);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void btnAdd(ActionEvent e) {
        UserDao userDao = UserDaoImpl.getInstance();
        String gender = "";
        String permission = "";
        if(this.radioButtonMale.isSelected()){
            gender = "男";
        }else if(this.radioButtonFemale.isSelected()){
            gender = "女";
        }
        if(this.comboBoxPermission.getSelectedIndex() == 0){
            permission = "";
        }else if(this.comboBoxPermission.getSelectedIndex() == 1){
            permission = "生活管家";
        }else if(this.comboBoxPermission.getSelectedIndex() == 2){
            permission = "后勤管理";
        }

        try {
            for(int i = 0; i < this.table1.getRowCount(); i++){
                if(this.txtUsername.getText().equals(this.table1.getValueAt(i,1)) && this.txtPassword.getText().equals(this.table1.getValueAt(i,2))){
                    JOptionPane.showMessageDialog(this,"已在表中！");
                    return;
                }
            }
            userDao.add(this.txtUsername.getText(),this.txtPassword.getText(),this.txtName.getText(),
                    gender,this.txtBirth.getText(),this.txtPhone.getText(),permission);
            JOptionPane.showMessageDialog(this,"添加成功！");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"添加失败！");
            throw new RuntimeException(ex);
        }

        resetTable();
    }

    private void btnReset(ActionEvent e) {
        resetTable();
        this.btnReset.setVisible(false);
    }

    private void btnRefresh(ActionEvent e) {
        this.txtUsername.setText("");
        this.txtPassword.setText("");
        this.txtName.setText("");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.radioButtonMale);
        buttonGroup.add(this.radioButtonFemale);
        buttonGroup.clearSelection();
        this.txtBirth.setText("");
        this.txtPhone.setText("");
        this.comboBoxPermission.setSelectedIndex(0);
    }

    private void btnDel(ActionEvent e) {
        int row = this.table1.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选中对象！");
            return;
        }
        int n = JOptionPane.showConfirmDialog(this,"是否删除？","提示",JOptionPane.YES_NO_OPTION);

        if(n == JOptionPane.YES_OPTION){

            UserDao userDao = UserDaoImpl.getInstance();
            MemberDao memberDao = MemberDaoImpl.getInstance();
            List<User> list = null;
            List<Member> memberList = null;

            try {
                list = userDao.getAll();
                memberList = memberDao.getAll();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"文件同步失败！");
                throw new RuntimeException(ex);
            }

            for (User user : list) {
                if(user.getUsername().equals(this.table1.getValueAt(row,1)) &&
                        user.getPassword().equals(this.table1.getValueAt(row,2))){
                    for (Member m : memberList) {
                        if(m.getLivingManagerUsername().equals(user.getUsername()) && m.getLivingManagerPassword().equals(user.getPassword())){
                            m.setLivingManagerUsername("");
                            m.setLivingManagerPassword("");
                        }
                    }

                    try {
                        userDao.delete(user);
                        memberDao.setAll(memberList);
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

            UserDao userDao = UserDaoImpl.getInstance();
            List<User> list = null;

            try {
                list = userDao.getAll();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"文件同步失败！");
                throw new RuntimeException(ex);
            }

            String gender = "";
            String permission = "";
            if(this.radioButtonMale.isSelected()){
                gender = "男";
            }else if(this.radioButtonFemale.isSelected()){
                gender = "女";
            }
            if(this.comboBoxPermission.getSelectedIndex() == 0){
                permission = "";
            }else if(this.comboBoxPermission.getSelectedIndex() == 1){
                permission = "生活管家";
            }else if(this.comboBoxPermission.getSelectedIndex() == 2){
                permission = "后勤管理";
            }

            for (User user : list) {
                if(user.getUsername().equals(this.table1.getValueAt(row,1)) &&
                        user.getPassword().equals(this.table1.getValueAt(row,2))){
                    user.setUsername(this.txtUsername.getText());
                    user.setPassword(this.txtPassword.getText());
                    user.setName(this.txtName.getText());
                    user.setGender(gender);
                    user.setBirth(this.txtBirth.getText());
                    user.setPhone(this.txtPhone.getText());
                    user.setPermission(permission);
                    try {
                        userDao.setAll(list);
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

    private void btnQue(ActionEvent e) {
        this.btnReset.setVisible(true);

        UserDao userDao = UserDaoImpl.getInstance();
        try {
            List<User> list = userDao.getAll();

            DefaultTableModel model = (DefaultTableModel)this.table1.getModel();

            String gender = "";
            String permission = "";

            model.fireTableDataChanged();
            Vector<Vector> data = model.getDataVector();
            data.clear();
            for(User user : list){
                if(!user.getPermission().equals("管理员")){
                    if(this.radioButtonMale.isSelected()){
                        gender = "男";
                    }else if(this.radioButtonFemale.isSelected()){
                        gender = "女";
                    }
                    if(this.comboBoxPermission.getSelectedIndex() == 0){
                        permission = "";
                    }else if(this.comboBoxPermission.getSelectedIndex() == 1){
                        permission = "生活管家";
                    }else if(this.comboBoxPermission.getSelectedIndex() == 2){
                        permission = "后勤管理";
                    }
                    if((user.getName().equals((String) this.txtName.getText()) || ((String)this.txtName.getText()).equals("")) &&
                            (user.getBirth().equals((String) this.txtBirth.getText()) || ((String)this.txtBirth.getText()).equals("")) &&
                            (user.getGender().equals(gender) || gender.equals("")) &&
                            (user.getPhone().equals((String) this.txtPhone.getText()) || ((String)this.txtPhone.getText()).equals("")) &&
                            (user.getPermission().equals(permission) || permission.equals("")) &&
                            (user.getUsername().equals((String) this.txtUsername.getText()) || ((String)this.txtUsername.getText()).equals("")) &&
                            (user.getPassword().equals((String) this.txtPassword.getText()) || ((String)this.txtPassword.getText()).equals(""))
                    ){
                        Vector vector = new Vector();
                        vector.add(user.getId());
                        vector.add(user.getUsername());
                        vector.add(user.getPassword());
                        vector.add(user.getName());
                        vector.add(user.getGender());
                        try {
                            vector.add(TimeHelper.ageHelper(user.getBirth()));
                        } catch (ParseException ex) {
                            vector.add("出生日期设置错误（年月日组成的连续数字，位数不足用0补齐）");
                        }
                        vector.add(user.getPhone());
                        vector.add(user.getPermission());

                        data.add(vector);
                    }
                }

            }


        } catch (IOException b) {
            b.printStackTrace();
        }
    }

    private void btnSetService(ActionEvent e) {

        int row = this.table1.getSelectedRow();
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> list = null;
        SetServiceDialog.getInstance();

        try {
            list = userDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (User user : list){
            if(user.getUsername().equals(this.table1.getValueAt(row,1)) &&
                    user.getPassword().equals(this.table1.getValueAt(row,2)) &&
                    user.getPermission().equals("生活管家")){
                SetServiceDialog.getInstance().setLivingManager(user);
                break;
            }
        }

        SetServiceDialog.getInstance().setSize(640,400);
        SetServiceDialog.getInstance().setVisible(true);

    }

    private void table1MousePressed(MouseEvent e) {
        int row = this.table1.getSelectedRow();
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> list = null;

        try {
            list = userDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (User user : list) {
            if(user.getId() == (int)this.table1.getValueAt(row, 0) && user.getUsername().equals((String) this.table1.getValueAt(row, 1)) && user.getPassword().equals((String) this.table1.getValueAt(row, 2))){
                this.txtUsername.setText(user.getUsername());
                this.txtPassword.setText(user.getPassword());
                this.txtName.setText(user.getName());
                if(user.getGender().equals("男")){
                    this.radioButtonMale.setSelected(true);
                }else if(user.getGender().equals("女")){
                    this.radioButtonFemale.setSelected(true);
                }
                this.txtBirth.setText(user.getBirth());
                this.txtPhone.setText(user.getPhone());
                int index = 0;
                if(user.getPermission().equals("")){
                    index = 0;
                }else if(user.getPermission().equals("生活管家")){
                    index = 1;
                }else if(user.getPermission().equals("后勤管理")){
                    index = 2;
                }
                this.comboBoxPermission.setSelectedIndex(index);
                if(index == 1){
                    this.btnSetService.setVisible(true);
                }else {
                    this.btnSetService.setVisible(false);
                }
                break;
            }
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel2 = new JPanel();
        label1 = new JLabel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        btnAdd = new JButton();
        btnDel = new JButton();
        btnEdt = new JButton();
        btnQue = new JButton();
        btnReset = new JButton();
        panel5 = new JPanel();
        panel6 = new JPanel();
        btnSetService = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel8 = new JPanel();
        btnRefresh = new JButton();
        panel1 = new JPanel();
        label2 = new JLabel();
        txtUsername = new JTextField();
        label3 = new JLabel();
        txtPassword = new JTextField();
        label4 = new JLabel();
        txtName = new JTextField();
        label5 = new JLabel();
        panel7 = new JPanel();
        radioButtonMale = new JRadioButton();
        radioButtonFemale = new JRadioButton();
        label6 = new JLabel();
        txtBirth = new JTextField();
        label7 = new JLabel();
        txtPhone = new JTextField();
        label8 = new JLabel();
        comboBoxPermission = new JComboBox<>();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel2 ========
        {
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

            //---- label1 ----
            label1.setText("\u7528\u6237\u7ba1\u7406");
            label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            panel2.add(label1);
        }
        add(panel2, BorderLayout.NORTH);

        //======== panel3 ========
        {
            panel3.setLayout(new BorderLayout());

            //======== panel4 ========
            {
                panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));

                //---- btnAdd ----
                btnAdd.setText("\u65b0\u589e");
                btnAdd.addActionListener(e -> btnAdd(e));
                panel4.add(btnAdd);

                //---- btnDel ----
                btnDel.setText("\u5220\u9664");
                btnDel.addActionListener(e -> btnDel(e));
                panel4.add(btnDel);

                //---- btnEdt ----
                btnEdt.setText("\u4fee\u6539");
                btnEdt.addActionListener(e -> btnEdt(e));
                panel4.add(btnEdt);

                //---- btnQue ----
                btnQue.setText("\u67e5\u8be2");
                btnQue.addActionListener(e -> btnQue(e));
                panel4.add(btnQue);

                //---- btnReset ----
                btnReset.setText("\u91cd\u7f6e");
                btnReset.setVisible(false);
                btnReset.addActionListener(e -> btnReset(e));
                panel4.add(btnReset);

                //======== panel5 ========
                {
                    panel5.setLayout(new BorderLayout());

                    //======== panel6 ========
                    {
                        panel6.setLayout(new BoxLayout(panel6, BoxLayout.X_AXIS));

                        //---- btnSetService ----
                        btnSetService.setText("\u670d\u52a1\u5bf9\u8c61");
                        btnSetService.setVisible(false);
                        btnSetService.addActionListener(e -> btnSetService(e));
                        panel6.add(btnSetService);
                    }
                    panel5.add(panel6, BorderLayout.EAST);
                }
                panel4.add(panel5);
            }
            panel3.add(panel4, BorderLayout.NORTH);

            //======== scrollPane1 ========
            {

                //---- table1 ----
                table1.setModel(new DefaultTableModel(
                    new Object[][] {
                    },
                    new String[] {
                        "ID", "\u8d26\u53f7", "\u5bc6\u7801", "\u59d3\u540d", "\u6027\u522b", "\u5e74\u9f84", "\u8054\u7cfb\u7535\u8bdd", "\u6743\u9650"
                    }
                ));
                table1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        table1MousePressed(e);
                    }
                });
                scrollPane1.setViewportView(table1);
            }
            panel3.add(scrollPane1, BorderLayout.CENTER);

            //======== panel8 ========
            {
                panel8.setLayout(new BoxLayout(panel8, BoxLayout.X_AXIS));

                //---- btnRefresh ----
                btnRefresh.setText("\u6e05\u7a7a");
                btnRefresh.addActionListener(e -> btnRefresh(e));
                panel8.add(btnRefresh);
            }
            panel3.add(panel8, BorderLayout.SOUTH);
        }
        add(panel3, BorderLayout.CENTER);

        //======== panel1 ========
        {
            panel1.setLayout(new GridLayout(4, 3));

            //---- label2 ----
            label2.setText("\u8d26\u53f7");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label2);
            panel1.add(txtUsername);

            //---- label3 ----
            label3.setText("\u5bc6\u7801");
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label3);
            panel1.add(txtPassword);

            //---- label4 ----
            label4.setText("\u59d3\u540d");
            label4.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label4);
            panel1.add(txtName);

            //---- label5 ----
            label5.setText("\u6027\u522b");
            label5.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label5);

            //======== panel7 ========
            {
                panel7.setLayout(new GridLayout());

                //---- radioButtonMale ----
                radioButtonMale.setText("\u7537");
                radioButtonMale.setHorizontalAlignment(SwingConstants.CENTER);
                panel7.add(radioButtonMale);

                //---- radioButtonFemale ----
                radioButtonFemale.setText("\u5973");
                radioButtonFemale.setHorizontalAlignment(SwingConstants.CENTER);
                panel7.add(radioButtonFemale);
            }
            panel1.add(panel7);

            //---- label6 ----
            label6.setText("\u51fa\u751f\u65e5\u671f");
            label6.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label6);
            panel1.add(txtBirth);

            //---- label7 ----
            label7.setText("\u8054\u7cfb\u7535\u8bdd");
            label7.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label7);
            panel1.add(txtPhone);

            //---- label8 ----
            label8.setText("\u6743\u9650");
            label8.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label8);

            //---- comboBoxPermission ----
            comboBoxPermission.setModel(new DefaultComboBoxModel<>(new String[] {
                "              ",
                "\u751f\u6d3b\u7ba1\u5bb6",
                "\u540e\u52e4\u7ba1\u7406"
            }));
            panel1.add(comboBoxPermission);
        }
        add(panel1, BorderLayout.SOUTH);

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButtonMale);
        buttonGroup1.add(radioButtonFemale);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel2;
    private JLabel label1;
    private JPanel panel3;
    private JPanel panel4;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnEdt;
    private JButton btnQue;
    private JButton btnReset;
    private JPanel panel5;
    private JPanel panel6;
    private JButton btnSetService;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel8;
    private JButton btnRefresh;
    private JPanel panel1;
    private JLabel label2;
    private JTextField txtUsername;
    private JLabel label3;
    private JTextField txtPassword;
    private JLabel label4;
    private JTextField txtName;
    private JLabel label5;
    private JPanel panel7;
    private JRadioButton radioButtonMale;
    private JRadioButton radioButtonFemale;
    private JLabel label6;
    private JTextField txtBirth;
    private JLabel label7;
    private JTextField txtPhone;
    private JLabel label8;
    private JComboBox<String> comboBoxPermission;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
