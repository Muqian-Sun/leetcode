package com.muqian.leetcode.backtracking.permutation;

import com.muqian.leetcode.backtracking.BacktrackUtils;
import com.muqian.leetcode.backtracking.DecisionTreeNode;
import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * LeetCode 46. 全排列
 *
 * 题目描述：
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。
 * 你可以 按任意顺序 返回答案。
 *
 * 示例 1:
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 示例 2:
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 *
 * 示例 3:
 * 输入：nums = [1]
 * 输出：[[1]]
 *
 * 解题思路：
 * 排列问题的回溯：
 * 1. 每个位置都可以选择任何未使用的元素
 * 2. 需要 used 数组标记已使用的元素
 * 3. 当 path 大小等于 nums.length 时收集结果
 *
 * 排列 vs 组合的区别：
 * - 组合：[1,2] 和 [2,1] 是同一个组合，需要 start 参数避免重复
 * - 排列：[1,2] 和 [2,1] 是不同的排列，每次从头遍历，但需要 used 数组
 *
 * 决策树分析（以 [1,2,3] 为例）：
 *                        []
 *          /              |              \
 *        [1]             [2]             [3]
 *       /   \           /   \           /   \
 *    [1,2] [1,3]     [2,1] [2,3]     [3,1] [3,2]
 *      |     |         |     |         |     |
 *   [1,2,3][1,3,2]  [2,1,3][2,3,1]  [3,1,2][3,2,1]
 *
 * 关键点：
 * - used[i] 标记 nums[i] 是否已在当前路径中使用
 * - 每层递归都从 0 开始遍历（不同于组合从 start 开始）
 * - 收集结果的条件：path.size() == nums.length
 *
 * 时间复杂度：O(n × n!)，n! 个排列，每个需要 O(n) 复制
 * 空间复杂度：O(n)，递归栈深度 + used 数组
 *
 * @author muqian
 */
public class Permutations {
    private static final Logger log = LoggerFactory.getLogger(Permutations.class);

    private List<List<Integer>> result;
    private List<Integer> path;
    private boolean[] used;
    private int stepCounter;

    // 决策树相关
    private DecisionTreeNode treeRoot;
    private Stack<DecisionTreeNode> nodeStack;

    /**
     * 生成所有排列（主方法）
     *
     * @param nums 整数数组
     * @return 所有排列的列表
     */
    public List<List<Integer>> permute(int[] nums) {
        log.info("\n========== 开始生成全排列 ==========");
        log.info("输入数组: {}", arrayToString(nums));
        log.info("排列总数: {} (= {}!)", factorial(nums.length), nums.length);

        result = new ArrayList<>();
        path = new ArrayList<>();
        used = new boolean[nums.length];
        stepCounter = 0;

        // 初始化决策树
        treeRoot = new DecisionTreeNode(0, new ArrayList<>(), null, "开始");
        nodeStack = new Stack<>();
        nodeStack.push(treeRoot);

        log.info("\n");
        backtrack(nums, 0);

        log.info("\n最终结果（共 {} 个排列）：", result.size());
        for (int i = 0; i < result.size(); i++) {
            log.info("  [{}] {}", i + 1, result.get(i));
        }

        // 打印决策树（纵向树形图）
        BacktrackUtils.printDecisionTreeVertical(treeRoot, "全排列决策树 - " + arrayToString(nums));

        log.info("\n========== 生成完成 ==========\n");
        return result;
    }

    /**
     * 回溯算法核心方法
     *
     * @param nums 原数组
     * @param depth 递归深度（用于日志缩进）
     */
    private void backtrack(int[] nums, int depth) {
        String indent = "  ".repeat(depth);
        DecisionTreeNode currentNode = nodeStack.peek();

        // 终止条件：路径长度等于数组长度
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            currentNode.markAsLeaf();
            log.info("{}{} {} 找到排列: {}",
                    indent,
                    ColorUtils.step(++stepCounter),
                    ColorUtils.success("✓"),
                    ColorUtils.highlight(path.toString()));
            return;
        }

        // 遍历所有元素（每次都从 0 开始，不是从 start）
        for (int i = 0; i < nums.length; i++) {
            // 跳过已使用的元素
            if (used[i]) {
                log.debug("{}  跳过已使用的 {}", indent, nums[i]);
                continue;
            }

            // 做选择
            path.add(nums[i]);
            used[i] = true;
            log.info("{}├─ {} 选择 {} → 路径: {} (已用: {})",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(nums[i]),
                    path,
                    usedToString(nums, used));

            // 创建新的决策树节点
            DecisionTreeNode childNode = new DecisionTreeNode(
                depth + 1,
                new ArrayList<>(path),
                nums[i],
                "已用: " + usedToString(nums, used)
            );
            currentNode.addChild(childNode);
            nodeStack.push(childNode);

            // 递归：继续填充下一个位置
            backtrack(nums, depth + 1);

            // 回溯：弹出节点
            nodeStack.pop();

            // 撤销选择（回溯）
            path.remove(path.size() - 1);
            used[i] = false;
            log.debug("{}└─ 回溯，移除 {} 并标记为未使用", indent, nums[i]);
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

    /**
     * 将 used 数组转换为已使用元素的字符串
     */
    private String usedToString(int[] nums, boolean[] used) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < used.length; i++) {
            if (used[i]) {
                if (!first) sb.append(",");
                sb.append(nums[i]);
                first = false;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 计算阶乘
     */
    private int factorial(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
