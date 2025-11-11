package com.muqian.leetcode.backtracking.subset;

import com.muqian.leetcode.backtracking.BacktrackUtils;
import com.muqian.leetcode.backtracking.DecisionTreeNode;
import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * LeetCode 78. 子集
 *
 * 题目描述：
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 *
 * 示例 1:
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * 示例 2:
 * 输入：nums = [0]
 * 输出：[[],[0]]
 *
 * 解题思路：
 * 子集问题是回溯算法的经典应用，核心思想是决策树：
 * 1. 每个元素都有"选"或"不选"两种状态
 * 2. 遍历决策树的所有路径，每个节点都是一个有效子集
 * 3. 使用 start 参数避免重复（保证组合的顺序性）
 *
 * 回溯三要素：
 * 1. 路径：已经做出的选择（path）
 * 2. 选择列表：当前可以做的选择（nums[start..end]）
 * 3. 结束条件：到达决策树底部（start == nums.length）
 *
 * 关键点：
 * - 子集问题每个节点都要收集结果，不是只在叶子节点
 * - 使用 start 参数保证不重复：[1,2] 和 [2,1] 是同一个子集
 * - 注意要 new ArrayList<>(path) 创建新对象加入结果
 *
 * 时间复杂度：O(n × 2^n)，一共 2^n 个子集，每个子集平均长度 n/2
 * 空间复杂度：O(n)，递归栈的深度
 *
 * @author muqian
 */
public class Subsets {
    private static final Logger log = LoggerFactory.getLogger(Subsets.class);

    private List<List<Integer>> result;
    private List<Integer> path;
    private int stepCounter;

    // 决策树相关
    private DecisionTreeNode treeRoot;
    private Stack<DecisionTreeNode> nodeStack;

    /**
     * 求所有子集（主方法）
     *
     * @param nums 整数数组
     * @return 所有子集的列表
     */
    public List<List<Integer>> subsets(int[] nums) {
        log.info("\n========== 开始生成所有子集 ==========");
        log.info("输入数组: {}", arrayToString(nums));

        result = new ArrayList<>();
        path = new ArrayList<>();
        stepCounter = 0;

        // 初始化决策树
        treeRoot = new DecisionTreeNode(0, new ArrayList<>(), null, "空集");
        nodeStack = new Stack<>();
        nodeStack.push(treeRoot);

        log.info("\n");
        backtrack(nums, 0, 0);

        log.info("\n最终结果（共 {} 个子集）：", result.size());
        for (int i = 0; i < result.size(); i++) {
            log.info("  [{}] {}", i + 1, result.get(i));
        }

        // 打印决策树（纵向树形图）
        BacktrackUtils.printDecisionTreeVertical(treeRoot, "子集决策树 - " + arrayToString(nums));

        log.info("\n========== 生成完成 ==========\n");
        return result;
    }

    /**
     * 回溯算法核心方法
     *
     * @param nums 原数组
     * @param start 当前开始位置
     * @param depth 递归深度（用于日志缩进）
     */
    private void backtrack(int[] nums, int start, int depth) {
        String indent = "  ".repeat(depth);
        DecisionTreeNode currentNode = nodeStack.peek();

        // 显示当前决策树节点状态
        log.info("{}╔═══ 决策树节点 (深度:{}) ═══",
                indent, ColorUtils.info(String.valueOf(depth)));
        log.info("{}║ 当前路径: {}",
                indent, ColorUtils.highlight(path.toString()));
        log.info("{}║ 可选元素: {}",
                indent, ColorUtils.info(BacktrackUtils.formatChoices(nums, start)));
        log.info("{}╚═══", indent);

        // 每个节点都是一个有效子集，收集结果
        result.add(new ArrayList<>(path));
        currentNode.markAsLeaf();  // 子集问题：每个节点都是一个结果
        log.info("{}{} {} 收集子集: {}",
                indent,
                ColorUtils.step(++stepCounter),
                ColorUtils.success("✓"),
                ColorUtils.highlight(path.toString()));

        // 遍历选择列表
        for (int i = start; i < nums.length; i++) {
            // 做选择
            path.add(nums[i]);
            log.info("{}├─ {} 选择 {} → 进入子树",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(nums[i]));

            // 创建新的决策树节点
            DecisionTreeNode childNode = new DecisionTreeNode(
                depth + 1,
                new ArrayList<>(path),
                nums[i],
                "可选: " + BacktrackUtils.formatChoices(nums, i + 1)
            );
            currentNode.addChild(childNode);
            nodeStack.push(childNode);

            // 递归：处理下一个位置
            backtrack(nums, i + 1, depth + 1);

            // 回溯：弹出节点
            nodeStack.pop();

            // 撤销选择（回溯）
            path.remove(path.size() - 1);
            log.info("{}└─ {} 回溯，移除 {}",
                    indent,
                    ColorUtils.highlight("◀"),
                    nums[i]);
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
