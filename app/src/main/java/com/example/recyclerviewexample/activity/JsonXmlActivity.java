package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.bean.User;
import com.example.recyclerviewexample.bean.User2;
import com.example.recyclerviewexample.json_xml.ModifierSample;
import com.example.recyclerviewexample.json_xml.Strategies;
import com.example.recyclerviewexample.json_xml.UserTypeAdapter;
import com.example.recyclerviewexample.utils.AppConstant;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

public class JsonXmlActivity extends AppCompatActivity {

    private TextView mJsonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_xml);

        mJsonTextView = findViewById(R.id.tv_json);
        initJson();
    }

    private void initJson() {
        json();
        jsonTolist();
        jsonToBean();
        serialization();
        exposeAnnotation();
        modifier();
        exclusionStrategy();
        formatPrint();
        typeAdapter();
        jsonSerializerAndJsonDeserializer();
    }

    private void json() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("String", "leavesC");
        jsonObject.addProperty("Number_Integer", 23);
        jsonObject.addProperty("Number_Double", 22.9);
        jsonObject.addProperty("Boolean", true);
        jsonObject.addProperty("Char", 'c');

        JsonObject jsonElement  = new JsonObject();
        jsonElement.addProperty("Boolean", false);
        jsonElement.addProperty("Double", 25.9);
        jsonElement.addProperty("Char", 'c');
        jsonObject.add("JsonElement", jsonElement);

        mJsonTextView.setText(jsonObject.toString());
        mJsonTextView.append(AppConstant.LINE_SEPARATOR);
        mJsonTextView.append(AppConstant.LINE_SEPARATOR);
    }

    private void jsonTolist() {
        Gson gson = new Gson();
        String jsonArray = "[\"https://github.com/leavesC\",\"https://www.jianshu.com/u/9df45b87cfdf\",\"Java\",\"Kotlin\",\"Git\",\"GitHub\"]";
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
        for (String s : stringList) {
            mJsonTextView.append(s.toString());
            mJsonTextView.append(AppConstant.LINE_SEPARATOR);
        }
        mJsonTextView.append(AppConstant.LINE_SEPARATOR);
    }

    private void jsonToBean() {
        String userJson = "{\"name\":\"maomao\",\"sex\":true,\"age\":24}";
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        mJsonTextView.append(user.toString());


    }

    private void serialization() {
        mJsonTextView.append(AppConstant.LINE_SEPARATOR);
        String userJson2 = "{\"userName\":\"maomao\",\"sex\":true,\"age\":24}";
        Gson gson2 = new Gson();
        User user2 = gson2.fromJson(userJson2, User.class);
        mJsonTextView.append(user2.toString());

        mJsonTextView.append(AppConstant.LINE_SEPARATOR);
        mJsonTextView.append(AppConstant.LINE_SEPARATOR);
        User user3 = new User("leavesC", 24, true);
        Gson gson3 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        String userToJson = gson3.toJson(user3);
        mJsonTextView.append(userToJson);
    }


    private void exposeAnnotation() {
        //bean当中不标@Expose就是没有,e就没有
        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        User2 user = new User2("A", "B", "C", "D", "E");
        mJsonTextView.append(gson.toJson(user));

        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        String json = "{\"a\":\"A\",\"b\":\"B\",\"c\":\"C\",\"d\":\"D\",\"e\":\"E\"}";
        User2 user2 = gson.fromJson(json,User2.class);
        mJsonTextView.append(user2.toString());
    }


    private void modifier() {
        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        //指定不进行序列化和反序列化操作的访问修饰符字段
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .create();

        ModifierSample modifierSample = new ModifierSample();
        String json = gson.toJson(modifierSample);
        mJsonTextView.append(json);
    }

    private void exclusionStrategy() {
        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        //排除指定字段类型
                        return f.getName().equals("intField");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return clazz.getName().equals(double.class.getName());
                    }
                })
                .create();
        Strategies strategies = new Strategies("stringField", 111, 11.22);
        String json = gson.toJson(strategies);
        mJsonTextView.append(json);

        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        String json2 = "{\"stringField\":\"stringField\",\"intField\":111,\"doubleField\":11.22}";
        Strategies strategies1 = gson.fromJson(json2,Strategies.class);
        mJsonTextView.append(strategies1.toString());
    }


    private void formatPrint() {
        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        Gson gson = new GsonBuilder()
                .serializeNulls() //输出null
                .setPrettyPrinting().create(); //格式化输出
        Strategies strategies = new Strategies(null, 24, 22.333);
        String json = gson.toJson(strategies);
        mJsonTextView.append(json);
    }


    private void typeAdapter() {
        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class,new UserTypeAdapter())
                .create();
        User user = new User("leavesC", 24, true);
        mJsonTextView.append(gson.toJson(user));

        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        String json = "{\"Name\":\"leavesC\",\"Age\":24,\"sex\":true}";
        User user1 = gson.fromJson(json,User.class);
        mJsonTextView.append(user1.toString());
    }


    private void jsonSerializerAndJsonDeserializer() {
        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new JsonSerializer<User>() {
                    @Override
                    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("NameHi",src.getName());
                        jsonObject.addProperty("AgeHi",src.getAge());
                        jsonObject.addProperty("SexHi",src.isSex());
                        return jsonObject;
                    }
                }).registerTypeAdapter(User.class, new JsonDeserializer<User>() {
                    @Override
                    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject jsonObject = json.getAsJsonObject();
                        String name = null;
                        if (jsonObject.has("userName")) {
                            name = jsonObject.get("userName").getAsString();
                        } else if (jsonObject.has("name")) {
                            name = jsonObject.get("name").getAsString();
                        }
                        int age = jsonObject.get("age").getAsInt();
                        boolean sex = jsonObject.get("sex").getAsBoolean();
                        return new User(name,age,sex);
                    }
                })
                .create();

        String json = "{\"userName\":\"leavesC\",\"sex\":true,\"age\":24}";
        User user = gson.fromJson(json,User.class);
        mJsonTextView.append(user.toString());

        mJsonTextView.append(AppConstant.LINE_SEPARATOR2);
        User user2 = new User("leavesC", 24, true);
        mJsonTextView.append(gson.toJson(user2));
    }


}
