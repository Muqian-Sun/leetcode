package com.leetcode.dp.problems;

import com.leetcode.dp.utils.DPVisualizer;
import java.util.Stack;

/**
 * LeetCode 32. 最长有效括号
 * 难度: 困难
 *
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 * 示例 1:
 * 输入: s = "(()"
 * 输出: 2
 * 解释: 最长有效括号子串是 "()"
 *
 * 示例 2:
 * 输入: s = ")()())"
 * 输出: 4
 * 解释: 最长有效括号子串是 "()()"
 *
 * 示例 3:
 * 输入: s = ""
 * 输出: 0
 */
public class P32_LongestValidParentheses {

    /**
     * 方法1: 动态规划
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * 思路:
     * dp[i] 表示以 s[i] 结尾的最长有效括号长度
     * 状态转移方程:
     * - 如果 s[i] == '(': dp[i] = 0
     * - 如果 s[i] == ')':
     *   - 如果 s[i-1] == '(': dp[i] = dp[i-2] + 2
     *   - 如果 s[i-1] == ')' 且 s[i-dp[i-1]-1] == '(':
     *     dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]
     */
    public int longestValidParentheses(String s) {
        return longestValidParenthesesWithVisualization(s, true);
    }

    public int longestValidParenthesesWithVisualization(String s, boolean debug) {
        if (debug) {
            DPVisualizer.printTitle("LeetCode 32. 最长有效括号");
            System.out.println("字符串: \"" + s + "\"");
            System.out.println("说明: dp[i]表示以s[i]结尾的最长有效括号长度\n");
        }

        if (s == null || s.length() < 2) {
            if (debug) DPVisualizer.printResult(0);
            return 0;
        }

        int n = s.length();
        int[] dp = new int[n];
        int maxLen = 0;

        if (debug) {
            DPVisualizer.printStep(0, "初始化");
            System.out.println("所有位置初始化为0");
            printStringWithIndex(s);
        }

        for (int i = 1; i < n; i++) {
            if (debug) {
                DPVisualizer.printStep(i, "处理位置 " + i + " (字符='" + s.charAt(i) + "')");
            }

            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // 情况1: ...()
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;

                    if (debug) {
                        System.out.println("  情况1: s[" + (i-1) + "]='(' 和 s[" + i + "]=')'");
                        if (i >= 2) {
                            System.out.println("  dp[" + i + "] = dp[" + (i-2) + "] + 2 = " +
                                             dp[i-2] + " + 2 = " + dp[i]);
                        } else {
                            System.out.println("  dp[" + i + "] = 2");
                        }
                    }
                } else if (dp[i - 1] > 0) {
                    // 情况2: ...))
                    int matchIndex = i - dp[i - 1] - 1;

                    if (matchIndex >= 0 && s.charAt(matchIndex) == '(') {
                        dp[i] = dp[i - 1] + 2;
                        if (matchIndex - 1 >= 0) {
                            dp[i] += dp[matchIndex - 1];
                        }

                        if (debug) {
                            System.out.println("  情况2: s[" + (i-1) + "]=')' 且存在匹配的'('");
                            System.out.println("  内部有效长度: dp[" + (i-1) + "] = " + dp[i-1]);
                            System.out.println("  匹配位置: " + matchIndex + " (字符='" +
                                             s.charAt(matchIndex) + "')");
                            System.out.println("  基础长度: dp[" + (i-1) + "] + 2 = " +
                                             dp[i-1] + " + 2 = " + (dp[i-1] + 2));
                            if (matchIndex - 1 >= 0) {
                                System.out.println("  前面的有效长度: dp[" + (matchIndex-1) +
                                                 "] = " + dp[matchIndex-1]);
                            }
                            System.out.println("  dp[" + i + "] = " + dp[i]);
                        }
                    } else if (debug) {
                        System.out.println("  无法匹配，dp[" + i + "] = 0");
                    }
                } else if (debug) {
                    System.out.println("  单独的')'，无法匹配，dp[" + i + "] = 0");
                }

                maxLen = Math.max(maxLen, dp[i]);

                if (debug) {
                    System.out.println("  当前最大长度: " + maxLen);
                    DPVisualizer.print1DArray(dp, "当前DP数组");
                }
            } else if (debug) {
                System.out.println("  字符为'('，dp[" + i + "] = 0");
            }
        }

        if (debug) {
            DPVisualizer.printResult(maxLen);
        }

        return maxLen;
    }

    /**
     * 方法2: 栈
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     */
    public int longestValidParenthesesStack(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }

        return maxLen;
    }

    /**
     * 方法3: 双向遍历
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public int longestValidParenthesesTwoPass(String s) {
        int left = 0, right = 0, maxLen = 0;

        // 从左到右
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                maxLen = Math.max(maxLen, 2 * right);
            } else if (right > left) {
                left = right = 0;
            }
        }

        left = right = 0;

        // 从右到左
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                maxLen = Math.max(maxLen, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }

        return maxLen;
    }

    private void printStringWithIndex(String s) {
        System.out.print("索引: ");
        for (int i = 0; i < s.length(); i++) {
            System.out.printf("%3d", i);
        }
        System.out.println();

        System.out.print("字符: ");
        for (int i = 0; i < s.length(); i++) {
            System.out.printf("%3c", s.charAt(i));
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        P32_LongestValidParentheses solution = new P32_LongestValidParentheses();

        // 测试用例1
        System.out.println("测试用例 1:");
        solution.longestValidParenthesesWithVisualization("(()", true);

        // 测试用例2
        System.out.println("\n测试用例 2:");
        solution.longestValidParenthesesWithVisualization(")()())", true);

        // 测试用例3
        System.out.println("\n测试用例 3:");
        solution.longestValidParenthesesWithVisualization("()(())", true);

        // 测试栈方法
        System.out.println("\n栈方法:");
        System.out.println("结果: " + solution.longestValidParenthesesStack(")()())"));

        // 测试双向遍历方法
        System.out.println("\n双向遍历方法:");
        System.out.println("结果: " + solution.longestValidParenthesesTwoPass(")()())"));
    }
}
