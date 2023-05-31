package org.basics.coding;

/**
 * Base64Coding Base64编码详解
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/30 16:04
 **/
public class Base64Test {

    //https://mp.weixin.qq.com/s/bgeNKZ7RwKxbKjknCyXYkw
    public static void main(String[] args) {
        String[] strs = {"wansuanfa.com(玩算法)，一个学习算法的网站", "跟着博哥学算法……"};
        for (int i = 0; i < strs.length; i++) {
            System.out.println("编码之前的字符串：" + strs[i]);// 编码之前的字符串。
            String encodeStr = Base64Coding.encodeToString(strs[i]);// 编码。
            System.out.println("编码之后的字符串：" + encodeStr);// 打印编码之后的结果。
            // 打印解码之后的结果。
            System.out.println("解码之后的字符串：" + Base64Coding.decodeToStr(encodeStr));
            System.out.println();// 换行。
        }
    }
}
