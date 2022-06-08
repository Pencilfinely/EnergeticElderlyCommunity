package com.eec.dao;

import com.eec.entity.Member;
import com.eec.entity.User;

import java.io.IOException;
import java.util.List;

public interface MemberDao{
    public int add(String name,
    String birth,
    String gender,
    String phone,
    String IC,
    String adress,
    String type,
    String contact,
    String contactPhone,
    String livingManagerUsername,
    String livingManagerPassword,
    String busCode) throws IOException;

    public int add(Member member) throws IOException;

    public void delete(int index) throws IOException;

    public void delete(Member member) throws IOException;

    public List<Member> getAll() throws IOException;

    public int setAll(List<Member> list) throws IOException;
}
