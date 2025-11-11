package com.muqian.leetcode.tree.traversal;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * LeetCode 144. 二叉树的前序遍历
 *
 * 题目描述：
 * 给定一个二叉树的根节点 root，返回它的前序遍历结果。
 *
 * 解题思路：
 * 前序遍历顺序：根节点 -> 左子树 -> 右子树
 *
 * 方法1：递归法
 * - 先访问当前节点
 * - 递归访问左子树
 * - 递归访问右子树
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 方法2：迭代法（使用栈）
 * - 使用栈，先压右子节点，再压左子节点
 * - 这样弹出时就是先左后右的顺序
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class PreorderTraversal {
    private static final Logger log = LoggerFactory.getLogger(PreorderTraversal.class);

    /**
     * 方法1：递归实现前序遍历
     *
     * @param root 二叉树根节点
     * @return 前序遍历结果列表
     */
    public List<Integer> preorderTraversalRecursive(TreeNode root) {
        log.info("========== 开始递归前序遍历 ==========");
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result, 0);
        log.info("========== 遍历完成，结果: {} ==========", result);
        return result;
    }

    /**
     * 递归辅助方法
     *
     * @param node 当前节点
     * @param result 结果列表
     * @param depth 当前递归深度
     */
    private void preorderHelper(TreeNode node, List<Integer> result, int depth) {
        if (node == null) {
            log.debug("{}→ 节点为null，返回", "  ".repeat(depth));
            return;
        }

        log.debug("{}→ 进入节点: {}", "  ".repeat(depth), node.val);

        // 1. 先访问当前节点
        log.debug("{}  ├─ 访问当前节点: {}", "  ".repeat(depth), node.val);
        result.add(node.val);

        // 2. 递归遍历左子树
        log.debug("{}  ├─ 遍历左子树", "  ".repeat(depth));
        preorderHelper(node.left, result, depth + 1);

        // 3. 递归遍历右子树
        log.debug("{}  └─ 遍历右子树", "  ".repeat(depth));
        preorderHelper(node.right, result, depth + 1);

        log.debug("{}← 节点 {} 处理完成", "  ".repeat(depth), node.val);
    }

    /**
     * 方法2：迭代实现前序遍历（使用栈）
     *
     * @param root 二叉树根节点
     * @return 前序遍历结果列表
     */
    public List<Integer> preorderTraversalIterative(TreeNode root) {
        log.info("========== 开始迭代前序遍历 ==========");
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            log.debug("根节点为null，直接返回");
            log.info("========== 遍历完成，结果: {} ==========", result);
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        log.debug("初始化：将根节点 {} 压入栈", root.val);

        while (!stack.isEmpty()) {
            // 1. 弹出栈顶节点并访问
            TreeNode node = stack.pop();
            log.debug("← 弹出节点: {}", node.val);
            log.debug("  访问节点: {}", node.val);
            result.add(node.val);

            // 2. 先压右子节点（后访问）
            if (node.right != null) {
                stack.push(node.right);
                log.debug("  → 将右子节点 {} 压入栈", node.right.val);
            }

            // 3. 再压左子节点（先访问）
            if (node.left != null) {
                stack.push(node.left);
                log.debug("  → 将左子节点 {} 压入栈", node.left.val);
            }

            log.debug("  当前栈大小: {}", stack.size());
        }

        log.info("========== 遍历完成，结果: {} ==========", result);
        return result;
    }
}
