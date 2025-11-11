package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;

/**
 * LeetCode 322. 零钱兑换
 * 难度: 中等
 *
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * 计算并返回可以凑成总金额所需的最少的硬币个数 。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1 。
 *
 * 你可以认为每种硬币的数量是无限的。
 *
 * 示例 1:
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 *
 * 示例 2:
 * 输入: coins = [2], amount = 3
 * 输出: -1
 *
 * 示例 3:
 * 输入: coins = [1], amount = 0
 * 输出: 0
 */
public class P322_CoinChange {

    /**
     * 方法1: 完全背包问题
     * 时间复杂度: O(n * amount)
     * 空间复杂度: O(amount)
     *
     * 思路:
     * dp[i] 表示凑成金额i所需的最少硬币数
     * 状态转移方程: dp[i] = min(dp[i], dp[i-coin] + 1)
     */
    public int coinChange(int[] coins, int amount) {
        return coinChangeWithVisualization(coins, amount, true);
    }

    public int coinChangeWithVisualization(int[] coins, int amount, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 322. 零钱兑换");
            System.out.println("硬币面额: " + Arrays.toString(coins));
            System.out.println("目标金额: " + amount);
            DPVisualizer.printTransition("dp[i] = min(dp[i], dp[i-coin] + 1)");
            System.out.println("说明: dp[i]表示凑成金额i所需的最少硬币数\n");
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);  // 初始化为一个不可能的大值
        dp[0] = 0;  // 金额为0需要0个硬币

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("dp[0] = 0 (金额为0需要0个硬币)");
            System.out.println("其他位置初始化为 " + (amount + 1) + " (表示不可达)");
            DPVisualizer.print1DArray(dp, "初始化后的DP数组");
        }

        for (int i = 1; i <= amount; i++) {
            if (debug) {
                DPVisualizer.printStep(i, "计算金额 " + i + " 的最少硬币数");
            }

            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != amount + 1) {
                    int newCount = dp[i - coin] + 1;

                    if (debug && newCount < dp[i]) {
                        System.out.println("  使用硬币 " + coin + ": dp[" + (i-coin) +
                                         "] + 1 = " + dp[i-coin] + " + 1 = " + newCount);
                    }

                    dp[i] = Math.min(dp[i], newCount);
                }
            }

            if (debug) {
                if (dp[i] == amount + 1) {
                    System.out.println("  结果: 无法凑成金额 " + i);
                } else {
                    DPVisualizer.highlightUpdate(i, dp[i], "最少硬币数");
                }
                DPVisualizer.print1DArray(dp, "当前DP数组状态");
            }
        }

        int result = dp[amount] > amount ? -1 : dp[amount];

        if (debug) {
            DPVisualizer.printResult(result);
        }

        return result;
    }

    /**
     * 方法2: 带路径记录的版本
     * 可以输出具体使用了哪些硬币
     */
    public int[] coinChangeWithPath(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        int[] parent = new int[amount + 1];  // 记录使用的硬币
        Arrays.fill(dp, amount + 1);
        Arrays.fill(parent, -1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != amount + 1) {
                    int newCount = dp[i - coin] + 1;
                    if (newCount < dp[i]) {
                        dp[i] = newCount;
                        parent[i] = coin;  // 记录使用的硬币
                    }
                }
            }
        }

        if (dp[amount] > amount) {
            return new int[]{-1};
        }

        // 重建路径
        int[] result = new int[dp[amount]];
        int curr = amount;
        for (int i = dp[amount] - 1; i >= 0; i--) {
            result[i] = parent[curr];
            curr -= parent[curr];
        }

        return result;
    }

    public static void main(String[] args) {
        P322_CoinChange solution = new P322_CoinChange();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[] coins1 = {1, 2, 5};
        solution.coinChangeWithVisualization(coins1, 11, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[] coins2 = {2};
        solution.coinChangeWithVisualization(coins2, 3, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        int[] coins3 = {1, 2, 5};
        solution.coinChangeWithVisualization(coins3, 7, true);

        // 测试带路径记录的版本
        System.out.println("\n带路径记录版本:");
        int[] path = solution.coinChangeWithPath(coins1, 11);
        if (path[0] == -1) {
            System.out.println("无法凑成");
        } else {
            System.out.println("使用的硬币: " + Arrays.toString(path));
        }
    }
}
