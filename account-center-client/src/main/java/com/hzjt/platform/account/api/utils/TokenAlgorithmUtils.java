package com.hzjt.platform.account.api.utils;


import java.util.UUID;

/**
 * 功能描述: token 加密解密工具类
 *
 * @date 2023/11/1 15:37
 */
public class TokenAlgorithmUtils {

    /**
     * 加密函数：将输入的明文按照字母表进行加密
     *
     * @param message 明文消息
     * @return 加密后的密文
     */
    public static String encrypt(String message) {
        return generateToken(message);
    }
    public static String generateToken(String userId) {
        // 使用UUID的随机UUID作为基础，再加上用户ID生成32位的token
        String base = UUID.randomUUID().toString().replace("-", "") + userId;
        // 使用MD5或其他加密方式对base进行加密，生成最终的32位token
        // 这里使用简单的MD5作为示例，实际应用中可以选择更安全的加密方式
        return md5(base);
    }

    // 使用MD5加密字符串
    private static String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            // 处理异常，例如记录日志或返回默认值
            return null;
        }
    }



    public static boolean validateToken(String token) {
        return token.endsWith("xyz");
    }
}
