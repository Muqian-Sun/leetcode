package com.muqian.leetcode.tree.lca;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 236. 二叉树的最近公共祖先
 *
 * 题目描述：
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 *
 * 最近公共祖先的定义：
 * 对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，
 * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。
 *
 * 解题思路：
 * 使用递归（后序遍历思想）
 *
 * 算法逻辑：
 * 1. 如果当前节点为空或等于p或q，返回当前节点
 * 2. 递归查找左右子树
 * 3. 如果左右子树都找到了，说明当前节点就是LCA
 * 4. 如果只有一边找到，返回找到的那边
 *
 * 关键点：
 * - 后序遍历：先处理子树，再处理当前节点
 * - 如果p和q分别在当前节点的左右子树，当前节点就是LCA
 * - 如果都在一边，那边的返回值就是LCA
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(h)
 *
 * @author muqian
 */
public class LowestCommonAncestor {
    private static final Logger log = LoggerFactory.getLogger(LowestCommonAncestor.class);

    /**
     * 查找最近公共祖先
     *
     * @param root 二叉树根节点
     * @param p 节点p
     * @param q 节点q
     * @return 最近公共祖先节点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        log.info("========== 查找节点 {} 和 {} 的最近公共祖先 ==========", p.val, q.val);
        TreeNode result = findLCA(root, p, q, 0);
        log.info("========== 找到最近公共祖先: {} ==========", result != null ? result.val : "null");
        return result;
    }

    /**
     * 递归查找LCA
     *
     * @param node 当前节点
     * @param p 节点p
     * @param q 节点q
     * @param depth 递归深度
     * @return LCA节点
     */
    private TreeNode findLCA(TreeNode node, TreeNode p, TreeNode q, int depth) {
        String indent = "  ".repeat(depth);

        // 基础情况：节点为空，或找到p或q
        if (node == null) {
            log.debug("{}→ 节点为null，返回null", indent);
            return null;
        }

        log.debug("{}→ 进入节点: {}", indent, node.val);

        // 如果当前节点等于p或q，返回当前节点
        if (node == p || node == q) {
            log.info("{}  ★ 找到目标节点: {}", indent, node.val);
            return node;
        }

        // 递归查找左子树
        log.debug("{}  ├─ 查找左子树", indent);
        TreeNode left = findLCA(node.left, p, q, depth + 1);

        // 递归查找右子树
        log.debug("{}  └─ 查找右子树", indent);
        TreeNode right = findLCA(node.right, p, q, depth + 1);

        // 分析结果
        log.debug("{}  左子树返回: {}, 右子树返回: {}",
                indent,
                left != null ? left.val : "null",
                right != null ? right.val : "null");

        // 情况1：左右子树都找到了，当前节点就是LCA
        if (left != null && right != null) {
            log.info("{}  ✓ 找到LCA: {} (左右子树都找到目标)", indent, node.val);
            return node;
        }

        // 情况2：只有一边找到，返回找到的那边
        TreeNode result = left != null ? left : right;
        if (result != null) {
            log.debug("{}← 向上传递: {}", indent, result.val);
        } else {
            log.debug("{}← 返回null", indent);
        }

        return result;
    }
}
