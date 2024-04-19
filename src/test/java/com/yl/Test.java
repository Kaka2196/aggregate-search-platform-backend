package com.yl;

public class Test {
    public Test(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String name;
    public String gender;
    class Inner{
        public int age;

        public Inner(int age) {
            this.age = age;
        }

    }

    public static void main(String[] args) {
        Test.Inner inner = new Test("qaz","Tank").new Inner(22);
        Test outer = new Test("qaz","Tank");
        System.out.println();
    }
}
