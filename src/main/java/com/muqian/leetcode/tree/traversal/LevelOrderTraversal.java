package com.muqian.leetcode.tree.traversal;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LeetCode 102. 二叉树的层序遍历
 *
 * 题目描述：
 * 给定一个二叉树的根节点 root，返回其节点值的层序遍历。
 * （即逐层地，从左到右访问所有节点）
 *
 * 解题思路：
 * 使用广度优先搜索（BFS）+ 队列
 *
 * 算法步骤：
 * 1. 创建队列，将根节点入队
 * 2. 当队列不为空时：
 *    - 记录当前层的节点数量
 *    - 遍历当前层的所有节点
 *    - 将子节点加入队列（下一层）
 * 3. 返回结果
 *
 * 时间复杂度：O(n)，每个节点访问一次
 * 空间复杂度：O(n)，队列最多存储一层的节点，最坏情况下为n/2
 *
 * @author muqian
 */
public class LevelOrderTraversal {
    private static final Logger log = LoggerFactory.getLogger(LevelOrderTraversal.class);

    /**
     * 层序遍历（BFS）
     *
     * @param root 二叉树根节点
     * @return 层序遍历结果，每一层作为一个子列表
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        log.info("========== 开始层序遍历 ==========");
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            log.debug("根节点为null，直接返回");
            log.info("========== 遍历完成，结果: {} ==========", result);
            return result;
        }

        // 创建队列用于BFS
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        log.debug("初始化：将根节点 {} 加入队列", root.val);

        int level = 0;  // 当前层数（从0开始）

        while (!queue.isEmpty()) {
            int levelSize = queue.size();  // 当前层的节点数量
            List<Integer> currentLevel = new ArrayList<>();

            log.info("\n--- 第 {} 层，节点数: {} ---", level, levelSize);

            // 遍历当前层的所有节点
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                log.debug("  [{}/{}] 访问节点: {}", i + 1, levelSize, node.val);
                currentLevel.add(node.val);

                // 将子节点加入队列（下一层）
                if (node.left != null) {
                    queue.offer(node.left);
                    log.debug("      → 左子节点 {} 加入队列", node.left.val);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    log.debug("      → 右子节点 {} 加入队列", node.right.val);
                }
            }

            log.info("第 {} 层遍历完成: {}", level, currentLevel);
            result.add(currentLevel);
            level++;
        }

        log.info("\n========== 遍历完成，总共 {} 层，结果: {} ==========", level, result);
        return result;
    }

    /**
     * 层序遍历变体：返回一维列表
     *
     * @param root 二叉树根节点
     * @return 层序遍历结果（一维列表）
     */
    public List<Integer> levelOrderFlat(TreeNode root) {
        log.info("========== 开始层序遍历（一维） ==========");
        List<Integer> result = new ArrayList<>();

        if (root == null) {
            log.debug("根节点为null，直接返回");
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            log.debug("访问节点: {}", node.val);
            result.add(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        log.info("========== 遍历完成，结果: {} ==========", result);
        return result;
    }
}
