package org.basics.coding;

/**
 * Base64Coding Base64编码详解
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/30 16:04
 **/
public class Base64CodingCustom {

    // 字符怎么定义都可以。
    private static final char[] toBase16 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P'};

    // 编码。
    private static String encodeToString(String str) {
        byte[] srcByte = str.getBytes();
        int srcLength = srcByte.length;
        byte[] dstByte = new byte[srcLength << 1];
        char[] base64 = toBase16;
        int srcIndex = 0;
        int desIndex = 0;
        int bits;
        while (srcIndex < srcLength) {
            // 一个字节 8 位分成 2 部分。
            bits = srcByte[srcIndex++];
            dstByte[desIndex++] = (byte) base64[(bits >>> 4) & 0x0f];
            dstByte[desIndex++] = (byte) base64[bits & 0x0f];
        }
        return new String(dstByte);
    }

    // 解码。
    public static String decodeToStr(String str) {
        int[] fromBase64 = new int[128];
        for (int i = 0; i < toBase16.length; i++)
            fromBase64[toBase16[i]] = i;

        byte[] srcByte = str.getBytes();
        int srcLength = srcByte.length;
        int dstLength = srcLength >> 1;
        byte[] dstByte = new byte[dstLength];
        int bits = 0;
        int srcIndex = 0;
        int desIndex = 0;
        while (desIndex < dstLength) {
            // 每两个合并成一个。
            bits = (fromBase64[srcByte[srcIndex++]] & 0x0f) << 4 |
                    (fromBase64[srcByte[srcIndex++]] & 0x0f);
            dstByte[desIndex++] = (byte) (bits & 0xff);
        }
        return new String(dstByte);
    }

    public static void main(String[] args) {
        String[] strs = {"博哥就是帅！", "跟着博哥学算法……"};
        for (int i = 0; i < strs.length; i++) {
            System.out.println("编码之前的字符串：" + strs[i]);// 编码之前的字符串。
            String encodeStr = encodeToString(strs[i]);// 编码。
            System.out.println("编码之后的字符串：" + encodeStr);// 打印编码之后的结果。
            // 打印解码之后的结果。
            System.out.println("解码之后的字符串：" + decodeToStr(encodeStr));
            System.out.println();// 换行。
        }
    }
}
