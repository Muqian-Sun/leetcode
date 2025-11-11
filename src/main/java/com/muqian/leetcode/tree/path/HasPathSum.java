package com.muqian.leetcode.tree.path;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 112. 路径总和
 *
 * 题目描述：
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，
 * 这条路径上所有节点值相加等于目标和。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 给定如下二叉树，以及目标和 sum = 22
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  4
 *  /  \      \
 * 7    2      1
 * 返回 true, 因为存在路径 5->4->11->2 的总和为 22。
 *
 * 解题思路：
 * 使用递归（DFS）
 *
 * 算法步骤：
 * 1. 如果节点为空，返回false
 * 2. 如果是叶子节点，判断值是否等于剩余目标和
 * 3. 否则递归检查左右子树，目标和减去当前节点值
 *
 * 关键点：
 * - 必须是根到叶子的路径
 * - 叶子节点：左右子节点都为空
 *
 * 时间复杂度：O(n)，最坏情况下访问所有节点
 * 空间复杂度：O(h)，递归栈深度
 *
 * @author muqian
 */
public class HasPathSum {
    private static final Logger log = LoggerFactory.getLogger(HasPathSum.class);

    /**
     * 判断是否存在路径总和等于目标值
     *
     * @param root 二叉树根节点
     * @param targetSum 目标和
     * @return 是否存在符合条件的路径
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        log.info("========== 开始查找路径总和 {} ==========", targetSum);
        boolean result = hasPathSumHelper(root, targetSum, 0, 0);
        log.info("========== 查找完成，结果: {} ==========", result);
        return result;
    }

    /**
     * 递归辅助方法
     *
     * @param node 当前节点
     * @param targetSum 目标和
     * @param currentSum 当前路径和
     * @param depth 递归深度
     * @return 是否存在符合条件的路径
     */
    private boolean hasPathSumHelper(TreeNode node, int targetSum, int currentSum, int depth) {
        String indent = "  ".repeat(depth);

        // 节点为空，返回false
        if (node == null) {
            log.debug("{}→ 节点为null，返回false", indent);
            return false;
        }

        // 更新当前路径和
        currentSum += node.val;
        log.debug("{}→ 进入节点: {}，当前路径和: {} (目标: {})",
                indent, node.val, currentSum, targetSum);

        // 判断是否是叶子节点
        boolean isLeaf = (node.left == null && node.right == null);
        if (isLeaf) {
            boolean found = (currentSum == targetSum);
            log.info("{}  ★ 叶子节点，路径和: {}，目标: {}，{}",
                    indent, currentSum, targetSum, found ? "找到 ✓" : "不匹配 ✗");
            return found;
        }

        log.debug("{}  非叶子节点，继续搜索...", indent);

        // 递归检查左子树
        if (node.left != null) {
            log.debug("{}  ├─ 搜索左子树", indent);
            if (hasPathSumHelper(node.left, targetSum, currentSum, depth + 1)) {
                log.debug("{}  ├─ 左子树找到路径 ✓", indent);
                return true;
            }
        }

        // 递归检查右子树
        if (node.right != null) {
            log.debug("{}  └─ 搜索右子树", indent);
            if (hasPathSumHelper(node.right, targetSum, currentSum, depth + 1)) {
                log.debug("{}  └─ 右子树找到路径 ✓", indent);
                return true;
            }
        }

        log.debug("{}← 节点 {} 未找到符合条件的路径", indent, node.val);
        return false;
    }
}
