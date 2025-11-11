package com.muqian.leetcode.tree;

import com.muqian.leetcode.tree.bst.IsValidBST;
import com.muqian.leetcode.tree.construction.BuildTreePreIn;
import com.muqian.leetcode.tree.depth.DiameterOfBinaryTree;
import com.muqian.leetcode.tree.depth.MaxDepth;
import com.muqian.leetcode.tree.lca.LowestCommonAncestor;
import com.muqian.leetcode.tree.path.HasPathSum;
import com.muqian.leetcode.tree.path.PathSumIII;
import com.muqian.leetcode.tree.structure.InvertTree;
import com.muqian.leetcode.tree.structure.IsSymmetric;
import com.muqian.leetcode.tree.traversal.InorderTraversal;
import com.muqian.leetcode.tree.traversal.LevelOrderTraversal;
import com.muqian.leetcode.tree.traversal.PreorderTraversal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 二叉树题目综合测试
 *
 * @author muqian
 */
@DisplayName("LeetCode Hot 100 - 二叉树题目测试")
public class BinaryTreeProblemsTest {
    private static final Logger log = LoggerFactory.getLogger(BinaryTreeProblemsTest.class);

    /**
     * 测试用例1：基础测试树
     *       1
     *      / \
     *     2   3
     *    / \
     *   4   5
     */
    private TreeNode createTestTree1() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        return root;
    }

    @Test
    @DisplayName("94. 二叉树的中序遍历 - 递归")
    public void testInorderTraversalRecursive() {
        log.info("\n\n==================== 测试：中序遍历（递归） ====================");
        TreeNode root = createTestTree1();

        InorderTraversal solution = new InorderTraversal();
        List<Integer> result = solution.inorderTraversalRecursive(root);

        assertEquals(Arrays.asList(4, 2, 5, 1, 3), result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("94. 二叉树的中序遍历 - 迭代")
    public void testInorderTraversalIterative() {
        log.info("\n\n==================== 测试：中序遍历（迭代） ====================");
        TreeNode root = createTestTree1();

        InorderTraversal solution = new InorderTraversal();
        List<Integer> result = solution.inorderTraversalIterative(root);

        assertEquals(Arrays.asList(4, 2, 5, 1, 3), result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("144. 二叉树的前序遍历 - 递归")
    public void testPreorderTraversalRecursive() {
        log.info("\n\n==================== 测试：前序遍历（递归） ====================");
        TreeNode root = createTestTree1();

        PreorderTraversal solution = new PreorderTraversal();
        List<Integer> result = solution.preorderTraversalRecursive(root);

        assertEquals(Arrays.asList(1, 2, 4, 5, 3), result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("144. 二叉树的前序遍历 - 迭代")
    public void testPreorderTraversalIterative() {
        log.info("\n\n==================== 测试：前序遍历（迭代） ====================");
        TreeNode root = createTestTree1();

        PreorderTraversal solution = new PreorderTraversal();
        List<Integer> result = solution.preorderTraversalIterative(root);

        assertEquals(Arrays.asList(1, 2, 4, 5, 3), result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("102. 二叉树的层序遍历")
    public void testLevelOrderTraversal() {
        log.info("\n\n==================== 测试：层序遍历 ====================");
        TreeNode root = createTestTree1();

        LevelOrderTraversal solution = new LevelOrderTraversal();
        List<List<Integer>> result = solution.levelOrder(root);

        assertEquals(3, result.size());
        assertEquals(Arrays.asList(1), result.get(0));
        assertEquals(Arrays.asList(2, 3), result.get(1));
        assertEquals(Arrays.asList(4, 5), result.get(2));
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("104. 二叉树的最大深度 - 递归")
    public void testMaxDepthRecursive() {
        log.info("\n\n==================== 测试：最大深度（递归） ====================");
        TreeNode root = createTestTree1();

        MaxDepth solution = new MaxDepth();
        int depth = solution.maxDepthRecursive(root);

        assertEquals(3, depth);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("104. 二叉树的最大深度 - 迭代")
    public void testMaxDepthIterative() {
        log.info("\n\n==================== 测试：最大深度（迭代） ====================");
        TreeNode root = createTestTree1();

        MaxDepth solution = new MaxDepth();
        int depth = solution.maxDepthIterative(root);

        assertEquals(3, depth);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("543. 二叉树的直径")
    public void testDiameterOfBinaryTree() {
        log.info("\n\n==================== 测试：二叉树的直径 ====================");
        TreeNode root = createTestTree1();

        DiameterOfBinaryTree solution = new DiameterOfBinaryTree();
        int diameter = solution.diameterOfBinaryTree(root);

        assertEquals(3, diameter);  // 路径 4 -> 2 -> 5 -> 1
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("226. 翻转二叉树 - 递归")
    public void testInvertTreeRecursive() {
        log.info("\n\n==================== 测试：翻转二叉树（递归） ====================");
        TreeNode root = createTestTree1();

        InvertTree solution = new InvertTree();
        TreeNode inverted = solution.invertTreeRecursive(root);

        // 验证翻转后的结构
        assertEquals(1, inverted.val);
        assertEquals(3, inverted.left.val);
        assertEquals(2, inverted.right.val);
        assertEquals(5, inverted.right.left.val);
        assertEquals(4, inverted.right.right.val);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("101. 对称二叉树 - 对称的树")
    public void testIsSymmetricTrue() {
        log.info("\n\n==================== 测试：对称二叉树（对称） ====================");
        // 创建对称树：
        //     1
        //    / \
        //   2   2
        //  / \ / \
        // 3  4 4  3
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        IsSymmetric solution = new IsSymmetric();
        boolean result = solution.isSymmetricRecursive(root);

        assertTrue(result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("101. 对称二叉树 - 不对称的树")
    public void testIsSymmetricFalse() {
        log.info("\n\n==================== 测试：对称二叉树（不对称） ====================");
        TreeNode root = createTestTree1();

        IsSymmetric solution = new IsSymmetric();
        boolean result = solution.isSymmetricRecursive(root);

        assertFalse(result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("112. 路径总和 - 存在路径")
    public void testHasPathSumTrue() {
        log.info("\n\n==================== 测试：路径总和（存在） ====================");
        // 创建测试树：
        //       5
        //      / \
        //     4   8
        //    /   / \
        //   11  13  4
        //  /  \      \
        // 7    2      1
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);

        HasPathSum solution = new HasPathSum();
        boolean result = solution.hasPathSum(root, 22);  // 5+4+11+2=22

        assertTrue(result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("437. 路径总和III - 前缀和方法")
    public void testPathSumIII() {
        log.info("\n\n==================== 测试：路径总和III ====================");
        // 创建测试树：
        //      10
        //     /  \
        //    5   -3
        //   / \    \
        //  3   2   11
        // / \   \
        //3  -2   1
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(11);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);

        PathSumIII solution = new PathSumIII();
        int count = solution.pathSumPrefixSum(root, 8);

        assertEquals(3, count);  // 路径：5->3, 5->2->1, 10->5->-3->11
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("105. 从前序与中序遍历构造二叉树")
    public void testBuildTreePreIn() {
        log.info("\n\n==================== 测试：前序+中序构造树 ====================");
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};

        BuildTreePreIn solution = new BuildTreePreIn();
        TreeNode root = solution.buildTree(preorder, inorder);

        // 验证构造的树
        assertEquals(3, root.val);
        assertEquals(9, root.left.val);
        assertEquals(20, root.right.val);
        assertEquals(15, root.right.left.val);
        assertEquals(7, root.right.right.val);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("236. 二叉树的最近公共祖先")
    public void testLowestCommonAncestor() {
        log.info("\n\n==================== 测试：最近公共祖先 ====================");
        TreeNode root = createTestTree1();
        TreeNode p = root.left.left;  // 节点4
        TreeNode q = root.left.right;  // 节点5

        LowestCommonAncestor solution = new LowestCommonAncestor();
        TreeNode lca = solution.lowestCommonAncestor(root, p, q);

        assertEquals(2, lca.val);  // LCA是节点2
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("98. 验证二叉搜索树 - 有效的BST")
    public void testIsValidBSTTrue() {
        log.info("\n\n==================== 测试：验证BST（有效） ====================");
        // 创建有效的BST：
        //     5
        //    / \
        //   3   7
        //  / \ / \
        // 1  4 6  8
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        IsValidBST solution = new IsValidBST();
        boolean result = solution.isValidBST(root);

        assertTrue(result);
        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("98. 验证二叉搜索树 - 无效的BST")
    public void testIsValidBSTFalse() {
        log.info("\n\n==================== 测试：验证BST（无效） ====================");
        // 创建无效的BST：
        //     5
        //    / \
        //   1   4
        //      / \
        //     3   6
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);  // 错误：4 < 5
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(6);

        IsValidBST solution = new IsValidBST();
        boolean result = solution.isValidBST(root);

        assertFalse(result);
        log.info("✓ 测试通过");
    }
}
