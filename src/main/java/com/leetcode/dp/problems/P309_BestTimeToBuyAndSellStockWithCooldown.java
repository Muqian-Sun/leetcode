package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 309. 最佳买卖股票时机含冷冻期
 * 难度: 中等
 *
 * 给定一个整数数组prices，其中第 prices[i] 表示第 i 天的股票价格 。
 *
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * - 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 示例 1:
 * 输入: prices = [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 *
 * 示例 2:
 * 输入: prices = [1]
 * 输出: 0
 */
public class P309_BestTimeToBuyAndSellStockWithCooldown {

    /**
     * 方法1: 动态规划（三状态）
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * 思路:
     * 定义三个状态:
     * dp[i][0]: 第i天持有股票的最大利润
     * dp[i][1]: 第i天不持有股票，且不在冷冻期的最大利润
     * dp[i][2]: 第i天不持有股票，且在冷冻期的最大利润
     */
    public int maxProfit(int[] prices) {
        return maxProfitWithVisualization(prices, true);
    }

    public int maxProfitWithVisualization(int[] prices, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 309. 最佳买卖股票时机含冷冻期");
            System.out.println("股票价格: " + Arrays.toString(prices));
            System.out.println("约束: 卖出后必须冷冻1天\n");
            System.out.println("状态定义:");
            System.out.println("  dp[i][0]: 第i天持有股票");
            System.out.println("  dp[i][1]: 第i天不持有股票(非冷冻期)");
            System.out.println("  dp[i][2]: 第i天不持有股票(冷冻期)");
        }

        if (prices.length == 1) {
            if (debug) DPVisualizer.printResult(0);
            return 0;
        }

        int n = prices.length;
        int[][] dp = new int[n][3];

        // 初始化第0天
        dp[0][0] = -prices[0];  // 持有
        dp[0][1] = 0;           // 不持有(非冷冻)
        dp[0][2] = 0;           // 不持有(冷冻)

        if (debug) {
            DPVisualizer.printStep(0, "初始化第0天");
            System.out.println("dp[0][0] = " + dp[0][0] + " (买入股票)");
            System.out.println("dp[0][1] = " + dp[0][1] + " (什么都不做)");
            System.out.println("dp[0][2] = " + dp[0][2] + " (不可能在冷冻期)");
        }

        for (int i = 1; i < n; i++) {
            if (debug) {
                DPVisualizer.printStep(i, "第 " + i + " 天，价格 = " + prices[i]);
            }

            // 持有 = max(昨天持有, 昨天非冷冻期今天买入)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);

            // 非冷冻期 = max(昨天非冷冻期, 昨天冷冻期)
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][2]);

            // 冷冻期 = 昨天持有今天卖出
            dp[i][2] = dp[i - 1][0] + prices[i];

            if (debug) {
                System.out.println("  持有股票:");
                System.out.println("    方案1 - 昨天持有: dp[" + (i-1) + "][0] = " + dp[i-1][0]);
                System.out.println("    方案2 - 今天买入: dp[" + (i-1) + "][1] - " +
                                 prices[i] + " = " + (dp[i-1][1] - prices[i]));
                System.out.println("    → dp[" + i + "][0] = " + dp[i][0]);

                System.out.println("  不持有(非冷冻):");
                System.out.println("    方案1 - 昨天非冷冻: dp[" + (i-1) + "][1] = " + dp[i-1][1]);
                System.out.println("    方案2 - 昨天冷冻: dp[" + (i-1) + "][2] = " + dp[i-1][2]);
                System.out.println("    → dp[" + i + "][1] = " + dp[i][1]);

                System.out.println("  不持有(冷冻):");
                System.out.println("    今天卖出: dp[" + (i-1) + "][0] + " +
                                 prices[i] + " = " + dp[i][2]);
                System.out.println("    → dp[" + i + "][2] = " + dp[i][2]);

                int[] currentState = {dp[i][0], dp[i][1], dp[i][2]};
                System.out.println("\n  第" + i + "天状态: " + Arrays.toString(currentState));
                System.out.println("  最大利润: " + Math.max(dp[i][1], dp[i][2]));
                System.out.println();
            }
        }

        int result = Math.max(dp[n - 1][1], dp[n - 1][2]);

        if (debug) {
            DPVisualizer.printResult(result);
        }

        return result;
    }

    /**
     * 方法2: 空间优化
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public int maxProfitOptimized(int[] prices) {
        if (prices.length == 1) return 0;

        int hold = -prices[0];      // 持有
        int notHold = 0;            // 不持有(非冷冻)
        int cooldown = 0;           // 不持有(冷冻)

        for (int i = 1; i < prices.length; i++) {
            int newHold = Math.max(hold, notHold - prices[i]);
            int newNotHold = Math.max(notHold, cooldown);
            int newCooldown = hold + prices[i];

            hold = newHold;
            notHold = newNotHold;
            cooldown = newCooldown;
        }

        return Math.max(notHold, cooldown);
    }

    public static void main(String[] args) {
        P309_BestTimeToBuyAndSellStockWithCooldown solution =
            new P309_BestTimeToBuyAndSellStockWithCooldown();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] prices1 = {1, 2, 3, 0, 2};
        solution.maxProfitWithVisualization(prices1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] prices2 = {1, 2, 4};
        solution.maxProfitWithVisualization(prices2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] prices3 = {1, 4, 2};
        solution.maxProfitWithVisualization(prices3, true);

        // 测试空间优化版本
        System.out.println("\n空间优化版本:");
        System.out.println("结果: " + solution.maxProfitOptimized(prices1));
    }
}
