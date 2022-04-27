package com.stream;

import javax.jws.soap.SOAPBinding;
import java.util.*;

public class test01 {
    public static void main(String[] args) {

        User a = new User(1, "a", 25);
        User b = new User(2, "b", 26);
        User c = new User(3, "c", 29);
        User d = new User(4, "d", 18);
        User e = new User(5, "e", 6);
        User f = new User(6, "f", 35);
        List<User>userList= Arrays.asList(a,b,c,d,e,f);
        userList.stream().filter(user ->  user.getAge()>23).filter(user -> user.getId()%2==0)
                .map(user -> user.getName().toUpperCase())
                .sorted()
                .forEach(System.out::println);


    }

}
