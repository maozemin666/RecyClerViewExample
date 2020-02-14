package com.example.recyclerviewexample.java;

import java.security.PublicKey;

/**
 * https://juejin.im/post/5e08077e51882512820af8d1
 */
public class EnumDemo {

    /**
     * Java定义枚举类型的语句很简约。它有以下特点：
     * <p>
     * 使用关键字enum
     * 类型名称，比如这里的Season
     * 一串允许的值，比如上面定义的春夏秋冬四季
     * 枚举可以单独定义在一个文件中，也可以嵌在其它Java类中
     * 枚举可以实现一个或多个接口（Interface）
     * 可以定义新的变量和方法
     */
    enum Season {
        SPRING, SUMMER, AUTUMN, WINTER
    }

    enum Season2 {
        SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4);

        private final int code;

        Season2(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public String getChineseSeason(Season2 season2) {
        StringBuilder result = new StringBuilder();
        switch (season2) {
            case SPRING:
                result.append("[中文：春天，枚举常量:" + season2.name() + "，数据:" + season2.getCode() + "]");
                break;
            case AUTUMN :
                result.append("[中文：秋天，枚举常量:" + season2.name() + "，数据:" + season2.getCode() + "]");
                break;
            case SUMMER :
                result.append("[中文：夏天，枚举常量:" + season2.name() + "，数据:" + season2.getCode() + "]");
                break;
            case WINTER :
                result.append("[中文：冬天，枚举常量:" + season2.name() + "，数据:" + season2.getCode() + "]");
                break;
            default :
                result.append("地球没有的季节 " + season2.name());
                break;
        }
        return result.toString();
    }

    public void doSomething() {
        for (Season2 season2 : Season2.values()) {
            System.out.println(getChineseSeason(season2));
        }
//        System.out.println(getChineseSeason(5));
        //此处已经是编译不通过了，这就保证了类型安全
    }

    /**
     * 通过调用枚举类型实例的 values() 方法可以将枚举的所有成员以数组形式返回，也可以通过该方法获取枚举类型的成员。
     */
    public void values() {
        for (int i = 0; i < Season.values().length; i++) {
            System.out.println("枚举："+Season.values()[i]);
        }
    }

    public void valueOf() {
        Season winter = Season.valueOf("WINTER");
        System.out.println(winter);
    }

    public void ordinal() {
        for (int i = 0; i < Season.values().length; i++) {
            System.out.println("索引："+Season.values()[i].ordinal()+"  枚举值："+Season.values()[i]);
        }
    }

    /**
     *  枚举的单例
     *
     *  因为单例本身就是私有构造的
     */
    public enum EnumSingleton {
        INSTANCE;

        public static EnumSingleton getInstance() {
            return INSTANCE;
        }
    }


    public static void main(String[] args) {
        EnumDemo enumDemo = new EnumDemo();
//        System.out.println(enumDemo.getChineseSeason(Season2.SPRING));
        enumDemo.doSomething();
        System.out.println("=======values=========");
        enumDemo.values();
        System.out.println("=======valueOf=========");
        enumDemo.valueOf();
        System.out.println("=======ordinal=========");
        enumDemo.ordinal();
    }

}



