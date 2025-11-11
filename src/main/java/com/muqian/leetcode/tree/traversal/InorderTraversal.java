package com.muqian.leetcode.tree.traversal;

import com.muqian.leetcode.tree.TreeNode;
import com.muqian.leetcode.tree.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * LeetCode 94. 二叉树的中序遍历
 *
 * 题目描述：
 * 给定一个二叉树的根节点 root，返回它的中序遍历结果。
 *
 * 解题思路：
 * 中序遍历顺序：左子树 -> 根节点 -> 右子树
 *
 * 方法1：递归法
 * - 递归访问左子树
 * - 访问当前节点
 * - 递归访问右子树
 * 时间复杂度：O(n)，每个节点访问一次
 * 空间复杂度：O(n)，递归栈深度最坏为n
 *
 * 方法2：迭代法（使用栈）
 * - 使用栈模拟递归过程
 * - 先将所有左子节点压栈
 * - 弹出栈顶元素访问，然后处理右子树
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class InorderTraversal {
    private static final Logger log = LoggerFactory.getLogger(InorderTraversal.class);

    private int stepCounter = 0;
    private int visitCount = 0;
    private int maxDepth = 0;

    /**
     * 方法1：递归实现中序遍历
     *
     * @param root 二叉树根节点
     * @return 中序遍历结果列表
     */
    public List<Integer> inorderTraversalRecursive(TreeNode root) {
        log.info("\n========== 开始递归中序遍历 ==========");

        // 显示树结构
        log.info("\n{}", TreeUtils.printTreeStructure(root));

        stepCounter = 0;
        visitCount = 0;
        maxDepth = 0;
        List<Integer> result = new ArrayList<>();

        log.info("\n");
        inorderHelper(root, result, 0);

        log.info("\n{}", TreeUtils.printSummary(visitCount, maxDepth, result));
        log.info("\n========== 遍历完成 ==========\n");
        return result;
    }

    /**
     * 递归辅助方法
     *
     * @param node 当前节点
     * @param result 结果列表
     * @param depth 当前递归深度
     */
    private void inorderHelper(TreeNode node, List<Integer> result, int depth) {
        if (node == null) {
            return;
        }

        maxDepth = Math.max(maxDepth, depth + 1);
        visitCount++;

        String indent = TreeUtils.indent(depth);

        log.info("{}[步骤{}] 进入节点 {}", indent, ++stepCounter, node.val);

        // 1. 递归遍历左子树
        if (node.left != null) {
            log.debug("{}│   ├─ 遍历左子树 → {}", indent, node.left.val);
            inorderHelper(node.left, result, depth + 1);
        }

        // 2. 访问当前节点
        log.info("{}│   ⚡ [步骤{}] 访问节点 {} 并加入结果", indent, ++stepCounter, node.val);
        result.add(node.val);

        // 3. 递归遍历右子树
        if (node.right != null) {
            log.debug("{}│   └─ 遍历右子树 → {}", indent, node.right.val);
            inorderHelper(node.right, result, depth + 1);
        }

        log.debug("{}└─ 节点 {} 处理完成", indent, node.val);
    }

    /**
     * 方法2：迭代实现中序遍历（使用栈）
     *
     * @param root 二叉树根节点
     * @return 中序遍历结果列表
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        log.info("\n========== 开始迭代中序遍历 ==========");

        // 显示树结构
        log.info("\n{}", TreeUtils.printTreeStructure(root));

        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        int stepCounter = 0;
        int visitCount = 0;

        log.info("\n[步骤{}] 初始化：current = {}, stack = []", ++stepCounter,
                current != null ? current.val : "null");

        while (current != null || !stack.isEmpty()) {
            // 1. 一直向左走，将所有左子节点压栈
            while (current != null) {
                log.debug("[步骤{}] 将节点 {} 压入栈", ++stepCounter, current.val);
                stack.push(current);
                current = current.left;
                if (current != null) {
                    log.debug("      向左移动 → {}", current.val);
                }
            }

            // 2. 弹出栈顶节点并访问
            current = stack.pop();
            log.info("⚡ [步骤{}] 弹出并访问节点: {}", ++stepCounter, current.val);
            result.add(current.val);
            visitCount++;

            // 3. 转向右子树
            current = current.right;
            if (current != null) {
                log.debug("      转向右子树 → {}", current.val);
            }
        }

        log.info("\n{}", TreeUtils.printSummary(visitCount, -1, result));
        log.info("\n========== 遍历完成 ==========\n");
        return result;
    }
}
