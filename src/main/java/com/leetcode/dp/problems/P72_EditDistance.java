package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;

/**
 * LeetCode 72. 编辑距离
 * 难度: 困难
 *
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
 *
 * 你可以对一个单词进行如下三种操作：
 * - 插入一个字符
 * - 删除一个字符
 * - 替换一个字符
 *
 * 示例 1:
 * 输入: word1 = "horse", word2 = "ros"
 * 输出: 3
 * 解释:
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 *
 * 示例 2:
 * 输入: word1 = "intention", word2 = "execution"
 * 输出: 5
 * 解释:
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 */
public class P72_EditDistance {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(m * n)
     *
     * 思路:
     * dp[i][j] 表示 word1[0..i-1] 转换成 word2[0..j-1] 的最少操作数
     * 状态转移方程:
     * - 如果 word1[i-1] == word2[j-1]: dp[i][j] = dp[i-1][j-1]
     * - 否则: dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
     *   - dp[i-1][j] + 1: 删除word1[i-1]
     *   - dp[i][j-1] + 1: 插入word2[j-1]
     *   - dp[i-1][j-1] + 1: 替换word1[i-1]为word2[j-1]
     */
    public int minDistance(String word1, String word2) {
        return minDistanceWithVisualization(word1, word2, true);
    }

    public int minDistanceWithVisualization(String word1, String word2, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 72. 编辑距离");
            System.out.println("word1: \"" + word1 + "\"");
            System.out.println("word2: \"" + word2 + "\"");
            System.out.println("\n操作: 插入、删除、替换");
            DPVisualizer.printTransition(
                "dp[i][j] = word1[i-1] == word2[j-1] ? dp[i-1][j-1] : " +
                "min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1"
            );
            System.out.println("说明: dp[i][j]表示word1[0..i-1]转换成word2[0..j-1]的最少操作数\n");
        }

        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        // 初始化: word1转换为空字符串需要删除所有字符
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // 初始化: 空字符串转换为word2需要插入所有字符
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        if (debug) {
            DPVisualizer.printStep(0, "初始化边界");
            System.out.println("dp[i][0] = i (word1[0..i-1]转换为空串需要删除i个字符)");
            System.out.println("dp[0][j] = j (空串转换为word2[0..j-1]需要插入j个字符)");
            DPVisualizer.print2DArray(dp, "初始化后的DP数组");
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char c1 = word1.charAt(i - 1);
                char c2 = word2.charAt(j - 1);

                if (debug) {
                    DPVisualizer.printStep((i - 1) * n + j - 1,
                        "计算 dp[" + i + "][" + j + "] - word1[" + (i-1) + "]='" +
                        c1 + "', word2[" + (j-1) + "]='" + c2 + "'");
                }

                if (c1 == c2) {
                    // 字符相同，不需要操作
                    dp[i][j] = dp[i - 1][j - 1];

                    if (debug) {
                        System.out.println("  字符相同，无需操作: dp[" + i + "][" + j +
                                         "] = dp[" + (i-1) + "][" + (j-1) + "] = " + dp[i][j]);
                    }
                } else {
                    // 字符不同，选择三种操作中的最小值
                    int delete = dp[i - 1][j] + 1;      // 删除
                    int insert = dp[i][j - 1] + 1;      // 插入
                    int replace = dp[i - 1][j - 1] + 1; // 替换

                    dp[i][j] = Math.min(Math.min(delete, insert), replace);

                    if (debug) {
                        System.out.println("  字符不同，选择最小操作:");
                        System.out.println("    删除: dp[" + (i-1) + "][" + j + "] + 1 = " + delete);
                        System.out.println("    插入: dp[" + i + "][" + (j-1) + "] + 1 = " + insert);
                        System.out.println("    替换: dp[" + (i-1) + "][" + (j-1) + "] + 1 = " + replace);
                        System.out.println("    → dp[" + i + "][" + j + "] = " + dp[i][j]);
                    }
                }
            }

            if (debug && m <= 10 && n <= 10) {  // 只在小规模时显示完整矩阵
                DPVisualizer.print2DArray(dp, "处理完word1[0.." + (i-1) + "]");
            }
        }

        if (debug) {
            System.out.println("\n最终DP数组:");
            DPVisualizer.print2DArray(dp, "完整DP数组");
            DPVisualizer.printResult(dp[m][n]);
        }

        return dp[m][n];
    }

    /**
     * 方法2: 空间优化
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(n)
     */
    public int minDistanceOptimized(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[] dp = new int[n + 1];

        // 初始化
        for (int j = 0; j <= n; j++) {
            dp[j] = j;
        }

        for (int i = 1; i <= m; i++) {
            int prev = dp[0];
            dp[0] = i;

            for (int j = 1; j <= n; j++) {
                int temp = dp[j];

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[j] = prev;
                } else {
                    dp[j] = Math.min(Math.min(dp[j], dp[j - 1]), prev) + 1;
                }

                prev = temp;
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        P72_EditDistance solution = new P72_EditDistance();

        // 测试用例1
        System.out.println("测试用例 1:");
        solution.minDistanceWithVisualization("horse", "ros", true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        solution.minDistanceWithVisualization("intention", "execution", true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        solution.minDistanceWithVisualization("abc", "abc", true);

        // 测试空间优化版本
        System.out.println("\n空间优化版本:");
        System.out.println("结果: " + solution.minDistanceOptimized("horse", "ros"));
    }
}
