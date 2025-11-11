package com.muqian.leetcode.backtracking.combination;

import com.muqian.leetcode.backtracking.BacktrackUtils;
import com.muqian.leetcode.backtracking.DecisionTreeNode;
import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * LeetCode 39. 组合总和
 *
 * 题目描述：
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
 * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，
 * 并以列表形式返回。你可以按 任意顺序 返回这些组合。
 *
 * candidates 中的 同一个 数字可以 无限制重复被选取 。
 * 如果至少一个数字的被选数量不同，则两种组合是不同的。
 *
 * 示例 1:
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 * 解释：
 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
 * 7 也是一个候选， 7 = 7 。
 * 仅有这两种组合。
 *
 * 示例 2:
 * 输入: candidates = [2,3,5], target = 8
 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
 *
 * 解题思路：
 * 组合总和问题的回溯：
 * 1. 每个元素可以无限次使用
 * 2. 递归时传入当前 index（不是 index+1），允许重复使用当前元素
 * 3. 当 target 减到 0 时收集结果
 *
 * 剪枝优化：
 * 1. target < 0 时直接返回（无效路径）
 * 2. 先排序，当 candidates[i] > target 时可以 break（后面的更大）
 *
 * 关键点：
 * - 允许重复使用：递归时传入 i 而非 i+1
 * - 避免重复组合：仍需使用 start 参数保证顺序性
 *   例如 [2,3,2] 和 [3,2,2] 是同一个组合，start 确保只生成一次
 * - 剪枝：target < 0 或 candidates[i] > target 时返回
 *
 * 时间复杂度：O(n^(target/min))，最坏情况下递归深度为 target/min
 * 空间复杂度：O(target/min)，递归栈深度
 *
 * @author muqian
 */
public class CombinationSum {
    private static final Logger log = LoggerFactory.getLogger(CombinationSum.class);

    private List<List<Integer>> result;
    private List<Integer> path;
    private int stepCounter;
    private int pruneCount;

    // 决策树相关
    private DecisionTreeNode treeRoot;
    private Stack<DecisionTreeNode> nodeStack;

    /**
     * 生成所有组合总和（主方法）
     *
     * @param candidates 候选数组
     * @param target 目标和
     * @return 所有和为target的组合
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        log.info("\n========== 开始查找组合总和 ==========");
        log.info("候选数组: {}", arrayToString(candidates));
        log.info("目标和: {}", ColorUtils.node(target));

        result = new ArrayList<>();
        path = new ArrayList<>();
        stepCounter = 0;
        pruneCount = 0;

        // 初始化决策树
        treeRoot = new DecisionTreeNode(0, new ArrayList<>(), null, "开始 (和=0)");
        nodeStack = new Stack<>();
        nodeStack.push(treeRoot);

        // 排序优化剪枝
        Arrays.sort(candidates);
        log.info("排序后: {}\n", arrayToString(candidates));

        backtrack(candidates, target, 0, 0, 0);

        log.info("\n{} 统计信息:", ColorUtils.phase(""));
        log.info("  总步骤数: {}", stepCounter);
        log.info("  剪枝次数: {}", ColorUtils.success(String.valueOf(pruneCount)));
        log.info("  组合总数: {}", result.size());

        log.info("\n最终结果（共 {} 个组合）：", result.size());
        for (int i = 0; i < result.size(); i++) {
            log.info("  [{}] {}", i + 1, result.get(i));
        }

        // 打印决策树（纵向树形图）
        BacktrackUtils.printDecisionTreeVertical(treeRoot,
            "组合总和决策树 - " + arrayToString(candidates) + " → 目标: " + target);

        log.info("\n========== 查找完成 ==========\n");
        return result;
    }

    /**
     * 回溯算法核心方法
     *
     * @param candidates 候选数组
     * @param target 剩余目标和
     * @param start 当前开始位置
     * @param sum 当前路径和
     * @param depth 递归深度
     */
    private void backtrack(int[] candidates, int target, int start, int sum, int depth) {
        String indent = "  ".repeat(depth);
        DecisionTreeNode currentNode = nodeStack.peek();

        // 终止条件：找到一个和为target的组合
        if (sum == target) {
            result.add(new ArrayList<>(path));
            currentNode.markAsLeaf();
            log.info("{}{} {} 找到组合: {} (和 = {})",
                    indent,
                    ColorUtils.step(++stepCounter),
                    ColorUtils.success("✓"),
                    ColorUtils.highlight(path.toString()),
                    sum);
            return;
        }

        // 剪枝：和已经超过target
        if (sum > target) {
            pruneCount++;
            currentNode.markAsPruned();
            log.debug("{}✂ 剪枝：当前和 {} > 目标 {}", indent, sum, target);
            return;
        }

        // 遍历候选数字
        for (int i = start; i < candidates.length; i++) {
            // 剪枝优化：由于数组已排序，后面的数字更大，不可能满足
            if (sum + candidates[i] > target) {
                pruneCount++;
                log.debug("{}✂ 剪枝：{} + {} = {} > 目标 {}, break",
                        indent, sum, candidates[i], sum + candidates[i], target);

                // 为剪枝的分支创建节点
                DecisionTreeNode prunedNode = new DecisionTreeNode(
                    depth + 1,
                    new ArrayList<>(path),
                    candidates[i],
                    "和=" + (sum + candidates[i]) + " > " + target
                );
                prunedNode.markAsPruned();
                currentNode.addChild(prunedNode);
                break;
            }

            // 做选择
            path.add(candidates[i]);
            int newSum = sum + candidates[i];
            log.info("{}├─ {} 选择 {} → 路径: {} (和: {} / 目标: {})",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(candidates[i]),
                    path,
                    newSum,
                    target);

            // 创建新的决策树节点
            DecisionTreeNode childNode = new DecisionTreeNode(
                depth + 1,
                new ArrayList<>(path),
                candidates[i],
                "和=" + newSum
            );
            currentNode.addChild(childNode);
            nodeStack.push(childNode);

            // 递归：注意传入 i 而非 i+1，允许重复使用当前元素
            backtrack(candidates, target, i, newSum, depth + 1);

            // 回溯：弹出节点
            nodeStack.pop();

            // 撤销选择
            path.remove(path.size() - 1);
            log.debug("{}└─ 回溯，移除 {}", indent, candidates[i]);
        }
    }

    /**
     * 数组转字符串（带颜色）
     */
    private String arrayToString(int[] nums) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nums.length; i++) {
            sb.append(ColorUtils.node(nums[i]));
            if (i < nums.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
