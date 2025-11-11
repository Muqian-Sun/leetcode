package com.leetcode.dp;

import com.leetcode.dp.problems.*;

/**
 * 主程序 - 运行所有动态规划题目的测试
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  LeetCode Hot 100 - 动态规划题解集合");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 提示用户
        System.out.println("注意: 可视化输出可能较长，建议单独运行各个题目的 main 方法");
        System.out.println("本程序将运行所有题目的简单测试（不显示可视化）\n");

        runBasicDP();
        runPathProblems();
        runSubsequenceProblems();
        runKnapsackProblems();
        runStockProblems();
        runStringDP();
        runMatrixDP();

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  所有测试完成！");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    private static void runBasicDP() {
        System.out.println("\n【基础动态规划】");
        System.out.println("-".repeat(60));

        // 70. 爬楼梯
        P70_ClimbingStairs p70 = new P70_ClimbingStairs();
        System.out.println("70. 爬楼梯: n=5 -> " + p70.climbStairsWithVisualization(5, false));

        // 198. 打家劫舍
        P198_HouseRobber p198 = new P198_HouseRobber();
        int[] nums198 = {2, 7, 9, 3, 1};
        System.out.println("198. 打家劫舍: [2,7,9,3,1] -> " +
                         p198.robWithVisualization(nums198, false));

        // 53. 最大子数组和
        P53_MaximumSubarray p53 = new P53_MaximumSubarray();
        int[] nums53 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("53. 最大子数组和: [-2,1,-3,4,-1,2,1,-5,4] -> " +
                         p53.maxSubArrayWithVisualization(nums53, false));
    }

    private static void runPathProblems() {
        System.out.println("\n【路径问题】");
        System.out.println("-".repeat(60));

        // 62. 不同路径
        P62_UniquePaths p62 = new P62_UniquePaths();
        System.out.println("62. 不同路径: m=3, n=7 -> " +
                         p62.uniquePathsWithVisualization(3, 7, false));

        // 64. 最小路径和
        P64_MinimumPathSum p64 = new P64_MinimumPathSum();
        int[][] grid64 = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println("64. 最小路径和: [[1,3,1],[1,5,1],[4,2,1]] -> " +
                         p64.minPathSumWithVisualization(grid64, false));
    }

    private static void runSubsequenceProblems() {
        System.out.println("\n【子序列问题】");
        System.out.println("-".repeat(60));

        // 300. 最长递增子序列
        P300_LongestIncreasingSubsequence p300 = new P300_LongestIncreasingSubsequence();
        int[] nums300 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("300. 最长递增子序列: [10,9,2,5,3,7,101,18] -> " +
                         p300.lengthOfLISWithVisualization(nums300, false));
    }

    private static void runKnapsackProblems() {
        System.out.println("\n【背包问题】");
        System.out.println("-".repeat(60));

        // 416. 分割等和子集
        P416_PartitionEqualSubsetSum p416 = new P416_PartitionEqualSubsetSum();
        int[] nums416 = {1, 5, 11, 5};
        System.out.println("416. 分割等和子集: [1,5,11,5] -> " +
                         p416.canPartitionWithVisualization(nums416, false));

        // 322. 零钱兑换
        P322_CoinChange p322 = new P322_CoinChange();
        int[] coins322 = {1, 2, 5};
        System.out.println("322. 零钱兑换: coins=[1,2,5], amount=11 -> " +
                         p322.coinChangeWithVisualization(coins322, 11, false));

        // 279. 完全平方数
        P279_PerfectSquares p279 = new P279_PerfectSquares();
        System.out.println("279. 完全平方数: n=12 -> " +
                         p279.numSquaresWithVisualization(12, false));
    }

    private static void runStockProblems() {
        System.out.println("\n【股票问题】");
        System.out.println("-".repeat(60));

        // 121. 买卖股票的最佳时机
        P121_BestTimeToBuyAndSellStock p121 = new P121_BestTimeToBuyAndSellStock();
        int[] prices121 = {7, 1, 5, 3, 6, 4};
        System.out.println("121. 买卖股票的最佳时机: [7,1,5,3,6,4] -> " +
                         p121.maxProfitWithVisualization(prices121, false));

        // 122. 买卖股票的最佳时机 II
        P122_BestTimeToBuyAndSellStockII p122 = new P122_BestTimeToBuyAndSellStockII();
        int[] prices122 = {7, 1, 5, 3, 6, 4};
        System.out.println("122. 买卖股票的最佳时机 II: [7,1,5,3,6,4] -> " +
                         p122.maxProfitWithVisualization(prices122, false));

        // 309. 最佳买卖股票时机含冷冻期
        P309_BestTimeToBuyAndSellStockWithCooldown p309 =
            new P309_BestTimeToBuyAndSellStockWithCooldown();
        int[] prices309 = {1, 2, 3, 0, 2};
        System.out.println("309. 最佳买卖股票时机含冷冻期: [1,2,3,0,2] -> " +
                         p309.maxProfitWithVisualization(prices309, false));
    }

    private static void runStringDP() {
        System.out.println("\n【字符串 DP】");
        System.out.println("-".repeat(60));

        // 5. 最长回文子串
        P5_LongestPalindromicSubstring p5 = new P5_LongestPalindromicSubstring();
        System.out.println("5. 最长回文子串: \"babad\" -> \"" +
                         p5.longestPalindromeWithVisualization("babad", false) + "\"");

        // 72. 编辑距离
        P72_EditDistance p72 = new P72_EditDistance();
        System.out.println("72. 编辑距离: \"horse\" -> \"ros\" : " +
                         p72.minDistanceWithVisualization("horse", "ros", false));
    }

    private static void runMatrixDP() {
        System.out.println("\n【矩阵 DP】");
        System.out.println("-".repeat(60));

        // 221. 最大正方形
        P221_MaximalSquare p221 = new P221_MaximalSquare();
        char[][] matrix221 = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        };
        System.out.println("221. 最大正方形: 4x5矩阵 -> " +
                         p221.maximalSquareWithVisualization(matrix221, false));

        // 32. 最长有效括号
        P32_LongestValidParentheses p32 = new P32_LongestValidParentheses();
        System.out.println("32. 最长有效括号: \")()())\" -> " +
                         p32.longestValidParenthesesWithVisualization(")()())", false));
    }
}
