package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;

/**
 * LeetCode 62. 不同路径
 * 难度: 中等
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 "Start" ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 "Finish" ）。
 * 问总共有多少条不同的路径？
 *
 * 示例 1:
 * 输入: m = 3, n = 7
 * 输出: 28
 *
 * 示例 2:
 * 输入: m = 3, n = 2
 * 输出: 3
 * 解释:
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向下
 */
public class P62_UniquePaths {

    /**
     * 方法1: 二维动态规划
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(m * n)
     *
     * 思路:
     * dp[i][j] 表示到达位置(i,j)的路径数
     * 状态转移方程: dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * 因为只能从上方或左方到达当前位置
     */
    public int uniquePaths(int m, int n) {
        return uniquePathsWithVisualization(m, n, true);
    }

    public int uniquePathsWithVisualization(int m, int n, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 62. 不同路径");
            System.out.println("网格大小: " + m + " x " + n);
            DPVisualizer.printTransition("dp[i][j] = dp[i-1][j] + dp[i][j-1]");
            System.out.println("说明: dp[i][j]表示到达位置(i,j)的路径数\n");
        }

        int[][] dp = new int[m][n];

        // 初始化第一行和第一列
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        if (debug) {
            DPVisualizer.printStep(0, "初始化边界");
            System.out.println("第一行和第一列都只有一条路径（一直向右或一直向下）");
            DPVisualizer.print2DArray(dp, "初始化后的DP数组");
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int fromTop = dp[i - 1][j];
                int fromLeft = dp[i][j - 1];
                dp[i][j] = fromTop + fromLeft;

                if (debug) {
                    DPVisualizer.printStep((i - 1) * n + j - 1, "计算位置(" + i + "," + j + ")");
                    System.out.println("  从上方来: dp[" + (i-1) + "][" + j + "] = " + fromTop);
                    System.out.println("  从左方来: dp[" + i + "][" + (j-1) + "] = " + fromLeft);
                    DPVisualizer.highlightUpdate(i, j, dp[i][j],
                        String.format("路径总数: %d + %d", fromTop, fromLeft));
                    DPVisualizer.print2DArray(dp, "当前DP数组状态");
                }
            }
        }

        if (debug) {
            DPVisualizer.printResult(dp[m - 1][n - 1]);
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 方法2: 一维空间优化
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(n)
     */
    public int uniquePathsOptimized(int m, int n) {
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }

        return dp[n - 1];
    }

    /**
     * 方法3: 数学组合公式
     * 时间复杂度: O(min(m, n))
     * 空间复杂度: O(1)
     *
     * 总共需要走 m+n-2 步，其中 m-1 步向下，n-1 步向右
     * 相当于从 m+n-2 步中选择 m-1 步向下，即 C(m+n-2, m-1)
     */
    public int uniquePathsMath(int m, int n) {
        long result = 1;
        // 计算 C(m+n-2, m-1)
        for (int i = 1; i <= Math.min(m - 1, n - 1); i++) {
            result = result * (m + n - 1 - i) / i;
        }
        return (int) result;
    }

    public static void main(String[] args) {
        P62_UniquePaths solution = new P62_UniquePaths();

        // 测试用例1
        System.out.println("测试用例 1:");
        solution.uniquePathsWithVisualization(3, 3, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        solution.uniquePathsWithVisualization(3, 7, true);

        // 测试优化版本
        System.out.println("\n空间优化版本:");
        System.out.println("m=3, n=7, 结果: " + solution.uniquePathsOptimized(3, 7));

        // 测试数学公式版本
        System.out.println("\n数学公式版本:");
        System.out.println("m=3, n=7, 结果: " + solution.uniquePathsMath(3, 7));
    }
}
