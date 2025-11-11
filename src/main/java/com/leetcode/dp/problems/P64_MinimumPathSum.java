package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;

/**
 * LeetCode 64. 最小路径和
 * 难度: 中等
 *
 * 给定一个包含非负整数的 m x n 网格 grid ，
 * 请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例 1:
 * 输入: grid = [[1,3,1],
 *              [1,5,1],
 *              [4,2,1]]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 *
 * 示例 2:
 * 输入: grid = [[1,2,3],
 *              [4,5,6]]
 * 输出: 12
 */
public class P64_MinimumPathSum {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(m * n)
     *
     * 思路:
     * dp[i][j] 表示从起点到达位置(i,j)的最小路径和
     * 状态转移方程: dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
     */
    public int minPathSum(int[][] grid) {
        return minPathSumWithVisualization(grid, true);
    }

    public int minPathSumWithVisualization(int[][] grid, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 64. 最小路径和");
            System.out.println("原始网格:");
            DPVisualizer.print2DArray(grid, "输入网格");
            DPVisualizer.printTransition("dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]");
            System.out.println("说明: dp[i][j]表示从起点到达(i,j)的最小路径和\n");
        }

        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // 初始化起点
        dp[0][0] = grid[0][0];

        // 初始化第一行
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // 初始化第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        if (debug) {
            DPVisualizer.printStep(0, "初始化边界");
            System.out.println("第一行: 只能从左边来，累加即可");
            System.out.println("第一列: 只能从上边来，累加即可");
            DPVisualizer.print2DArray(dp, "初始化后的DP数组");
        }

        // 填充其他位置
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int fromTop = dp[i - 1][j];
                int fromLeft = dp[i][j - 1];
                dp[i][j] = Math.min(fromTop, fromLeft) + grid[i][j];

                if (debug) {
                    DPVisualizer.printStep((i - 1) * n + j - 1,
                        "计算位置(" + i + "," + j + "), 当前值=" + grid[i][j]);
                    System.out.println("  从上方来的路径和: dp[" + (i-1) + "][" + j + "] = " + fromTop);
                    System.out.println("  从左方来的路径和: dp[" + i + "][" + (j-1) + "] = " + fromLeft);
                    System.out.println("  选择较小值: min(" + fromTop + ", " + fromLeft + ") = " +
                                     Math.min(fromTop, fromLeft));
                    DPVisualizer.highlightUpdate(i, j, dp[i][j],
                        String.format("加上当前值: %d + %d", Math.min(fromTop, fromLeft), grid[i][j]));
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
     * 方法2: 空间优化 - 原地修改
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(1)
     */
    public int minPathSumInPlace(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // 初始化第一行
        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j - 1];
        }

        // 初始化第一列
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        // 填充其他位置
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }

        return grid[m - 1][n - 1];
    }

    /**
     * 方法3: 一维空间优化
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(n)
     */
    public int minPathSumOptimized(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];

        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        P64_MinimumPathSum solution = new P64_MinimumPathSum();

        // 测试用例1
        System.out.println("测试用例 1:");
        int[][] grid1 = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        solution.minPathSumWithVisualization(grid1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        int[][] grid2 = {
            {1, 2, 3},
            {4, 5, 6}
        };
        solution.minPathSumWithVisualization(grid2, true);

        // 测试优化版本
        System.out.println("\n空间优化版本:");
        int[][] grid3 = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };
        System.out.println("结果: " + solution.minPathSumOptimized(grid3));
    }
}
