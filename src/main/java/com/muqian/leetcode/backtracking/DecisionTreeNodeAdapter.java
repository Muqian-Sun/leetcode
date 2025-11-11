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
     * 返回节点的内容文本（增强版）
     * 包含节点显示文本、选择信息、深度、状态标记和颜色
     */
    @Override
    public String content() {
        if (node == null) {
            return ColorUtils.nullNode();
        }

        StringBuilder sb = new StringBuilder();

        // 主显示文本（路径）
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

        sb.append(coloredText);

        // 添加状态标记
        if (!marker.isEmpty()) {
            sb.append(marker);
        }

        // 添加选择信息（如果有）
        if (node.getChoice() != null && node.getDepth() > 0) {
            sb.append(" ");
            sb.append(ColorUtils.info("选:" + node.getChoice()));
        }

        // 添加深度信息（更紧凑的显示）
        if (node.getDepth() > 0) {
            sb.append(" ");
            sb.append(ColorUtils.dim("(深度" + node.getDepth() + ")"));
        }

        // 添加描述信息（如果有且不是默认描述）
        if (node.getDescription() != null && !node.getDescription().isEmpty()
                && !node.getDescription().equals("开始")) {
            sb.append(" ");
            sb.append(ColorUtils.info("[" + node.getDescription() + "]"));
        }

        return sb.toString();
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
