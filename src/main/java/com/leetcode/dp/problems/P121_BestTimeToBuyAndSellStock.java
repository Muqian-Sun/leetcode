package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 121. 买卖股票的最佳时机
 * 难度: 简单
 *
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择某一天买入这只股票，并选择在未来的某一个不同的日子卖出该股票。
 * 设计一个算法来计算你所能获取的最大利润。
 *
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 * 示例 1:
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，
 *      最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
 *
 * 示例 2:
 * 输入: prices = [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 */
public class P121_BestTimeToBuyAndSellStock {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     *
     * 思路:
     * 维护两个变量:
     * - minPrice: 到目前为止的最低价格
     * - maxProfit: 到目前为止的最大利润
     */
    public int maxProfit(int[] prices) {
        return maxProfitWithVisualization(prices, true);
    }

    public int maxProfitWithVisualization(int[] prices, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 121. 买卖股票的最佳时机");
            System.out.println("股票价格: " + Arrays.toString(prices));
            System.out.println("约束: 只能买卖一次\n");
        }

        if (prices == null || prices.length < 2) {
            if (debug) DPVisualizer.printResult(0);
            return 0;
        }

        int minPrice = prices[0];
        int maxProfit = 0;

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("minPrice = " + minPrice);
            System.out.println("maxProfit = " + maxProfit);
        }

        for (int i = 1; i < prices.length; i++) {
            int currentPrice = prices[i];
            int currentProfit = currentPrice - minPrice;

            if (debug) {
                DPVisualizer.printStep(i, "第 " + i + " 天，价格 = " + currentPrice);
                System.out.println("  当前最低价: " + minPrice);
                System.out.println("  今天卖出的利润: " + currentPrice + " - " +
                                 minPrice + " = " + currentProfit);
            }

            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
                if (debug) {
                    System.out.println("  更新最大利润: " + maxProfit);
                }
            }

            if (currentPrice < minPrice) {
                minPrice = currentPrice;
                if (debug) {
                    System.out.println("  更新最低价: " + minPrice);
                }
            }

            if (debug) {
                System.out.println("  当前状态 - 最低价: " + minPrice + ", 最大利润: " + maxProfit);
            }
        }

        if (debug) {
            DPVisualizer.printResult(maxProfit);
        }

        return maxProfit;
    }

    /**
     * 方法2: 状态机DP
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

        // 第0天
        dp[0][0] = 0;           // 未持有
        dp[0][1] = -prices[0];  // 持有（买入）

        for (int i = 1; i < n; i++) {
            // 未持有 = max(昨天未持有, 昨天持有今天卖出)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 持有 = max(昨天持有, 今天买入)
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }

        return dp[n - 1][0];
    }

    public static void main(String[] args) {
        P121_BestTimeToBuyAndSellStock solution = new P121_BestTimeToBuyAndSellStock();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        solution.maxProfitWithVisualization(prices1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] prices2 = {7, 6, 4, 3, 1};
        solution.maxProfitWithVisualization(prices2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] prices3 = {1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        solution.maxProfitWithVisualization(prices3, true);

        // 测试状态机DP版本
        System.out.println("\n状态机DP版本:");
        System.out.println("结果: " + solution.maxProfitDP(prices1));
    }
}
