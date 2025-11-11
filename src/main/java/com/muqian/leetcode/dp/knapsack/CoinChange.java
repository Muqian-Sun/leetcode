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
 * ============================================================================
 * 💡 思路讲解：如何想到用动态规划（完全背包）？
 * ============================================================================
 *
 * 【第1步】分析问题特征
 * - 问题：凑成总金额需要的最少硬币数
 * - 约束：每种硬币可以用无限次（这是"完全背包"的特征）
 * - 关键观察：凑成金额i，可以尝试使用每一种硬币
 *   → 如果使用面额为coin的硬币，那么问题变成：凑成金额(i-coin)需要多少硬币？
 *   → 然后再加上这一枚硬币，总数为 dp[i-coin] + 1
 * - 发现：大问题依赖小问题的解，符合动态规划特征！
 *
 * 【第2步】定义状态
 * - 思考：我们要求的是什么？→ 凑成某个金额需要的最少硬币数
 * - 定义：dp[i] = 凑成金额i所需的最少硬币数
 * - 如果无法凑成金额i，则dp[i] = ∞（或一个很大的数）
 *
 * 【第3步】推导状态转移方程
 * - 站在金额i，思考：我可以用哪种硬币？
 *   → 遍历每一种面额coin
 *   → 如果 i >= coin（金额足够使用这个硬币）
 *   → 使用这个硬币后：dp[i] = dp[i - coin] + 1
 * - 因为要求最少硬币数，所以取所有可能的最小值
 * - 得出：dp[i] = min(dp[i], dp[i-coin] + 1) 对所有coin
 *
 * 【第4步】确定初始状态
 * - dp[0] = 0：凑成金额0需要0个硬币
 * - dp[其他] = ∞：初始时认为无法凑成
 *
 * 【第5步】确定计算顺序
 * - 从小到大计算金额1, 2, 3, ..., amount
 * - 对每个金额，尝试所有硬币
 *
 * 💡 为什么是完全背包？
 * - 01背包：每个物品只能用一次
 * - 完全背包：每个物品可以用无限次
 * - 这道题每种硬币可以用无限次，所以是完全背包
 *
 * 💡 完全背包的遍历顺序
 * - 外层循环：遍历金额（从小到大）
 * - 内层循环：遍历硬币（顺序任意）
 * - 关键：每个金额都尝试所有硬币，取最小值
 *
 * 💡 这道题的精髓
 * - 完全背包的经典应用
 * - "求最少"对应取最小值，"求方案数"对应累加
 * - 初始化技巧：用一个不可能的大值表示"无法达到"
 *
 * ============================================================================
 * 方法1：完全背包动态规划
 * 时间复杂度：O(amount * coins.length)
 * 空间复杂度：O(amount)
 * ============================================================================
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
        log.info("\n╔══════════════════════════════════════════════════════════════════╗");
        log.info("║  LeetCode 322. 零钱兑换 - 完全背包DP详解                       ║");
        log.info("╚══════════════════════════════════════════════════════════════════╝");
        log.info("\n【问题】硬币面额：{}，目标金额：{}，求最少硬币数\n",
                DPUtils.arrayToString(coins), amount);

        log.info("【思考过程】");
        log.info("  💭 如何凑成金额i？");
        log.info("     → 尝试使用每一种面额的硬币coin");
        log.info("     → 使用coin后，还需凑成金额(i-coin)");
        log.info("     → 总硬币数 = dp[i-coin] + 1");
        log.info("  💡 这是完全背包问题（每种硬币可用无限次）");
        log.info("  ✓ 状态转移：dp[i] = min(dp[i], dp[i-coin] + 1)\n");

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);  // 初始化为一个不可能的大值
        dp[0] = 0;  // 金额为0需要0个硬币

        stepCounter = 0;

        log.info("【初始化】");
        log.info("  dp[0] = 0 (金额为0需要0个硬币)");
        log.info("  其他位置 = {} (表示暂时无法凑成，用大值标记)", amount + 1);
        if (amount <= 20) {
            log.info("  DP数组: {}\n", DPUtils.format1DArray(dp));
        }

        log.info("【完全背包过程】从金额1开始，逐个尝试所有硬币");
        log.info("─".repeat(70));

        for (int i = 1; i <= amount; i++) {
            boolean updated = false;
            int oldValue = dp[i];

            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != amount + 1) {
                    int newCount = dp[i - coin] + 1;
                    if (newCount < dp[i]) {
                        if ((i <= 15 || i == amount) && !updated) {
                            log.info("\n[步骤{}] 凑成金额 {}", ++stepCounter, i);
                            log.info("│");
                        }

                        dp[i] = newCount;
                        updated = true;

                        if (i <= 15 || i == amount) {
                            log.info("│  💡 尝试使用硬币 {}", coin);
                            log.info("│     剩余金额 {} 需要 {} 个硬币", i-coin, dp[i-coin]);
                            log.info("│     加上这枚硬币 → 总共 {} + 1 = {} 个", dp[i-coin], newCount);
                            log.info("│     ✓ 更新：dp[{}] = {} (原值: {})", i, dp[i],
                                    oldValue == amount + 1 ? "∞" : oldValue);
                            oldValue = dp[i];
                        }
                    }
                }
            }

            if ((i <= 15 || i == amount) && updated) {
                log.info("│");
                if (amount <= 20) {
                    log.info("│  当前DP数组: {}", DPUtils.format1DArray(dp));
                } else {
                    log.info("│  dp[{}] = {} (凑成金额{}需要{}个硬币)",
                            i, dp[i], i, dp[i]);
                }
                log.info("─".repeat(70));
            } else if (i <= 15 && !updated) {
                log.info("\n[步骤{}] 金额 {} 无法凑成（所有硬币都试过了）", ++stepCounter, i);
                log.info("─".repeat(70));
            }
        }

        int result = dp[amount] > amount ? -1 : dp[amount];

        log.info("\n【结果】");
        if (result == -1) {
            log.info("  无法凑成金额 {} （返回 -1）", amount);
        } else {
            log.info("  凑成金额 {} 最少需要 {} 个硬币", amount, result);
            log.info("  （通过完全背包DP，在O(amount*coins)时间内求解）");
        }
        log.info("\n{}", DPUtils.printSummary(stepCounter, result));

        return result;
    }
}
