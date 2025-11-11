package com.muqian.leetcode.tree.structure;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 226. 翻转二叉树
 *
 * 题目描述：
 * 翻转一棵二叉树。
 *
 * 示例：
 * 输入：
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 *
 * 输出：
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 * 解题思路：
 *
 * 方法1：递归
 * - 交换当前节点的左右子树
 * - 递归翻转左子树
 * - 递归翻转右子树
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，递归栈深度
 *
 * 方法2：迭代（BFS）
 * - 使用队列进行层序遍历
 * - 对每个节点交换其左右子节点
 * 时间复杂度：O(n)
 * 空间复杂度：O(w)，w为树的最大宽度
 *
 * @author muqian
 */
public class InvertTree {
    private static final Logger log = LoggerFactory.getLogger(InvertTree.class);

    /**
     * 方法1：递归翻转二叉树
     *
     * @param root 二叉树根节点
     * @return 翻转后的根节点
     */
    public TreeNode invertTreeRecursive(TreeNode root) {
        log.info("========== 开始递归翻转二叉树 ==========");
        TreeNode result = invertHelper(root, 0);
        log.info("========== 翻转完成 ==========");
        return result;
    }

    /**
     * 递归辅助方法
     *
     * @param node 当前节点
     * @param depth 当前递归深度
     * @return 翻转后的节点
     */
    private TreeNode invertHelper(TreeNode node, int depth) {
        if (node == null) {
            log.debug("{}→ 节点为null，返回", "  ".repeat(depth));
            return null;
        }

        log.debug("{}→ 处理节点: {}", "  ".repeat(depth), node.val);
        log.debug("{}  原始：左={}, 右={}",
                "  ".repeat(depth),
                node.left != null ? node.left.val : "null",
                node.right != null ? node.right.val : "null");

        // 递归翻转左右子树
        log.debug("{}  ├─ 翻转左子树", "  ".repeat(depth));
        TreeNode left = invertHelper(node.left, depth + 1);

        log.debug("{}  └─ 翻转右子树", "  ".repeat(depth));
        TreeNode right = invertHelper(node.right, depth + 1);

        // 交换左右子节点
        node.left = right;
        node.right = left;

        log.debug("{}  ★ 交换后：左={}, 右={}",
                "  ".repeat(depth),
                node.left != null ? node.left.val : "null",
                node.right != null ? node.right.val : "null");

        log.debug("{}← 节点 {} 处理完成", "  ".repeat(depth), node.val);
        return node;
    }

    /**
     * 方法2：迭代翻转二叉树（BFS）
     *
     * @param root 二叉树根节点
     * @return 翻转后的根节点
     */
    public TreeNode invertTreeIterative(TreeNode root) {
        log.info("========== 开始迭代翻转二叉树 ==========");

        if (root == null) {
            log.debug("根节点为null，直接返回");
            log.info("========== 翻转完成 ==========");
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        log.debug("初始化：将根节点 {} 加入队列", root.val);

        int level = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            log.info("\n--- 第 {} 层，节点数: {} ---", level, levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                log.debug("  处理节点: {}", node.val);
                log.debug("    原始：左={}, 右={}",
                        node.left != null ? node.left.val : "null",
                        node.right != null ? node.right.val : "null");

                // 交换左右子节点
                TreeNode temp = node.left;
                node.left = node.right;
                node.right = temp;

                log.debug("    ★ 交换后：左={}, 右={}",
                        node.left != null ? node.left.val : "null",
                        node.right != null ? node.right.val : "null");

                // 将子节点加入队列
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            log.info("第 {} 层翻转完成", level);
            level++;
        }

        log.info("\n========== 翻转完成 ==========");
        return root;
    }
}
