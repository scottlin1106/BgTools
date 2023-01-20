package com.bgtools;

import static com.bgtools.Utils.AESDecrypt;

public class SafeTest {
    public static void main(String[] args) {
        String keyword = "230104051721268698";
        String dataStr = keyword.substring(6, 8);
        System.out.println("[ dataStr ] : " + dataStr);
        System.out.println("[ validateDate ] : " + DateUtil.validatehours(dataStr));
    }
}
