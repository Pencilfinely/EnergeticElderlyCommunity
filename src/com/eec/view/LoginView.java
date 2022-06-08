/*
 * Created by JFormDesigner on Tue May 31 22:35:08 CST 2022
 */

package com.eec.view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import com.eec.dao.UserDao;
import com.eec.dao.impl.UserDaoImpl;
import com.eec.entity.User;

/**
 * @author unknown
 */
public class LoginView extends JFrame {

    private static LoginView instance = null;

    public static void main(String[] args){
        new LoginView().setVisible(true);
    }

    public static LoginView getInstance(){
        if(instance == null){
            instance = new LoginView();
        }

        return instance;
    }

    private LoginView() {
        initComponents();

        this.setSize(400,300);
    }

    private void btnLogin(ActionEvent e){
        String username = this.txtUsername.getText();
        String password = new String(this.txtPassword.getPassword());
        UserDao userDao = UserDaoImpl.getInstance();

        try {
            User user = userDao.login(username, password);

            if(user != null){
                this.setVisible(false);
                MainView.getInstance().setVisible(true);
                MainView.getInstance().modeSet(user);
                this.txtPassword.setText("");
                this.txtUsername.setText("");
            }else{
                this.labelWarning.setText("用户名或密码错误！");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void btnReset(ActionEvent e) {
        this.txtUsername.setText("");
        this.txtPassword.setText("");
        labelWarning.setText(" ");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel6 = new JPanel();
        panel5 = new JPanel();
        label2 = new JLabel();
        txtUsername = new JTextField();
        label3 = new JLabel();
        txtPassword = new JPasswordField();
        labelWarning = new JLabel();
        btnLogin = new JButton();
        btnReset = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(400, 300));
        setMaximizedBounds(new Rectangle(0, 0, 400, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("\u767b\u5f55");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label1, BorderLayout.CENTER);
        }
        contentPane.add(panel1, BorderLayout.NORTH);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());
        }
        contentPane.add(panel2, BorderLayout.WEST);

        //======== panel3 ========
        {
            panel3.setLayout(new BorderLayout());
        }
        contentPane.add(panel3, BorderLayout.EAST);

        //======== panel4 ========
        {
            panel4.setLayout(new BorderLayout());
        }
        contentPane.add(panel4, BorderLayout.SOUTH);

        //======== panel6 ========
        {
            panel6.setLayout(new BorderLayout());

            //======== panel5 ========
            {

                //---- label2 ----
                label2.setText("\u8d26\u53f7");
                label2.setHorizontalAlignment(SwingConstants.CENTER);

                //---- label3 ----
                label3.setText("\u5bc6\u7801");
                label3.setHorizontalAlignment(SwingConstants.CENTER);

                //---- labelWarning ----
                labelWarning.setForeground(Color.red);
                labelWarning.setText(" ");

                //---- btnLogin ----
                btnLogin.setText("\u767b\u5f55");
                btnLogin.addActionListener(e -> btnLogin(e));

                //---- btnReset ----
                btnReset.setText("\u91cd\u7f6e");
                btnReset.addActionListener(e -> btnReset(e));

                GroupLayout panel5Layout = new GroupLayout(panel5);
                panel5.setLayout(panel5Layout);
                panel5Layout.setHorizontalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addGroup(panel5Layout.createParallelGroup()
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                        .addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addGap(78, 78, 78)
                                    .addGroup(panel5Layout.createParallelGroup()
                                        .addComponent(labelWarning, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel5Layout.createSequentialGroup()
                                            .addGap(24, 24, 24)
                                            .addComponent(btnLogin)
                                            .addGap(57, 57, 57)
                                            .addComponent(btnReset)))))
                            .addContainerGap(52, Short.MAX_VALUE))
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(labelWarning)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnReset)
                                .addComponent(btnLogin))
                            .addContainerGap(49, Short.MAX_VALUE))
                );
            }
            panel6.add(panel5, BorderLayout.CENTER);
        }
        contentPane.add(panel6, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel6;
    private JPanel panel5;
    private JLabel label2;
    private JTextField txtUsername;
    private JLabel label3;
    private JPasswordField txtPassword;
    private JLabel labelWarning;
    private JButton btnLogin;
    private JButton btnReset;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
