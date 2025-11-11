package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 198. 打家劫舍
 * 难度: 中等
 *
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
 * 影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，
 * 计算你不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 *
 * 示例 1:
 * 输入: [1,2,3,1]
 * 输出: 4
 * 解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 *
 * 示例 2:
 * 输入: [2,7,9,3,1]
 * 输出: 12
 * 解释: 偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 */
public class P198_HouseRobber {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * 思路:
     * dp[i] 表示偷到第i家时能获得的最大金额
     * 状态转移方程: dp[i] = max(dp[i-1], dp[i-2] + nums[i])
     * 解释: 对于第i家，可以选择偷或不偷
     *      - 不偷: dp[i] = dp[i-1]
     *      - 偷: dp[i] = dp[i-2] + nums[i] (因为不能偷相邻的)
     */
    public int rob(int[] nums) {
        return robWithVisualization(nums, true);
    }

    public int robWithVisualization(int[] nums, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 198. 打家劫舍");
            System.out.println("房屋金额: " + Arrays.toString(nums));
            DPVisualizer.printTransition("dp[i] = max(dp[i-1], dp[i-2] + nums[i])");
        }

        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        if (debug) {
            DPVisualizer.printStep(0, "初始化基础情况");
            System.out.println("dp[0] = " + dp[0] + " (只有第0家，必偷)");
            System.out.println("dp[1] = max(nums[0], nums[1]) = max(" +
                             nums[0] + ", " + nums[1] + ") = " + dp[1]);
            DPVisualizer.print1DArray(dp, "初始化后的DP数组");
        }

        for (int i = 2; i < n; i++) {
            int notRob = dp[i - 1];
            int rob = dp[i - 2] + nums[i];
            dp[i] = Math.max(notRob, rob);

            if (debug) {
                DPVisualizer.printStep(i - 1, "决策第 " + i + " 家 (金额=" + nums[i] + ")");
                System.out.println("  方案1 - 不偷第" + i + "家: dp[" + (i-1) + "] = " + notRob);
                System.out.println("  方案2 - 偷第" + i + "家: dp[" + (i-2) + "] + nums[" + i +
                                 "] = " + dp[i-2] + " + " + nums[i] + " = " + rob);
                DPVisualizer.highlightUpdate(i, dp[i],
                    String.format("选择较大值: max(%d, %d)", notRob, rob));
                DPVisualizer.print1DArray(dp, "当前DP数组状态");
            }
        }

        if (debug) {
            DPVisualizer.printResult(dp[n - 1]);
        }

        return dp[n - 1];
    }

    /**
     * 方法2: 空间优化
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public int robOptimized(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    public static void main(String[] args) {
        P198_HouseRobber solution = new P198_HouseRobber();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] nums1 = {1, 2, 3, 1};
        solution.robWithVisualization(nums1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] nums2 = {2, 7, 9, 3, 1};
        solution.robWithVisualization(nums2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] nums3 = {5, 3, 4, 11, 2};
        solution.robWithVisualization(nums3, true);
    }
}
