/*
 * Created by JFormDesigner on Wed Jun 08 23:24:19 CST 2022
 */

package com.eec.view;

import java.awt.event.*;
import com.eec.dao.MemberDao;
import com.eec.dao.impl.MemberDaoImpl;
import com.eec.entity.Bus;
import com.eec.entity.Member;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * @author unknown
 */
public class PassengerDialog extends JDialog {

    private static PassengerDialog instance = null;
    private Bus bus;

    public static PassengerDialog getInstance(Bus bus){
        if (instance == null){
            instance = new PassengerDialog(null);
        }
        instance.setBus(bus);
        instance.resetTable();

        return instance;
    }

    public void setBus(Bus bus){
        this.bus = bus;
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
                if(member.getBusCode().equals(this.bus.getBusCode())){
                    Vector vector = new Vector();
                    vector.add(member.getId());
                    vector.add(member.getName());
                    vector.add(member.getIC());

                    data.add(vector);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PassengerDialog(Window owner) {
        super(owner);
        initComponents();
    }

    private void ok(ActionEvent e) {

        this.setVisible(false);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
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
                    panel1.setLayout(new BorderLayout());

                    //---- label1 ----
                    label1.setText("\u4e58\u5ba2\u4fe1\u606f");
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                    panel1.add(label1, BorderLayout.NORTH);

                    //======== scrollPane1 ========
                    {

                        //---- table1 ----
                        table1.setModel(new DefaultTableModel(
                            new Object[][] {
                                {null, null, null},
                            },
                            new String[] {
                                "ID", "\u59d3\u540d", "\u8eab\u4efd\u8bc1"
                            }
                        ));
                        scrollPane1.setViewportView(table1);
                    }
                    panel1.add(scrollPane1, BorderLayout.CENTER);
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
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
