package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 122. 买卖股票的最佳时机 II
 * 难度: 中等
 *
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候最多只能持有一股股票。
 * 你也可以先购买，然后在同一天出售。
 *
 * 返回你能获得的最大利润 。
 *
 * 示例 1:
 * 输入: prices = [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）买入，在第 3 天（股票价格 = 5）卖出, 利润 = 5-1 = 4 。
 *      随后，在第 4 天（股票价格 = 3）买入，在第 5 天（股票价格 = 6）卖出, 利润 = 6-3 = 3 。
 *      总利润 = 4 + 3 = 7 。
 *
 * 示例 2:
 * 输入: prices = [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）买入，在第 5 天 （股票价格 = 5）卖出, 利润 = 5-1 = 4 。
 *      总利润 = 4 。
 */
public class P122_BestTimeToBuyAndSellStockII {

    /**
     * 方法1: 贪心算法
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * 思路:
     * 只要第二天价格比第一天高，就在第一天买入第二天卖出
     * 相当于把所有上涨的利润都收入囊中
     */
    public int maxProfit(int[] prices) {
        return maxProfitWithVisualization(prices, true);
    }

    public int maxProfitWithVisualization(int[] prices, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 122. 买卖股票的最佳时机 II");
            System.out.println("股票价格: " + Arrays.toString(prices));
            System.out.println("约束: 可以多次买卖，但同时最多持有一股\n");
        }

        int maxProfit = 0;

        if (debug) {
            DPVisualizer.printStep(0, "贪心策略: 只要明天涨就今天买明天卖");
        }

        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - prices[i - 1];

            if (profit > 0) {
                maxProfit += profit;

                if (debug) {
                    DPVisualizer.printStep(i, "第 " + i + " 天");
                    System.out.println("  第" + (i-1) + "天价格: " + prices[i-1]);
                    System.out.println("  第" + i + "天价格: " + prices[i]);
                    System.out.println("  利润: " + prices[i] + " - " + prices[i-1] + " = " + profit);
                    System.out.println("  累计利润: " + maxProfit);
                }
            } else if (debug) {
                System.out.println("  第" + i + "天: 价格下跌，不交易");
            }
        }

        if (debug) {
            DPVisualizer.printResult(maxProfit);
        }

        return maxProfit;
    }

    /**
     * 方法2: 动态规划（状态机）
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * 思路:
     * dp[i][0] 表示第i天未持有股票的最大利润
     * dp[i][1] 表示第i天持有股票的最大利润
     */
    public int maxProfitDP(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];

        dp[0][0] = 0;           // 未持有
        dp[0][1] = -prices[0];  // 持有

        for (int i = 1; i < n; i++) {
            // 未持有 = max(昨天未持有, 昨天持有今天卖出)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 持有 = max(昨天持有, 昨天未持有今天买入)
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[n - 1][0];
    }

    /**
     * 方法3: 空间优化的动态规划
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public int maxProfitDPOptimized(int[] prices) {
        int hold = -prices[0];    // 持有股票
        int notHold = 0;          // 未持有股票

        for (int i = 1; i < prices.length; i++) {
            int newNotHold = Math.max(notHold, hold + prices[i]);
            int newHold = Math.max(hold, notHold - prices[i]);
            notHold = newNotHold;
            hold = newHold;
        }

        return notHold;
    }

    public static void main(String[] args) {
        P122_BestTimeToBuyAndSellStockII solution = new P122_BestTimeToBuyAndSellStockII();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        solution.maxProfitWithVisualization(prices1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] prices2 = {1, 2, 3, 4, 5};
        solution.maxProfitWithVisualization(prices2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] prices3 = {7, 6, 4, 3, 1};
        solution.maxProfitWithVisualization(prices3, true);

        // 测试动态规划版本
        System.out.println("\n动态规划版本:");
        System.out.println("结果: " + solution.maxProfitDP(prices1));

        // 测试空间优化版本
        System.out.println("\n空间优化版本:");
        System.out.println("结果: " + solution.maxProfitDPOptimized(prices1));
    }
}
