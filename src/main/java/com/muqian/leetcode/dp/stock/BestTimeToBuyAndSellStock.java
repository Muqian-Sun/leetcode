package com.muqian.leetcode.dp.stock;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 121. 买卖股票的最佳时机
 *
 * 题目描述：
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择某一天买入这只股票，并选择在未来的某一个不同的日子卖出该股票。
 * 设计一个算法来计算你所能获取的最大利润。
 *
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 * 示例 1:
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，
 *      最大利润 = 6-1 = 5 。注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
 *
 * 示例 2:
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 *
 * 解题思路：
 *
 * 方法1：一次遍历
 * - 记录历史最低价格minPrice
 * - 记录最大利润maxProfit
 * - 遍历每个价格，计算当前价格卖出的利润，更新最大利润
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 方法2：动态规划（状态机）
 * - dp[i][0]：第i天未持有股票的最大利润
 * - dp[i][1]：第i天持有股票的最大利润
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class BestTimeToBuyAndSellStock {
    private static final Logger log = LoggerFactory.getLogger(BestTimeToBuyAndSellStock.class);

    private int stepCounter = 0;

    /**
     * 方法1：一次遍历
     *
     * @param prices 股票价格数组
     * @return 最大利润
     */
    public int maxProfit(int[] prices) {
        log.info("\n========== 开始计算买卖股票最大利润（一次遍历） ==========");
        log.info("股票价格：{}", DPUtils.arrayToString(prices));
        log.info("约束：只能买卖一次\n");

        if (prices == null || prices.length < 2) {
            log.info("数组长度不足，无法交易，返回 0");
            log.info("\n========== 计算完成 ==========\n");
            return 0;
        }

        int minPrice = prices[0];
        int maxProfit = 0;

        stepCounter = 0;

        log.info("[步骤{}] 初始化", ++stepCounter);
        log.info("│   minPrice = {} (第0天价格)", minPrice);
        log.info("│   maxProfit = 0\n", maxProfit);

        for (int i = 1; i < prices.length; i++) {
            int currentPrice = prices[i];
            int currentProfit = currentPrice - minPrice;

            log.info("[步骤{}] 第 {} 天，价格 = {}", ++stepCounter, i, currentPrice);
            log.info("│   当前最低价: {}", minPrice);
            log.info("│   今天卖出利润: {} - {} = {}", currentPrice, minPrice, currentProfit);

            if (currentProfit > maxProfit) {
                log.info("│   ✓ 更新最大利润: {} → {}", maxProfit, currentProfit);
                maxProfit = currentProfit;
            }

            if (currentPrice < minPrice) {
                log.info("│   ✓ 更新最低价: {} → {}", minPrice, currentPrice);
                minPrice = currentPrice;
            }

            log.info("│   当前状态 - 最低价: {}, 最大利润: {}\n", minPrice, maxProfit);
        }

        log.info("{}", DPUtils.printSummary(stepCounter, maxProfit));
        log.info("\n========== 计算完成 ==========\n");

        return maxProfit;
    }

    /**
     * 方法2：动态规划（状态机）
     *
     * @param prices 股票价格数组
     * @return 最大利润
     */
    public int maxProfitDP(int[] prices) {
        log.info("\n========== 开始计算买卖股票最大利润（状态机DP） ==========");
        log.info("股票价格：{}", DPUtils.arrayToString(prices));
        log.info("状态定义：dp[i][0]=未持有股票, dp[i][1]=持有股票\n");

        int n = prices.length;
        int[][] dp = new int[n][2];

        // 第0天
        dp[0][0] = 0;           // 未持有
        dp[0][1] = -prices[0];  // 持有（买入）

        stepCounter = 0;

        log.info("[步骤{}] 第0天初始化", ++stepCounter);
        log.info("│   dp[0][0] = 0 (未持有)", dp[0][0]);
        log.info("│   dp[0][1] = -{} (买入)", prices[0]);

        for (int i = 1; i < n; i++) {
            // 未持有 = max(昨天未持有, 昨天持有今天卖出)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 持有 = max(昨天持有, 今天买入)
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);

            if (i <= 5 || i == n - 1) {
                log.info("\n[步骤{}] 第 {} 天，价格 = {}", ++stepCounter, i, prices[i]);
                log.info("│   未持有: max(昨天未持有={}, 今天卖={}) = {}",
                        dp[i-1][0], dp[i-1][1] + prices[i], dp[i][0]);
                log.info("│   持有: max(昨天持有={}, 今天买={}) = {}",
                        dp[i-1][1], -prices[i], dp[i][1]);
            }
        }

        log.info("\n{}", DPUtils.printSummary(stepCounter, dp[n - 1][0]));
        log.info("\n========== 计算完成 ==========\n");

        return dp[n - 1][0];
    }
}
