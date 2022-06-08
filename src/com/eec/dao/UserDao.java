package com.eec.dao;

import com.eec.entity.Member;
import com.eec.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserDao{

    public User login(String username, String password) throws IOException;

    public int add(String username, String password, String name, String gender, String birth, String phone, String permission) throws IOException;

    public int add(User user) throws IOException;

    public void delete(int index) throws IOException;

    public void delete(User user) throws IOException;

    public List<User> getAll() throws IOException;

    public int setAll(List<User> list) throws IOException;

}
