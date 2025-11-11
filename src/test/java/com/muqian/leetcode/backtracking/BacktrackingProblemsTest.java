package com.muqian.leetcode.backtracking;

import com.muqian.leetcode.backtracking.combination.CombinationSum;
import com.muqian.leetcode.backtracking.combination.Combinations;
import com.muqian.leetcode.backtracking.permutation.Permutations;
import com.muqian.leetcode.backtracking.permutation.PermutationsII;
import com.muqian.leetcode.backtracking.string.GenerateParentheses;
import com.muqian.leetcode.backtracking.string.LetterCombinations;
import com.muqian.leetcode.backtracking.subset.Subsets;
import com.muqian.leetcode.backtracking.subset.SubsetsII;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 回溯算法专题测试类
 *
 * @author muqian
 */
public class BacktrackingProblemsTest {
    private static final Logger log = LoggerFactory.getLogger(BacktrackingProblemsTest.class);

    @Test
    public void testSubsets() {
        log.info("\n==================== 测试：子集 ====================\n");

        Subsets solution = new Subsets();
        int[] nums = {1, 2, 3};

        List<List<Integer>> result = solution.subsets(nums);

        // 验证结果
        assertEquals(8, result.size(), "应该有 2^3 = 8 个子集");
        assertTrue(result.contains(Arrays.asList()), "应包含空集");
        assertTrue(result.contains(Arrays.asList(1, 2, 3)), "应包含全集");

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testCombinations() {
        log.info("\n==================== 测试：组合 ====================\n");

        Combinations solution = new Combinations();
        int n = 4, k = 2;

        List<List<Integer>> result = solution.combine(n, k);

        // 验证结果
        assertEquals(6, result.size(), "C(4,2) = 6");
        assertTrue(result.contains(Arrays.asList(1, 2)));
        assertTrue(result.contains(Arrays.asList(3, 4)));

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testPermutations() {
        log.info("\n==================== 测试：全排列 ====================\n");

        Permutations solution = new Permutations();
        int[] nums = {1, 2, 3};

        List<List<Integer>> result = solution.permute(nums);

        // 验证结果
        assertEquals(6, result.size(), "应该有 3! = 6 个排列");
        assertTrue(result.contains(Arrays.asList(1, 2, 3)));
        assertTrue(result.contains(Arrays.asList(3, 2, 1)));

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testLetterCombinations() {
        log.info("\n==================== 测试：电话号码的字母组合 ====================\n");

        LetterCombinations solution = new LetterCombinations();
        String digits = "23";

        List<String> result = solution.letterCombinations(digits);

        // 验证结果
        assertEquals(9, result.size(), "2和3各有3个字母，应该有 3*3 = 9 个组合");
        assertTrue(result.contains("ad"));
        assertTrue(result.contains("cf"));

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testLetterCombinationsEmpty() {
        log.info("\n==================== 测试：电话号码（空输入） ====================\n");

        LetterCombinations solution = new LetterCombinations();
        String digits = "";

        List<String> result = solution.letterCombinations(digits);

        // 验证结果
        assertEquals(0, result.size(), "空输入应返回空列表");

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testCombinationSum() {
        log.info("\n==================== 测试：组合总和 ====================\n");

        CombinationSum solution = new CombinationSum();
        int[] candidates = {2, 3, 6, 7};
        int target = 7;

        List<List<Integer>> result = solution.combinationSum(candidates, target);

        // 验证结果
        assertEquals(2, result.size(), "应该有2个组合");
        assertTrue(result.contains(Arrays.asList(2, 2, 3)));
        assertTrue(result.contains(Arrays.asList(7)));

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testGenerateParentheses() {
        log.info("\n==================== 测试：括号生成 ====================\n");

        GenerateParentheses solution = new GenerateParentheses();
        int n = 3;

        List<String> result = solution.generateParenthesis(n);

        // 验证结果
        assertEquals(5, result.size(), "n=3应该有5个有效组合");
        assertTrue(result.contains("((()))"));
        assertTrue(result.contains("()()()"));

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testSubsetsII() {
        log.info("\n==================== 测试：子集II（去重） ====================\n");

        SubsetsII solution = new SubsetsII();
        int[] nums = {1, 2, 2};

        List<List<Integer>> result = solution.subsetsWithDup(nums);

        // 验证结果
        assertEquals(6, result.size(), "应该有6个不重复的子集");
        assertTrue(result.contains(Arrays.asList()));
        assertTrue(result.contains(Arrays.asList(1, 2, 2)));

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testPermutationsII() {
        log.info("\n==================== 测试：全排列II（去重） ====================\n");

        PermutationsII solution = new PermutationsII();
        int[] nums = {1, 1, 2};

        List<List<Integer>> result = solution.permuteUnique(nums);

        // 验证结果
        assertEquals(3, result.size(), "应该有3个不重复的排列");
        assertTrue(result.contains(Arrays.asList(1, 1, 2)));
        assertTrue(result.contains(Arrays.asList(1, 2, 1)));
        assertTrue(result.contains(Arrays.asList(2, 1, 1)));

        log.info("✓ 测试通过\n");
    }

    @Test
    public void testDecisionTreeVisualization() {
        log.info("\n==================== 测试：决策树可视化 ====================\n");

        log.info("\n--- 1. 全排列决策树 ---");
        Permutations permutations = new Permutations();
        int[] nums1 = {1, 2};
        List<List<Integer>> result1 = permutations.permute(nums1);
        assertEquals(2, result1.size(), "应该有 2! = 2 个排列");

        log.info("\n--- 2. 组合总和决策树（含剪枝） ---");
        CombinationSum combinationSum = new CombinationSum();
        int[] nums2 = {2, 3, 5};
        int target = 8;
        List<List<Integer>> result2 = combinationSum.combinationSum(nums2, target);
        assertEquals(3, result2.size(), "应该有3个组合");

        log.info("\n--- 3. 子集决策树 ---");
        Subsets subsets = new Subsets();
        int[] nums3 = {1, 2};
        List<List<Integer>> result3 = subsets.subsets(nums3);
        assertEquals(4, result3.size(), "应该有 2^2 = 4 个子集");

        log.info("✓ 决策树可视化测试通过\n");
    }
}
