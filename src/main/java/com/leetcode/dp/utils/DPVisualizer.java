package com.leetcode.dp.utils;

import java.util.Arrays;

/**
 * 动态规划可视化工具类
 * 用于打印DP数组的变化过程，帮助理解算法执行流程
 */
public class DPVisualizer {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    /**
     * 打印1D DP数组
     */
    public static void print1DArray(int[] dp, String description) {
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
        System.out.println(ANSI_YELLOW + description + ANSI_RESET);
        System.out.println("索引: " + ANSI_BLUE + formatIndices(dp.length) + ANSI_RESET);
        System.out.println("数值: " + ANSI_GREEN + Arrays.toString(dp) + ANSI_RESET);
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
    }

    /**
     * 打印1D DP数组（long类型）
     */
    public static void print1DArray(long[] dp, String description) {
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
        System.out.println(ANSI_YELLOW + description + ANSI_RESET);
        System.out.println("索引: " + ANSI_BLUE + formatIndices(dp.length) + ANSI_RESET);
        System.out.println("数值: " + ANSI_GREEN + Arrays.toString(dp) + ANSI_RESET);
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
    }

    /**
     * 打印1D DP数组（boolean类型）
     */
    public static void print1DArray(boolean[] dp, String description) {
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
        System.out.println(ANSI_YELLOW + description + ANSI_RESET);
        System.out.println("索引: " + ANSI_BLUE + formatIndices(dp.length) + ANSI_RESET);
        System.out.print("数值: " + ANSI_GREEN + "[");
        for (int i = 0; i < dp.length; i++) {
            System.out.print(dp[i] ? "T" : "F");
            if (i < dp.length - 1) System.out.print(", ");
        }
        System.out.println("]" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
    }

    /**
     * 打印2D DP数组
     */
    public static void print2DArray(int[][] dp, String description) {
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
        System.out.println(ANSI_YELLOW + description + ANSI_RESET);

        if (dp.length == 0) {
            System.out.println("空数组");
            System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
            return;
        }

        int cols = dp[0].length;

        // 打印列索引
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.printf(ANSI_BLUE + "%4d" + ANSI_RESET, j);
        }
        System.out.println();

        // 打印分隔线
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print("----");
        }
        System.out.println();

        // 打印每一行
        for (int i = 0; i < dp.length; i++) {
            System.out.printf(ANSI_BLUE + "%2d |" + ANSI_RESET, i);
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] == Integer.MAX_VALUE || dp[i][j] == Integer.MIN_VALUE) {
                    System.out.print(ANSI_RED + " INF" + ANSI_RESET);
                } else {
                    System.out.printf(ANSI_GREEN + "%4d" + ANSI_RESET, dp[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
    }

    /**
     * 打印2D DP数组（boolean类型）
     */
    public static void print2DArray(boolean[][] dp, String description) {
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
        System.out.println(ANSI_YELLOW + description + ANSI_RESET);

        if (dp.length == 0) {
            System.out.println("空数组");
            System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
            return;
        }

        int cols = dp[0].length;

        // 打印列索引
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.printf(ANSI_BLUE + "%3d" + ANSI_RESET, j);
        }
        System.out.println();

        // 打印分隔线
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print("---");
        }
        System.out.println();

        // 打印每一行
        for (int i = 0; i < dp.length; i++) {
            System.out.printf(ANSI_BLUE + "%2d |" + ANSI_RESET, i);
            for (int j = 0; j < dp[i].length; j++) {
                System.out.print(dp[i][j] ? ANSI_GREEN + "  T" : ANSI_RED + "  F");
                System.out.print(ANSI_RESET);
            }
            System.out.println();
        }
        System.out.println(ANSI_CYAN + "=".repeat(60) + ANSI_RESET);
    }

    /**
     * 打印当前步骤信息
     */
    public static void printStep(int step, String description) {
        System.out.println(ANSI_PURPLE + "\n>>> 步骤 " + step + ": " + description + ANSI_RESET);
    }

    /**
     * 打印分隔线
     */
    public static void printDivider() {
        System.out.println(ANSI_CYAN + "\n" + "=".repeat(80) + "\n" + ANSI_RESET);
    }

    /**
     * 打印标题
     */
    public static void printTitle(String title) {
        System.out.println(ANSI_PURPLE + "\n" + "═".repeat(80));
        System.out.println("  " + title);
        System.out.println("═".repeat(80) + "\n" + ANSI_RESET);
    }

    /**
     * 高亮显示当前更新的位置
     */
    public static void highlightUpdate(int index, int value, String description) {
        System.out.println(ANSI_YELLOW + "  → " + description +
                          ": dp[" + index + "] = " + ANSI_GREEN + value + ANSI_RESET);
    }

    /**
     * 高亮显示2D数组更新
     */
    public static void highlightUpdate(int i, int j, int value, String description) {
        System.out.println(ANSI_YELLOW + "  → " + description +
                          ": dp[" + i + "][" + j + "] = " + ANSI_GREEN + value + ANSI_RESET);
    }

    /**
     * 格式化索引
     */
    private static String formatIndices(int length) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(i);
            if (i < length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 打印状态转移方程
     */
    public static void printTransition(String equation) {
        System.out.println(ANSI_CYAN + "状态转移方程: " + ANSI_YELLOW + equation + ANSI_RESET);
    }

    /**
     * 打印最终结果
     */
    public static void printResult(Object result) {
        System.out.println(ANSI_GREEN + "\n✓ 最终结果: " + result + ANSI_RESET);
        printDivider();
    }
}
