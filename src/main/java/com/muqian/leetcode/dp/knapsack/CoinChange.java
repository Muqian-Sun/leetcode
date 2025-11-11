package com.muqian.leetcode.dp.knapsack;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * LeetCode 322. 零钱兑换
 *
 * 题目描述：
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * 计算并返回可以凑成总金额所需的最少的硬币个数 。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1 。
 *
 * 你可以认为每种硬币的数量是无限的。
 *
 * 示例 1:
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 *
 * 示例 2:
 * 输入：coins = [2], amount = 3
 * 输出：-1
 *
 * 示例 3:
 * 输入：coins = [1], amount = 0
 * 输出：0
 *
 * 解题思路：
 *
 * 方法1：完全背包问题（动态规划）
 * - 状态定义：dp[i] 表示凑成金额i所需的最少硬币数
 * - 状态转移方程：dp[i] = min(dp[i], dp[i-coin] + 1)
 *   对每个硬币coin，尝试使用它来凑金额i
 * - 初始状态：dp[0] = 0，其他为∞
 * 时间复杂度：O(amount * coins.length)
 * 空间复杂度：O(amount)
 *
 * @author muqian
 */
public class CoinChange {
    private static final Logger log = LoggerFactory.getLogger(CoinChange.class);

    private int stepCounter = 0;

    /**
     * 方法1：完全背包动态规划
     *
     * @param coins 硬币面额数组
     * @param amount 总金额
     * @return 最少硬币数，无法凑成返回-1
     */
    public int coinChange(int[] coins, int amount) {
        log.info("\n========== 开始计算零钱兑换最少硬币数（动态规划） ==========");
        log.info("硬币面额：{}", DPUtils.arrayToString(coins));
        log.info("目标金额：{}", amount);
        log.info("状态转移方程：dp[i] = min(dp[i], dp[i-coin] + 1)");
        log.info("说明：dp[i]表示凑成金额i所需的最少硬币数\n");

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);  // 初始化为一个不可能的大值
        dp[0] = 0;  // 金额为0需要0个硬币

        stepCounter = 0;

        log.info("[步骤{}] 初始化", ++stepCounter);
        log.info("│   dp[0] = 0 (金额为0需要0个硬币)");
        log.info("│   其他位置初始化为 {} (表示不可达)", amount + 1);
        if (amount <= 20) {
            log.info("│   DP数组: {}\n", DPUtils.format1DArray(dp));
        }

        for (int i = 1; i <= amount; i++) {
            boolean updated = false;

            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != amount + 1) {
                    int newCount = dp[i - coin] + 1;
                    if (newCount < dp[i]) {
                        dp[i] = newCount;
                        updated = true;

                        if (i <= 15 || i == amount) {
                            log.info("[步骤{}] 金额 {} 使用硬币 {}", ++stepCounter, i, coin);
                            log.info("│   dp[{}] + 1 = {} + 1 = {}", i-coin, dp[i-coin], newCount);
                            log.info("│   更新 dp[{}] = {}", i, dp[i]);
                        }
                    }
                }
            }

            if ((i <= 15 || i == amount) && dp[i] != amount + 1) {
                if (amount <= 20) {
                    log.info("│   DP数组: {}\n", DPUtils.format1DArray(dp));
                } else {
                    log.info("│   dp[{}] = {}\n", i, dp[i]);
                }
            } else if (i <= 15 && !updated) {
                log.info("[步骤{}] 金额 {} 无法凑成\n", ++stepCounter, i);
            }
        }

        int result = dp[amount] > amount ? -1 : dp[amount];

        log.info("{}", DPUtils.printSummary(stepCounter, result));
        log.info("\n========== 计算完成 ==========\n");

        return result;
    }
}
