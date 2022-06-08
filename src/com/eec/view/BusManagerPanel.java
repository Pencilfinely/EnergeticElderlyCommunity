/*
 * Created by JFormDesigner on Sun Jun 05 16:13:25 CST 2022
 */

package com.eec.view;

import java.awt.event.*;
import com.eec.dao.BusDao;
import com.eec.dao.MemberDao;
import com.eec.dao.UserDao;
import com.eec.dao.impl.BusDaoImpl;
import com.eec.dao.impl.MemberDaoImpl;
import com.eec.dao.impl.UserDaoImpl;
import com.eec.entity.Bus;
import com.eec.entity.Member;
import com.eec.entity.User;
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
public class BusManagerPanel extends JPanel {

    private static BusManagerPanel instance = null;

    public static BusManagerPanel getInstance(){
        if(instance == null){
            instance = new BusManagerPanel();
        }
        instance.resetTable();

        return instance;
    }

    public void resetTable(){
        BusDao busDao = BusDaoImpl.getInstance();
        try {
            List<Bus> list = busDao.getAll();

            DefaultTableModel model = (DefaultTableModel)this.table1.getModel();

            model.fireTableDataChanged();
            Vector<Vector> data = model.getDataVector();
            data.clear();
            for(Bus bus : list){
                if(bus.getBusCode().equals("")){
                    continue;
                }
                Vector vector = new Vector();
                vector.add(bus.getId());
                vector.add(bus.getBusCode());
                vector.add(bus.getName());
                vector.add(bus.getDirection());
                vector.add(bus.getDate());
                vector.add(bus.getPeriod());
                vector.add(bus.getTime());
                if(!bus.getEndBookingTime().equals("")){
                    vector.add(bus.getEndBookingTime());
                }else {
                    vector.add("未确认");
                }
                vector.add(bus.getBookingCnt());

                data.add(vector);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private BusManagerPanel() {
        initComponents();

        resetTable();
    }

    private void btnReset(ActionEvent e) {
        resetTable();
        this.btnReset.setVisible(false);
    }

    private void table1MousePressed(MouseEvent e) {
        int row = this.table1.getSelectedRow();
        BusDao busDao = BusDaoImpl.getInstance();
        List<Bus> list = null;

        try {
            list = busDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (Bus bus : list) {
            if(bus.getId() == (int)this.table1.getValueAt(row, 0) &&
                    bus.getBusCode().equals((String) this.table1.getValueAt(row, 1)) &&
                    bus.getName().equals((String) this.table1.getValueAt(row, 2))){
                this.txtBusCode.setText(bus.getBusCode());
                this.txtName.setText(bus.getName());

                if(bus.getType().equals("岛内班车")){
                    this.radioButtonInner.setSelected(true);
                }else if(bus.getType().equals("岛外班车")){
                    this.radioButtonOuter.setSelected(true);
                }

                if(bus.getDirection().equals("上行")){
                    this.radioButtonUp.setSelected(true);
                }else if(bus.getDirection().equals("下行")){
                    this.radioButtonDown.setSelected(true);
                }

                int dateIndex = 0;
                if(bus.getDate().equals("")){
                    dateIndex = 0;
                }else if(bus.getDate().equals("星期一")){
                    dateIndex = 1;
                }else if(bus.getDate().equals("星期二")){
                    dateIndex = 2;
                }else if(bus.getDate().equals("星期三")){
                    dateIndex = 3;
                }else if(bus.getDate().equals("星期四")){
                    dateIndex = 4;
                }else if(bus.getDate().equals("星期五")){
                    dateIndex = 5;
                }else if(bus.getDate().equals("星期六")){
                    dateIndex = 6;
                }else if(bus.getDate().equals("星期日")){
                    dateIndex = 7;
                }else if(bus.getDate().equals("每天")){
                    dateIndex = 8;
                }
                this.comboBoxDate.setSelectedIndex(dateIndex);

                int periodIndex = 0;
                if(bus.getPeriod().equals("")){
                    periodIndex = 0;
                }else if(bus.getPeriod().equals("凌晨")){
                    periodIndex = 1;
                }else if(bus.getPeriod().equals("早上")){
                    periodIndex = 2;
                }else if(bus.getPeriod().equals("上午")){
                    periodIndex = 3;
                }else if(bus.getPeriod().equals("中午")){
                    periodIndex = 4;
                }else if(bus.getPeriod().equals("下午")){
                    periodIndex = 5;
                }else if(bus.getPeriod().equals("傍晚")){
                    periodIndex = 6;
                }else if(bus.getPeriod().equals("晚上")){
                    periodIndex = 7;
                }
                this.comboBoxPeriod.setSelectedIndex(periodIndex);

                this.txtTime.setText(bus.getTime());

                break;
            }
        }
    }

    private void btnRefresh(ActionEvent e) {
        this.txtBusCode.setText("");
        this.txtName.setText("");

        ButtonGroup buttonGroupIO = new ButtonGroup();
        buttonGroupIO.add(this.radioButtonInner);
        buttonGroupIO.add(this.radioButtonOuter);
        buttonGroupIO.clearSelection();

        ButtonGroup buttonGroupUD = new ButtonGroup();
        buttonGroupUD.add(this.radioButtonUp);
        buttonGroupUD.add(this.radioButtonDown);
        buttonGroupUD.clearSelection();

        this.comboBoxDate.setSelectedIndex(0);
        this.comboBoxPeriod.setSelectedIndex(0);
        this.txtTime.setText("");
    }

    private void btnAdd(ActionEvent e) {
        BusDao busDao = BusDaoImpl.getInstance();

        String type = "";
        if(this.radioButtonInner.isSelected()){
            type = "岛内班车";
        }else if(this.radioButtonOuter.isSelected()){
            type = "岛外班车";
        }

        String direction = "";
        if(this.radioButtonUp.isSelected()){
            direction = "上行";
        }else if(this.radioButtonDown.isSelected()){
            direction = "下行";
        }

        String date = "";
        if(this.comboBoxDate.getSelectedIndex() == 0){
            date = "";
        }else if(this.comboBoxDate.getSelectedIndex() == 1){
            date = "星期一";
        }else if(this.comboBoxDate.getSelectedIndex() == 2){
            date = "星期二";
        }else if(this.comboBoxDate.getSelectedIndex() == 3){
            date = "星期三";
        }else if(this.comboBoxDate.getSelectedIndex() == 4){
            date = "星期四";
        }else if(this.comboBoxDate.getSelectedIndex() == 5){
            date = "星期五";
        }else if(this.comboBoxDate.getSelectedIndex() == 6){
            date = "星期六";
        }else if(this.comboBoxDate.getSelectedIndex() == 7){
            date = "星期日";
        }else if(this.comboBoxDate.getSelectedIndex() == 8){
            date = "每天";
        }

        String period = "";
        if(this.comboBoxPeriod.getSelectedIndex() == 0){
            period = "";
        }else if(this.comboBoxPeriod.getSelectedIndex() == 1){
            period = "凌晨";
        }else if(this.comboBoxPeriod.getSelectedIndex() == 2){
            period = "早上";
        }else if(this.comboBoxPeriod.getSelectedIndex() == 3){
            period = "上午";
        }else if(this.comboBoxPeriod.getSelectedIndex() == 4){
            period = "中午";
        }else if(this.comboBoxPeriod.getSelectedIndex() == 5){
            period = "下午";
        }else if(this.comboBoxPeriod.getSelectedIndex() == 6){
            period = "傍晚";
        }else if(this.comboBoxPeriod.getSelectedIndex() == 7){
            period = "晚上";
        }

        try {
            for(int i = 0; i < this.table1.getRowCount(); i++){
                if(this.txtBusCode.getText().equals(this.table1.getValueAt(i,1))){
                    JOptionPane.showMessageDialog(this,"已在表中！");
                    return;
                }
            }
            busDao.add(this.txtBusCode.getText(),this.txtName.getText(),type,direction,date,period,this.txtTime.getText(),"",0);
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

            BusDao busDao = BusDaoImpl.getInstance();
            MemberDao memberDao = MemberDaoImpl.getInstance();

            List<Bus> list = null;
            List<Member> memberList = null;
//            ((DefaultTableModel)this.table1.getModel()).removeRow(row);

            try {
                list = busDao.getAll();
                memberList = memberDao.getAll();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"文件同步失败！");
                throw new RuntimeException(ex);
            }

            for (Bus bus : list) {
                if(bus.getId() == (int)this.table1.getValueAt(row,0) &&
                        bus.getBusCode().equals(this.table1.getValueAt(row,1))){

                    for (Member member : memberList) {
                        if (member.getBusCode().equals(bus.getBusCode())){
                            member.setBusCode("");
                        }
                    }

                    try {
                        memberDao.setAll(memberList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        busDao.delete(bus);
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

            BusDao busDao = BusDaoImpl.getInstance();
            MemberDao memberDao = MemberDaoImpl.getInstance();

            List<Bus> list = null;
            List<Member> memberList = null;

            try {
                list = busDao.getAll();
                memberList = memberDao.getAll();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,"文件同步失败！");
                throw new RuntimeException(ex);
            }

            String type = "";
            if(this.radioButtonInner.isSelected()){
                type = "岛内班车";
            }else if(this.radioButtonOuter.isSelected()){
                type = "岛外班车";
            }

            String direction = "";
            if(this.radioButtonUp.isSelected()){
                direction = "上行";
            }else if(this.radioButtonDown.isSelected()){
                direction = "下行";
            }

            String date = "";
            if(this.comboBoxDate.getSelectedIndex() == 0){
                date = "";
            }else if(this.comboBoxDate.getSelectedIndex() == 1){
                date = "星期一";
            }else if(this.comboBoxDate.getSelectedIndex() == 2){
                date = "星期二";
            }else if(this.comboBoxDate.getSelectedIndex() == 3){
                date = "星期三";
            }else if(this.comboBoxDate.getSelectedIndex() == 4){
                date = "星期四";
            }else if(this.comboBoxDate.getSelectedIndex() == 5){
                date = "星期五";
            }else if(this.comboBoxDate.getSelectedIndex() == 6){
                date = "星期六";
            }else if(this.comboBoxDate.getSelectedIndex() == 7){
                date = "星期日";
            }else if(this.comboBoxDate.getSelectedIndex() == 8){
                date = "每天";
            }

            String period = "";
            if(this.comboBoxPeriod.getSelectedIndex() == 0){
                period = "";
            }else if(this.comboBoxPeriod.getSelectedIndex() == 1){
                period = "凌晨";
            }else if(this.comboBoxPeriod.getSelectedIndex() == 2){
                period = "早上";
            }else if(this.comboBoxPeriod.getSelectedIndex() == 3){
                period = "上午";
            }else if(this.comboBoxPeriod.getSelectedIndex() == 4){
                period = "中午";
            }else if(this.comboBoxPeriod.getSelectedIndex() == 5){
                period = "下午";
            }else if(this.comboBoxPeriod.getSelectedIndex() == 6){
                period = "傍晚";
            }else if(this.comboBoxPeriod.getSelectedIndex() == 7){
                period = "晚上";
            }

            for (Bus bus : list) {
                if(bus.getBusCode().equals(this.table1.getValueAt(row,1))){
                    bus.setBusCode(this.txtBusCode.getText());
                    bus.setName(this.txtName.getText());
                    bus.setType(type);
                    bus.setDirection(direction);
                    bus.setDate(date);
                    bus.setPeriod(period);
                    bus.setTime(this.txtTime.getText());

                    for (Member member : memberList) {
                        if(member.getBusCode().equals(bus.getBusCode())){
                            member.setBusCode(bus.getBusCode());
                        }
                    }
                    try {
                        memberDao.setAll(memberList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        busDao.setAll(list);
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

        BusDao busDao = BusDaoImpl.getInstance();
        try {
            List<Bus> list = busDao.getAll();

            DefaultTableModel model = (DefaultTableModel)this.table1.getModel();

            model.fireTableDataChanged();
            Vector<Vector> data = model.getDataVector();
            data.clear();
            for(Bus bus : list){
                if(bus.getId() == 0 && bus.getBusCode().equals("")){
                    continue;
                }
                String type = "";
                if(this.radioButtonInner.isSelected()){
                    type = "岛内班车";
                }else if(this.radioButtonOuter.isSelected()){
                    type = "岛外班车";
                }

                String direction = "";
                if(this.radioButtonUp.isSelected()){
                    direction = "上行";
                }else if(this.radioButtonDown.isSelected()){
                    direction = "下行";
                }

                String date = "";
                if(this.comboBoxDate.getSelectedIndex() == 0){
                    date = "";
                }else if(this.comboBoxDate.getSelectedIndex() == 1){
                    date = "星期一";
                }else if(this.comboBoxDate.getSelectedIndex() == 2){
                    date = "星期二";
                }else if(this.comboBoxDate.getSelectedIndex() == 3){
                    date = "星期三";
                }else if(this.comboBoxDate.getSelectedIndex() == 4){
                    date = "星期四";
                }else if(this.comboBoxDate.getSelectedIndex() == 5){
                    date = "星期五";
                }else if(this.comboBoxDate.getSelectedIndex() == 6){
                    date = "星期六";
                }else if(this.comboBoxDate.getSelectedIndex() == 7){
                    date = "星期日";
                }else if(this.comboBoxDate.getSelectedIndex() == 8){
                    date = "每天";
                }

                String period = "";
                if(this.comboBoxPeriod.getSelectedIndex() == 0){
                    period = "";
                }else if(this.comboBoxPeriod.getSelectedIndex() == 1){
                    period = "凌晨";
                }else if(this.comboBoxPeriod.getSelectedIndex() == 2){
                    period = "早上";
                }else if(this.comboBoxPeriod.getSelectedIndex() == 3){
                    period = "上午";
                }else if(this.comboBoxPeriod.getSelectedIndex() == 4){
                    period = "中午";
                }else if(this.comboBoxPeriod.getSelectedIndex() == 5){
                    period = "下午";
                }else if(this.comboBoxPeriod.getSelectedIndex() == 6){
                    period = "傍晚";
                }else if(this.comboBoxPeriod.getSelectedIndex() == 7){
                    period = "晚上";
                }

                if((bus.getBusCode().equals((String) this.txtBusCode.getText()) || ((String)this.txtBusCode.getText()).equals("")) &&
                        (bus.getName().equals((String) this.txtName.getText()) || ((String)this.txtName.getText()).equals("")) &&
                        (bus.getType().equals(type) || type.equals("")) &&
                        (bus.getDirection().equals(direction) || direction.equals("")) &&
                        (bus.getDate().equals(date) || date.equals("")) &&
                        (bus.getPeriod().equals(period) || period.equals("")) &&
                        (bus.getTime().equals((String) this.txtTime.getText()) || ((String)this.txtTime.getText()).equals(""))
                ){
                    Vector vector = new Vector();
                    vector.add(bus.getId());
                    vector.add(bus.getBusCode());
                    vector.add(bus.getName());
                    vector.add(bus.getDirection());
                    vector.add(bus.getDate());
                    vector.add(bus.getPeriod());
                    vector.add(bus.getTime());
                    if(!bus.getEndBookingTime().equals("")){
                        vector.add(bus.getEndBookingTime());
                    }else {
                        vector.add("未确认");
                    }
                    vector.add(bus.getBookingCnt());

                    data.add(vector);
                }

            }


        } catch (IOException b) {
            b.printStackTrace();
        }
    }

    private void btnEndBookingTime(ActionEvent e) {
        int row = this.table1.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选择班车！");
            return;
        }
        BusDao busDao = BusDaoImpl.getInstance();
        List<Bus> list = null;
        Bus bus = null;

        try {
            list = busDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (Bus b : list){
            if(b.getBusCode().equals(this.table1.getValueAt(row,1))){
                bus = b;
                break;
            }
        }

        SetEndBookingTimeDialog.getInstance(bus).setSize(250,150);
        SetEndBookingTimeDialog.getInstance(bus).setVisible(true);
    }

    private void btnBook(ActionEvent e) {
        int row = this.table1.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选择班车！");
            return;
        }
        BusDao busDao = BusDaoImpl.getInstance();
        List<Bus> list = null;
        Bus bus = null;

        try {
            list = busDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (Bus b : list){
            if(b.getBusCode().equals(this.table1.getValueAt(row,1))){
                bus = b;
                break;
            }
        }

        try {
            if((Tools.weekHelper().equals(bus.getDate()) || bus.getDate().equals("每天")) && Tools.timeEnough(bus.getEndBookingTime())){
                BookDialog.getInstance(bus).setVisible(true);
                BookDialog.getInstance(bus).setSize(400,170);
            }else {
                JOptionPane.showMessageDialog(this,"预约失败：时间非法！");
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this,"截止时间未确认或格式非法！");
            throw new RuntimeException(ex);
        }

    }

    private void btnPassenger(ActionEvent e) {
        int row = this.table1.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"未选择班车！");
            return;
        }
        BusDao busDao = BusDaoImpl.getInstance();
        List<Bus> list = null;
        Bus bus = null;

        try {
            list = busDao.getAll();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"文件同步失败！");
            throw new RuntimeException(ex);
        }

        for (Bus b : list){
            if(b.getBusCode().equals(this.table1.getValueAt(row,1))){
                bus = b;
                break;
            }
        }

        PassengerDialog.getInstance(bus).setSize(500,400);
        PassengerDialog.getInstance(bus).setVisible(true);
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        label2 = new JLabel();
        txtBusCode = new JTextField();
        label3 = new JLabel();
        txtName = new JTextField();
        label4 = new JLabel();
        panel4 = new JPanel();
        radioButtonInner = new JRadioButton();
        radioButtonOuter = new JRadioButton();
        label5 = new JLabel();
        panel5 = new JPanel();
        radioButtonUp = new JRadioButton();
        radioButtonDown = new JRadioButton();
        label6 = new JLabel();
        comboBoxDate = new JComboBox<>();
        label7 = new JLabel();
        comboBoxPeriod = new JComboBox<>();
        label8 = new JLabel();
        txtTime = new JTextField();
        panel6 = new JPanel();
        panel9 = new JPanel();
        btnAdd = new JButton();
        btnDel = new JButton();
        btnEdt = new JButton();
        btnQue = new JButton();
        btnReset = new JButton();
        panel10 = new JPanel();
        btnEndBookingTime = new JButton();
        btnBook = new JButton();
        btnPassenger = new JButton();
        panel7 = new JPanel();
        panel8 = new JPanel();
        btnRefresh = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

            //---- label1 ----
            label1.setText("\u73ed\u8f66\u8def\u7ebf\u7ba1\u7406");
            label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
            panel1.add(label1);
        }
        add(panel1, BorderLayout.NORTH);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //======== panel3 ========
            {
                panel3.setLayout(new GridLayout(4, 4));

                //---- label2 ----
                label2.setText("\u7ebf\u8def\u4ee3\u7801");
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                panel3.add(label2);
                panel3.add(txtBusCode);

                //---- label3 ----
                label3.setText("\u7ebf\u8def\u540d\u79f0");
                label3.setHorizontalAlignment(SwingConstants.CENTER);
                panel3.add(label3);
                panel3.add(txtName);

                //---- label4 ----
                label4.setText("\u7ebf\u8def\u7c7b\u578b");
                label4.setHorizontalAlignment(SwingConstants.CENTER);
                panel3.add(label4);

                //======== panel4 ========
                {
                    panel4.setLayout(new GridLayout(1, 2));

                    //---- radioButtonInner ----
                    radioButtonInner.setText("\u5c9b\u5185\u73ed\u8f66");
                    radioButtonInner.setHorizontalAlignment(SwingConstants.CENTER);
                    panel4.add(radioButtonInner);

                    //---- radioButtonOuter ----
                    radioButtonOuter.setText("\u5c9b\u5916\u73ed\u8f66");
                    radioButtonOuter.setHorizontalAlignment(SwingConstants.CENTER);
                    panel4.add(radioButtonOuter);
                }
                panel3.add(panel4);

                //---- label5 ----
                label5.setText("\u7ebf\u8def\u65b9\u5411");
                label5.setHorizontalAlignment(SwingConstants.CENTER);
                panel3.add(label5);

                //======== panel5 ========
                {
                    panel5.setLayout(new GridLayout(1, 2));

                    //---- radioButtonUp ----
                    radioButtonUp.setText("\u4e0a\u884c");
                    radioButtonUp.setHorizontalAlignment(SwingConstants.CENTER);
                    panel5.add(radioButtonUp);

                    //---- radioButtonDown ----
                    radioButtonDown.setText("\u4e0b\u884c");
                    radioButtonDown.setHorizontalAlignment(SwingConstants.CENTER);
                    panel5.add(radioButtonDown);
                }
                panel3.add(panel5);

                //---- label6 ----
                label6.setText("\u8fd0\u8425\u65e5\u671f");
                label6.setHorizontalAlignment(SwingConstants.CENTER);
                panel3.add(label6);

                //---- comboBoxDate ----
                comboBoxDate.setModel(new DefaultComboBoxModel<>(new String[] {
                    " ",
                    "\u661f\u671f\u4e00",
                    "\u661f\u671f\u4e8c",
                    "\u661f\u671f\u4e09",
                    "\u661f\u671f\u56db",
                    "\u661f\u671f\u4e94",
                    "\u661f\u671f\u516d",
                    "\u661f\u671f\u65e5",
                    "\u6bcf\u5929"
                }));
                panel3.add(comboBoxDate);

                //---- label7 ----
                label7.setText("\u8fd0\u8425\u65f6\u6bb5");
                label7.setHorizontalAlignment(SwingConstants.CENTER);
                panel3.add(label7);

                //---- comboBoxPeriod ----
                comboBoxPeriod.setModel(new DefaultComboBoxModel<>(new String[] {
                    " ",
                    "\u51cc\u6668",
                    "\u65e9\u4e0a",
                    "\u4e0a\u5348",
                    "\u4e2d\u5348",
                    "\u4e0b\u5348",
                    "\u508d\u665a",
                    "\u665a\u4e0a"
                }));
                panel3.add(comboBoxPeriod);

                //---- label8 ----
                label8.setText("\u53d1\u8f66\u65f6\u95f4");
                label8.setHorizontalAlignment(SwingConstants.CENTER);
                panel3.add(label8);
                panel3.add(txtTime);
            }
            panel2.add(panel3, BorderLayout.SOUTH);

            //======== panel6 ========
            {
                panel6.setLayout(new BorderLayout());

                //======== panel9 ========
                {
                    panel9.setLayout(new BoxLayout(panel9, BoxLayout.X_AXIS));

                    //---- btnAdd ----
                    btnAdd.setText("\u65b0\u589e");
                    btnAdd.addActionListener(e -> btnAdd(e));
                    panel9.add(btnAdd);

                    //---- btnDel ----
                    btnDel.setText("\u5220\u9664");
                    btnDel.addActionListener(e -> btnDel(e));
                    panel9.add(btnDel);

                    //---- btnEdt ----
                    btnEdt.setText("\u4fee\u6539");
                    btnEdt.addActionListener(e -> btnEdt(e));
                    panel9.add(btnEdt);

                    //---- btnQue ----
                    btnQue.setText("\u67e5\u8be2");
                    btnQue.addActionListener(e -> btnQue(e));
                    panel9.add(btnQue);

                    //---- btnReset ----
                    btnReset.setText("\u91cd\u7f6e");
                    btnReset.addActionListener(e -> btnReset(e));
                    panel9.add(btnReset);
                }
                panel6.add(panel9, BorderLayout.WEST);

                //======== panel10 ========
                {
                    panel10.setLayout(new BoxLayout(panel10, BoxLayout.X_AXIS));

                    //---- btnEndBookingTime ----
                    btnEndBookingTime.setText("\u622a\u6b62\u65f6\u95f4");
                    btnEndBookingTime.addActionListener(e -> btnEndBookingTime(e));
                    panel10.add(btnEndBookingTime);

                    //---- btnBook ----
                    btnBook.setText("\u767b\u8bb0");
                    btnBook.addActionListener(e -> btnBook(e));
                    panel10.add(btnBook);

                    //---- btnPassenger ----
                    btnPassenger.setText("\u67e5\u770b\u4e58\u5ba2");
                    btnPassenger.addActionListener(e -> btnPassenger(e));
                    panel10.add(btnPassenger);
                }
                panel6.add(panel10, BorderLayout.EAST);
            }
            panel2.add(panel6, BorderLayout.NORTH);

            //======== panel7 ========
            {
                panel7.setLayout(new BorderLayout());

                //======== panel8 ========
                {
                    panel8.setLayout(new BoxLayout(panel8, BoxLayout.X_AXIS));

                    //---- btnRefresh ----
                    btnRefresh.setText("\u6e05\u7a7a");
                    btnRefresh.addActionListener(e -> btnRefresh(e));
                    panel8.add(btnRefresh);
                }
                panel7.add(panel8, BorderLayout.SOUTH);

                //======== scrollPane1 ========
                {

                    //---- table1 ----
                    table1.setModel(new DefaultTableModel(
                        new Object[][] {
                            {null, null, null, null, null, null, null, null, null},
                        },
                        new String[] {
                            "ID", "\u7ebf\u8def\u4ee3\u7801", "\u7ebf\u8def\u540d\u79f0", "\u65b9\u5411", "\u8fd0\u8425\u65e5\u671f", "\u8fd0\u8425\u65f6\u6bb5", "\u53d1\u8f66\u65e5\u671f", "\u622a\u6b62\u65f6\u95f4", "\u9884\u7ea6\u4eba\u6570"
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
                panel7.add(scrollPane1, BorderLayout.CENTER);
            }
            panel2.add(panel7, BorderLayout.CENTER);
        }
        add(panel2, BorderLayout.CENTER);

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButtonInner);
        buttonGroup1.add(radioButtonOuter);

        //---- buttonGroup2 ----
        var buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(radioButtonUp);
        buttonGroup2.add(radioButtonDown);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel label2;
    private JTextField txtBusCode;
    private JLabel label3;
    private JTextField txtName;
    private JLabel label4;
    private JPanel panel4;
    private JRadioButton radioButtonInner;
    private JRadioButton radioButtonOuter;
    private JLabel label5;
    private JPanel panel5;
    private JRadioButton radioButtonUp;
    private JRadioButton radioButtonDown;
    private JLabel label6;
    private JComboBox<String> comboBoxDate;
    private JLabel label7;
    private JComboBox<String> comboBoxPeriod;
    private JLabel label8;
    private JTextField txtTime;
    private JPanel panel6;
    private JPanel panel9;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnEdt;
    private JButton btnQue;
    private JButton btnReset;
    private JPanel panel10;
    private JButton btnEndBookingTime;
    private JButton btnBook;
    private JButton btnPassenger;
    private JPanel panel7;
    private JPanel panel8;
    private JButton btnRefresh;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
