package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 279. 完全平方数
 * 难度: 中等
 *
 * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
 * 完全平方数 是一个整数，其值等于另一个整数的平方；
 * 换句话说，其值等于一个整数自乘的积。
 * 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
 *
 * 示例 1:
 * 输入: n = 12
 * 输出: 3
 * 解释: 12 = 4 + 4 + 4
 *
 * 示例 2:
 * 输入: n = 13
 * 输出: 2
 * 解释: 13 = 4 + 9
 */
public class P279_PerfectSquares {

    /**
     * 方法1: 动态规划（完全背包）
     * 时间复杂度: O(n * sqrt(n))
     * 空间复杂度: O(n)
     *
     * 思路:
     * dp[i] 表示和为i的完全平方数的最少数量
     * 状态转移方程: dp[i] = min(dp[i], dp[i - j*j] + 1)
     */
    public int numSquares(int n) {
        return numSquaresWithVisualization(n, true);
    }

    public int numSquaresWithVisualization(int n, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 279. 完全平方数");
            System.out.println("目标数: " + n);

            // 显示可用的完全平方数
            List<Integer> squares = new ArrayList<>();
            for (int i = 1; i * i <= n; i++) {
                squares.add(i * i);
            }
            System.out.println("可用的完全平方数: " + squares);

            DPVisualizer.printTransition("dp[i] = min(dp[i], dp[i - j*j] + 1)");
            System.out.println("说明: dp[i]表示和为i的完全平方数的最少数量\n");
        }

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("dp[0] = 0 (和为0需要0个数)");
            System.out.println("其他位置初始化为 MAX_VALUE (表示不可达)");
        }

        for (int i = 1; i <= n; i++) {
            if (debug) {
                DPVisualizer.printStep(i, "计算和为 " + i + " 的最少数量");
            }

            for (int j = 1; j * j <= i; j++) {
                int square = j * j;
                if (dp[i - square] != Integer.MAX_VALUE) {
                    int newCount = dp[i - square] + 1;

                    if (debug && newCount < dp[i]) {
                        System.out.println("  使用完全平方数 " + square + " (" + j + "²): " +
                                         "dp[" + (i - square) + "] + 1 = " +
                                         dp[i - square] + " + 1 = " + newCount);
                    }

                    dp[i] = Math.min(dp[i], newCount);
                }
            }

            if (debug) {
                DPVisualizer.highlightUpdate(i, dp[i], "最少数量");
                if (i <= 20 || i == n) {  // 只显示前20个和最后一个，避免输出太多
                    DPVisualizer.print1DArray(Arrays.copyOfRange(dp, 0, Math.min(i + 1, 21)),
                                             "DP数组状态 (前" + Math.min(i + 1, 21) + "个)");
                }
            }
        }

        if (debug) {
            DPVisualizer.printResult(dp[n]);
        }

        return dp[n];
    }

    /**
     * 方法2: 带路径记录的版本
     * 可以输出具体使用了哪些完全平方数
     */
    public int[] numSquaresWithPath(int n) {
        int[] dp = new int[n + 1];
        int[] parent = new int[n + 1];  // 记录使用的完全平方数
        Arrays.fill(dp, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                int square = j * j;
                if (dp[i - square] != Integer.MAX_VALUE) {
                    int newCount = dp[i - square] + 1;
                    if (newCount < dp[i]) {
                        dp[i] = newCount;
                        parent[i] = square;
                    }
                }
            }
        }

        // 重建路径
        int[] result = new int[dp[n]];
        int curr = n;
        for (int i = dp[n] - 1; i >= 0; i--) {
            result[i] = parent[curr];
            curr -= parent[curr];
        }

        return result;
    }

    /**
     * 方法3: 数学方法（四平方和定理）
     * 时间复杂度: O(sqrt(n))
     * 空间复杂度: O(1)
     *
     * 四平方和定理: 任何正整数都可以表示为最多4个完全平方数的和
     * 进一步优化:
     * - 如果 n 本身是完全平方数，返回1
     * - 如果 n = 4^k * (8m + 7)，返回4
     * - 否则尝试2个完全平方数，不行就返回3
     */
    public int numSquaresMath(int n) {
        // 判断是否为完全平方数
        if (isPerfectSquare(n)) return 1;

        // 判断是否满足 4^k * (8m + 7)
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) return 4;

        // 尝试两个完全平方数
        for (int i = 1; i * i <= n; i++) {
            if (isPerfectSquare(n - i * i)) {
                return 2;
            }
        }

        return 3;
    }

    private boolean isPerfectSquare(int n) {
        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    public static void main(String[] args) {
        P279_PerfectSquares solution = new P279_PerfectSquares();

        // 测试用例1
        System.out.println("测试用例 1:");
        solution.numSquaresWithVisualization(12, true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        solution.numSquaresWithVisualization(13, true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        solution.numSquaresWithVisualization(7, true);

        // 测试带路径记录的版本
        System.out.println("\n带路径记录版本:");
        int[] path = solution.numSquaresWithPath(12);
        System.out.println("使用的完全平方数: " + Arrays.toString(path));
        System.out.println("验证: " + Arrays.stream(path).sum() + " = 12");

        // 测试数学方法
        System.out.println("\n数学方法:");
        System.out.println("n=12, 结果: " + solution.numSquaresMath(12));
        System.out.println("n=13, 结果: " + solution.numSquaresMath(13));
    }
}
