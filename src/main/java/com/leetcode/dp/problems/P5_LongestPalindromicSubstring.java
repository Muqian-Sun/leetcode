package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;

/**
 * LeetCode 5. 最长回文子串
 * 难度: 中等
 *
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 * 示例 1:
 * 输入: s = "babad"
 * 输出: "bab"
 * 解释: "aba" 同样是符合题意的答案。
 *
 * 示例 2:
 * 输入: s = "cbbd"
 * 输出: "bb"
 */
public class P5_LongestPalindromicSubstring {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n^2)
     *
     * 思路:
     * dp[i][j] 表示 s[i..j] 是否为回文串
     * 状态转移方程: dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]
     */
    public String longestPalindrome(String s) {
        return longestPalindromeWithVisualization(s, true);
    }

    public String longestPalindromeWithVisualization(String s, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 5. 最长回文子串");
            System.out.println("字符串: \"" + s + "\"");
            DPVisualizer.printTransition("dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]");
            System.out.println("说明: dp[i][j]表示s[i..j]是否为回文串\n");
        }

        int n = s.length();
        if (n < 2) {
            if (debug) DPVisualizer.printResult(s);
            return s;
        }

        boolean[][] dp = new boolean[n][n];
        int maxLen = 1;
        int start = 0;

        // 初始化: 单个字符都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("所有单个字符都是回文");
            DPVisualizer.print2DArray(dp, "初始化后的DP数组");
        }

        // 按长度递增遍历
        for (int len = 2; len <= n; len++) {
            if (debug) {
                DPVisualizer.printStep(len - 1, "检查长度为 " + len + " 的子串");
            }

            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                String substring = s.substring(i, j + 1);

                if (debug) {
                    System.out.println("  检查 s[" + i + ".." + j + "] = \"" + substring + "\"");
                }

                if (s.charAt(i) == s.charAt(j)) {
                    // 长度为2或3时，只需要首尾相同
                    if (len <= 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }

                    if (dp[i][j] && len > maxLen) {
                        maxLen = len;
                        start = i;

                        if (debug) {
                            System.out.println("    ✓ 是回文! 更新最长回文: \"" +
                                             s.substring(start, start + maxLen) + "\"");
                        }
                    } else if (debug && dp[i][j]) {
                        System.out.println("    ✓ 是回文");
                    }
                } else if (debug) {
                    System.out.println("    ✗ 首尾字符不同，不是回文");
                }
            }

            if (debug && len <= 5) {  // 只显示前几轮，避免输出太多
                DPVisualizer.print2DArray(dp, "长度" + len + "检查完毕");
            }
        }

        String result = s.substring(start, start + maxLen);

        if (debug) {
            DPVisualizer.printResult("\"" + result + "\" (长度: " + maxLen + ")");
        }

        return result;
    }

    /**
     * 方法2: 中心扩展法
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(1)
     */
    public String longestPalindromeExpandCenter(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // 以单个字符为中心
            int len1 = expandAroundCenter(s, i, i);
            // 以两个字符之间为中心
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    /**
     * 方法3: Manacher算法
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     */
    public String longestPalindromeManacher(String s) {
        // 预处理字符串，插入特殊字符
        StringBuilder sb = new StringBuilder("^");
        for (int i = 0; i < s.length(); i++) {
            sb.append("#").append(s.charAt(i));
        }
        sb.append("#$");
        String t = sb.toString();

        int n = t.length();
        int[] p = new int[n];  // p[i]表示以i为中心的最长回文半径
        int center = 0, right = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            if (i < right) {
                p[i] = Math.min(right - i, p[mirror]);
            }

            // 尝试扩展
            while (t.charAt(i + p[i] + 1) == t.charAt(i - p[i] - 1)) {
                p[i]++;
            }

            // 更新中心和右边界
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }

        // 找到最长回文
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    public static void main(String[] args) {
        P5_LongestPalindromicSubstring solution = new P5_LongestPalindromicSubstring();

        // 测试用例1
        System.out.println("测试用例 1:");
        solution.longestPalindromeWithVisualization("babad", true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        solution.longestPalindromeWithVisualization("cbbd", true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        solution.longestPalindromeWithVisualization("a", true);

        // 测试中心扩展法
        System.out.println("\n中心扩展法:");
        System.out.println("结果: " + solution.longestPalindromeExpandCenter("babad"));

        // 测试Manacher算法
        System.out.println("\nManacher算法:");
        System.out.println("结果: " + solution.longestPalindromeManacher("babad"));
    }
}
