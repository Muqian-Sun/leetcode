package com.muqian.leetcode.dp.basic;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 70. 爬楼梯
 *
 * 题目描述：
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
 *
 * 解题思路：
 *
 * 方法1：动态规划
 * - 状态定义：dp[i] 表示爬到第 i 阶楼梯的方法数
 * - 状态转移方程：dp[i] = dp[i-1] + dp[i-2]
 *   因为到达第i阶可以从第i-1阶爬1步，或从第i-2阶爬2步
 * - 初始状态：dp[1] = 1, dp[2] = 2
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 方法2：空间优化
 * - 因为只需要前两个状态，可以用两个变量代替数组
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * @author muqian
 */
public class ClimbingStairs {
    private static final Logger log = LoggerFactory.getLogger(ClimbingStairs.class);

    private int stepCounter = 0;

    /**
     * 方法1：动态规划
     *
     * @param n 楼梯阶数
     * @return 爬到楼顶的方法数
     */
    public int climbStairs(int n) {
        log.info("\n========== 开始计算爬楼梯方法数（动态规划） ==========");
        log.info("问题：爬到第 {} 阶楼梯有多少种方法？", n);
        log.info("状态转移方程：dp[i] = dp[i-1] + dp[i-2]\n");

        if (n <= 2) {
            log.info("n <= 2，直接返回 {}", n);
            log.info("\n========== 计算完成 ==========\n");
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        stepCounter = 0;

        log.info("[步骤{}] 初始化：dp[1] = 1, dp[2] = 2", ++stepCounter);
        log.info("DP数组: {}\n", DPUtils.format1DArray(dp));

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];

            log.info("[步骤{}] 计算第 {} 阶", ++stepCounter, i);
            log.info("│   dp[{}] = dp[{}] + dp[{}]", i, i-1, i-2);
            log.info("│   dp[{}] = {} + {} = {}", i, dp[i-1], dp[i-2], dp[i]);
            log.info("│   DP数组: {}\n", DPUtils.format1DArray(dp));
        }

        log.info("{}", DPUtils.printSummary(stepCounter, dp[n]));
        log.info("\n========== 计算完成 ==========\n");

        return dp[n];
    }

    /**
     * 方法2：空间优化的动态规划
     *
     * @param n 楼梯阶数
     * @return 爬到楼顶的方法数
     */
    public int climbStairsOptimized(int n) {
        log.info("\n========== 开始计算爬楼梯方法数（空间优化） ==========");
        log.info("问题：爬到第 {} 阶楼梯有多少种方法？", n);
        log.info("优化：使用两个变量代替数组\n");

        if (n <= 2) {
            log.info("n <= 2，直接返回 {}", n);
            log.info("\n========== 计算完成 ==========\n");
            return n;
        }

        int prev2 = 1;  // dp[i-2]
        int prev1 = 2;  // dp[i-1]
        int current = 0;

        stepCounter = 0;

        log.info("[步骤{}] 初始化：prev2 = 1, prev1 = 2", ++stepCounter);

        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;

            log.info("\n[步骤{}] 计算第 {} 阶", ++stepCounter, i);
            log.info("│   current = prev1 + prev2");
            log.info("│   current = {} + {} = {}", prev1, prev2, current);
            log.info("│   更新：prev2 = {}, prev1 = {}", prev1, current);

            prev2 = prev1;
            prev1 = current;
        }

        log.info("\n{}", DPUtils.printSummary(stepCounter, current));
        log.info("\n========== 计算完成 ==========\n");

        return current;
    }

    /**
     * 方法3：斐波那契数列（数学方法）
     * 使用通项公式直接计算
     *
     * @param n 楼梯阶数
     * @return 爬到楼顶的方法数
     */
    public int climbStairsMath(int n) {
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;
        double psi = (1 - sqrt5) / 2;
        return (int) Math.round((Math.pow(phi, n + 1) - Math.pow(psi, n + 1)) / sqrt5);
    }
}
