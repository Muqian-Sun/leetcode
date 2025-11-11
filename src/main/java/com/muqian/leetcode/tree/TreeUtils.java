package com.muqian.leetcode.tree;

import java.util.*;

/**
 * 二叉树工具类
 * 提供构建树、打印树、日志输出等辅助功能
 *
 * @author muqian
 */
public class TreeUtils {

    /**
     * 从数组构建二叉树（层序遍历方式）
     * null 表示空节点
     *
     * 例如: [1,2,3,null,4] 表示:
     *       1
     *      / \
     *     2   3
     *      \
     *       4
     *
     * @param array 层序遍历数组，null表示空节点
     * @return 树的根节点
     */
    public static TreeNode buildTree(Integer[] array) {
        if (array == null || array.length == 0 || array[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(array[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int index = 1;

        while (!queue.isEmpty() && index < array.length) {
            TreeNode current = queue.poll();

            // 处理左子节点
            if (index < array.length) {
                if (array[index] != null) {
                    current.left = new TreeNode(array[index]);
                    queue.offer(current.left);
                }
                index++;
            }

            // 处理右子节点
            if (index < array.length) {
                if (array[index] != null) {
                    current.right = new TreeNode(array[index]);
                    queue.offer(current.right);
                }
                index++;
            }
        }

        return root;
    }

    /**
     * 打印二叉树结构（可视化）
     *
     * @param root 树的根节点
     * @return 树的字符串表示
     */
    public static String printTree(TreeNode root) {
        if (root == null) {
            return "空树";
        }

        List<List<String>> lines = new ArrayList<>();
        List<TreeNode> level = new ArrayList<>();
        List<TreeNode> next = new ArrayList<>();

        level.add(root);
        int widest = 0;

        while (!level.isEmpty()) {
            List<String> line = new ArrayList<>();
            for (TreeNode node : level) {
                if (node == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String val = String.valueOf(node.val);
                    line.add(val);
                    widest = Math.max(widest, val.length());
                    next.add(node.left);
                    next.add(node.right);
                }
            }

            if (lines.size() > 0) {
                boolean allNull = true;
                for (TreeNode node : next) {
                    if (node != null) {
                        allNull = false;
                        break;
                    }
                }
                if (allNull) {
                    break;
                }
            }

            lines.add(line);
            level = new ArrayList<>(next);
            next.clear();
        }

        StringBuilder result = new StringBuilder();
        for (List<String> line : lines) {
            for (String val : line) {
                if (val == null) {
                    result.append("null").append(" ");
                } else {
                    result.append(val).append(" ");
                }
            }
            result.append("\n");
        }

        return result.toString();
    }

    /**
     * 生成ASCII艺术风格的树形结构
     * 用于在日志中直观展示二叉树
     *
     * @param root 树的根节点
     * @return ASCII艺术风格的树形字符串
     */
    public static String printTreeStructure(TreeNode root) {
        if (root == null) {
            return "空树";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════\n");
        sb.append("║ 树结构预览：\n");
        sb.append("║\n");

        List<String> lines = buildTreeLines(root);
        for (String line : lines) {
            sb.append("║  ").append(line).append("\n");
        }

        sb.append("╚═══════════════════════════════════════════");
        return sb.toString();
    }

    /**
     * 递归构建树的ASCII艺术行
     */
    private static List<String> buildTreeLines(TreeNode root) {
        List<String> lines = new ArrayList<>();
        if (root == null) {
            return lines;
        }

        String rootStr = String.valueOf(root.val);

        // 获取左右子树的行
        List<String> leftLines = buildTreeLines(root.left);
        List<String> rightLines = buildTreeLines(root.right);

        // 如果是叶子节点
        if (leftLines.isEmpty() && rightLines.isEmpty()) {
            lines.add(rootStr);
            return lines;
        }

        // 计算根节点位置
        int leftWidth = leftLines.isEmpty() ? 0 : leftLines.get(0).length();
        int rightWidth = rightLines.isEmpty() ? 0 : rightLines.get(0).length();

        // 添加根节点
        int rootPos = leftWidth + (leftLines.isEmpty() ? 0 : 2);
        lines.add(" ".repeat(rootPos) + rootStr);

        // 添加分支线
        if (!leftLines.isEmpty() && !rightLines.isEmpty()) {
            int leftBranch = leftLines.get(0).indexOf(findFirstNonSpace(leftLines.get(0)));
            int rightBranch = leftWidth + 2;
            StringBuilder branch = new StringBuilder();
            branch.append(" ".repeat(leftBranch + 1));
            branch.append("╱");
            branch.append(" ".repeat(rootPos - leftBranch - 1));
            branch.append("╲");
            lines.add(branch.toString());
        } else if (!leftLines.isEmpty()) {
            lines.add(" ".repeat(rootPos) + "╱");
        } else {
            lines.add(" ".repeat(rootPos + rootStr.length()) + "╲");
        }

        // 合并左右子树的行
        int maxLines = Math.max(leftLines.size(), rightLines.size());
        for (int i = 0; i < maxLines; i++) {
            StringBuilder line = new StringBuilder();
            if (i < leftLines.size()) {
                line.append(leftLines.get(i));
            } else {
                line.append(" ".repeat(leftWidth));
            }

            if (!rightLines.isEmpty()) {
                line.append("  ");
                if (i < rightLines.size()) {
                    line.append(rightLines.get(i));
                }
            }
            lines.add(line.toString());
        }

        return lines;
    }

    /**
     * 查找字符串中第一个非空格字符
     */
    private static char findFirstNonSpace(String str) {
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                return c;
            }
        }
        return ' ';
    }

    /**
     * 生成递归层级的缩进（改进版，使用Unicode字符）
     *
     * @param depth 递归深度
     * @param isLast 是否是最后一个子节点
     * @return 缩进字符串
     */
    public static String getTreePrefix(int depth, boolean isLast) {
        if (depth == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth - 1; i++) {
            sb.append("│   ");
        }
        sb.append(isLast ? "└── " : "├── ");
        return sb.toString();
    }

    /**
     * 生成递归层级的缩进（简单版）
     *
     * @param depth 递归深度
     * @return 缩进字符串
     */
    public static String indent(int depth) {
        if (depth == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("│   ");
        }
        return sb.toString();
    }

    /**
     * 格式化节点信息
     *
     * @param node 树节点
     * @return 节点的字符串表示
     */
    public static String formatNode(TreeNode node) {
        return node == null ? "null" : String.valueOf(node.val);
    }

    /**
     * 打印执行摘要
     *
     * @param visitedCount 访问的节点数
     * @param maxDepth 最大递归深度
     * @param result 结果
     */
    public static String printSummary(int visitedCount, int maxDepth, Object result) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔═══════════════════════════════════════════\n");
        sb.append("║ 📊 执行摘要\n");
        sb.append("║\n");
        sb.append(String.format("║  • 访问节点数: %d\n", visitedCount));
        sb.append(String.format("║  • 最大递归深度: %d\n", maxDepth));
        sb.append(String.format("║  • 执行结果: %s\n", result));
        sb.append("╚═══════════════════════════════════════════");
        return sb.toString();
    }

    /**
     * 将树转换为层序遍历数组（用于验证）
     *
     * @param root 树的根节点
     * @return 层序遍历数组
     */
    public static List<Integer> toArray(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
            }
        }

        // 移除末尾的null
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    /**
     * 简化的树形显示（更好的对齐，支持null显示）
     * 使用层序遍历方式展示，每层一行
     *
     * @param root 树的根节点
     * @param showNull 是否显示null节点
     * @return 树的字符串表示
     */
    public static String printSimpleTree(TreeNode root, boolean showNull) {
        if (root == null) {
            return "  (空树)";
        }

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            sb.append("  ");  // 缩进

            boolean hasNonNull = false;
            List<String> nodeStrings = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    nodeStrings.add(ColorUtils.node(node.val));
                    queue.offer(node.left);
                    queue.offer(node.right);
                    hasNonNull = true;
                } else {
                    if (showNull) {
                        nodeStrings.add(ColorUtils.nullNode());
                    }
                    queue.offer(null);
                    queue.offer(null);
                }
            }

