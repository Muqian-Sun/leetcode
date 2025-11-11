package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 53. 最大子数组和
 * 难度: 中等
 *
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），
 * 返回其最大和。
 *
 * 子数组是数组中的一个连续部分。
 *
 * 示例 1:
 * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 * 示例 2:
 * 输入: nums = [1]
 * 输出: 1
 *
 * 示例 3:
 * 输入: nums = [5,4,-1,7,8]
 * 输出: 23
 */
public class P53_MaximumSubarray {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * 思路:
     * dp[i] 表示以 nums[i] 结尾的最大子数组和
     * 状态转移方程: dp[i] = max(dp[i-1] + nums[i], nums[i])
     * 解释: 对于每个位置，要么接着前面的子数组，要么重新开始
     */
    public int maxSubArray(int[] nums) {
        return maxSubArrayWithVisualization(nums, true);
    }

    public int maxSubArrayWithVisualization(int[] nums, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 53. 最大子数组和");
            System.out.println("数组: " + Arrays.toString(nums));
            DPVisualizer.printTransition("dp[i] = max(dp[i-1] + nums[i], nums[i])");
            System.out.println("说明: dp[i]表示以nums[i]结尾的最大子数组和\n");
        }

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int maxSum = dp[0];

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("dp[0] = nums[0] = " + dp[0]);
            System.out.println("maxSum = " + maxSum);
            DPVisualizer.print1DArray(dp, "初始化后的DP数组");
        }

        for (int i = 1; i < n; i++) {
            int extendPrev = dp[i - 1] + nums[i];
            int startNew = nums[i];
            dp[i] = Math.max(extendPrev, startNew);
            maxSum = Math.max(maxSum, dp[i]);

            if (debug) {
                DPVisualizer.printStep(i, "处理位置 " + i + " (值=" + nums[i] + ")");
                System.out.println("  方案1 - 延续之前子数组: dp[" + (i-1) + "] + nums[" + i +
                                 "] = " + dp[i-1] + " + " + nums[i] + " = " + extendPrev);
                System.out.println("  方案2 - 重新开始: nums[" + i + "] = " + startNew);
                DPVisualizer.highlightUpdate(i, dp[i],
                    String.format("选择较大值: max(%d, %d)", extendPrev, startNew));
                System.out.println("  当前最大和: " + maxSum);
                DPVisualizer.print1DArray(dp, "当前DP数组状态");
            }
        }

        if (debug) {
            DPVisualizer.printResult(maxSum);
        }

        return maxSum;
    }

    /**
     * 方法2: 空间优化 (Kadane算法)
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public int maxSubArrayOptimized(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(currentSum + nums[i], nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    /**
     * 方法3: 分治法
     * 时间复杂度: O(n log n)
     * 空间复杂度: O(log n)
     */
    public int maxSubArrayDivideConquer(int[] nums) {
        return divideConquer(nums, 0, nums.length - 1);
    }

    private int divideConquer(int[] nums, int left, int right) {
        if (left == right) return nums[left];

        int mid = left + (right - left) / 2;

        // 左半部分最大子数组
        int leftMax = divideConquer(nums, left, mid);
        // 右半部分最大子数组
        int rightMax = divideConquer(nums, mid + 1, right);
        // 跨越中点的最大子数组
        int crossMax = crossSum(nums, left, right, mid);

        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    private int crossSum(int[] nums, int left, int right, int mid) {
        // 从中点向左找最大和
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }

        // 从中点向右找最大和
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }

        return leftSum + rightSum;
    }

    public static void main(String[] args) {
        P53_MaximumSubarray solution = new P53_MaximumSubarray();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        solution.maxSubArrayWithVisualization(nums1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] nums2 = {5, 4, -1, 7, 8};
        solution.maxSubArrayWithVisualization(nums2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] nums3 = {1};
        solution.maxSubArrayWithVisualization(nums3, true);

        // 测试空间优化版本
        System.out.println("\n空间优化版本 (Kadane算法):");
        System.out.println("结果: " + solution.maxSubArrayOptimized(nums1));
    }
}
