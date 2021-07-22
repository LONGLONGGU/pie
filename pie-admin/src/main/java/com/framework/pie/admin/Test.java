package com.framework.pie.admin;

public class Test {

     public static void main(String[] args) {
         String str = "ABCDE";
         System.out.println(reverseStringByRecursion(str));
     }
    public static String test() {
        String str = null;
        int i = 0;
        if (i == 0) {
            return str;//直接返回未执行到finally语句块
        }
        try {
            System.out.println("try...");
            return str;
        } finally {
            System.out.println("finally...");
        }
    }

    public static String test2() {
        String str = null;
        int i = 0;
        i = i / 0;//抛出异常未执行到finally语句块
        try {
            System.out.println("try...");
            return str;
        } finally {
            System.out.println("finally...");
        }
    }

    public static String test3() {
        String str = null;
        try {
            System.out.println("try...");
            System.exit(0);//系统退出未执行到finally语句块
            return str;
        } finally {
            System.out.println("finally...");
        }
    }

    public static String getString() {
        String str = "A";
        try {
            str = "B";
            return str;
        } finally {
            System.out.println("finally change return string to C");
            str = "C";
        }
    }

    /**
     * 自己实现
     * @param str
     * @return
     */
    public static String reverseString(String str) {
        if (str != null && str.length() > 0) {
            int len = str.length();
            char[] chars = new char[len];
            for (int i = len - 1; i >= 0; i--) {
                chars[len - 1 - i] = str.charAt(i);
            }
            return new String(chars);
        }
        return str;
    }

    /**
     * 使用 StringBuilder
     * @param str
     * @return
     */
    public static String reverseStringByStringBuilderApi(String str) {
        if (str != null && str.length() > 0) {
            return new StringBuilder(str).reverse().toString();
        }
        return str;
    }
    /**
     * 递归
     * @param str
     * @return
     */
    public static String reverseStringByRecursion(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        System.out.println(str.substring(1) + str.charAt(0));
        return reverseStringByRecursion(str.substring(1)) + str.charAt(0);
    }

    public void aa(){
        Integer a = 8;
        int c = a; ;
    }

   




}
