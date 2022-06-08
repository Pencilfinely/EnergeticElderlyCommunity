/*
 * Created by JFormDesigner on Wed Jun 08 00:12:28 CST 2022
 */

package com.eec.view;

import java.awt.event.*;
import com.eec.dao.UserDao;
import com.eec.dao.impl.UserDaoImpl;
import com.eec.entity.User;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class EditPersonalDialog extends JDialog {

    private static EditPersonalDialog instance = null;
    private User user;

    public static EditPersonalDialog getInstance(User user){
        if(instance == null){
            instance = new EditPersonalDialog(null);
        }
        instance.setUser(user);
        instance.reset();

        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    private EditPersonalDialog(Window owner) {
        super(owner);
        initComponents();
    }

    public void reset(){
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> list = null;

        try {
            list = userDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (User user : list) {
            if(user.getId() == this.user.getId() &&
                    user.getUsername().equals(this.user.getUsername()) &&
                    user.getPassword().equals(this.user.getPassword())){
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

                break;
            }
        }
    }

    private void ok(ActionEvent e) {
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> list = null;

        try {
            list = userDao.getAll();
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

        for (User user : list) {
            if(user.getUsername().equals(this.user.getUsername()) &&
                    user.getPassword().equals(this.user.getPassword()) &&
                    user.getId() == this.user.getId()){
                user.setUsername(this.txtUsername.getText());
                user.setPassword(this.txtPassword.getText());
                user.setName(this.txtName.getText());
                user.setGender(gender);
                user.setBirth(this.txtBirth.getText());
                user.setPhone(this.txtPhone.getText());
                user.setPermission(this.user.getPermission());
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

        this.setVisible(false);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        txtUsername = new JTextField();
        label2 = new JLabel();
        txtPassword = new JTextField();
        label3 = new JLabel();
        txtName = new JTextField();
        label4 = new JLabel();
        panel2 = new JPanel();
        radioButtonMale = new JRadioButton();
        radioButtonFemale = new JRadioButton();
        label5 = new JLabel();
        txtBirth = new JTextField();
        label6 = new JLabel();
        txtPhone = new JTextField();
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
                contentPanel.setLayout(new GridLayout());

                //======== panel1 ========
                {
                    panel1.setLayout(new GridLayout(6, 2));

                    //---- label1 ----
                    label1.setText("\u8d26\u53f7");
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label1);
                    panel1.add(txtUsername);

                    //---- label2 ----
                    label2.setText("\u5bc6\u7801");
                    label2.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label2);
                    panel1.add(txtPassword);

                    //---- label3 ----
                    label3.setText("\u59d3\u540d");
                    label3.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label3);
                    panel1.add(txtName);

                    //---- label4 ----
                    label4.setText("\u6027\u522b");
                    label4.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label4);

                    //======== panel2 ========
                    {
                        panel2.setLayout(new GridLayout(1, 2));

                        //---- radioButtonMale ----
                        radioButtonMale.setText("\u7537");
                        radioButtonMale.setHorizontalAlignment(SwingConstants.CENTER);
                        panel2.add(radioButtonMale);

                        //---- radioButtonFemale ----
                        radioButtonFemale.setText("\u5973");
                        radioButtonFemale.setHorizontalAlignment(SwingConstants.CENTER);
                        panel2.add(radioButtonFemale);
                    }
                    panel1.add(panel2);

                    //---- label5 ----
                    label5.setText("\u51fa\u751f\u65e5\u671f");
                    label5.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label5);
                    panel1.add(txtBirth);

                    //---- label6 ----
                    label6.setText("\u8054\u7cfb\u7535\u8bdd");
                    label6.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label6);
                    panel1.add(txtPhone);
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

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButtonMale);
        buttonGroup1.add(radioButtonFemale);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel panel1;
    private JLabel label1;
    private JTextField txtUsername;
    private JLabel label2;
    private JTextField txtPassword;
    private JLabel label3;
    private JTextField txtName;
    private JLabel label4;
    private JPanel panel2;
    private JRadioButton radioButtonMale;
    private JRadioButton radioButtonFemale;
    private JLabel label5;
    private JTextField txtBirth;
    private JLabel label6;
    private JTextField txtPhone;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