            if (!hasNonNull && level > 0) {
                break;  // 所有节点都是null，停止
            }

            sb.append(String.join("  ", nodeStrings));
            sb.append("\n");
            level++;
        }

        return sb.toString();
    }

    /**
     * 打印数组及当前指针位置
     *
     * @param name 数组名称
     * @param array 数组
     * @param pointer 当前指针位置
     * @return 格式化的字符串
     */
    public static String printArrayWithPointer(String name, int[] array, int pointer) {
        StringBuilder sb = new StringBuilder();

        // 数组名称
        sb.append(String.format("%s: [ ", name));

        // 数组元素
        for (int i = 0; i < array.length; i++) {
            if (i == pointer) {
                sb.append(ColorUtils.highlight(String.valueOf(array[i])));
            } else {
                sb.append(array[i]);
            }
            if (i < array.length - 1) {
                sb.append("  ");
            }
        }
        sb.append(" ]\n");

        // 索引行
        sb.append("索引: [ ");
        for (int i = 0; i < array.length; i++) {
            sb.append(i);
            if (i < array.length - 1) {
                sb.append("  ");
            }
        }
        sb.append(" ]\n");

        // 指针行
        if (pointer >= 0 && pointer < array.length) {
            sb.append("      ");
            int spaces = 0;
            for (int i = 0; i < pointer; i++) {
                spaces += String.valueOf(array[i]).length() + 2;
            }
            sb.append(" ".repeat(spaces));
            sb.append(ColorUtils.highlight("^"));
        }

        return sb.toString();
    }

    /**
     * 打印信息框
     *
     * @param title 标题
     * @param lines 内容行
     * @return 格式化的框
     */
    public static String printBox(String title, String... lines) {
        StringBuilder sb = new StringBuilder();

        // 计算最大宽度
        int maxWidth = title.length();
        for (String line : lines) {
            maxWidth = Math.max(maxWidth, line.length());
        }
        maxWidth += 4;  // 留边距

        // 顶部
        sb.append("┌");
        sb.append("─".repeat(maxWidth));
        sb.append("┐\n");

        // 标题
        if (!title.isEmpty()) {
            sb.append(String.format("│ %s", ColorUtils.phase(title)));
            sb.append(" ".repeat(maxWidth - title.length() - 1));
            sb.append("│\n");
            sb.append("├");
            sb.append("─".repeat(maxWidth));
            sb.append("┤\n");
        }

        // 内容
        for (String line : lines) {
            sb.append(String.format("│ %s", line));
            sb.append(" ".repeat(maxWidth - line.length() - 1));
            sb.append("│\n");
        }

        // 底部
        sb.append("└");
        sb.append("─".repeat(maxWidth));
        sb.append("┘");

        return sb.toString();
    }

    /**
     * 打印分隔线
     *
     * @param title 标题（可选）
     * @return 分隔线
     */
    public static String printSeparator(String title) {
        if (title == null || title.isEmpty()) {
            return "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
        }
        return String.format("━━━━━━━ %s ━━━━━━━", ColorUtils.phase(title));
    }

    /**
     * 打印空行（增强可读性）
     *
     * @param count 空行数量
     * @return 空行
     */
    public static String printEmptyLines(int count) {
        return "\n".repeat(Math.max(0, count));
    }
}
