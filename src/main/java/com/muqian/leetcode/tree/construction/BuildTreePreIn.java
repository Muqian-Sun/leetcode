package com.muqian.leetcode.tree.construction;

import com.muqian.leetcode.tree.ColorUtils;
import com.muqian.leetcode.tree.TreeNode;
import com.muqian.leetcode.tree.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 105. 从前序与中序遍历构造二叉树
 *
 * 题目描述：
 * 给定一棵树的前序遍历 preorder 与中序遍历 inorder。
 * 请构造二叉树并返回其根节点。
 *
 * 示例:
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 *
 * 返回如下的二叉树：
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 解题思路：
 * 1. 前序遍历：根节点 -> 左子树 -> 右子树
 * 2. 中序遍历：左子树 -> 根节点 -> 右子树
 *
 * 算法步骤：
 * 1. 前序遍历的第一个元素是根节点
 * 2. 在中序遍历中找到根节点位置
 * 3. 中序遍历中，根节点左边是左子树，右边是右子树
 * 4. 递归构造左右子树
 *
 * 优化：使用哈希表存储中序遍历的值->索引映射，快速定位
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class BuildTreePreIn {
    private static final Logger log = LoggerFactory.getLogger(BuildTreePreIn.class);

    private int[] preorder;
    private int[] inorder;
    private Map<Integer, Integer> inorderMap;
    private int preIndex;
    private int stepCounter;
    private TreeNode treeRoot;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        log.info("\n" + "=".repeat(50));
        log.info("开始构造二叉树");
        log.info("=" + "=".repeat(49));

        this.preorder = preorder;
        this.inorder = inorder;
        this.preIndex = 0;
        this.stepCounter = 0;

        // 阶段1：显示输入
        log.info("\n{}", ColorUtils.phase("[阶段1] 输入数据"));
        log.info(TreeUtils.printSeparator(""));
        log.info("\n{}", TreeUtils.printArrayWithPointer("前序", preorder, -1));
        log.info("{}", TreeUtils.printArrayWithPointer("中序", inorder, -1));

        // 阶段2：初始化
        log.info("\n{}", ColorUtils.phase("[阶段2] 初始化"));
        log.info(TreeUtils.printSeparator(""));
        inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        log.info("✓ 构建中序遍历索引映射完成");

        // 阶段3：构造树
        log.info("\n{}", ColorUtils.phase("[阶段3] 开始构造树"));
        log.info(TreeUtils.printSeparator(""));

        TreeNode root = build(0, inorder.length - 1, 0);

        // 阶段4：完成
        log.info("\n{}", ColorUtils.phase("[阶段4] 构造完成"));
        log.info(TreeUtils.printSeparator(""));
        log.info("\n{}", TreeUtils.printTreeStructure(root));

        log.info("\n" + "=".repeat(50));
        log.info(ColorUtils.success("✓ 构造成功"));
        log.info("=" + "=".repeat(49) + "\n");

        return root;
    }

    /**
     * 显示当前树的状态（紧凑格式）
     */
    private void showTreeState(String indent) {
        if (treeRoot == null) return;

        log.info("{}当前树状态:", indent);
        List<String> lines = buildTreeLines(treeRoot);
        for (String line : lines) {
            log.info("{}  {}", indent, line);
        }
    }

    /**
     * 构建树的ASCII艺术行（简化版）
     */
    private List<String> buildTreeLines(TreeNode root) {
        List<String> lines = new ArrayList<>();
        if (root == null) {
            return lines;
        }

        String rootStr = ColorUtils.node(root.val);

        // 获取左右子树的行
        List<String> leftLines = buildTreeLines(root.left);
        List<String> rightLines = buildTreeLines(root.right);

        // 如果是叶子节点
        if (leftLines.isEmpty() && rightLines.isEmpty()) {
            lines.add(rootStr);
            return lines;
        }

        // 计算根节点位置
        int leftWidth = leftLines.isEmpty() ? 0 : getPlainTextLength(leftLines.get(0));

        // 添加根节点
        int rootPos = leftWidth + (leftLines.isEmpty() ? 0 : 2);
        lines.add(" ".repeat(rootPos) + rootStr);

        // 添加分支线
        if (!leftLines.isEmpty() && !rightLines.isEmpty()) {
            lines.add(" ".repeat(rootPos) + "╱ ╲");
        } else if (!leftLines.isEmpty()) {
            lines.add(" ".repeat(rootPos) + "╱");
        } else {
            lines.add(" ".repeat(rootPos + 1) + "╲");
        }

        // 合并左右子树的行
        int maxLines = Math.max(leftLines.size(), rightLines.size());
        for (int i = 0; i < maxLines; i++) {
            StringBuilder line = new StringBuilder();
            if (i < leftLines.size()) {
                line.append(leftLines.get(i));
            } else {
                line.append(" ".repeat(leftWidth));
            }

            if (!rightLines.isEmpty()) {
                line.append("  ");
                if (i < rightLines.size()) {
                    line.append(rightLines.get(i));
                }
            }
            lines.add(line.toString());
        }

        return lines;
    }

    /**
     * 获取字符串的纯文本长度（不包含ANSI颜色代码）
     */
    private int getPlainTextLength(String str) {
        // 移除ANSI颜色代码
        return str.replaceAll("\\u001B\\[[0-9;]+m", "").length();
    }

    private TreeNode build(int inLeft, int inRight, int depth) {
        String indent = "  ".repeat(depth);

        // 递归终止条件
        if (inLeft > inRight) {
            log.debug("{}区间无效 [{}:{}]，返回null", indent, inLeft, inRight);
            return null;
        }

        // 从前序遍历中取出当前根节点
        int rootVal = preorder[preIndex++];
        TreeNode root = new TreeNode(rootVal);

        // 如果是根节点，保存引用
        if (depth == 0) {
            treeRoot = root;
        }

        log.info("{}{} 创建节点 {}",
                indent,
                ColorUtils.step(++stepCounter),
                ColorUtils.node(rootVal));

        // 在中序遍历中找到根节点位置
        int inRootIndex = inorderMap.get(rootVal);
        int leftSize = inRootIndex - inLeft;
        int rightSize = inRight - inRootIndex;

        // 显示详细信息框
        String box = TreeUtils.printBox("",
                String.format("前序索引: %d  │  值: %s", preIndex - 1, ColorUtils.node(rootVal)),
                String.format("中序区间: [%d:%d]  │  根位置: %d", inLeft, inRight, inRootIndex),
                String.format("左子树大小: %s  │  右子树大小: %s",
                        ColorUtils.info(String.valueOf(leftSize)),
                        ColorUtils.info(String.valueOf(rightSize)))
        );

        for (String line : box.split("\n")) {
            log.info("{}{}", indent, line);
        }

        // 递归构造左子树
        if (leftSize > 0) {
            log.info("{}├─ {} 构造左子树 [{}:{}]",
                    indent,
                    ColorUtils.highlight("▶"),
                    inLeft, inRootIndex - 1);
            root.left = build(inLeft, inRootIndex - 1, depth + 1);
            // 显示当前树状态（仅在深度0-1时显示）
            if (depth <= 1) {
                showTreeState(indent);
            }
        } else {
            log.debug("{}├─ 左子树为空", indent);
        }

        // 递归构造右子树
        if (rightSize > 0) {
            log.info("{}└─ {} 构造右子树 [{}:{}]",
                    indent,
                    ColorUtils.highlight("▶"),
                    inRootIndex + 1, inRight);
            root.right = build(inRootIndex + 1, inRight, depth + 1);
            // 显示当前树状态（仅在深度0-1时显示）
            if (depth <= 1) {
                showTreeState(indent);
            }
        } else {
            log.debug("{}└─ 右子树为空", indent);
        }

        return root;
    }
}
