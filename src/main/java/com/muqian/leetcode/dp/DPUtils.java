package com.muqian.leetcode.dp;

/**
 * 动态规划工具类
 * 提供常用的格式化和打印工具方法
 *
 * @author muqian
 */
public class DPUtils {

    /**
     * 生成缩进字符串
     *
     * @param level 缩进级别
     * @return 缩进字符串
     */
    public static String indent(int level) {
        return "  ".repeat(Math.max(0, level));
    }

    /**
     * 格式化1D数组为字符串
     *
     * @param arr 数组
     * @return 格式化后的字符串
     */
    public static String format1DArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MAX_VALUE) {
                sb.append("∞");
            } else if (arr[i] == Integer.MIN_VALUE) {
                sb.append("-∞");
            } else {
                sb.append(arr[i]);
            }
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 格式化1D boolean数组为字符串
     *
     * @param arr 数组
     * @return 格式化后的字符串
     */
    public static String format1DBoolArray(boolean[] arr) {
        if (arr == null || arr.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i] ? "T" : "F");
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 打印2D数组（表格形式）
     *
     * @param dp 2D数组
     * @return 格式化后的字符串
     */
    public static String format2DArray(int[][] dp) {
        if (dp == null || dp.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        int rows = dp.length;
        int cols = dp[0].length;

        // 打印列索引
        sb.append("\n    ");
        for (int j = 0; j < cols; j++) {
            sb.append(String.format("%5d", j));
        }
        sb.append("\n");

        // 打印分隔线
        sb.append("    ");
        sb.append("─────".repeat(cols));
        sb.append("\n");

        // 打印每一行
        for (int i = 0; i < rows; i++) {
            sb.append(String.format("%3d │", i));
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] == Integer.MAX_VALUE) {
                    sb.append("    ∞");
                } else if (dp[i][j] == Integer.MIN_VALUE) {
                    sb.append("   -∞");
                } else {
                    sb.append(String.format("%5d", dp[i][j]));
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 打印2D boolean数组（表格形式）
     *
     * @param dp 2D boolean数组
     * @return 格式化后的字符串
     */
    public static String format2DBoolArray(boolean[][] dp) {
        if (dp == null || dp.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        int rows = dp.length;
        int cols = dp[0].length;

        // 打印列索引
        sb.append("\n    ");
        for (int j = 0; j < cols; j++) {
            sb.append(String.format("%4d", j));
        }
        sb.append("\n");

        // 打印分隔线
        sb.append("    ");
        sb.append("────".repeat(cols));
        sb.append("\n");

        // 打印每一行
        for (int i = 0; i < rows; i++) {
            sb.append(String.format("%3d │", i));
            for (int j = 0; j < dp[i].length; j++) {
                sb.append(dp[i][j] ? "   T" : "   F");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 打印执行摘要
     *
     * @param totalSteps 总步骤数
     * @param result 最终结果
     * @return 摘要字符串
     */
    public static String printSummary(int totalSteps, Object result) {
        return String.format("\n╔══════════════════════════════════════╗\n" +
                           "║  执行摘要                            ║\n" +
                           "╠══════════════════════════════════════╣\n" +
                           "║  总步骤数: %-26d║\n" +
                           "║  最终结果: %-26s║\n" +
                           "╚══════════════════════════════════════╝",
                totalSteps, result);
    }

    /**
     * 格式化数组为字符串（用于日志）
     *
     * @param arr 数组
     * @return 格式化后的字符串
     */
    public static String arrayToString(int[] arr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 格式化2D数组为字符串（用于日志）
     *
     * @param arr 2D数组
     * @return 格式化后的字符串
     */
    public static String array2DToString(int[][] arr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arrayToString(arr[i]));
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
