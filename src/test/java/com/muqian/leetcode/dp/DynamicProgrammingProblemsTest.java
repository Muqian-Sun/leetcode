package com.muqian.leetcode.dp;

import com.muqian.leetcode.dp.basic.ClimbingStairs;
import com.muqian.leetcode.dp.basic.HouseRobber;
import com.muqian.leetcode.dp.basic.MaximumSubarray;
import com.muqian.leetcode.dp.path.UniquePaths;
import com.muqian.leetcode.dp.knapsack.CoinChange;
import com.muqian.leetcode.dp.stock.BestTimeToBuyAndSellStock;
import com.muqian.leetcode.dp.string.LongestPalindromicSubstring;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 动态规划题目综合测试
 *
 * @author muqian
 */
@DisplayName("LeetCode Hot 100 - 动态规划题目测试")
public class DynamicProgrammingProblemsTest {
    private static final Logger log = LoggerFactory.getLogger(DynamicProgrammingProblemsTest.class);

    // ==================== 基础动态规划 ====================

    @Test
    @DisplayName("70. 爬楼梯 - 动态规划")
    public void testClimbStairs() {
        log.info("\n\n==================== 测试：爬楼梯（动态规划） ====================");

        ClimbingStairs solution = new ClimbingStairs();

        assertEquals(2, solution.climbStairs(2));
        assertEquals(3, solution.climbStairs(3));
        assertEquals(8, solution.climbStairs(5));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("70. 爬楼梯 - 空间优化")
    public void testClimbStairsOptimized() {
        log.info("\n\n==================== 测试：爬楼梯（空间优化） ====================");

        ClimbingStairs solution = new ClimbingStairs();

        assertEquals(2, solution.climbStairsOptimized(2));
        assertEquals(3, solution.climbStairsOptimized(3));
        assertEquals(8, solution.climbStairsOptimized(5));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("198. 打家劫舍 - 动态规划")
    public void testHouseRobber() {
        log.info("\n\n==================== 测试：打家劫舍（动态规划） ====================");

        HouseRobber solution = new HouseRobber();

        assertEquals(4, solution.rob(new int[]{1, 2, 3, 1}));
        assertEquals(12, solution.rob(new int[]{2, 7, 9, 3, 1}));
        assertEquals(4, solution.rob(new int[]{2, 1, 1, 2}));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("198. 打家劫舍 - 空间优化")
    public void testHouseRobberOptimized() {
        log.info("\n\n==================== 测试：打家劫舍（空间优化） ====================");

        HouseRobber solution = new HouseRobber();

        assertEquals(4, solution.robOptimized(new int[]{1, 2, 3, 1}));
        assertEquals(12, solution.robOptimized(new int[]{2, 7, 9, 3, 1}));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("53. 最大子数组和 - 动态规划")
    public void testMaximumSubarray() {
        log.info("\n\n==================== 测试：最大子数组和（动态规划） ====================");

        MaximumSubarray solution = new MaximumSubarray();

        assertEquals(6, solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        assertEquals(1, solution.maxSubArray(new int[]{1}));
        assertEquals(23, solution.maxSubArray(new int[]{5, 4, -1, 7, 8}));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("53. 最大子数组和 - 空间优化")
    public void testMaximumSubarrayOptimized() {
        log.info("\n\n==================== 测试：最大子数组和（空间优化） ====================");

        MaximumSubarray solution = new MaximumSubarray();

        assertEquals(6, solution.maxSubArrayOptimized(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        assertEquals(23, solution.maxSubArrayOptimized(new int[]{5, 4, -1, 7, 8}));

        log.info("✓ 测试通过");
    }

    // ==================== 路径问题 ====================

    @Test
    @DisplayName("62. 不同路径 - 二维动态规划")
    public void testUniquePaths() {
        log.info("\n\n==================== 测试：不同路径（二维动态规划） ====================");

        UniquePaths solution = new UniquePaths();

        assertEquals(3, solution.uniquePaths(3, 2));
        assertEquals(28, solution.uniquePaths(3, 7));
        assertEquals(6, solution.uniquePaths(3, 3));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("62. 不同路径 - 一维空间优化")
    public void testUniquePathsOptimized() {
        log.info("\n\n==================== 测试：不同路径（一维空间优化） ====================");

        UniquePaths solution = new UniquePaths();

        assertEquals(3, solution.uniquePathsOptimized(3, 2));
        assertEquals(28, solution.uniquePathsOptimized(3, 7));

        log.info("✓ 测试通过");
    }

    // ==================== 背包问题 ====================

    @Test
    @DisplayName("322. 零钱兑换 - 完全背包")
    public void testCoinChange() {
        log.info("\n\n==================== 测试：零钱兑换（完全背包） ====================");

        CoinChange solution = new CoinChange();

        assertEquals(3, solution.coinChange(new int[]{1, 2, 5}, 11));
        assertEquals(-1, solution.coinChange(new int[]{2}, 3));
        assertEquals(0, solution.coinChange(new int[]{1}, 0));
        assertEquals(2, solution.coinChange(new int[]{1, 2, 5}, 7));

        log.info("✓ 测试通过");
    }

    // ==================== 股票问题 ====================

    @Test
    @DisplayName("121. 买卖股票的最佳时机 - 一次遍历")
    public void testBestTimeToBuyAndSellStock() {
        log.info("\n\n==================== 测试：买卖股票的最佳时机（一次遍历） ====================");

        BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();

        assertEquals(5, solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        assertEquals(0, solution.maxProfit(new int[]{7, 6, 4, 3, 1}));
        assertEquals(6, solution.maxProfit(new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0}));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("121. 买卖股票的最佳时机 - 状态机DP")
    public void testBestTimeToBuyAndSellStockDP() {
        log.info("\n\n==================== 测试：买卖股票的最佳时机（状态机DP） ====================");

        BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();

        assertEquals(5, solution.maxProfitDP(new int[]{7, 1, 5, 3, 6, 4}));
        assertEquals(0, solution.maxProfitDP(new int[]{7, 6, 4, 3, 1}));

        log.info("✓ 测试通过");
    }

    // ==================== 字符串DP ====================

    @Test
    @DisplayName("5. 最长回文子串 - 动态规划")
    public void testLongestPalindromicSubstring() {
        log.info("\n\n==================== 测试：最长回文子串（动态规划） ====================");

        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();

        String result1 = solution.longestPalindrome("babad");
        assertTrue(result1.equals("bab") || result1.equals("aba"));

        assertEquals("bb", solution.longestPalindrome("cbbd"));
        assertEquals("a", solution.longestPalindrome("a"));

        log.info("✓ 测试通过");
    }

    @Test
    @DisplayName("5. 最长回文子串 - 中心扩展")
    public void testLongestPalindromicSubstringExpandCenter() {
        log.info("\n\n==================== 测试：最长回文子串（中心扩展） ====================");

        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();

        String result1 = solution.longestPalindromeExpandCenter("babad");
        assertTrue(result1.equals("bab") || result1.equals("aba"));

        assertEquals("bb", solution.longestPalindromeExpandCenter("cbbd"));

        log.info("✓ 测试通过");
    }
}
