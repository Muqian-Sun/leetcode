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
 * ============================================================================
 * 💡 思路讲解：如何想到用动态规划？
 * ============================================================================
 *
 * 【第1步】分析问题特征
 * - 问题：在不触发警报的情况下，能偷到的最大金额是多少？
 * - 约束：不能偷相邻的两间房屋
 * - 关键观察：对于第i间房屋，我有两个选择：
 *   → 选择1：偷这间房，那么就不能偷第i-1间房，最多能获得 dp[i-2] + nums[i]
 *   → 选择2：不偷这间房，那么最多能获得 dp[i-1]
 * - 发现：每个位置的最优决策依赖于前面位置的结果，这是动态规划的信号！
 *
 * 【第2步】定义状态（核心）
 * - 思考：我们要求的是什么？→ 偷到第i间房屋时的最大金额
 * - 定义：dp[i] = 偷到第i间房屋（不一定偷第i间）时能获得的最大金额
 * - 注意：dp[i]不是"必须偷第i间"，而是"考虑到第i间为止的最大金额"
 *
 * 【第3步】推导状态转移方程
 * - 站在第i间房屋前，思考：偷还是不偷？
 *   → 如果偷：获得 nums[i]，但不能偷第i-1间，所以总金额 = dp[i-2] + nums[i]
 *   → 如果不偷：总金额 = dp[i-1]
 * - 我们要最大金额，所以取两者较大值
 * - 得出：dp[i] = max(dp[i-1], dp[i-2] + nums[i])
 *
 * 【第4步】确定初始状态
 * - dp[0] = nums[0]：只有第0间房屋，必然偷它
 * - dp[1] = max(nums[0], nums[1])：两间房屋，偷金额大的那间
 *
 * 【第5步】确定计算顺序
 * - 因为dp[i]依赖dp[i-1]和dp[i-2]，所以要从小到大计算
 *
 * 💡 为什么是取最大值？
 * - 因为我们要"最优"决策，在"偷"和"不偷"之间选收益更大的
 * - 每一步都做最优选择，最终得到全局最优解
 *
 * 💡 这道题的精髓
 * - 将"是否偷某间房"的决策问题，转化为"到达某位置的最大金额"的状态问题
 * - 通过状态转移方程，避免了枚举所有可能的偷窃组合
 *
 * ============================================================================
 * 方法1：动态规划（完整版本）
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 方法2：空间优化
 * - 观察：dp[i]只依赖dp[i-1]和dp[i-2]，不需要保存整个数组
 * - 优化：只用两个变量滚动更新
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 * ============================================================================
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
        log.info("\n╔══════════════════════════════════════════════════════════════════╗");
        log.info("║  LeetCode 198. 打家劫舍 - 动态规划思路详解                      ║");
        log.info("╚══════════════════════════════════════════════════════════════════╝");
        log.info("\n【问题】房屋金额：{}，求不触发警报的最大金额\n", DPUtils.arrayToString(nums));

        log.info("【思考过程】");
        log.info("  💭 对于第i间房屋，我要决策：偷还是不偷？");
        log.info("     → 如果偷第i间：不能偷第i-1间，最多获得 dp[i-2] + nums[i]");
        log.info("     → 如果不偷第i间：最多获得 dp[i-1]");
        log.info("  💡 取两者的最大值：dp[i] = max(dp[i-1], dp[i-2] + nums[i])");
        log.info("  ✓ 这样每一步都做最优选择，最终得到全局最优解\n");

        if (nums == null || nums.length == 0) {
            log.info("【特殊情况】数组为空，返回 0");
            log.info("\n========== 计算完成 ==========\n");
            return 0;
        }

        if (nums.length == 1) {
            log.info("【特殊情况】只有一间房屋，直接偷窃，返回 {}", nums[0]);
            log.info("\n========== 计算完成 ==========\n");
            return nums[0];
        }

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        stepCounter = 0;

        log.info("【初始化】");
        log.info("  dp[0] = {} (只有第0家，必偷)", dp[0]);
        log.info("  dp[1] = max({}, {}) = {} (两家选金额大的)", nums[0], nums[1], dp[1]);
        log.info("  DP数组: {}\n", DPUtils.format1DArray(dp));

        log.info("【动态规划过程】从第2间房屋开始决策");
        log.info("─".repeat(70));

        for (int i = 2; i < n; i++) {
            int notRob = dp[i - 1];
            int rob = dp[i - 2] + nums[i];
            dp[i] = Math.max(notRob, rob);

            log.info("\n[步骤{}] 决策第 {} 间房屋（金额 = {}）", ++stepCounter, i, nums[i]);
            log.info("│");
            log.info("│  💭 思考：偷还是不偷？");
            log.info("│     方案1 - 不偷：保持上一间的最大金额 = dp[{}] = {}", i-1, notRob);
            log.info("│     方案2 - 偷：跳过上一间，加上这间 = dp[{}] + {} = {} + {} = {}",
                    i-2, nums[i], dp[i-2], nums[i], rob);
            log.info("│");
            log.info("│  ✓ 选择最优：max({}, {}) = {}", notRob, rob, dp[i]);
            String decision = dp[i] == rob ? "偷第" + i + "间" : "不偷第" + i + "间";
            log.info("│     决策：{}", decision);
            log.info("│");
            log.info("│  当前DP数组: {}", DPUtils.format1DArray(dp));
            log.info("─".repeat(70));
        }

        log.info("\n【结果】");
        log.info("  最大金额 = {}", dp[n - 1]);
        log.info("  （通过 {} 步决策得到最优方案）", stepCounter);
        log.info("\n{}", DPUtils.printSummary(stepCounter, dp[n - 1]));

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
