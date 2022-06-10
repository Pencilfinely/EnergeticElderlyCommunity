/*
 * Created by JFormDesigner on Tue Jun 07 16:58:21 CST 2022
 */

package com.eec.view;

import com.eec.dao.MemberDao;
import com.eec.dao.UserDao;
import com.eec.dao.impl.MemberDaoImpl;
import com.eec.dao.impl.UserDaoImpl;
import com.eec.entity.Member;
import com.eec.entity.User;
import com.eec.utils.TimeHelper;
import com.eec.utils.Tools;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author unknown
 */
public class LivingManagerPanel extends JPanel {

    private static LivingManagerPanel instance = null;
    private User user;

    public static LivingManagerPanel getInstance(User user){
        if(instance == null){
            instance = new LivingManagerPanel();
        }
        instance.setUser(user);

        return instance;
    }

    public void setUser(User user){
        this.user = user;
        resetTable();
    }

    public void resetTable(){
        if(user == null){
            return;
        }
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
                if(member.getLivingManagerUsername().equals(user.getUsername()) &&
                        member.getLivingManagerPassword().equals(user.getPassword())){
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
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private LivingManagerPanel() {
        initComponents();

        resetTable();
    }

    private void btnEdt(ActionEvent e) {
        int row = this.table1.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选中对象！");
            return;
        }

        MemberDao memberDao = MemberDaoImpl.getInstance();
        List<Member> list = null;

        try {
            list = memberDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        Member m = null;
        for (Member member : list){
            if(member.getIC().equals("")){
                continue;
            }
            if(member.getIC().equals((String) this.table1.getValueAt(row,5))){
                EditMemberDialog.getInstance(member,user);
                m = member;
                break;
            }
        }

        if(m != null){
            EditMemberDialog.getInstance(m,user).setSize(600,400);
            EditMemberDialog.getInstance(m,user).setVisible(true);
        }else {
            JOptionPane.showMessageDialog(this,"无所辖会员！");
        }


    }

    private void btnPersonal(ActionEvent e) {
        EditPersonalDialog.getInstance(user).setSize(600,400);
        EditPersonalDialog.getInstance(user).setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        panel2 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel6 = new JPanel();
        btnEdt = new JButton();
        panel7 = new JPanel();
        btnPersonal = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        setLayout(new BorderLayout());

        //---- label1 ----
        label1.setText("\u751f\u6d3b\u7ba1\u5bb6");
        label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        add(label1, BorderLayout.NORTH);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //======== panel4 ========
            {
                panel4.setLayout(new BorderLayout());

                //======== panel5 ========
                {
                    panel5.setLayout(new BorderLayout());

                    //======== panel6 ========
                    {
                        panel6.setLayout(new BoxLayout(panel6, BoxLayout.X_AXIS));

                        //---- btnEdt ----
                        btnEdt.setText("\u4fee\u6539");
                        btnEdt.addActionListener(e -> btnEdt(e));
                        panel6.add(btnEdt);
                    }
                    panel5.add(panel6, BorderLayout.WEST);

                    //======== panel7 ========
                    {
                        panel7.setLayout(new BoxLayout(panel7, BoxLayout.X_AXIS));

                        //---- btnPersonal ----
                        btnPersonal.setText("\u4e2a\u4eba\u4fe1\u606f");
                        btnPersonal.addActionListener(e -> btnPersonal(e));
                        panel7.add(btnPersonal);
                    }
                    panel5.add(panel7, BorderLayout.EAST);
                }
                panel4.add(panel5, BorderLayout.NORTH);

                //======== scrollPane1 ========
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
                    scrollPane1.setViewportView(table1);
                }
                panel4.add(scrollPane1, BorderLayout.CENTER);
            }
            panel2.add(panel4, BorderLayout.CENTER);
        }
        add(panel2, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JPanel panel2;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JButton btnEdt;
    private JPanel panel7;
    private JButton btnPersonal;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
