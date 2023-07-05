package basics.linearList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Array   线性表 - 数组和矩阵
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/29 17:20
 **/
public class Array {
    /**
     * 数组是一种连续存储线性结构，元素类型相同，大小相等，数组是多维的，通过使用整型索引值来访问他们的元素，数组尺寸不能改变。
     * <p>
     * 数组的优点: 存取速度快
     * 数组的缺点:
     * 1.事先必须知道数组的长度
     * 2.插入删除元素很慢
     * 3.空间通常是有限制的
     * 4.需要大块连续的内存块
     * 5.插入删除元素的效率很低
     */


    public static void main(String[] args) {
        //int[] nums = {0, 1, 0, 3, 12};
        //moveZeroes(nums);

        //int[][] numss = {{1, 2},
        //        {3, 4}};
        //int r = 2, c = 4;
        //int[][] ints = matrixReshape(numss, r, c);
        //List<List<Integer>> list = new ArrayList<>();
        //for (int[] row : ints) {
        //    list.add(Arrays.stream(row).boxed().collect(Collectors.toList()));
        //}
        //System.out.println(list);

        //int[] nums = {1,1,0,1,1,1};
        //System.out.println(findMaxConsecutiveOnes(nums));

        //int[][] matrix = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        //int target = 5;
        //System.out.println(searchMatrix(matrix, target));

        //int[][] matrix = {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
        //int k = 8;
        //System.out.println(kthSmallest_dichotomy(matrix, k));
        //System.out.println(kthSmallest_heap(matrix, k));
        //System.out.println(kthSmallest_java(matrix,k));

        int[] nums = {1,2,2,4};
        int[] errorNums = findErrorNums(nums);
        System.out.println(Arrays.stream(errorNums).boxed().collect(Collectors.toList()));
    }

    /**
     * 把数组中的 0 移到末尾  leetcode 283
     * https://leetcode.cn/problems/move-zeroes/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
     */
    private static void moveZeroes(int[] nums) {
        int idx = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[idx++] = num;
            }
        }
        while (idx < nums.length) {
            nums[idx++] = 0;
        }
        System.out.println(Arrays.stream(nums).boxed().collect(Collectors.toList()));
    }

    /**
     * 改变矩阵维度 leetcode 566
     * https://leetcode.com/problems/reshape-the-matrix/description/
     */
    private static int[][] matrixReshape(int[][] numss, int r, int c) {
        int m = numss.length, n = numss[0].length;
        if (m * n != r * c) {
            return numss;
        }
        int[][] reshapedNums = new int[r][c];
        int index = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                reshapedNums[i][j] = numss[index / n][index % n];
                index++;
            }
        }
        return reshapedNums;
    }

    /**
     * 找出数组中最长的连续 1   leetcode 485
     * https://leetcode.com/problems/max-consecutive-ones/description/
     */
    private static int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, cur = 0;
        for (int num : nums) {
            cur = num == 0 ? 0 : cur + 1;
            max = Math.max(max, cur);
        }
        return max;
    }

    /**
     * 有序矩阵查找 leetcode 240
     * https://leetcode.cn/problems/search-a-2d-matrix-ii/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
     */
    private static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int m = matrix.length, n = matrix[0].length;
        int row = 0, col = n - 1;
        while (row < m && col >= 0) {
            if (target == matrix[row][col]) {
                return true;
            } else if (target < matrix[row][col]) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    /**
     * 排序矩阵中的第 k 个最小元素   leetcode 378
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/description/
     */
    //堆解法
    private static int kthSmallest_heap(int[][] matrix, int k) {
        int m = matrix.length, n = matrix[0].length;
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            pq.offer(new Tuple(0, i, matrix[0][i]));
        }
        for (int i = 0; i < k - 1; i++) {// 小根堆，去掉 k - 1 个堆顶元素，此时堆顶元素就是第 k 的数
            Tuple t = pq.poll();
            if (t.x == m - 1) continue;
            pq.offer(new Tuple(t.x + 1, t.y, matrix[t.x + 1][t.y]));
        }
        return pq.poll().val;
    }

    //二分查找解法:
    private static int kthSmallest_dichotomy(int[][] matrix, int k) {
        int m = matrix.length, n = matrix[0].length;
        int lo = matrix[0][0], hi = matrix[m - 1][n - 1];
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cnt = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n && matrix[i][j] <= mid; j++) {
                    cnt++;
                }
            }
            if (cnt < k) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
    //java
    private static Integer kthSmallest_java(int[][] matrix, int k) {
        Map<Integer ,Integer> map = new TreeMap<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                map.put(matrix[i][j],map.getOrDefault(matrix[i][j],0)+1);
            }
        }
        for (Integer n : map.keySet()) {
            k -= map.get(n);
            if (k<=0) {
                return n;
            }
        }
        return -1;
    }

    /**
     * 一个数组元素在 [1, n] 之间，其中一个数被替换为另一个数，找出重复的数和丢失的数   leetcode 645
     * 最直接的方法是先对数组进行排序，这种方法时间复杂度为 O(NlogN)。本题可以以 O(N) 的时间复杂度、O(1) 空间复杂度来求解。
     * 主要思想是通过交换数组元素，使得数组上的元素在正确的位置上。
     * https://leetcode.com/problems/set-mismatch/description/
     * @return
     */
    private static int[] findErrorNums(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i+1 && nums[nums[i]-1] != nums[i]) {
                swap(nums,i,nums[i] -1);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]!=i +1) {
                return new int[]{nums[i],i+1};
            }
        }
        return null;
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

class Tuple implements Comparable<Tuple> {

    int x, y, val;

    public Tuple(int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    @Override
    public int compareTo(Tuple that) {
        return this.val - that.val;
    }
}
