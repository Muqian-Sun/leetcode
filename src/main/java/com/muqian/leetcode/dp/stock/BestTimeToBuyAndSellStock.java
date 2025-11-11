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
 * ============================================================================
 * 💡 思路讲解：两种解法的思考方式
 * ============================================================================
 *
 * 【方法1：贪心思路】
 * - 核心思想：在历史最低点买入，在当前价格卖出
 * - 维护两个变量：
 *   → minPrice：到目前为止的最低价格
 *   → maxProfit：到目前为止的最大利润
 * - 遍历每一天：
 *   → 如果今天价格更低，更新minPrice
 *   → 计算今天卖出的利润：currentPrice - minPrice
 *   → 更新maxProfit
 * - 时间：O(n)，空间：O(1)
 * - 适合只买卖一次的场景
 *
 * 【方法2：状态机DP思路】
 * - 这是股票问题的通用框架，可以扩展到多次交易
 * - 定义状态：
 *   → dp[i][0]：第i天未持有股票的最大利润
 *   → dp[i][1]：第i天持有股票的最大利润（利润为负，因为花钱买入）
 * - 状态转移：
 *   → 未持有：可能是昨天就没有，或今天卖出
 *     dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
 *   → 持有：可能是昨天就持有，或今天买入（只能买一次，所以是-prices[i]）
 *     dp[i][1] = max(dp[i-1][1], -prices[i])
 * - 初始状态：
 *   → dp[0][0] = 0（第0天未持有，利润为0）
 *   → dp[0][1] = -prices[0]（第0天买入，利润为负）
 * - 最终答案：dp[n-1][0]（最后一天未持有的最大利润）
 *
 * 💡 状态机理解
 * - 把买入卖出看作状态转换：
 *   未持有 --[买入]--> 持有 --[卖出]--> 未持有
 * - 每一天都在这两个状态之间选择最优决策
 *
 * 💡 为什么DP方法通用？
 * - 可以轻松扩展到：
 *   → 允许多次交易（LeetCode 122）
 *   → 有冷却期（LeetCode 309）
 *   → 有手续费（LeetCode 714）
 *   → 最多k次交易（LeetCode 188）
 * - 状态机DP是股票问题的万能模板
 *
 * ============================================================================
 * 方法1：一次遍历（贪心）
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 方法2：动态规划（状态机）
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * ============================================================================
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
        log.info("\n╔══════════════════════════════════════════════════════════════════╗");
        log.info("║  LeetCode 121. 买卖股票的最佳时机 - 状态机DP详解               ║");
        log.info("╚══════════════════════════════════════════════════════════════════╝");
        log.info("\n【问题】股票价格：{}，只能买卖一次，求最大利润\n",
                DPUtils.arrayToString(prices));

        log.info("【思考过程】");
        log.info("  💭 每一天都有两个状态：");
        log.info("     → 状态0：未持有股票（可能从未买过，或已经卖出）");
        log.info("     → 状态1：持有股票（已经买入，还没卖）");
        log.info("  💡 状态转移：");
        log.info("     → dp[i][0] = max(昨天未持有, 今天卖出)");
        log.info("     → dp[i][1] = max(昨天持有, 今天买入)");
        log.info("  ✓ 这是股票问题的通用状态机DP框架\n");

        int n = prices.length;
        int[][] dp = new int[n][2];

        // 第0天
        dp[0][0] = 0;           // 未持有
        dp[0][1] = -prices[0];  // 持有（买入）

        stepCounter = 0;

        log.info("【初始化】第0天");
        log.info("  dp[0][0] = 0 (未持有股票，利润为0)");
        log.info("  dp[0][1] = -{} (买入股票，花掉{}，利润为-{})\n",
                prices[0], prices[0], prices[0]);

        log.info("【状态机DP过程】从第1天开始，在两个状态间做最优决策");
        log.info("─".repeat(70));

        for (int i = 1; i < n; i++) {
            int prevNotHold = dp[i - 1][0];
            int sellToday = dp[i - 1][1] + prices[i];
            dp[i][0] = Math.max(prevNotHold, sellToday);

            int prevHold = dp[i - 1][1];
            int buyToday = -prices[i];
            dp[i][1] = Math.max(prevHold, buyToday);

            if (i <= 5 || i == n - 1) {
                log.info("\n[步骤{}] 第 {} 天，价格 = {}", ++stepCounter, i, prices[i]);
                log.info("│");
                log.info("│  💭 状态0（未持有）的决策：");
                log.info("│     选择1：保持未持有 = dp[{}][0] = {}", i-1, prevNotHold);
                log.info("│     选择2：今天卖出 = dp[{}][1] + {} = {} + {} = {}",
                        i-1, prices[i], prevHold, prices[i], sellToday);
                log.info("│     ✓ dp[{}][0] = max({}, {}) = {}", i, prevNotHold, sellToday, dp[i][0]);

                String decision0 = dp[i][0] == sellToday ? "今天卖出" : "保持未持有";
                log.info("│       决策：{}", decision0);
                log.info("│");
                log.info("│  💭 状态1（持有）的决策：");
                log.info("│     选择1：保持持有 = dp[{}][1] = {}", i-1, prevHold);
                log.info("│     选择2：今天买入 = -{} = {}", prices[i], buyToday);
                log.info("│     ✓ dp[{}][1] = max({}, {}) = {}", i, prevHold, buyToday, dp[i][1]);

                String decision1 = dp[i][1] == buyToday ? "今天买入" : "保持持有";
                log.info("│       决策：{}", decision1);
                log.info("─".repeat(70));
            }
        }

        log.info("\n【结果】");
        log.info("  最大利润 = dp[{}][0] = {} (最后一天不持有股票的最大利润)",
                n-1, dp[n - 1][0]);
        log.info("  （通过状态机DP，清晰地模拟了买卖决策过程）");
        log.info("\n{}", DPUtils.printSummary(stepCounter, dp[n - 1][0]));

        return dp[n - 1][0];
    }
}
