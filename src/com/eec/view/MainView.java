/*
 * Created by JFormDesigner on Thu Jun 02 21:57:05 CST 2022
 */

package com.eec.view;

import java.awt.event.*;
import javax.swing.border.*;

import com.eec.entity.User;
import com.eec.utils.Tools;
import com.sun.source.tree.InstanceOfTree;

import java.awt.*;
import java.beans.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;

/**
 * @author unknown
 */
public class MainView extends JFrame{

    private static MainView instance = null;
    private int modeSetter = 0;
    private User user;

    public static MainView getInstance(){
        if(instance == null) {
            instance = new MainView();
        }
        return instance;
    }

    private MainView() {
        initComponents();

//        List<User> list = new ArrayList<>();
//        list.add(new User(0,"admin","000000","","","","","管理员"));
//        try {
//            Tools.writeListToJSON("User.json",list);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void main(String[] args) {
        new MainView().setVisible(true);
    }

    public void modeSet(User user) {
        this.user = user;

        this.splitPane1.setRightComponent(VoidPanel.getInstance());
        this.labelUserNow.setText(user.getUsername());

        if(!user.getPermission().equals("管理员")){
            if(user.getPermission().equals("后勤管理")){
                this.modeSetter = 2;
            }else if(user.getPermission().equals("生活管家")){
                this.modeSetter = 1;
            }else{
                this.dispose();
                JOptionPane.showMessageDialog(this,"您没有系统权限！");
                LoginView.getInstance().setVisible(true);
            }
        }else {
            this.modeSetter = 0;
        }
    }

    private void tree1ValueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getNewLeadSelectionPath().getLastPathComponent());

        String name = node.toString();

        if(name.equals("会员管理") && (modeSetter == 0)){
            splitPane1.setRightComponent(MembershipManagerPanel.getInstance());
        }else if(name.equals("房间管理") && (modeSetter == 0)) {
            //splitPane1.setRightComponent(RoomManagerPanel.getInstance());
        }else if(name.equals("用户管理") && (modeSetter == 0)) {
            splitPane1.setRightComponent(UserManagerPanel.getInstance());
        }else if(name.equals("入住管理") && (modeSetter == 0)){
            //splitPane1.setRightComponent(CheckInManagerPanel.getInstance());
        }else if(name.equals("场馆设施管理") && (modeSetter == 0)){
            //splitPane1.setRightComponent(FacilityManagerPanel.getInstance());
        }else if(name.equals("班车路线管理") && (modeSetter == 0 || modeSetter == 2)){
            splitPane1.setRightComponent(BusManagerPanel.getInstance());
        }else if(name.equals("生活管家") && (modeSetter == 0 || modeSetter == 1)){
            splitPane1.setRightComponent(LivingManagerPanel.getInstance(user));
        }else if(!name.equals("系统")){
            JOptionPane.showMessageDialog(this,"您无权访问此页面！");
        }
    }

    private void btnLogout(ActionEvent e) {
        this.modeSetter = 0;
        this.dispose();
        LoginView.getInstance().setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label11 = new JLabel();
        panel2 = new JPanel();
        btnLogout = new JButton();
        panel4 = new JPanel();
        labelUserNow = new JLabel();
        label2 = new JLabel();
        splitPane1 = new JSplitPane();
        scrollPane1 = new JScrollPane();
        tree1 = new JTree();
        panel3 = new JPanel();

        //======== this ========
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //---- label11 ----
            label11.setText("\u6d3b\u529b\u957f\u8005\u793e\u533a");
            label11.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
            panel1.add(label11, BorderLayout.WEST);

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout(5, 5));

                //---- btnLogout ----
                btnLogout.setText("\u6ce8\u9500");
                btnLogout.addActionListener(e -> btnLogout(e));
                panel2.add(btnLogout, BorderLayout.EAST);

                //======== panel4 ========
                {
                    panel4.setLayout(new BorderLayout(3, 0));

                    //---- labelUserNow ----
                    labelUserNow.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
                    panel4.add(labelUserNow, BorderLayout.CENTER);

                    //---- label2 ----
                    label2.setText("\u8d26\u53f7\uff1a");
                    label2.setHorizontalAlignment(SwingConstants.CENTER);
                    label2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
                    panel4.add(label2, BorderLayout.WEST);
                }
                panel2.add(panel4, BorderLayout.WEST);
            }
            panel1.add(panel2, BorderLayout.EAST);
        }
        contentPane.add(panel1, BorderLayout.NORTH);

        //======== splitPane1 ========
        {

            //======== scrollPane1 ========
            {

                //---- tree1 ----
                tree1.setModel(new DefaultTreeModel(
                    new DefaultMutableTreeNode("\u7cfb\u7edf") {
                        {
                            add(new DefaultMutableTreeNode("\u4f1a\u5458\u7ba1\u7406"));
                            add(new DefaultMutableTreeNode("\u7528\u6237\u7ba1\u7406"));
                            add(new DefaultMutableTreeNode("\u751f\u6d3b\u7ba1\u5bb6"));
                            add(new DefaultMutableTreeNode("\u73ed\u8f66\u8def\u7ebf\u7ba1\u7406"));
                        }
                    }));
                tree1.addTreeSelectionListener(e -> tree1ValueChanged(e));
                scrollPane1.setViewportView(tree1);
            }
            splitPane1.setLeftComponent(scrollPane1);

            //======== panel3 ========
            {
                panel3.setLayout(new BorderLayout());
            }
            splitPane1.setRightComponent(panel3);
        }
        contentPane.add(splitPane1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label11;
    private JPanel panel2;
    private JButton btnLogout;
    private JPanel panel4;
    private JLabel labelUserNow;
    private JLabel label2;
    private JSplitPane splitPane1;
    private JScrollPane scrollPane1;
    private JTree tree1;
    private JPanel panel3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
