package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;

/**
 * LeetCode 70. 爬楼梯
 * 难度: 简单
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 示例 1:
 * 输入: n = 2
 * 输出: 2
 * 解释: 有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 *
 * 示例 2:
 * 输入: n = 3
 * 输出: 3
 * 解释: 有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 */
public class P70_ClimbingStairs {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * 思路:
     * dp[i] 表示爬到第i阶楼梯的方法数
     * 状态转移方程: dp[i] = dp[i-1] + dp[i-2]
     * 因为到达第i阶可以从第i-1阶爬1步，或从第i-2阶爬2步
     */
    public int climbStairs(int n) {
        return climbStairsWithVisualization(n, true);
    }

    public int climbStairsWithVisualization(int n, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 70. 爬楼梯");
            System.out.println("问题: 爬到第 " + n + " 阶楼梯有多少种方法？");
            DPVisualizer.printTransition("dp[i] = dp[i-1] + dp[i-2]");
        }

        if (n <= 2) {
            if (debug) DPVisualizer.printResult(n);
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        if (debug) {
            DPVisualizer.printStep(0, "初始化基础情况");
            DPVisualizer.print1DArray(dp, "初始化: dp[1]=1, dp[2]=2");
        }

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];

            if (debug) {
                DPVisualizer.printStep(i - 2, "计算第 " + i + " 阶");
                DPVisualizer.highlightUpdate(i, dp[i],
                    String.format("dp[%d] = dp[%d] + dp[%d] = %d + %d",
                                 i, i-1, i-2, dp[i-1], dp[i-2]));
                DPVisualizer.print1DArray(dp, "当前DP数组状态");
            }
        }

        if (debug) {
            DPVisualizer.printResult(dp[n]);
        }

        return dp[n];
    }

    /**
     * 方法2: 空间优化
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * 因为只需要前两个状态，可以用两个变量代替数组
     */
    public int climbStairsOptimized(int n) {
        if (n <= 2) return n;

        int prev2 = 1;  // dp[i-2]
        int prev1 = 2;  // dp[i-1]
        int current = 0;

        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return current;
    }

    public static void main(String[] args) {
        P70_ClimbingStairs solution = new P70_ClimbingStairs();

        // 测试用例1
        System.out.println("测试用例 1:");
        int result1 = solution.climbStairsWithVisualization(2, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int result2 = solution.climbStairsWithVisualization(5, true);

        // 测试用例3 - 不显示debug信息
        System.out.println("\n测试用例 3 (无可视化):");
        int result3 = solution.climbStairs(10);
        System.out.println("n=10, 结果: " + result3);

        // 测试空间优化版本
        System.out.println("\n空间优化版本:");
        System.out.println("n=10, 结果: " + solution.climbStairsOptimized(10));
    }
}
