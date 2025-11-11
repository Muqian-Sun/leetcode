package com.muqian.leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯算法的决策树节点
 * 用于记录回溯过程中的决策信息，支持可视化展示
 */
public class DecisionTreeNode {
    /**
     * 节点类型枚举
     */
    public enum NodeType {
        NORMAL,     // 普通节点
        LEAF,       // 叶子节点（找到结果）
        PRUNED      // 被剪枝的节点
    }

    private int depth;                          // 当前深度
    private List<Object> path;                  // 从根到当前节点的路径
    private Object choice;                      // 当前选择的元素
    private String description;                 // 节点描述信息
    private NodeType type;                      // 节点类型
    private DecisionTreeNode parent;            // 父节点
    private List<DecisionTreeNode> children;    // 子节点列表

    /**
     * 构造函数
     */
    public DecisionTreeNode(int depth, List<Object> path, Object choice, String description) {
        this.depth = depth;
        this.path = new ArrayList<>(path);
        this.choice = choice;
        this.description = description;
        this.type = NodeType.NORMAL;
        this.children = new ArrayList<>();
    }

    /**
     * 添加子节点
     */
    public void addChild(DecisionTreeNode child) {
        child.parent = this;
        this.children.add(child);
    }

    /**
     * 标记为叶子节点（找到结果）
     */
    public void markAsLeaf() {
        this.type = NodeType.LEAF;
    }

    /**
     * 标记为剪枝节点
     */
    public void markAsPruned() {
        this.type = NodeType.PRUNED;
    }

    /**
     * 判断是否为叶子节点
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * 获取节点的显示文本
     */
    public String getDisplayText() {
        if (choice == null && depth == 0) {
            return "[]";  // 根节点
        }
        if (choice != null) {
            return path.toString();
        }
        return description != null ? description : "";
    }

    /**
     * 获取节点状态标记
     */
    public String getStatusMarker() {
        switch (type) {
            case LEAF:
                return "✓";
            case PRUNED:
                return "✂";
            default:
                return "";
        }
    }

    // Getters
    public int getDepth() {
        return depth;
    }

    public List<Object> getPath() {
        return path;
    }

    public Object getChoice() {
        return choice;
    }

    public String getDescription() {
        return description;
    }

    public NodeType getType() {
        return type;
    }

    public DecisionTreeNode getParent() {
        return parent;
    }

    public List<DecisionTreeNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return String.format("Node[depth=%d, path=%s, choice=%s, type=%s]",
                depth, path, choice, type);
    }
}
