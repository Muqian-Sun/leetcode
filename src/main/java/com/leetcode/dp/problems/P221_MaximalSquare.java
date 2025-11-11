package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;

/**
 * LeetCode 221. 最大正方形
 * 难度: 中等
 *
 * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
 *
 * 示例 1:
 * 输入: matrix = [["1","0","1","0","0"],
 *               ["1","0","1","1","1"],
 *               ["1","1","1","1","1"],
 *               ["1","0","0","1","0"]]
 * 输出: 4
 *
 * 示例 2:
 * 输入: matrix = [["0","1"],
 *               ["1","0"]]
 * 输出: 1
 *
 * 示例 3:
 * 输入: matrix = [["0"]]
 * 输出: 0
 */
public class P221_MaximalSquare {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(m * n)
     *
     * 思路:
     * dp[i][j] 表示以 (i, j) 为右下角的最大正方形的边长
     * 状态转移方程:
     * - 如果 matrix[i][j] == '1':
     *   dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
     * - 否则: dp[i][j] = 0
     */
    public int maximalSquare(char[][] matrix) {
        return maximalSquareWithVisualization(matrix, true);
    }

    public int maximalSquareWithVisualization(char[][] matrix, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 221. 最大正方形");
            System.out.println("输入矩阵:");
            printCharMatrix(matrix);
            DPVisualizer.printTransition(
                "dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1"
            );
            System.out.println("说明: dp[i][j]表示以(i,j)为右下角的最大正方形的边长\n");
        }

        if (matrix == null || matrix.length == 0) {
            if (debug) DPVisualizer.printResult(0);
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxSide = 0;

        // 初始化第一行和第一列
        for (int i = 0; i < m; i++) {
            dp[i][0] = matrix[i][0] - '0';
            maxSide = Math.max(maxSide, dp[i][0]);
        }

        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j] - '0';
            maxSide = Math.max(maxSide, dp[0][j]);
        }

        if (debug) {
            DPVisualizer.printStep(0, "初始化边界");
            System.out.println("第一行和第一列直接从矩阵复制");
            DPVisualizer.print2DArray(dp, "初始化后的DP数组");
            System.out.println("当前最大边长: " + maxSide);
        }

        // 填充其他位置
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == '1') {
                    int top = dp[i - 1][j];
                    int left = dp[i][j - 1];
                    int topLeft = dp[i - 1][j - 1];
                    int minSide = Math.min(Math.min(top, left), topLeft);
                    dp[i][j] = minSide + 1;

                    if (debug) {
                        DPVisualizer.printStep((i - 1) * n + j - 1,
                            "计算 dp[" + i + "][" + j + "] (值='1')");
                        System.out.println("  上方: dp[" + (i-1) + "][" + j + "] = " + top);
                        System.out.println("  左方: dp[" + i + "][" + (j-1) + "] = " + left);
                        System.out.println("  左上: dp[" + (i-1) + "][" + (j-1) + "] = " + topLeft);
                        System.out.println("  最小值: min(" + top + ", " + left + ", " +
                                         topLeft + ") = " + minSide);
                        DPVisualizer.highlightUpdate(i, j, dp[i][j],
                            "正方形边长: " + minSide + " + 1");
                    }

                    maxSide = Math.max(maxSide, dp[i][j]);

                    if (debug) {
                        System.out.println("  当前最大边长: " + maxSide);
                    }
                }
            }

            if (debug && m <= 5 && n <= 5) {  // 只在小规模时显示完整矩阵
                DPVisualizer.print2DArray(dp, "处理完第" + i + "行");
            }
        }

        int area = maxSide * maxSide;

        if (debug) {
            System.out.println("\n最终DP数组:");
            DPVisualizer.print2DArray(dp, "完整DP数组");
            System.out.println("最大边长: " + maxSide);
            DPVisualizer.printResult("面积 = " + maxSide + " × " + maxSide + " = " + area);
        }

        return area;
    }

    /**
     * 方法2: 空间优化
     * 时间复杂度: O(m * n)
     * 空间复杂度: O(n)
     */
    public int maximalSquareOptimized(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;
        int[] dp = new int[n];
        int maxSide = 0;
        int prev = 0;  // 保存左上角的值

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = dp[j];

                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[j] = 1;
                    } else {
                        dp[j] = Math.min(Math.min(dp[j], dp[j - 1]), prev) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[j]);
                } else {
                    dp[j] = 0;
                }

                prev = temp;
            }
        }

        return maxSide * maxSide;
    }

    private void printCharMatrix(char[][] matrix) {
        for (char[] row : matrix) {
            System.out.print("  [");
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j]);
                if (j < row.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }

    public static void main(String[] args) {
        P221_MaximalSquare solution = new P221_MaximalSquare();

        // 测试用例1
        System.out.println("测试用例 1:");
        char[][] matrix1 = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        };
        solution.maximalSquareWithVisualization(matrix1, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        char[][] matrix2 = {
            {'0', '1'},
            {'1', '0'}
        };
        solution.maximalSquareWithVisualization(matrix2, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        char[][] matrix3 = {
            {'1', '1'},
            {'1', '1'}
        };
        solution.maximalSquareWithVisualization(matrix3, true);

        // 测试空间优化版本
        System.out.println("\n空间优化版本:");
        System.out.println("结果: " + solution.maximalSquareOptimized(matrix1));
    }
}
