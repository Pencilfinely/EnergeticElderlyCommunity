package com.eec.dao.impl;

import com.eec.dao.MemberDao;
import com.eec.entity.Member;
import com.eec.entity.User;
import com.eec.utils.Tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class MemberDaoImpl implements MemberDao {

    private static String filePath = "Member.json";
    private static MemberDaoImpl instance = null;

    public static MemberDaoImpl getInstance(){
        if(instance == null){
            instance = new MemberDaoImpl();
        }

        return instance;
    }

    private MemberDaoImpl(){}

    @Override
    public int add(String name, String birth, String gender, String phone, String IC,
                   String adress, String type, String contact, String contactPhone, String livingManagerUsername,
                   String livingManagerPassword, String busCode) throws IOException {
        List<Member> list = Tools.getJsonToList(filePath, Member.class);
        int newId = list.get(list.size() - 1).getId() + 1;
        list.add(new Member(newId,name,birth,gender,phone,IC,adress,type,contact,contactPhone,livingManagerUsername,livingManagerPassword,busCode));

        Tools.writeListToJSON(filePath,list);
        return 1;

    }

    @Override
    public int add(Member member) throws IOException{
        List<Member> list = Tools.getJsonToList(filePath, Member.class);
        int newId = list.get(list.size() - 1).getId() + 1;
        list.add(member);

        Tools.writeListToJSON(filePath,list);
        return 1;
    }

    @Override
    public void delete(int index) throws IOException {
        List<Object> list = Tools.getJsonToList(filePath, Member.class);
        list.remove(index);

        Tools.writeListToJSON(filePath,list);
    }

    @Override
    public void delete(Member member) throws IOException {
        List<Member> list = Tools.getJsonToList(filePath, Member.class);
        for (Member m : list) {
            if (m.getIC().equals(member.getIC())){
                list.remove(m);
                break;
            }
        }

        Tools.writeListToJSON(filePath,list);
    }

    @Override
    public List<Member> getAll() throws IOException {
        File file = new File(filePath);
        Member member = new Member(0,"","","","","","","","","","","","");
        if(!file.exists()){
            file.createNewFile();
            List<Member> list = new ArrayList<>();
            list.add(member);
            Tools.writeListToJSON(filePath,list);
        }
        List<Member> list = Tools.getJsonToList(filePath, Member.class);

        return list;
    }

    @Override
    public int setAll(List<Member> list) throws IOException {
        Tools.writeListToJSON(filePath,list);
        return 1;
    }
}
