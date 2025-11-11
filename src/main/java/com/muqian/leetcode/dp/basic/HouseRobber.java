package com.muqian.leetcode.dp.basic;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 198. 打家劫舍
 *
 * 题目描述：
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
 * 影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，
 * 计算你不触动警报装置的情况下，一夜之内能够偷窃到的最高金额。
 *
 * 示例 1:
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 *
 * 示例 2:
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 *
 * 解题思路：
 *
 * 方法1：动态规划
 * - 状态定义：dp[i] 表示偷到第i家时能获得的最大金额
 * - 状态转移方程：dp[i] = max(dp[i-1], dp[i-2] + nums[i])
 *   - 不偷第i家：dp[i] = dp[i-1]
 *   - 偷第i家：dp[i] = dp[i-2] + nums[i]（因为不能偷相邻的）
 * - 初始状态：dp[0] = nums[0], dp[1] = max(nums[0], nums[1])
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 方法2：空间优化
 * - 只需要前两个状态，可以用两个变量代替数组
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * @author muqian
 */
public class HouseRobber {
    private static final Logger log = LoggerFactory.getLogger(HouseRobber.class);

    private int stepCounter = 0;

    /**
     * 方法1：动态规划
     *
     * @param nums 每个房屋的金额数组
     * @return 不触动警报装置能够偷窃到的最高金额
     */
    public int rob(int[] nums) {
        log.info("\n========== 开始计算打家劫舍最大金额（动态规划） ==========");
        log.info("房屋金额：{}", DPUtils.arrayToString(nums));
        log.info("状态转移方程：dp[i] = max(dp[i-1], dp[i-2] + nums[i])\n");

        if (nums == null || nums.length == 0) {
            log.info("数组为空，返回 0");
            log.info("\n========== 计算完成 ==========\n");
            return 0;
        }

        if (nums.length == 1) {
            log.info("只有一间房屋，直接偷窃，返回 {}", nums[0]);
            log.info("\n========== 计算完成 ==========\n");
            return nums[0];
        }

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        stepCounter = 0;

        log.info("[步骤{}] 初始化基础情况", ++stepCounter);
        log.info("│   dp[0] = nums[0] = {} (只有第0家，必偷)", dp[0]);
        log.info("│   dp[1] = max(nums[0], nums[1]) = max({}, {}) = {} (两家选金额大的)",
                nums[0], nums[1], dp[1]);
        log.info("│   DP数组: {}\n", DPUtils.format1DArray(dp));

        for (int i = 2; i < n; i++) {
            int notRob = dp[i - 1];
            int rob = dp[i - 2] + nums[i];
            dp[i] = Math.max(notRob, rob);

            log.info("[步骤{}] 决策第 {} 家 (金额={})", ++stepCounter, i, nums[i]);
            log.info("│   方案1 - 不偷第{}家: dp[{}] = {}", i, i-1, notRob);
            log.info("│   方案2 - 偷第{}家: dp[{}] + nums[{}] = {} + {} = {}",
                    i, i-2, i, dp[i-2], nums[i], rob);
            log.info("│   选择: max({}, {}) = {}", notRob, rob, dp[i]);
            log.info("│   DP数组: {}\n", DPUtils.format1DArray(dp));
        }

        log.info("{}", DPUtils.printSummary(stepCounter, dp[n - 1]));
        log.info("\n========== 计算完成 ==========\n");

        return dp[n - 1];
    }

    /**
     * 方法2：空间优化的动态规划
     *
     * @param nums 每个房屋的金额数组
     * @return 不触动警报装置能够偷窃到的最高金额
     */
    public int robOptimized(int[] nums) {
        log.info("\n========== 开始计算打家劫舍最大金额（空间优化） ==========");
        log.info("房屋金额：{}", DPUtils.arrayToString(nums));
        log.info("优化：使用两个变量代替数组\n");

        if (nums == null || nums.length == 0) {
            log.info("数组为空，返回 0");
            log.info("\n========== 计算完成 ==========\n");
            return 0;
        }

        if (nums.length == 1) {
            log.info("只有一间房屋，直接偷窃，返回 {}", nums[0]);
            log.info("\n========== 计算完成 ==========\n");
            return nums[0];
        }

        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);

        stepCounter = 0;

        log.info("[步骤{}] 初始化：prev2 = {}, prev1 = {}", ++stepCounter, prev2, prev1);

        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);

            log.info("\n[步骤{}] 决策第 {} 家 (金额={})", ++stepCounter, i, nums[i]);
            log.info("│   不偷: prev1 = {}", prev1);
            log.info("│   偷: prev2 + nums[{}] = {} + {} = {}", i, prev2, nums[i], prev2 + nums[i]);
            log.info("│   current = max({}, {}) = {}", prev1, prev2 + nums[i], current);
            log.info("│   更新：prev2 = {}, prev1 = {}", prev1, current);

            prev2 = prev1;
            prev1 = current;
        }

        log.info("\n{}", DPUtils.printSummary(stepCounter, prev1));
        log.info("\n========== 计算完成 ==========\n");

        return prev1;
    }
}
