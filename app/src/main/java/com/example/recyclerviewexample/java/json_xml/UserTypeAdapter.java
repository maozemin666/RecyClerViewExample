package com.example.recyclerviewexample.java.json_xml;

import com.example.recyclerviewexample.bean.User;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UserTypeAdapter extends TypeAdapter<User> {

    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("Name").value(value.getName());
        out.name("Age").value(value.getAge());
        out.name("IsSex").value(value.isSex());
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        User user = new User();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                //首字母大小写均合法
                case "name":
                case "Name":
                    user.setName(in.nextString());
                    break;
                case "age":
                case "Age":
                    user.setAge(in.nextInt());
                    break;
                case "sex":
                    user.setSex(in.nextBoolean());
                    break;
            }
        }
        in.endObject();
        return user;
    }
}
