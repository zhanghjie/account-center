package com.hzjt.platform.account.api.utils;


/**
 * 功能描述: token 加密解密工具类
 *
 * @date 2023/11/1 15:37
 */
public class TokenAlgorithmUtils {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz12345678";

    private static String SEPARATOR = "-";

    /**
     * 加密函数：将输入的明文按照字母表进行加密
     *
     * @param message 明文消息
     * @return 加密后的密文
     */
    public static String encrypt(String message) {
        message = message + SEPARATOR + System.currentTimeMillis();
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int index = ALPHABET.indexOf(c);
            if (index != -1) {
                encryptedMessage.append(ALPHABET.charAt((index + 10) % 34));
            } else {
                encryptedMessage.append(c);
            }
        }

        return encryptedMessage.toString();
    }


    /**
     * 解密方法，将给定的密文按照字母表替换规则进行解密，返回解密后的明文。
     *
     * @param message 密文字符串
     * @return 解密后的明文字符串
     */
    public static String decrypt(String message) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int index = ALPHABET.indexOf(c);
            if (index != -1) {
                decryptedMessage.append(ALPHABET.charAt((index - 10 + 26) % 26));
            } else {
                decryptedMessage.append(c);
            }
        }
        String[] split = decryptedMessage.toString().split(SEPARATOR);
        return split[0];
    }


    public static boolean validateToken(String token) {
        return token.endsWith("xyz");
    }
}
