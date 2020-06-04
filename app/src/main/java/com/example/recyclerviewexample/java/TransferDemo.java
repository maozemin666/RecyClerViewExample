package com.example.recyclerviewexample.java;

import java.util.ArrayList;
import java.util.Arrays;

public class
TransferDemo {

    static class Student {
        public String name;

        public Student(String name) {
            this.name = name;
        }
    }

    private void collectionCopy() {
        Student stu = new Student("maomao");
        ArrayList<Student> students = new ArrayList<>(Arrays.asList(stu));

        ArrayList<Student> students1 = new ArrayList<>(students);
        for (Student student : students1) {
            System.out.println("student1 name="+student.name);
            student.name = "zemin";
        }

        for (Student student : students) {
            System.out.println("student2 name="+student.name);
        }
    }

    public static void main(String[] args) {
        TransferDemo transferDemo = new TransferDemo();
        transferDemo.collectionCopy();
    }

}
