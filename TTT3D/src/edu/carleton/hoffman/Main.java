package edu.carleton.hoffman;

public class Main {

    public static void main(String[] args) {
		String str = "St\"uff";
		String[] strs = str.split("[\"]");
		System.out.println(strs[0].toString());
		System.out.println(strs[1].toString());
    }
}
