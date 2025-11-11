package com.muqian.leetcode.tree.structure;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 101. 对称二叉树
 *
 * 题目描述：
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的：
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 * 解题思路：
 *
 * 方法1：递归
 * - 判断两个子树是否镜像对称
 * - 左子树的左节点 == 右子树的右节点
 * - 左子树的右节点 == 右子树的左节点
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 方法2：迭代（使用队列）
 * - 使用队列成对存储节点
 * - 每次取出两个节点比较
 * 时间复杂度：O(n)
 * 空间复杂度：O(w)
 *
 * @author muqian
 */
public class IsSymmetric {
    private static final Logger log = LoggerFactory.getLogger(IsSymmetric.class);

    /**
     * 方法1：递归判断是否对称
     *
     * @param root 二叉树根节点
     * @return 是否对称
     */
    public boolean isSymmetricRecursive(TreeNode root) {
        log.info("========== 开始检查二叉树是否对称（递归） ==========");
        if (root == null) {
            log.debug("根节点为null，对称");
            log.info("========== 检查完成，结果: true ==========");
            return true;
        }

        boolean result = isMirror(root.left, root.right, 0);
        log.info("========== 检查完成，结果: {} ==========", result);
        return result;
    }

    /**
     * 判断两个子树是否镜像对称
     *
     * @param left 左子树
     * @param right 右子树
     * @param depth 递归深度
     * @return 是否镜像
     */
    private boolean isMirror(TreeNode left, TreeNode right, int depth) {
        String indent = "  ".repeat(depth);

        // 两个节点都为空，对称
        if (left == null && right == null) {
            log.debug("{}→ 左右节点都为null，对称 ✓", indent);
            return true;
        }

        // 一个为空一个不为空，不对称
        if (left == null || right == null) {
            log.debug("{}→ 左={}, 右={}, 不对称 ✗",
                    indent,
                    left != null ? left.val : "null",
                    right != null ? right.val : "null");
            return false;
        }

        log.debug("{}→ 比较节点：左={}, 右={}", indent, left.val, right.val);

        // 值不相等，不对称
        if (left.val != right.val) {
            log.debug("{}  ✗ 值不相等，不对称", indent);
            return false;
        }

        log.debug("{}  ✓ 值相等", indent);

        // 递归检查：
        // 1. 左子树的左节点 与 右子树的右节点
        // 2. 左子树的右节点 与 右子树的左节点
        log.debug("{}  ├─ 检查外侧：左.left vs 右.right", indent);
        boolean outerSymmetric = isMirror(left.left, right.right, depth + 1);

        log.debug("{}  └─ 检查内侧：左.right vs 右.left", indent);
        boolean innerSymmetric = isMirror(left.right, right.left, depth + 1);

        boolean result = outerSymmetric && innerSymmetric;
        log.debug("{}← 结果: {} (外侧:{}, 内侧:{})",
                indent, result, outerSymmetric, innerSymmetric);

        return result;
    }

    /**
     * 方法2：迭代判断是否对称（使用队列）
     *
     * @param root 二叉树根节点
     * @return 是否对称
     */
    public boolean isSymmetricIterative(TreeNode root) {
        log.info("========== 开始检查二叉树是否对称（迭代） ==========");

        if (root == null) {
            log.debug("根节点为null，对称");
            log.info("========== 检查完成，结果: true ==========");
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        log.debug("初始化：将左右子树加入队列");

        int round = 0;

        while (!queue.isEmpty()) {
            round++;
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();

            log.debug("\n[轮次 {}] 比较节点：左={}, 右={}",
                    round,
                    left != null ? left.val : "null",
                    right != null ? right.val : "null");

            // 两个都为空，继续
            if (left == null && right == null) {
                log.debug("  ✓ 都为null，继续");
                continue;
            }

            // 一个为空或值不相等，不对称
            if (left == null || right == null || left.val != right.val) {
                log.debug("  ✗ 不对称");
                log.info("========== 检查完成，结果: false ==========");
                return false;
            }

            log.debug("  ✓ 值相等");

            // 按镜像顺序加入队列
            // 外侧：左.left 与 右.right
            queue.offer(left.left);
            queue.offer(right.right);
            log.debug("  → 加入外侧：左.left={}, 右.right={}",
                    left.left != null ? left.left.val : "null",
                    right.right != null ? right.right.val : "null");

            // 内侧：左.right 与 右.left
            queue.offer(left.right);
            queue.offer(right.left);
            log.debug("  → 加入内侧：左.right={}, 右.left={}",
                    left.right != null ? left.right.val : "null",
                    right.left != null ? right.left.val : "null");
        }

        log.info("\n========== 检查完成，结果: true ==========");
        return true;
    }
}
