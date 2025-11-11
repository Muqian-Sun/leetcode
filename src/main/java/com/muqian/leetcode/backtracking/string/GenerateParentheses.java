package com.muqian.leetcode.backtracking.string;

import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 22. 括号生成
 *
 * 题目描述：
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 * 示例 1:
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 *
 * 示例 2:
 * 输入：n = 1
 * 输出：["()"]
 *
 * 解题思路：
 * 括号生成是回溯算法的经典应用，关键在于理解有效括号的约束条件：
 *
 * 1. 任何时候左括号数量 >= 右括号数量
 * 2. 左括号总数 = 右括号总数 = n
 *
 * 回溯策略：
 * - 左括号可以随时添加（只要没用完）
 * - 右括号只能在 右括号数量 < 左括号数量 时添加
 * - 终止条件：左右括号都用完（各用了 n 个）
 *
 * 剪枝条件：
 * - 左括号用完 n 个后不能再加左括号
 * - 右括号数量 >= 左括号数量时不能加右括号
 *
 * 决策树分析（以 n=2 为例）：
 *                    ""
 *                   /
 *                  (          (left=1, right=0)
 *               /     \
 *             ((       ()      (left=2,1  right=0,1)
 *            /        /  \
 *          (()      ()(  ()()  (left=2,2,2  right=1,1,2)
 *           |        |
 *         (())     ()()         (有效组合)
 *
 * 关键点：
 * - 用 left 和 right 分别记录已使用的左右括号数量
 * - 剪枝：left > n 或 right > left 时不继续
 * - StringBuilder 优化字符串拼接
 *
 * 时间复杂度：O(4^n / √n)，第 n 个卡特兰数
 * 空间复杂度：O(n)，递归栈深度
 *
 * @author muqian
 */
public class GenerateParentheses {
    private static final Logger log = LoggerFactory.getLogger(GenerateParentheses.class);

    private List<String> result;
    private StringBuilder path;
    private int stepCounter;
    private int pruneCount;

    /**
     * 生成所有有效括号组合（主方法）
     *
     * @param n 括号对数
     * @return 所有有效括号组合
     */
    public List<String> generateParenthesis(int n) {
        log.info("\n========== 开始生成括号组合 ==========");
        log.info("括号对数 n = {}", ColorUtils.node(n));

        result = new ArrayList<>();
        path = new StringBuilder();
        stepCounter = 0;
        pruneCount = 0;

        log.info("\n");
        backtrack(n, 0, 0, 0);

        log.info("\n{} 统计信息:", ColorUtils.phase(""));
        log.info("  总步骤数: {}", stepCounter);
        log.info("  剪枝次数: {}", ColorUtils.success(String.valueOf(pruneCount)));
        log.info("  有效组合数: {}", result.size());

        log.info("\n最终结果（共 {} 个有效组合）：", result.size());
        for (int i = 0; i < result.size(); i++) {
            log.info("  [{}] {}", i + 1, "\"" + result.get(i) + "\"");
        }

        log.info("\n========== 生成完成 ==========\n");
        return result;
    }

    /**
     * 回溯算法核心方法
     *
     * @param n 括号对数
     * @param left 已使用的左括号数量
     * @param right 已使用的右括号数量
     * @param depth 递归深度
     */
    private void backtrack(int n, int left, int right, int depth) {
        String indent = "  ".repeat(depth);

        // 剪枝1：左括号数量超过 n
        if (left > n) {
            pruneCount++;
            log.debug("{}✂ 剪枝：左括号数量 {} > {}", indent, left, n);
            return;
        }

        // 剪枝2：右括号数量超过左括号（不合法）
        if (right > left) {
            pruneCount++;
            log.debug("{}✂ 剪枝：右括号数量 {} > 左括号数量 {}", indent, right, left);
            return;
        }

        // 终止条件：左右括号都用完
        if (left == n && right == n) {
            String combination = path.toString();
            result.add(combination);
            log.info("{}{} {} 找到有效组合: {}",
                    indent,
                    ColorUtils.step(++stepCounter),
                    ColorUtils.success("✓"),
                    ColorUtils.highlight("\"" + combination + "\""));
            return;
        }

        // 选择1：添加左括号（只要没用完）
        if (left < n) {
            path.append('(');
            log.info("{}├─ {} 添加 {} → 路径: {} (左:{}, 右:{})",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node("("),
                    "\"" + path.toString() + "\"",
                    left + 1,
                    right);
            backtrack(n, left + 1, right, depth + 1);
            path.deleteCharAt(path.length() - 1);
            log.debug("{}└─ 回溯，移除 (", indent);
        }

        // 选择2：添加右括号（只要数量 < 左括号）
        if (right < left) {
            path.append(')');
            log.info("{}├─ {} 添加 {} → 路径: {} (左:{}, 右:{})",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(")"),
                    "\"" + path.toString() + "\"",
                    left,
                    right + 1);
            backtrack(n, left, right + 1, depth + 1);
            path.deleteCharAt(path.length() - 1);
            log.debug("{}└─ 回溯，移除 )", indent);
        }
    }
}
