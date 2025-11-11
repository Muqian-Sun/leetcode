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
 * ============================================================================
 * 💡 思路讲解：如何想到用动态规划？
 * ============================================================================
 *
 * 【第1步】理解回文串
 * - 回文串：正着读和反着读都一样的字符串
 * - 例如："aba"、"abba"、"a" 都是回文串
 * - 关键性质：如果 s[i..j] 是回文串，那么：
 *   → 首尾字符必须相同：s[i] == s[j]
 *   → 去掉首尾后的子串也是回文：s[i+1..j-1] 也是回文
 * - 这个递归性质暗示可以用动态规划！
 *
 * 【第2步】定义状态
 * - 思考：我们要求的是什么？→ 最长的回文子串
 * - 定义：dp[i][j] = s[i..j]（从i到j）是否为回文串
 * - 这是一个布尔值，true表示是回文，false表示不是
 *
 * 【第3步】推导状态转移方程
 * - 对于子串 s[i..j]，如何判断是否为回文？
 *   → 条件1：首尾字符相同 s[i] == s[j]
 *   → 条件2：中间部分是回文 dp[i+1][j-1] == true
 * - 特殊情况：
 *   → 长度为1：单个字符肯定是回文 dp[i][i] = true
 *   → 长度为2：只需首尾相同 dp[i][i+1] = (s[i] == s[i+1])
 *   → 长度为3：如 "aba"，只需首尾相同（中间只有1个字符）
 * - 一般情况（长度≥4）：
 *   dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]
 *
 * 【第4步】确定计算顺序
 * - 关键！dp[i][j] 依赖 dp[i+1][j-1]（左下方的格子）
 * - 必须按长度从小到大遍历（先计算短子串，再计算长子串）
 * - 或者斜着遍历对角线
 *
 * 【第5步】维护最长回文
 * - 在计算过程中，记录最长的回文子串的起始位置和长度
 *
 * 💡 为什么不能直接暴力枚举？
 * - 暴力：枚举所有子串O(n²)，每个判断是否回文O(n)，总共O(n³)
 * - DP优化：通过保存子问题结果，避免重复计算，降到O(n²)
 *
 * 💡 中心扩展法的思路
 * - 换个角度：每个回文串都有一个"中心"
 * - 中心可以是单个字符（奇数长度回文），或两个字符之间（偶数长度回文）
 * - 从每个中心向两边扩展，直到不是回文为止
 * - 时间复杂度也是O(n²)，但空间只需O(1)
 *
 * ============================================================================
 * 方法1：动态规划
 * 时间复杂度：O(n²)
 * 空间复杂度：O(n²)
 *
 * 方法2：中心扩展法
 * 时间复杂度：O(n²)
 * 空间复杂度：O(1)
 * ============================================================================
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
        log.info("\n╔══════════════════════════════════════════════════════════════════╗");
        log.info("║  LeetCode 5. 最长回文子串 - 动态规划详解                       ║");
        log.info("╚══════════════════════════════════════════════════════════════════╝");
        log.info("\n【问题】字符串：\"{}\"，求最长回文子串\n", s);

        log.info("【思考过程】");
        log.info("  💭 如何判断 s[i..j] 是回文？");
        log.info("     → 条件1：首尾字符相同 s[i] == s[j]");
        log.info("     → 条件2：中间部分是回文 dp[i+1][j-1] == true");
        log.info("  💡 这个递归性质让我们想到用DP");
        log.info("  ✓ 状态转移：dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]\n");

        int n = s.length();
        if (n < 2) {
            log.info("【特殊情况】字符串长度 < 2，直接返回");
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

        log.info("【初始化】");
        log.info("  所有单个字符都是回文：dp[i][i] = true\n");

        log.info("【动态规划过程】按长度从小到大检查所有子串");
        log.info("─".repeat(70));

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

                    if (dp[i][j]) {
                        if (len <= 4) {
                            log.info("│");
                            log.info("│  ✓ s[{}..{}] = \"{}\" 是回文！", i, j, substring);
                            log.info("│     首尾字符：'{}' == '{}'", s.charAt(i), s.charAt(j));
                            if (len > 3) {
                                log.info("│     中间部分 s[{}..{}] 也是回文", i+1, j-1);
                            }
                        }

                        if (len > maxLen) {
                            maxLen = len;
                            start = i;
                            if (len <= 4) {
                                log.info("│     🎯 更新最长回文：\"{}\" (长度 {})", substring, len);
                            }
                        }
                    }
                } else {
                    if (len <= 4 && i <= 3) {
                        log.info("│");
                        log.info("│  ✗ s[{}..{}] = \"{}\" 不是回文", i, j, substring);
                        log.info("│     首尾不同：'{}' != '{}'", s.charAt(i), s.charAt(j));
                    }
                }
            }

            if (len <= 4) {
                log.info("─".repeat(70));
            }
        }

        String result = s.substring(start, start + maxLen);

        log.info("\n【结果】");
        log.info("  最长回文子串：\"{}\" (长度: {})", result, maxLen);
        log.info("  位置：从索引 {} 到 {}", start, start + maxLen - 1);
        log.info("  （通过DP，在O(n²)时间和空间内求解）");
        log.info("\n{}", DPUtils.printSummary(stepCounter,
                String.format("\"%s\" (长度: %d)", result, maxLen)));

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
