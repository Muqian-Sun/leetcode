package com.muqian.leetcode.tree.path;

import com.muqian.leetcode.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 437. 路径总和 III
 *
 * 题目描述：
 * 给定一个二叉树的根节点 root，和一个整数 targetSum，
 * 求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 *
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，
 * 但是路径方向必须是向下的（只能从父节点到子节点）。
 *
 * 解题思路：
 *
 * 方法1：双重递归
 * - 外层递归遍历每个节点
 * - 内层递归从当前节点开始计算路径
 * 时间复杂度：O(n²)，最坏情况
 * 空间复杂度：O(h)
 *
 * 方法2：前缀和 + 哈希表（推荐）
 * - 使用哈希表记录前缀和出现次数
 * - 当前和 - 目标和 = 需要的前缀和
 * - 如果前缀和存在，说明找到路径
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class PathSumIII {
    private static final Logger log = LoggerFactory.getLogger(PathSumIII.class);

    /**
     * 方法1：双重递归
     *
     * @param root 二叉树根节点
     * @param targetSum 目标和
     * @return 符合条件的路径数量
     */
    public int pathSumDoubleRecursion(TreeNode root, int targetSum) {
        log.info("========== 开始查找路径总和III（双重递归）, 目标: {} ==========", targetSum);
        int result = countPaths(root, targetSum, 0);
        log.info("========== 查找完成，找到 {} 条路径 ==========", result);
        return result;
    }

    /**
     * 外层递归：遍历每个节点
     */
    private int countPaths(TreeNode node, int targetSum, int depth) {
        if (node == null) {
            return 0;
        }

        String indent = "  ".repeat(depth);
        log.debug("{}→ 遍历节点: {}", indent, node.val);

        // 从当前节点开始计算路径数
        int pathsFromCurrent = countPathsFrom(node, targetSum, 0, depth);
        log.debug("{}  从节点 {} 开始的路径数: {}", indent, node.val, pathsFromCurrent);

        // 递归遍历左右子树
        int pathsFromLeft = countPaths(node.left, targetSum, depth + 1);
        int pathsFromRight = countPaths(node.right, targetSum, depth + 1);

        return pathsFromCurrent + pathsFromLeft + pathsFromRight;
    }

    /**
     * 内层递归：从指定节点开始计算路径
     */
    private int countPathsFrom(TreeNode node, long targetSum, long currentSum, int depth) {
        if (node == null) {
            return 0;
        }

        currentSum += node.val;
        String indent = "  ".repeat(depth + 1);

        int count = 0;
        if (currentSum == targetSum) {
            log.info("{}  ★ 找到路径！当前和: {} = 目标: {}", indent, currentSum, targetSum);
            count = 1;
        }

        // 继续向下搜索
        count += countPathsFrom(node.left, targetSum, currentSum, depth + 1);
        count += countPathsFrom(node.right, targetSum, currentSum, depth + 1);

        return count;
    }

    /**
     * 方法2：前缀和 + 哈希表（推荐）
     *
     * @param root 二叉树根节点
     * @param targetSum 目标和
     * @return 符合条件的路径数量
     */
    public int pathSumPrefixSum(TreeNode root, int targetSum) {
        log.info("========== 开始查找路径总和III（前缀和）, 目标: {} ==========", targetSum);

        // 哈希表：前缀和 -> 出现次数
        Map<Long, Integer> prefixSumCount = new HashMap<>();
        // 前缀和为0出现1次（用于处理从根节点开始的路径）
        prefixSumCount.put(0L, 1);

        int result = dfs(root, targetSum, 0L, prefixSumCount, 0);
        log.info("========== 查找完成，找到 {} 条路径 ==========", result);
        return result;
    }

    /**
     * DFS + 前缀和
     */
    private int dfs(TreeNode node, int targetSum, long currentSum,
                    Map<Long, Integer> prefixSumCount, int depth) {
        if (node == null) {
            return 0;
        }

        String indent = "  ".repeat(depth);
        log.debug("{}→ 进入节点: {}", indent, node.val);

        // 更新当前路径和
        currentSum += node.val;
        log.debug("{}  当前路径和: {}", indent, currentSum);

        // 查找是否存在前缀和 = currentSum - targetSum
        // 如果存在，说明从那个前缀和位置到当前节点的路径和为 targetSum
        long needSum = currentSum - targetSum;
        int count = prefixSumCount.getOrDefault(needSum, 0);

        if (count > 0) {
            log.info("{}  ★ 找到 {} 条路径！(需要前缀和: {}, 当前和: {})",
                    indent, count, needSum, currentSum);
        }

        // 将当前前缀和加入哈希表
        prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        log.debug("{}  记录前缀和 {} 出现次数: {}",
                indent, currentSum, prefixSumCount.get(currentSum));

        // 递归处理左右子树
        count += dfs(node.left, targetSum, currentSum, prefixSumCount, depth + 1);
        count += dfs(node.right, targetSum, currentSum, prefixSumCount, depth + 1);

        // 回溯：移除当前节点的前缀和（保证不影响其他分支）
        prefixSumCount.put(currentSum, prefixSumCount.get(currentSum) - 1);
        log.debug("{}← 回溯，移除前缀和 {}", indent, currentSum);

        return count;
    }
}
