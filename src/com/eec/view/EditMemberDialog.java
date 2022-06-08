/*
 * Created by JFormDesigner on Tue Jun 07 23:42:06 CST 2022
 */

package com.eec.view;

import java.awt.event.*;

import com.eec.dao.impl.MemberDaoImpl;
import com.eec.entity.Member;
import com.eec.entity.User;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class EditMemberDialog extends JDialog {

    private static EditMemberDialog instance = null;
    private Member member;
    private User user;

    public static EditMemberDialog getInstance(Member member, User user){
        if (instance == null){
            instance = new EditMemberDialog(null);
        }
        instance.setMember(member);
        instance.setUser(user);
        instance.reset();

        return instance;
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void reset(){
        if (member == null){
            return;
        }
        this.txtName.setText(member.getName());
        this.txtBirth.setText(member.getBirth());
        if(member.getGender().equals("男")){
            this.radioButtonMale.setSelected(true);
        }else if(member.getGender().equals("女")){
            this.radioButtonFemale.setSelected(true);
        }
        this.txtPhone.setText(member.getPhone());
        this.txtIC.setText(member.getIC());
        this.txtAddress.setText(member.getAdress());
        this.txtType.setText(member.getType());
        this.txtContact.setText(member.getContact());
        this.txtContactPhone.setText(member.getContactPhone());
    }

    private EditMemberDialog(Window owner) {
        super(owner);
        initComponents();
        reset();
    }

    private void ok(ActionEvent e) {
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
            if(member.getId() == this.member.getId() && member.getIC().equals(this.member.getIC())){
                member.setName(this.txtName.getText());
                member.setBirth(this.txtBirth.getText());
                member.setGender(gender);
                member.setPhone(this.txtPhone.getText());
                member.setIC(this.txtIC.getText());
                member.setAdress(this.txtAddress.getText());
                member.setType(this.txtType.getText());
                member.setContact(this.txtContact.getText());
                member.setContactPhone(this.txtContactPhone.getText());
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

        LivingManagerPanel.getInstance(this.user).resetTable();
        this.setVisible(false);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        txtName = new JTextField();
        label2 = new JLabel();
        panel2 = new JPanel();
        radioButtonMale = new JRadioButton();
        radioButtonFemale = new JRadioButton();
        label3 = new JLabel();
        txtBirth = new JTextField();
        label4 = new JLabel();
        txtPhone = new JTextField();
        label5 = new JLabel();
        txtAddress = new JTextField();
        label6 = new JLabel();
        txtContact = new JTextField();
        label7 = new JLabel();
        txtIC = new JTextField();
        label8 = new JLabel();
        txtType = new JTextField();
        label9 = new JLabel();
        txtContactPhone = new JTextField();
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
                    panel1.setLayout(new GridLayout(9, 2));

                    //---- label1 ----
                    label1.setText("\u59d3\u540d");
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label1);
                    panel1.add(txtName);

                    //---- label2 ----
                    label2.setText("\u6027\u522b");
                    label2.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label2);

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

                    //---- label3 ----
                    label3.setText("\u51fa\u751f\u65e5\u671f");
                    label3.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label3);
                    panel1.add(txtBirth);

                    //---- label4 ----
                    label4.setText("\u8054\u7cfb\u7535\u8bdd");
                    label4.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label4);
                    panel1.add(txtPhone);

                    //---- label5 ----
                    label5.setText("\u5c45\u4f4f\u5730\u5740");
                    label5.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label5);
                    panel1.add(txtAddress);

                    //---- label6 ----
                    label6.setText("\u8054\u7cfb\u4eba");
                    label6.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label6);
                    panel1.add(txtContact);

                    //---- label7 ----
                    label7.setText("\u8eab\u4efd\u8bc1");
                    label7.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label7);
                    panel1.add(txtIC);

                    //---- label8 ----
                    label8.setText("\u4f1a\u5458\u7c7b\u578b");
                    label8.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label8);
                    panel1.add(txtType);

                    //---- label9 ----
                    label9.setText("\u8054\u7cfb\u4eba\u7535\u8bdd");
                    label9.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label9);
                    panel1.add(txtContactPhone);
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
    private JTextField txtName;
    private JLabel label2;
    private JPanel panel2;
    private JRadioButton radioButtonMale;
    private JRadioButton radioButtonFemale;
    private JLabel label3;
    private JTextField txtBirth;
    private JLabel label4;
    private JTextField txtPhone;
    private JLabel label5;
    private JTextField txtAddress;
    private JLabel label6;
    private JTextField txtContact;
    private JLabel label7;
    private JTextField txtIC;
    private JLabel label8;
    private JTextField txtType;
    private JLabel label9;
    private JTextField txtContactPhone;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
