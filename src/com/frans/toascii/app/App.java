package com.frans.toascii.app;

import com.frans.toascii.util.Image2ASCIIUtil;

public class App {

    public static void main(String[] args) {
        for (String s : Image2ASCIIUtil.image2ascii("timg.jpg", 10, 20)) {
            System.out.println(s);
        }
    }
}
