package com.muqian.leetcode.tree;

/**
 * 二叉树节点定义
 * 这是LeetCode标准的二叉树节点结构
 *
 * @author muqian
 */
public class TreeNode {
    /** 节点值 */
    public int val;

    /** 左子节点 */
    public TreeNode left;

    /** 右子节点 */
    public TreeNode right;

    /**
     * 无参构造函数
     */
    public TreeNode() {
    }

    /**
     * 只设置节点值的构造函数
     *
     * @param val 节点值
     */
    public TreeNode(int val) {
        this.val = val;
    }

    /**
     * 完整构造函数
     *
     * @param val 节点值
     * @param left 左子节点
     * @param right 右子节点
     */
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
