package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 300. 最长递增子序列
 * 难度: 中等
 *
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 *
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
 * 例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 *
 * 示例 1:
 * 输入: nums = [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 *
 * 示例 2:
 * 输入: nums = [0,1,0,3,2,3]
 * 输出: 4
 *
 * 示例 3:
 * 输入: nums = [7,7,7,7,7,7,7]
 * 输出: 1
 */
public class P300_LongestIncreasingSubsequence {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n)
     *
     * 思路:
     * dp[i] 表示以 nums[i] 结尾的最长递增子序列的长度
     * 状态转移方程: dp[i] = max(dp[j] + 1)，其中 0 <= j < i 且 nums[j] < nums[i]
     */
    public int lengthOfLIS(int[] nums) {
        return lengthOfLISWithVisualization(nums, true);
    }

    public int lengthOfLISWithVisualization(int[] nums, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 300. 最长递增子序列");
            System.out.println("数组: " + Arrays.toString(nums));
            DPVisualizer.printTransition("dp[i] = max(dp[j] + 1), where j < i and nums[j] < nums[i]");
            System.out.println("说明: dp[i]表示以nums[i]结尾的最长递增子序列的长度\n");
        }

        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);  // 每个元素自己就是长度为1的递增子序列

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("所有位置初始化为1（每个元素本身构成长度为1的子序列）");
            DPVisualizer.print1DArray(dp, "初始化后的DP数组");
        }

        int maxLength = 1;

        for (int i = 1; i < n; i++) {
            if (debug) {
                DPVisualizer.printStep(i, "处理位置 " + i + " (值=" + nums[i] + ")");
                System.out.println("  寻找所有 j < " + i + " 且 nums[j] < nums[" + i + "] 的位置");
            }

            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    int newLength = dp[j] + 1;
                    if (debug) {
                        System.out.println("    j=" + j + ", nums[j]=" + nums[j] +
                                         " < nums[i]=" + nums[i] +
                                         ", dp[j]+1=" + dp[j] + "+1=" + newLength);
                    }
                    if (newLength > dp[i]) {
                        dp[i] = newLength;
                        if (debug) {
                            System.out.println("    更新: dp[" + i + "] = " + dp[i]);
                        }
                    }
                }
            }

            maxLength = Math.max(maxLength, dp[i]);

            if (debug) {
                DPVisualizer.highlightUpdate(i, dp[i], "最终确定 dp[" + i + "]");
                System.out.println("  当前最大长度: " + maxLength);
                DPVisualizer.print1DArray(dp, "当前DP数组状态");
            }
        }

        if (debug) {
            DPVisualizer.printResult(maxLength);
        }

        return maxLength;
    }

    /**
     * 方法2: 贪心 + 二分查找
     * 时间复杂度: O(n log n)
     * 空间复杂度: O(n)
     *
     * 思路:
     * 维护一个数组 tails，tails[i] 表示长度为 i+1 的递增子序列的最小末尾元素
     * 对于每个元素，用二分查找找到它应该插入的位置
     */
    public int lengthOfLISOptimized(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;

        for (int num : nums) {
            int left = 0, right = size;

            // 二分查找第一个 >= num 的位置
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = num;
            if (left == size) {
                size++;
            }
        }

        return size;
    }

    /**
     * 方法3: 带路径记录的动态规划
     * 可以输出具体的最长递增子序列
     */
    public int[] findLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] prev = new int[n];  // 记录前驱节点
        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);

        int maxLength = 1;
        int maxIndex = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;  // 记录前驱
                }
            }
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                maxIndex = i;
            }
        }

        // 重建路径
        int[] result = new int[maxLength];
        int idx = maxIndex;
        for (int i = maxLength - 1; i >= 0; i--) {
            result[i] = nums[idx];
            idx = prev[idx];
        }

        return result;
    }

    public static void main(String[] args) {
        P300_LongestIncreasingSubsequence solution = new P300_LongestIncreasingSubsequence();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        solution.lengthOfLISWithVisualization(nums1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] nums2 = {0, 1, 0, 3, 2, 3};
        solution.lengthOfLISWithVisualization(nums2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] nums3 = {7, 7, 7, 7, 7, 7, 7};
        solution.lengthOfLISWithVisualization(nums3, true);

        // 测试优化版本
        System.out.println("\n贪心+二分查找优化版本:");
        System.out.println("结果: " + solution.lengthOfLISOptimized(nums1));

        // 测试带路径记录的版本
        System.out.println("\n带路径记录版本:");
        int[] lis = solution.findLIS(nums1);
        System.out.println("最长递增子序列: " + Arrays.toString(lis));
    }
}
