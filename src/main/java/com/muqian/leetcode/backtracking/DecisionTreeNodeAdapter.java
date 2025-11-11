package com.muqian.leetcode.backtracking;

import com.muqian.leetcode.tree.ColorUtils;
import hu.webarticum.treeprinter.TreeNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 适配器类：将DecisionTreeNode转换为tree-printer库的TreeNode接口
 * 用于使用专业的树形打印库渲染决策树
 *
 * @author muqian
 */
public class DecisionTreeNodeAdapter implements TreeNode {

    private final DecisionTreeNode node;

    public DecisionTreeNodeAdapter(DecisionTreeNode node) {
        this.node = node;
    }

    /**
     * 返回节点的内容文本
     * 包含节点显示文本、状态标记和颜色
     */
    @Override
    public String content() {
        if (node == null) {
            return ColorUtils.nullNode();
        }

        String displayText = node.getDisplayText();
        String marker = node.getStatusMarker();

        // 根据节点类型添加颜色
        String coloredText;
        switch (node.getType()) {
            case LEAF:
                coloredText = ColorUtils.success(displayText);
                break;
            case PRUNED:
                coloredText = ColorUtils.error(displayText);
                break;
            default:
                coloredText = ColorUtils.node(displayText);
                break;
        }

        return coloredText + marker;
    }

    /**
     * 返回子节点列表
     */
    @Override
    public List<TreeNode> children() {
        if (node == null || node.getChildren() == null) {
            return List.of();
        }

        return node.getChildren().stream()
                .map(DecisionTreeNodeAdapter::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return content();
    }
}
