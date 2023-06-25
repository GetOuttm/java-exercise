package org.basics.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ListRemove 循环删除 List 中的元素
 * https://mp.weixin.qq.com/s/TGoQz3oUxytZ956APo9m9A
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/25 10:01
 **/
public class ListRemove {

    public static void main(String[] args) {
        List<String> platformList = new ArrayList<>();
        platformList.add("博客园");
        platformList.add("CSDN");
        platformList.add("掘金");
        //forIfRemove(platformList);
        //iteratorRemove(platformList);
        forSortAscRemove(platformList);
        forSortDescRemove(platformList);
        streamFilterRemove(platformList);
    }

    /**
     * stream filter过滤
     *
     * @param platformList
     */
    private static void streamFilterRemove(List<String> platformList) {
        platformList = platformList.stream().filter(e -> !e.startsWith("博客园")).collect(Collectors.toList());
        System.out.println(platformList);
    }

    /**
     * 使用for循环倒序遍历
     *
     * @param platformList
     */
    private static void forSortDescRemove(List<String> platformList) {
        for (int i = platformList.size() - 1; i >= 0; i--) {
            String item = platformList.get(i);

            if (item.equals("掘金")) {
                platformList.remove(i);
            }
        }

        System.out.println(platformList);
    }

    /**
     * 使用for循环正序遍历
     * 通过数组的下标来删除，不过有个注意事项就是删除元素后，要修正下下标的值：
     *
     * @param platformList
     */
    private static void forSortAscRemove(List<String> platformList) {
        for (int i = 0; i < platformList.size(); i++) {
            String item = platformList.get(i);

            if (item.equals("博客园")) {
                platformList.remove(i);
                i = i - 1;
            }
        }

        System.out.println(platformList);
    }

    /**
     * 使用Iterator的remove()方法
     * 每次删除一个元素，都会将modCount的值重新赋值给expectedModCount，这样2个变量就相等了，
     * 不会触发java.util.ConcurrentModificationException异常！
     *
     * @param platformList
     */
    private static void iteratorRemove(List<String> platformList) {
        Iterator<String> iterator = platformList.iterator();
        while (iterator.hasNext()) {
            String platform = iterator.next();
            if (platform.equals("博客园")) {
                iterator.remove();
            }
        }
        System.out.println(platformList);
    }

    /**
     * for 加  if判断进行删除
     * java.util.ConcurrentModificationException 并发修改异常
     * 查看生成的字节码文件，foreach循环在实际执行时，其实使用的是Iterator，使用的核心方法是hasnext()和next()。
     *
     * @param platformList
     */
    private static void forIfRemove(List<String> platformList) {
        for (String platform : platformList) {
            if (platform.equals("博客园")) {
                platformList.remove(platform);
            }
        }
        System.out.println(platformList);
    }


}
