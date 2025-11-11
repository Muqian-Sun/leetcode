package com.muqian.leetcode.tree;

import hu.webarticum.treeprinter.TreeNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 适配器类：将RecursionTreeNode转换为tree-printer库的TreeNode接口
 * 用于使用专业的树形打印库渲染递归调用树
 *
 * @author muqian
 */
public class RecursionTreeNodeAdapter implements TreeNode {

    private final RecursionTreeNode node;

    public RecursionTreeNodeAdapter(RecursionTreeNode node) {
        this.node = node;
    }

    /**
     * 返回节点的内容文本
     * 包含函数调用信息、参数、返回值等
     */
    @Override
    public String content() {
        if (node == null) {
            return ColorUtils.nullNode();
        }

        return node.getDetailedDisplayText();
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
                .map(RecursionTreeNodeAdapter::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return content();
    }
}
