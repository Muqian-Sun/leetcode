package com.muqian.leetcode.dp.path;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 62. 不同路径
 *
 * 题目描述：
 * 一个机器人位于一个 m x n 网格的左上角（起始点在下图中标记为 "Start" ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 "Finish" ）。
 * 问总共有多少条不同的路径？
 *
 * 示例 1:
 * 输入：m = 3, n = 7
 * 输出：28
 *
 * 示例 2:
 * 输入：m = 3, n = 2
 * 输出：3
 * 解释：
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向下
 *
 * 解题思路：
 *
 * 方法1：动态规划
 * - 状态定义：dp[i][j] 表示到达位置(i,j)的路径数
 * - 状态转移方程：dp[i][j] = dp[i-1][j] + dp[i][j-1]
 *   因为只能从上方或左方到达当前位置
 * - 初始状态：dp[i][0] = 1, dp[0][j] = 1（第一行和第一列只有一条路径）
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 *
 * 方法2：空间优化
 * - 只需要上一行的数据，可以优化到一维数组
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class UniquePaths {
    private static final Logger log = LoggerFactory.getLogger(UniquePaths.class);

    private int stepCounter = 0;

    /**
     * 方法1：二维动态规划
     *
     * @param m 网格行数
     * @param n 网格列数
     * @return 不同路径数
     */
    public int uniquePaths(int m, int n) {
        log.info("\n========== 开始计算不同路径数（二维动态规划） ==========");
        log.info("网格大小：{} × {}", m, n);
        log.info("状态转移方程：dp[i][j] = dp[i-1][j] + dp[i][j-1]");
        log.info("说明：dp[i][j]表示到达位置(i,j)的路径数\n");

        int[][] dp = new int[m][n];

        // 初始化第一行和第一列
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        stepCounter = 0;

        log.info("[步骤{}] 初始化边界", ++stepCounter);
        log.info("│   第一行和第一列都只有一条路径（一直向右或一直向下）");
        log.info("│   DP数组:{}", DPUtils.format2DArray(dp));

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int fromTop = dp[i - 1][j];
                int fromLeft = dp[i][j - 1];
                dp[i][j] = fromTop + fromLeft;

                if ((i <= 2 && j <= 2) || (i == m-1 && j == n-1)) {
                    log.info("\n[步骤{}] 计算位置({}, {})", ++stepCounter, i, j);
                    log.info("│   从上方来: dp[{}][{}] = {}", i-1, j, fromTop);
                    log.info("│   从左方来: dp[{}][{}] = {}", i, j-1, fromLeft);
                    log.info("│   dp[{}][{}] = {} + {} = {}", i, j, fromTop, fromLeft, dp[i][j]);
                }
            }
        }

        log.info("\n最终DP数组:{}", DPUtils.format2DArray(dp));
        log.info("{}", DPUtils.printSummary(stepCounter, dp[m - 1][n - 1]));
        log.info("\n========== 计算完成 ==========\n");

        return dp[m - 1][n - 1];
    }

    /**
     * 方法2：一维空间优化
     *
     * @param m 网格行数
     * @param n 网格列数
     * @return 不同路径数
     */
    public int uniquePathsOptimized(int m, int n) {
        log.info("\n========== 开始计算不同路径数（一维空间优化） ==========");
        log.info("网格大小：{} × {}", m, n);
        log.info("优化：使用一维数组滚动更新\n");

        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        stepCounter = 0;

        log.info("[步骤{}] 初始化：dp = {}", ++stepCounter, DPUtils.format1DArray(dp));

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }

            if (i <= 2 || i == m - 1) {
                log.info("\n[步骤{}] 处理第 {} 行", ++stepCounter, i);
                log.info("│   dp = {}", DPUtils.format1DArray(dp));
            }
        }

        log.info("\n{}", DPUtils.printSummary(stepCounter, dp[n - 1]));
        log.info("\n========== 计算完成 ==========\n");

        return dp[n - 1];
    }
}
