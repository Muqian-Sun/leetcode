package com.muqian.leetcode.tree;

/**
 * ANSI颜色工具类
 * 为日志输出提供颜色支持，增强可读性
 *
 * @author muqian
 */
public class ColorUtils {

    // ANSI颜色代码
    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String GRAY = "\u001B[90m";

    // 粗体
    private static final String BOLD = "\u001B[1m";

    // 颜色开关（可通过环境变量控制）
    private static final boolean COLORS_ENABLED =
        !"false".equalsIgnoreCase(System.getenv("LEETCODE_COLORS"));

    /**
     * 应用颜色（如果启用）
     */
    private static String color(String text, String colorCode) {
        if (!COLORS_ENABLED) {
            return text;
        }
        return colorCode + text + RESET;
    }

    /**
     * 节点值（蓝色）
     */
    public static String node(Object val) {
        return color(String.valueOf(val), BLUE + BOLD);
    }

    /**
     * 步骤编号（紫色）
     */
    public static String step(int num) {
        return color("[步骤" + num + "]", MAGENTA + BOLD);
    }

    /**
     * 步骤编号（紫色，自定义文本）
     */
    public static String step(String text) {
        return color(text, MAGENTA + BOLD);
    }

    /**
     * 成功/找到（绿色）
     */
    public static String success(String text) {
        return color(text, GREEN + BOLD);
    }

    /**
     * 关键操作高亮（黄色）
     */
    public static String highlight(String text) {
        return color(text, YELLOW + BOLD);
    }

    /**
     * 错误/失败（红色）
     */
    public static String error(String text) {
        return color(text, RED + BOLD);
    }

    /**
     * 信息/统计（青色）
     */
    public static String info(String text) {
        return color(text, CYAN);
    }

    /**
     * null节点（灰色）
     */
    public static String nullNode() {
        return color("ø", GRAY);
    }

    /**
     * null文本（灰色）
     */
    public static String nullText() {
        return color("null", GRAY);
    }

    /**
     * 阶段标题（粗体青色）
     */
    public static String phase(String text) {
        return color(text, CYAN + BOLD);
    }

    /**
     * 重要信息（粗体黄色）
     */
    public static String important(String text) {
        return color(text, YELLOW + BOLD);
    }

    /**
     * 检查颜色是否启用
     */
    public static boolean isEnabled() {
        return COLORS_ENABLED;
    }
}
