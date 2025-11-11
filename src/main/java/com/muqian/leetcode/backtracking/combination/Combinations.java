package com.muqian.leetcode.backtracking.combination;

import com.muqian.leetcode.backtracking.BacktrackUtils;
import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 77. 组合
 *
 * 题目描述：
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * 你可以按 任何顺序 返回答案。
 *
 * 示例 1:
 * 输入：n = 4, k = 2
 * 输出：
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * 示例 2:
 * 输入：n = 1, k = 1
 * 输出：[[1]]
 *
 * 解题思路：
 * 组合问题的回溯：
 * 1. 从 1 到 n 中选 k 个数
 * 2. 使用 start 参数避免重复组合（保证顺序性）
 * 3. 当 path 大小等于 k 时收集结果
 *
 * 剪枝优化：
 * - 剩余可选数字数量 < 还需要的数字数量时，提前返回
 * - 剩余可选：n - i + 1
 * - 还需要：k - path.size()
 * - 剪枝条件：n - i + 1 < k - path.size()
 * - 即：i > n - (k - path.size()) + 1 时可以break
 *
 * 关键点：
 * - 只在 path.size() == k 时收集结果（与子集不同）
 * - for循环从 start 开始，保证组合不重复
 * - 剪枝可以大幅减少递归次数
 *
 * 时间复杂度：O(C(n,k) × k)，组合数 × 每次复制k个元素
 * 空间复杂度：O(k)，递归栈深度为k
 *
 * @author muqian
 */
public class Combinations {
    private static final Logger log = LoggerFactory.getLogger(Combinations.class);

    private List<List<Integer>> result;
    private List<Integer> path;
    private int stepCounter;
    private int pruneCount;

    /**
     * 生成所有组合（主方法）
     *
     * @param n 范围上限
     * @param k 组合大小
     * @return 所有k个数的组合
     */
    public List<List<Integer>> combine(int n, int k) {
        log.info("\n========== 开始生成组合 ==========");
        log.info("参数: n = {}, k = {}", ColorUtils.node(n), ColorUtils.node(k));

        result = new ArrayList<>();
        path = new ArrayList<>();
        stepCounter = 0;
        pruneCount = 0;

        log.info("\n");
        backtrack(n, k, 1, 0);

        log.info("\n{} 统计信息:", ColorUtils.phase(""));
        log.info("  总步骤数: {}", stepCounter);
        log.info("  剪枝次数: {}", ColorUtils.success(String.valueOf(pruneCount)));
        log.info("  组合总数: {}", result.size());

        log.info("\n========== 生成完成 ==========\n");
        return result;
    }

    /**
     * 回溯算法核心方法（带剪枝优化）
     *
     * @param n 范围上限
     * @param k 组合大小
     * @param start 当前开始位置
     * @param depth 递归深度
     */
    private void backtrack(int n, int k, int start, int depth) {
        String indent = "  ".repeat(depth);

        // 终止条件：找到一个大小为 k 的组合
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            log.info("{}{} {} 找到组合: {}",
                    indent,
                    ColorUtils.step(++stepCounter),
                    ColorUtils.success("✓"),
                    ColorUtils.highlight(path.toString()));
            return;
        }

        // 剪枝：剩余数字不够凑成 k 个
        // 还需要：k - path.size() 个
        // 剩余可选：[start, n] 共 n - start + 1 个
        int need = k - path.size();
        int remain = n - start + 1;

        // 遍历选择列表（带剪枝）
        for (int i = start; i <= n; i++) {
            // 剪枝优化
            if (n - i + 1 < need) {
                pruneCount++;
                log.debug("{}✂ 剪枝：剩余 {} 个数不足需要的 {} 个",
                        indent, n - i + 1, need);
                break;
            }

            // 做选择
            path.add(i);
            log.info("{}├─ {} 选择 {} → 路径: {} (还需{}个)",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(i),
                    path,
                    need - 1);

            // 递归
            backtrack(n, k, i + 1, depth + 1);

            // 撤销选择
            path.remove(path.size() - 1);
            log.debug("{}└─ 回溯，移除 {}", indent, i);
        }
    }
}
