package com.muqian.leetcode.dp.string;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 5. 最长回文子串
 *
 * 题目描述：
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 * 示例 1:
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 *
 * 示例 2:
 * 输入：s = "cbbd"
 * 输出："bb"
 *
 * 解题思路：
 *
 * 方法1：动态规划
 * - 状态定义：dp[i][j] 表示 s[i..j] 是否为回文串
 * - 状态转移方程：
 *   - 如果 s[i] == s[j] 且 dp[i+1][j-1] == true，则 dp[i][j] = true
 *   - 特殊情况：长度为1或2时的处理
 * - 按长度递增遍历所有子串
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 *
 * 方法2：中心扩展法
 * - 遍历每个可能的中心（单字符或双字符）
 * - 从中心向两边扩展，检查是否为回文
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 *
 * @author muqian
 */
public class LongestPalindromicSubstring {
    private static final Logger log = LoggerFactory.getLogger(LongestPalindromicSubstring.class);

    private int stepCounter = 0;

    /**
     * 方法1：动态规划
     *
     * @param s 输入字符串
     * @return 最长回文子串
     */
    public String longestPalindrome(String s) {
        log.info("\n========== 开始查找最长回文子串（动态规划） ==========");
        log.info("字符串：\"{}\"", s);
        log.info("状态转移方程：dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]");
        log.info("说明：dp[i][j]表示s[i..j]是否为回文串\n");

        int n = s.length();
        if (n < 2) {
            log.info("字符串长度 < 2，直接返回");
            log.info("\n========== 查找完成 ==========\n");
            return s;
        }

        boolean[][] dp = new boolean[n][n];
        int maxLen = 1;
        int start = 0;

        // 初始化：单个字符都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        stepCounter = 0;

        log.info("[步骤{}] 初始化：所有单个字符都是回文", ++stepCounter);

        // 按长度递增遍历
        for (int len = 2; len <= n; len++) {
            if (len <= 4) {
                log.info("\n[步骤{}] 检查长度为 {} 的子串", ++stepCounter, len);
            }

            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                String substring = s.substring(i, j + 1);

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

                        if (len <= 4) {
                            log.info("│   s[{}..{}] = \"{}\" ✓ 是回文！更新最长回文",
                                    i, j, substring);
                        }
                    }
                } else {
                    if (len <= 4 && i <= 3) {
                        log.info("│   s[{}..{}] = \"{}\" ✗ 首尾不同",
                                i, j, substring);
                    }
                }
            }
        }

        String result = s.substring(start, start + maxLen);

        log.info("\n{}", DPUtils.printSummary(stepCounter, String.format("\"%s\" (长度: %d)", result, maxLen)));
        log.info("\n========== 查找完成 ==========\n");

        return result;
    }

    /**
     * 方法2：中心扩展法
     *
     * @param s 输入字符串
     * @return 最长回文子串
     */
    public String longestPalindromeExpandCenter(String s) {
        log.info("\n========== 开始查找最长回文子串（中心扩展） ==========");
        log.info("字符串：\"{}\"", s);
        log.info("方法：从每个中心向两边扩展\n");

        if (s == null || s.length() < 1) {
            return "";
        }

        int start = 0, end = 0;
        stepCounter = 0;

        for (int i = 0; i < s.length(); i++) {
            // 以单个字符为中心
            int len1 = expandAroundCenter(s, i, i);
            // 以两个字符之间为中心
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;

                if (stepCounter < 5) {
                    log.info("[步骤{}] 中心 = {}, 找到回文 \"{}\" (长度{})",
                            ++stepCounter, i, s.substring(start, end + 1), len);
                }
            }
        }

        String result = s.substring(start, end + 1);

        log.info("\n{}", DPUtils.printSummary(stepCounter, String.format("\"%s\" (长度: %d)", result, result.length())));
        log.info("\n========== 查找完成 ==========\n");

        return result;
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}
