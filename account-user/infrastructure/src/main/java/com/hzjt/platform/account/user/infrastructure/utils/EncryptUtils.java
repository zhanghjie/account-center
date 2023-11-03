package com.hzjt.platform.account.user.infrastructure.utils;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

/**
 * 加密解密工具类<br/>
 * 注意，解密是末尾的 \0 终止符都会被忽略掉
 *
 * @author daihui.gu
 * @create 2015年9月23日
 */
public class EncryptUtils {

    /**
     * 算法名称
     */
    private static final String KEY_ALGORITHM = "DES";
    /**
     * 算法名称/加密模式/填充方式
     * DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
     */
    private static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";

    public static void main(String[] args) {
        String randomHex = UUID.randomUUID().toString().replace("-", "");
        System.out.println(randomHex);
    }
    private static final String PHONE_KEY = "ca6ecb2947a04190b2f66ebf02ae7679";

    private static final String ID_CARE_KEY = "fb78b345c23546aca844b1a5";

    private static final String PASSWORD_KEY = "fb78b345b8da4c948c5bf492c8a820e7";

    /**
     * 对手机号进行加密
     *
     * @param data 要加密的手机号
     * @return 加密后的手机号
     */
    public static String encryptPhone(String data) {
        return encrypt(data, PHONE_KEY);
    }

    /**
     * 对身份证号进行加密
     *
     * @param idCareNo 身份证号
     * @return 加密后的身份证号
     */
    public static String encryptIdCareNo(String idCareNo) {
        return encrypt(idCareNo, ID_CARE_KEY);
    }

    /**
     * 解密手机号
     *
     * @param data 需要解密的手机号
     * @return 解密后的手机号
     */
    public static String decryptPhone(String data) {
        return decrypt(data, PHONE_KEY);
    }


    /**
     * 对用户密码进行加密
     *
     * @param password 需要加密的密码
     * @return 加密后的密码
     */
    public static String encryptUserPassWord(String password) {
        return encrypt(password, PASSWORD_KEY);
    }


    /**
     * 加密数据
     *
     * @param data   数据
     * @param keyStr 秘钥是 16 位的十六进制
     * @author daihui.gu
     */
    private static String encrypt(String data, String keyStr) {
        Key key = generateSecretKey(keyStr);
        return encrypt(data, key);
    }

    /**
     * 解密数据
     *
     * @param data   数据
     * @param keyStr 秘钥是 16 位的十六进制
     * @return String
     * @author daihui.gu
     */
    private static String decrypt(String data, String keyStr) {
        Key key = generateSecretKey(keyStr);
        return decrypt(data, key);
    }

    /**
     * 以指定秘钥解密数据
     *
     * @param data 数据
     * @param key  秘钥
     * @author daihui.gu
     */
    private static String encrypt(String data, Key key) {
        try {
            // 实例化Cipher对象，它用于完成实际的加密操作
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecureRandom random = new SecureRandom();
            // 初始化Cipher对象，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, key, random);
            // 获取字节并填充长度
            byte[] src = fillForLength(data.getBytes());
            // 加密
            byte[] results = cipher.doFinal(src);
            // 转 Base64 编码方便传输
            return Base64.encodeBase64String(results);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 以指定秘钥解密数据
     *
     * @param data 数据
     * @param key  秘钥
     * @author daihui.gu
     */
    private static String decrypt(String data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化Cipher对象，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解 Base64
            byte[] results = Base64.decodeBase64(data);
            // 执行解密操作
            results = cipher.doFinal(results);
            // 处理额外添加的字节
            results = trimForLength(results);
            return new String(results);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 生成密钥key对象
     *
     * @param keyStr 秘钥是 16 位的十六进制
     * @author daihui.gu
     */
    private static SecretKey generateSecretKey(String keyStr) {
        try {
            byte[] input = hexString2Bytes(keyStr);
            DESKeySpec desKey = new DESKeySpec(input);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generateSecret(desKey);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 填充长度
     *
     * @param src 字符串
     * @return byte[]
     * @author daihui.gu
     */
    private static byte[] fillForLength(byte[] src) {
        // 最多填充 7 个空白
        int more = src.length % 8;
        if (more == 0) {
            return src;
        }
        int fill = 8 - more;
        return Arrays.copyOf(src, src.length + fill);
    }

    /**
     * 处理掉末尾的长度，如果是本来的末尾有空白的话，这种情况也应该考虑末尾trim
     *
     * @param results 结果
     * @return byte[]
     * @author daihui.gu
     */
    private static byte[] trimForLength(byte[] results) {
        int end = results.length - 1;
        int i = 0;
        while (results[end - i] == '\0') {
            if (++i >= 7) {
                // 最大不超过7个空白
                break;
            }
        }
        if (i > 0) {
            results = Arrays.copyOf(results, results.length - i);
        }
        return results;
    }

    /**
     * 从十六进制字符串到字节数组转换
     *
     * @param hexstr hex
     * @return byte[]
     * @author daihui.gu
     */
    private static byte[] hexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parseByte(c0) << 4) | parseByte(c1));
        }
        return b;
    }

    /**
     * 获取十六进制的byte值
     *
     * @param c c
     * @return int
     * @author daihui.gu
     */
    private static int parseByte(char c) {
        if (c >= 'a') {
            return (c - 'a' + 10) & 0x0f;
        }
        if (c >= 'A') {
            return (c - 'A' + 10) & 0x0f;
        }
        return (c - '0') & 0x0f;
    }
}