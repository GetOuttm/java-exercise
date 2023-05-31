package org.basics.coding;

/**
 * Base64Coding Base64编码详解
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/30 16:04
 **/
public class Base64Coding {

    // 字符怎么定义都可以。
    private static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    public static String encodeToString(String str) {
        byte[] srcByte = str.getBytes();// 先把字符串转换成字节数组。
        int srcLength = srcByte.length;// 原字节的长度。
        int dstLength = ((srcLength + 2) / 3) << 2;// 编码之后的字节最大长度。
        byte[] dstByte = new byte[dstLength];// 编码之后的字节数组。
        char[] base64 = toBase64;
        int srcIndex = 0;// 原字节数组的下标。
        int desIndex = 0;// 转换之后的字节数组下标。
        // 每 3 个字节一组截取，后面如果有不够 3 个的会补 0 ，然后单独计算。
        int preLength = srcLength / 3 * 3;
        int bits;
        // 每 3 个字节转成 4 个。
        while (srcIndex < preLength) {
            // 每 3 个字节截取，每个字节 8 位，总共 24 位，byte 有负数，这里和 0xff 运算是截取。
            bits = (srcByte[srcIndex++] & 0xff) << 16 |
                    (srcByte[srcIndex++] & 0xff) << 8 |
                    (srcByte[srcIndex++] & 0xff);
            // 分成 4 份，每 6 位一份。
            dstByte[desIndex++] = (byte) base64[(bits >>> 18) & 0x3f];
            dstByte[desIndex++] = (byte) base64[(bits >>> 12) & 0x3f];
            dstByte[desIndex++] = (byte) base64[(bits >>> 6) & 0x3f];
            dstByte[desIndex++] = (byte) base64[bits & 0x3f];
        }
        // 因为每 3 个字节截取，如果有剩余的，要么剩余 1 个要么剩余 2 个。
        if (srcIndex < srcLength) {
            if (srcIndex + 1 == srcLength) {// 如果剩余 1 个。
                bits = srcByte[srcIndex++] & 0xff;
                // 先截取 6 位。
                dstByte[desIndex++] = (byte) base64[bits >> 2];
                // 剩下 2 位在补 4 个 0 。
                dstByte[desIndex++] = (byte) base64[(bits & 0x03) << 4];
                dstByte[desIndex++] = '=';// 表示缺失的意思。
            } else {// 如果剩余 2 个。
                // 2 字节总有 16 位，每 6 位一组只能分 3 组。
                bits = (srcByte[srcIndex++] & 0xff) << 8 |
                        (srcByte[srcIndex++] & 0xff);
                // 先截取 6 位。
                dstByte[desIndex++] = (byte) base64[(bits >>> 10) & 0x3f];
                // 在截取 6 位。
                dstByte[desIndex++] = (byte) base64[(bits >>> 4) & 0x3f];
                // 剩下 4 位然后在补 2 个 0 凑够 6 位。
                dstByte[desIndex++] = (byte) base64[(bits & 0x0F) << 2];
            }
            dstByte[desIndex++] = '=';// 最后一个一定是等号。
        }
        return new String(dstByte);
    }

    public static String decodeToStr(String str) {
        // 逆序转换，转码的时候是把 byte 数字转成对应的字母，解码的时候要
        // 把的字母在转换为对应的 byte 数字。
        int[] fromBase64 = new int[128];
        for (int i = 0; i < toBase64.length; i++)
            fromBase64[toBase64[i]] = i;

        byte[] srcByte = str.getBytes();
        int srcLength = srcByte.length;
        int equalsSign = 0;// 等号的个数，最多只能有 2 个。
        if (srcByte[srcLength - 1] == '=') {
            equalsSign++;
            if (srcByte[srcLength - 2] == '=')
                equalsSign++;
        }
        int dstLength = (srcLength >> 2) * 3 - equalsSign;
        byte[] dstByte = new byte[dstLength];
        int bits = 0;
        int srcIndex = 0;
        int desIndex = 0;
        int preLength = dstByte.length / 3 * 3;
        while (desIndex < preLength) {
            // 每 4 个字节一组，每个字节截取 6 位，总共 24 位。
            bits = (fromBase64[srcByte[srcIndex++]]) << 18 |
                    (fromBase64[srcByte[srcIndex++]] & 0x3f) << 12 |
                    (fromBase64[srcByte[srcIndex++]] & 0x3f) << 6 |
                    (fromBase64[srcByte[srcIndex++]] & 0x3f);
            // 24 位分成 3 份，每 8 位一份。
            dstByte[desIndex++] = (byte) ((bits >>> 16) & 0xff);
            dstByte[desIndex++] = (byte) ((bits >>> 8) & 0xff);
            dstByte[desIndex++] = (byte) (bits & 0xff);
        }
        // 如果有等号，要么有 1 个等号，要么有 2 个等号，要单独处理。
        if (srcIndex < srcLength) {
            if (srcByte[srcLength - 2] == '=') {// 有 2 个等号。
                bits = (fromBase64[srcByte[srcIndex++]] & 0x3f) << 2 |
                        (fromBase64[srcByte[srcIndex++]] & 0x3f) >> 4;
            } else {// 有 1 个等号。
                bits = (fromBase64[srcByte[srcIndex++]] & 0x3f) << 10 |
                        (fromBase64[srcByte[srcIndex++]] & 0x3f) << 4 |
                        (fromBase64[srcByte[srcIndex++]] & 0x3f) >> 2;
                dstByte[desIndex++] = (byte) ((bits >>> 8) & 0xff);
            }
            dstByte[desIndex++] = (byte) (bits & 0xff);
        }
        return new String(dstByte);
    }
}
