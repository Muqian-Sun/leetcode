package com.muqian.leetcode.tree.depth;

import com.muqian.leetcode.tree.TreeNode;
import com.muqian.leetcode.tree.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 104. 二叉树的最大深度
 *
 * 题目描述：
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 解题思路：
 *
 * 方法1：递归（DFS）
 * - 如果节点为空，深度为0
 * - 否则深度 = max(左子树深度, 右子树深度) + 1
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)，h为树的高度
 *
 * 方法2：迭代（BFS层序遍历）
 * - 使用队列进行层序遍历
 * - 每遍历一层，深度+1
 * 时间复杂度：O(n)
 * 空间复杂度：O(w)，w为树的最大宽度
 *
 * @author muqian
 */
public class MaxDepth {
    private static final Logger log = LoggerFactory.getLogger(MaxDepth.class);

    private int stepCounter = 0;
    private int visitCount = 0;

    /**
     * 方法1：递归求最大深度
     *
     * @param root 二叉树根节点
     * @return 最大深度
     */
    public int maxDepthRecursive(TreeNode root) {
        log.info("\n========== 开始计算最大深度（递归DFS） ==========");

        // 显示树结构
        log.info("\n{}", TreeUtils.printTreeStructure(root));

        stepCounter = 0;
        visitCount = 0;

        log.info("\n");
        int result = maxDepthHelper(root, 0);

        log.info("\n{}", TreeUtils.printSummary(visitCount, result, result));
        log.info("\n========== 计算完成 ==========\n");
        return result;
    }

    /**
     * 递归辅助方法
     *
     * @param node 当前节点
     * @param depth 当前深度
     * @return 以当前节点为根的子树的最大深度
     */
    private int maxDepthHelper(TreeNode node, int depth) {
        // 基础情况：空节点深度为0
        if (node == null) {
            return 0;
        }

        visitCount++;
        String indent = TreeUtils.indent(depth);

        log.info("{}[步骤{}] 进入节点 {}，当前深度: {}", indent, ++stepCounter, node.val, depth + 1);

        // 递归计算左子树深度
        int leftDepth = 0;
        if (node.left != null) {
            log.debug("{}│   ├─ 计算左子树深度 → {}", indent, node.left.val);
            leftDepth = maxDepthHelper(node.left, depth + 1);
            log.info("{}│   ├─ 左子树深度: {}", indent, leftDepth);
        } else {
            log.debug("{}│   ├─ 左子树为空，深度: 0", indent);
        }

        // 递归计算右子树深度
        int rightDepth = 0;
        if (node.right != null) {
            log.debug("{}│   └─ 计算右子树深度 → {}", indent, node.right.val);
            rightDepth = maxDepthHelper(node.right, depth + 1);
            log.info("{}│   └─ 右子树深度: {}", indent, rightDepth);
        } else {
            log.debug("{}│   └─ 右子树为空，深度: 0", indent);
        }

        // 当前节点的深度 = max(左子树, 右子树) + 1
        int currentDepth = Math.max(leftDepth, rightDepth) + 1;
        log.info("{}⚡ 节点 {} 的深度 = max({}, {}) + 1 = {}",
                indent, node.val, leftDepth, rightDepth, currentDepth);

        return currentDepth;
    }

    /**
     * 方法2：迭代求最大深度（BFS层序遍历）
     *
     * @param root 二叉树根节点
     * @return 最大深度
     */
    public int maxDepthIterative(TreeNode root) {
        log.info("\n========== 开始计算最大深度（迭代BFS） ==========");

        // 显示树结构
        log.info("\n{}", TreeUtils.printTreeStructure(root));

        if (root == null) {
            log.debug("根节点为null，深度为0");
            log.info("\n========== 计算完成，最大深度: 0 ==========\n");
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        int stepCounter = 0;
        int visitCount = 0;

        log.info("\n[步骤{}] 初始化：将根节点 {} 加入队列\n", ++stepCounter, root.val);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;
            visitCount += levelSize;

            log.info("╔═══ 第 {} 层（节点数: {}）═══", depth, levelSize);

            // 遍历当前层的所有节点
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                log.info("║ [步骤{}] 访问节点: {}", ++stepCounter, node.val);

                // 将下一层节点加入队列
                if (node.left != null) {
                    queue.offer(node.left);
                    log.debug("║   → 左子节点 {} 加入队列", node.left.val);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    log.debug("║   → 右子节点 {} 加入队列", node.right.val);
                }
            }

            log.info("╚═══ 第 {} 层遍历完成 ═══\n", depth);
        }

        log.info("{}", TreeUtils.printSummary(visitCount, depth, depth));
        log.info("\n========== 计算完成 ==========\n");
        return depth;
    }
}
