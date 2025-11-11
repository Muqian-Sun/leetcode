package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 416. 分割等和子集
 * 难度: 中等
 *
 * 给你一个只包含正整数的非空数组 nums 。
 * 请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 *
 * 示例 1:
 * 输入: nums = [1,5,11,5]
 * 输出: true
 * 解释: 数组可以分割成 [1, 5, 5] 和 [11] 。
 *
 * 示例 2:
 * 输入: nums = [1,2,3,5]
 * 输出: false
 * 解释: 数组不能分割成两个元素和相等的子集。
 */
public class P416_PartitionEqualSubsetSum {

    /**
     * 方法1: 0-1背包问题
     * 时间复杂度: O(n * target)
     * 空间复杂度: O(n * target)
     *
     * 思路:
     * 问题转化为: 能否从数组中选出一些数，使其和为 sum/2
     * dp[i][j] 表示从前i个数中选，能否凑出和为j
     * 状态转移方程: dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]
     */
    public boolean canPartition(int[] nums) {
        return canPartitionWithVisualization(nums, true);
    }

    public boolean canPartitionWithVisualization(int[] nums, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 416. 分割等和子集");
            System.out.println("数组: " + Arrays.toString(nums));
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // 如果总和为奇数，不可能分成两个相等的子集
        if (sum % 2 != 0) {
            if (debug) {
                System.out.println("总和 = " + sum + " (奇数)");
                DPVisualizer.printResult(false);
            }
            return false;
        }

        int target = sum / 2;
        if (debug) {
            System.out.println("总和 = " + sum);
            System.out.println("目标 = " + target + " (需要找到和为target的子集)");
            DPVisualizer.printTransition("dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]]");
            System.out.println("说明: dp[i][j]表示从前i个数中能否凑出和为j\n");
        }

        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][target + 1];

        // 初始化: 和为0总是可以达到(不选任何数)
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("dp[i][0] = true (和为0总是可以达到)");
            DPVisualizer.print2DArray(dp, "初始化后的DP数组");
        }

        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];

            if (debug) {
                DPVisualizer.printStep(i, "处理第 " + i + " 个数 (值=" + num + ")");
            }

            for (int j = 1; j <= target; j++) {
                // 不选当前数
                dp[i][j] = dp[i - 1][j];

                // 如果能选当前数(j >= num)，尝试选择
                if (j >= num) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - num];
                }

                if (debug && dp[i][j] && !dp[i-1][j]) {
                    System.out.println("  找到新的可达和: j=" + j +
                                     " (选择nums[" + (i-1) + "]=" + num + ")");
                }
            }

            if (debug) {
                DPVisualizer.print2DArray(dp, "处理完第" + i + "个数后的DP数组");
            }
        }

        if (debug) {
            DPVisualizer.printResult(dp[n][target]);
        }

        return dp[n][target];
    }

    /**
     * 方法2: 一维空间优化
     * 时间复杂度: O(n * target)
     * 空间复杂度: O(target)
     */
    public boolean canPartitionOptimized(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) return false;

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            // 从后往前更新，避免重复使用同一个数
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
        }

        return dp[target];
    }

    public static void main(String[] args) {
        P416_PartitionEqualSubsetSum solution = new P416_PartitionEqualSubsetSum();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] nums1 = {1, 5, 11, 5};
        solution.canPartitionWithVisualization(nums1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] nums2 = {1, 2, 3, 5};
        solution.canPartitionWithVisualization(nums2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] nums3 = {1, 2, 5};
        solution.canPartitionWithVisualization(nums3, true);

        // 测试优化版本
        System.out.println("\n空间优化版本:");
        System.out.println("结果: " + solution.canPartitionOptimized(nums1));
    }
}
