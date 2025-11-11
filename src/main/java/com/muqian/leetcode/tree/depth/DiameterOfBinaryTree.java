package com.muqian.leetcode.tree.depth;

import com.muqian.leetcode.tree.TreeNode;
import com.muqian.leetcode.tree.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 543. 二叉树的直径
 *
 * 题目描述：
 * 给定一棵二叉树，你需要计算它的直径长度。
 * 一棵二叉树的直径长度是任意两个结点路径长度中的最大值。
 * 这条路径可能穿过也可能不穿过根结点。
 *
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 *
 * 解题思路：
 * 树的直径 = 某个节点的左子树高度 + 右子树高度的最大值
 *
 * 算法步骤：
 * 1. 对每个节点，计算其左右子树的高度
 * 2. 当前节点的直径 = 左子树高度 + 右子树高度
 * 3. 在递归过程中维护全局最大直径
 * 4. 返回当前节点作为子树的高度给父节点
 *
 * 关键点：
 * - 直径不一定经过根节点
 * - 需要在递归过程中记录最大值
 * - 递归函数返回高度，同时更新直径
 *
 * 时间复杂度：O(n)，每个节点访问一次
 * 空间复杂度：O(h)，递归栈深度
 *
 * @author muqian
 */
public class DiameterOfBinaryTree {
    private static final Logger log = LoggerFactory.getLogger(DiameterOfBinaryTree.class);

    private int maxDiameter;
    private int stepCounter;
    private int visitCount;

    /**
     * 计算二叉树的直径
     *
     * @param root 二叉树根节点
     * @return 直径长度
     */
    public int diameterOfBinaryTree(TreeNode root) {
        log.info("\n========== 开始计算二叉树直径 ==========");

        // 显示树结构
        log.info("\n{}", TreeUtils.printTreeStructure(root));

        maxDiameter = 0;
        stepCounter = 0;
        visitCount = 0;

        log.info("\n");
        calculateHeight(root, 0);

        log.info("\n{}", TreeUtils.printSummary(visitCount, -1, maxDiameter));
        log.info("\n========== 计算完成 ==========\n");
        return maxDiameter;
    }

    /**
     * 计算节点高度，同时更新最大直径
     *
     * @param node 当前节点
     * @param depth 当前递归深度（用于日志缩进）
     * @return 当前节点的高度
     */
    private int calculateHeight(TreeNode node, int depth) {
        // 空节点高度为0
        if (node == null) {
            return 0;
        }

        visitCount++;
        String indent = TreeUtils.indent(depth);

        log.info("{}[步骤{}] 进入节点 {}", indent, ++stepCounter, node.val);

        // 递归计算左子树高度
        int leftHeight = 0;
        if (node.left != null) {
            log.debug("{}│   ├─ 计算左子树高度 → {}", indent, node.left.val);
            leftHeight = calculateHeight(node.left, depth + 1);
            log.info("{}│   ├─ 左子树高度: {}", indent, leftHeight);
        } else {
            log.debug("{}│   ├─ 左子树为空，高度: 0", indent);
        }

        // 递归计算右子树高度
        int rightHeight = 0;
        if (node.right != null) {
            log.debug("{}│   └─ 计算右子树高度 → {}", indent, node.right.val);
            rightHeight = calculateHeight(node.right, depth + 1);
            log.info("{}│   └─ 右子树高度: {}", indent, rightHeight);
        } else {
            log.debug("{}│   └─ 右子树为空，高度: 0", indent);
        }

        // 计算经过当前节点的路径长度（直径候选值）
        int currentDiameter = leftHeight + rightHeight;
        log.info("{}│   ⚡ 经过节点 {} 的路径长度: {} + {} = {}",
                indent, node.val, leftHeight, rightHeight, currentDiameter);

        // 更新最大直径
        if (currentDiameter > maxDiameter) {
            log.info("{}│   ✓ 更新最大直径: {} → {}",
                    indent, maxDiameter, currentDiameter);
            maxDiameter = currentDiameter;
        } else {
            log.debug("{}│   保持最大直径: {}", indent, maxDiameter);
        }

        // 返回当前节点的高度（给父节点使用）
        int height = Math.max(leftHeight, rightHeight) + 1;
        log.info("{}└─ 节点 {} 的高度: {} (返回给父节点)",
                indent, node.val, height);

        return height;
    }
}
