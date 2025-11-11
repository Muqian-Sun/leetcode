package com.muqian.leetcode.tree.bst;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 98. 验证二叉搜索树
 *
 * 题目描述：
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 二叉搜索树（BST）定义：
 * - 节点的左子树只包含小于当前节点的数
 * - 节点的右子树只包含大于当前节点的数
 * - 所有左子树和右子树自身必须也是二叉搜索树
 *
 * 解题思路：
 *
 * 方法1：递归 + 范围检查
 * - 维护每个节点的有效值范围 (min, max)
 * - 左子树范围：(min, node.val)
 * - 右子树范围：(node.val, max)
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * 方法2：中序遍历
 * - BST的中序遍历是严格递增序列
 * - 遍历过程中检查是否递增
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * @author muqian
 */
public class IsValidBST {
    private static final Logger log = LoggerFactory.getLogger(IsValidBST.class);

    /**
     * 方法1：递归 + 范围检查
     *
     * @param root 二叉树根节点
     * @return 是否是有效的BST
     */
    public boolean isValidBST(TreeNode root) {
        log.info("========== 开始验证BST（递归+范围检查） ==========");
        boolean result = isValidRange(root, null, null, 0);
        log.info("========== 验证完成，结果: {} ==========", result);
        return result;
    }

    /**
     * 递归验证节点值在有效范围内
     *
     * @param node 当前节点
     * @param min 最小值（不包含）
     * @param max 最大值（不包含）
     * @param depth 递归深度
     * @return 是否有效
     */
    private boolean isValidRange(TreeNode node, Integer min, Integer max, int depth) {
        String indent = "  ".repeat(depth);

        if (node == null) {
            log.debug("{}→ 节点为null，有效 ✓", indent);
            return true;
        }

        log.debug("{}→ 验证节点: {}，范围: ({}, {})",
                indent, node.val,
                min != null ? min : "-∞",
                max != null ? max : "+∞");

        // 检查当前节点值是否在有效范围内
        if (min != null && node.val <= min) {
            log.info("{}  ✗ 节点值 {} <= 最小值 {}，无效", indent, node.val, min);
            return false;
        }
        if (max != null && node.val >= max) {
            log.info("{}  ✗ 节点值 {} >= 最大值 {}，无效", indent, node.val, max);
            return false;
        }

        log.debug("{}  ✓ 节点值在有效范围内", indent);

        // 递归验证左子树：值范围 (min, node.val)
        log.debug("{}  ├─ 验证左子树，范围: ({}, {})",
                indent, min != null ? min : "-∞", node.val);
        boolean leftValid = isValidRange(node.left, min, node.val, depth + 1);

        if (!leftValid) {
            log.debug("{}  ├─ 左子树无效 ✗", indent);
            return false;
        }

        // 递归验证右子树：值范围 (node.val, max)
        log.debug("{}  └─ 验证右子树，范围: ({}, {})",
                indent, node.val, max != null ? max : "+∞");
        boolean rightValid = isValidRange(node.right, node.val, max, depth + 1);

        if (!rightValid) {
            log.debug("{}  └─ 右子树无效 ✗", indent);
            return false;
        }

        log.debug("{}← 节点 {} 及其子树有效 ✓", indent, node.val);
        return true;
    }

    /**
     * 方法2：中序遍历检查
     *
     * @param root 二叉树根节点
     * @return 是否是有效的BST
     */
    public boolean isValidBSTInorder(TreeNode root) {
        log.info("========== 开始验证BST（中序遍历） ==========");

        List<Integer> inorderList = new ArrayList<>();
        inorderTraversal(root, inorderList);

        log.debug("中序遍历结果: {}", inorderList);

        // 检查是否严格递增
        for (int i = 1; i < inorderList.size(); i++) {
            if (inorderList.get(i) <= inorderList.get(i - 1)) {
                log.info("发现非递增元素: {} -> {}，无效 ✗",
                        inorderList.get(i - 1), inorderList.get(i));
                log.info("========== 验证完成，结果: false ==========");
                return false;
            }
        }

        log.info("中序遍历严格递增，有效 ✓");
        log.info("========== 验证完成，结果: true ==========");
        return true;
    }

    /**
     * 中序遍历
     */
    private void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }
}
