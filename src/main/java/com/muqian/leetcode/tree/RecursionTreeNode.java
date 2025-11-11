package com.muqian.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归调用树节点
 * 用于记录二叉树递归算法的调用过程，支持可视化展示
 *
 * @author muqian
 */
public class RecursionTreeNode {
    /**
     * 节点类型枚举
     */
    public enum NodeType {
        CALL,       // 正常调用
        BASE_CASE,  // 基础情况（递归终止）
        RETURN      // 返回节点
    }

    private TreeNode treeNode;              // 对应的二叉树节点
    private String functionName;            // 函数名称
    private Object[] params;                // 函数参数
    private Object returnValue;             // 返回值
    private int depth;                      // 递归深度
    private NodeType type;                  // 节点类型
    private String description;             // 描述信息
    private List<RecursionTreeNode> children;  // 子调用列表

    /**
     * 构造函数
     */
    public RecursionTreeNode(TreeNode treeNode, String functionName, Object[] params,
                             int depth, String description) {
        this.treeNode = treeNode;
        this.functionName = functionName;
        this.params = params;
        this.depth = depth;
        this.description = description;
        this.type = NodeType.CALL;
        this.children = new ArrayList<>();
    }

    /**
     * 添加子调用
     */
    public void addChild(RecursionTreeNode child) {
        this.children.add(child);
    }

    /**
     * 设置返回值
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * 标记为基础情况
     */
    public void markAsBaseCase() {
        this.type = NodeType.BASE_CASE;
    }

    /**
     * 获取显示文本（紧凑格式）
     */
    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();

        // 函数名和参数
        sb.append(functionName).append("(");
        if (treeNode != null) {
            sb.append(ColorUtils.node(treeNode.val));
        } else {
            sb.append(ColorUtils.nullNode());
        }

        // 添加其他参数（如果有）
        if (params != null && params.length > 0) {
            for (Object param : params) {
                sb.append(", ").append(param);
            }
        }
        sb.append(")");

        // 返回值
        if (returnValue != null) {
            sb.append(" → ").append(ColorUtils.highlight(String.valueOf(returnValue)));
        }

        // 深度信息
        sb.append(" ").append(ColorUtils.dim("(深度" + depth + ")"));

        return sb.toString();
    }

    /**
     * 获取详细显示文本
     */
    public String getDetailedDisplayText() {
        StringBuilder sb = new StringBuilder();

        // 基础情况标记
        if (type == NodeType.BASE_CASE) {
            sb.append(ColorUtils.error("[基础情况] "));
        }

        // 函数调用
        sb.append(functionName).append("(");
        if (treeNode != null) {
            sb.append("node=").append(ColorUtils.node(treeNode.val));
        } else {
            sb.append("node=").append(ColorUtils.nullNode());
        }

        // 其他参数
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                sb.append(", param").append(i + 1).append("=").append(params[i]);
            }
        }
        sb.append(")");

        // 返回值
        if (returnValue != null) {
            sb.append(" ");
            sb.append(ColorUtils.success("→ "));
            sb.append(ColorUtils.highlight(String.valueOf(returnValue)));
        }

        // 描述信息
        if (description != null && !description.isEmpty()) {
            sb.append(" ");
            sb.append(ColorUtils.info("[" + description + "]"));
        }

        return sb.toString();
    }

    // Getters
    public TreeNode getTreeNode() {
        return treeNode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Object[] getParams() {
        return params;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public int getDepth() {
        return depth;
    }

    public NodeType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public List<RecursionTreeNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return getDisplayText();
    }
}
