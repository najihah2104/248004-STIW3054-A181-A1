package com.myassignment;

public class MainTest extends ReadWebPage {

    public MainTest(String data, String info) {
        super(data, info);
    }

    public static void main (String[]args){
        ReadWebPage.collectData();
        ReadWebPage.writeToExcel();
    }
}
