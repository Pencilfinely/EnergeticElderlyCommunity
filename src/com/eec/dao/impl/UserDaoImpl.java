package com.eec.dao.impl;

import com.eec.dao.UserDao;
import com.eec.entity.Member;
import com.eec.entity.User;
import com.eec.utils.Tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static String filePath = "User.json";
    private static UserDaoImpl instance = null;

    public static UserDaoImpl getInstance(){
        if(instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }

    private UserDaoImpl(){}

    @Override
    public User login(String username, String password) throws IOException {
        File file = new File(filePath);
        User u = new User(0,"admin","000000","","","","","管理员");
        if(!file.exists()){
            file.createNewFile();
            List<User> list = new ArrayList<>();
            list.add(u);
            Tools.writeListToJSON(filePath,list);
        }

        List<User> list = Tools.getJsonToList(filePath, User.class);

        for(User user : list){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }

        return null;
    }

    @Override
    public int add(String username, String password, String name, String gender, String birth, String phone, String permission) throws IOException {
        List<User> list = Tools.getJsonToList(filePath, User.class);
        int newId = list.get(list.size() - 1).getId() + 1;
        list.add(new User(newId,username,password,name,gender,birth,phone,permission));

        Tools.writeListToJSON(filePath,list);
        return 1;
    }

    @Override
    public int add(User user) throws IOException {
        List<User> list = Tools.getJsonToList(filePath, User.class);
        int newId = list.get(list.size() - 1).getId() + 1;
        list.add(user);

        Tools.writeListToJSON(filePath,list);
        return 1;
    }

    @Override
    public void delete(int index) throws IOException {
        List<User> list = Tools.getJsonToList(filePath, User.class);
        list.remove(index);

        Tools.writeListToJSON(filePath,list);
    }

    @Override
    public void delete(User user) throws IOException {
        List<User> list = Tools.getJsonToList(filePath, User.class);
        for (User u : list) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())){
                list.remove(u);
                break;
            }
        }

        Tools.writeListToJSON(filePath,list);
    }

    @Override
    public List<User> getAll() throws IOException {
        File file = new File(filePath);
        User user = new User(0,"admin","000000","","","","","管理员");
        if(!file.exists()){
            file.createNewFile();
            List<User> list = new ArrayList<>();
            list.add(user);
            Tools.writeListToJSON(filePath,list);
        }

        List<User> list = Tools.getJsonToList(filePath, User.class);

        if(list.isEmpty()){
            list.add(new User(0,"","","","","","",""));
        }else {
            list.remove(new User(0,"","","","","","",""));
        }

        return list;
    }

    @Override
    public int setAll(List<User> list) throws IOException {
        Tools.writeListToJSON(filePath,list);
        return 1;
    }

}
