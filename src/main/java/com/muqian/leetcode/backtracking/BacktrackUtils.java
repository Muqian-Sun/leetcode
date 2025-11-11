package com.muqian.leetcode.backtracking;

import com.muqian.leetcode.tree.ColorUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 回溯算法可视化工具类
 * 提供决策树可视化、路径显示等辅助功能
 *
 * @author muqian
 */
public class BacktrackUtils {

    /**
     * 显示当前决策树节点状态
     *
     * @param depth 当前深度
     * @param path 当前路径
     * @param choices 当前可选择列表
     * @param action 当前动作描述
     */
    public static String printTreeNode(int depth, Object path, Object choices, String action) {
        StringBuilder sb = new StringBuilder();
        String indent = "  ".repeat(depth);

        sb.append(indent).append("┌─ 决策树节点\n");
        sb.append(indent).append("│  深度: ").append(ColorUtils.info(String.valueOf(depth))).append("\n");
        sb.append(indent).append("│  路径: ").append(ColorUtils.highlight(path.toString())).append("\n");
        sb.append(indent).append("│  可选: ").append(ColorUtils.info(choices.toString())).append("\n");
        sb.append(indent).append("│  动作: ").append(action).append("\n");
        sb.append(indent).append("└─");

        return sb.toString();
    }

    /**
     * 显示紧凑的决策树节点
     *
     * @param depth 深度
     * @param path 路径
     * @param current 当前选择
     */
    public static String printCompactNode(int depth, Object path, Object current) {
        String indent = "  ".repeat(depth);
        return String.format("%s[深度:%s] 路径:%s 选择:%s",
                indent,
                ColorUtils.info(String.valueOf(depth)),
                ColorUtils.highlight(path.toString()),
                ColorUtils.node(current));
    }

    /**
     * 打印路径树（显示从根到当前节点的路径）
     *
     * @param path 当前路径
     * @param depth 当前深度
     * @return 路径树字符串
     */
    public static String printPathTree(List<?> path, int depth) {
        if (path.isEmpty()) {
            return "    []";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            String indent = "    " + "  ".repeat(i);
            sb.append(indent);

            if (i == path.size() - 1) {
                // 当前节点
                sb.append("└─ ").append(ColorUtils.node(path.get(i))).append(" ← 当前");
            } else {
                // 父节点
                sb.append("└─ ").append(path.get(i));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 打印当前位置在决策树中的状态
     *
     * @param stepNum 步骤编号
     * @param depth 深度
     * @param path 路径
     * @param choices 可选项
     */
    public static void printDecisionTreeState(int stepNum, int depth, List<?> path, Object choices) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "╔═══ " + ColorUtils.step(stepNum) + " ═══");
        System.out.println(indent + "║ 深度: " + depth);
        System.out.println(indent + "║ 当前路径: " + ColorUtils.highlight(path.toString()));
        System.out.println(indent + "║ 可选列表: " + ColorUtils.info(choices.toString()));
        System.out.println(indent + "╚═══");
    }

    /**
     * 格式化选择列表
     */
    public static String formatChoices(int[] arr, int start) {
        if (start >= arr.length) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = start; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 格式化选择列表（带范围）
     */
    public static String formatChoices(int start, int end) {
        if (start > end) return "[]";
        return String.format("[%d..%d]", start, end);
    }

    /**
     * 打印完整的决策树（树形图形化输出）
     *
     * @param root 决策树的根节点
     * @param title 标题
     */
    public static void printDecisionTree(DecisionTreeNode root, String title) {
        if (root == null) {
            System.out.println("决策树为空");
            return;
        }

        // 打印标题
        System.out.println("\n" + ColorUtils.phase("╔═══════════════════════════════════════════"));
        System.out.println(ColorUtils.phase("║ " + title));
        System.out.println(ColorUtils.phase("╚═══════════════════════════════════════════"));
        System.out.println();

        // 打印树形结构
        printTreeStructure(root, "", true, true);

        // 打印统计信息
        int[] stats = calculateTreeStats(root);
        System.out.println("\n" + ColorUtils.info("统计信息:"));
        System.out.println("  • 总节点数: " + ColorUtils.node(String.valueOf(stats[0])));
        System.out.println("  • 叶子节点: " + ColorUtils.success(String.valueOf(stats[1])));
        System.out.println("  • 剪枝节点: " + ColorUtils.error(String.valueOf(stats[2])));
        System.out.println("  • 最大深度: " + ColorUtils.highlight(String.valueOf(stats[3])));
        System.out.println();
    }

    /**
     * 递归打印树形结构（ASCII艺术风格）
     *
     * @param node 当前节点
     * @param prefix 前缀字符串
     * @param isTail 是否为最后一个子节点
     * @param isRoot 是否为根节点
     */
    private static void printTreeStructure(DecisionTreeNode node, String prefix, boolean isTail, boolean isRoot) {
        if (node == null) return;

        // 打印当前节点
        String nodeText = formatNodeText(node);
        String connector = isRoot ? "" : (isTail ? "└── " : "├── ");
        System.out.println(prefix + connector + nodeText);

        // 计算子节点的前缀
        String childPrefix = isRoot ? "" : (prefix + (isTail ? "    " : "│   "));

        // 递归打印子节点
        List<DecisionTreeNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            boolean isLastChild = (i == children.size() - 1);
            printTreeStructure(children.get(i), childPrefix, isLastChild, false);
        }
    }

    /**
     * 打印纵向树形结构（包含详细节点信息）
     *
     * @param node 当前节点
     * @param prefix 前缀字符串
     * @param isLast 是否为最后一个子节点
     */
    private static void printVerticalTreeStructure(DecisionTreeNode node, String prefix, boolean isLast) {
        if (node == null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + ColorUtils.nullNode());
            return;
        }

        // 构建节点显示文本
        StringBuilder sb = new StringBuilder();

        // 获取基本显示文本
        String displayText = node.getDisplayText();
        String coloredText;
        switch (node.getType()) {
            case LEAF:
                coloredText = ColorUtils.success(displayText);
                break;
            case PRUNED:
                coloredText = ColorUtils.error(displayText);
                break;
            default:
                coloredText = ColorUtils.node(displayText);
                break;
        }

        sb.append(coloredText);

        // 添加状态标记
        String marker = node.getStatusMarker();
        if (!marker.isEmpty()) {
            sb.append(marker);
        }

        // 添加选择信息
        if (node.getChoice() != null && node.getDepth() > 0) {
            sb.append(" ");
            sb.append(ColorUtils.info("选:" + node.getChoice()));
        }

        // 添加深度信息
        if (node.getDepth() > 0) {
            sb.append(" ");
            sb.append(ColorUtils.dim("(深度" + node.getDepth() + ")"));
        }

        // 添加描述信息
        if (node.getDescription() != null && !node.getDescription().isEmpty()
                && !node.getDescription().equals("开始")) {
            sb.append(" ");
            sb.append(ColorUtils.info("[" + node.getDescription() + "]"));
        }

        // 打印当前节点
        System.out.println(prefix + (isLast ? "└── " : "├── ") + sb.toString());

        // 递归打印子节点
        List<DecisionTreeNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            String newPrefix = prefix + (isLast ? "    " : "│   ");
            printVerticalTreeStructure(children.get(i), newPrefix, i == children.size() - 1);
        }
    }

    /**
     * 格式化节点文本（带颜色和状态标记）
     *
     * @param node 节点
     * @return 格式化后的文本
     */
    private static String formatNodeText(DecisionTreeNode node) {
        String displayText = node.getDisplayText();
        String marker = node.getStatusMarker();

        // 根据节点类型添加颜色
        String coloredText;
        switch (node.getType()) {
            case LEAF:
                coloredText = ColorUtils.success(displayText);
                break;
            case PRUNED:
                coloredText = ColorUtils.error(displayText);
                break;
            default:
                coloredText = ColorUtils.node(displayText);
                break;
        }

        // 添加选择的元素信息（如果有）
        String choiceInfo = "";
        if (node.getChoice() != null) {
            choiceInfo = " (选择: " + ColorUtils.highlight(node.getChoice().toString()) + ")";
        }

        // 添加描述信息（如果有）
        String descInfo = "";
        if (node.getDescription() != null && !node.getDescription().isEmpty()) {
            descInfo = " " + ColorUtils.info("[" + node.getDescription() + "]");
        }

        return coloredText + choiceInfo + descInfo + (marker.isEmpty() ? "" : " " + marker);
    }

    /**
     * 计算决策树的统计信息
     *
     * @param root 根节点
     * @return [总节点数, 叶子节点数, 剪枝节点数, 最大深度]
     */
    private static int[] calculateTreeStats(DecisionTreeNode root) {
        int[] stats = new int[4]; // [total, leaf, pruned, maxDepth]
        calculateStatsRecursive(root, stats);
        return stats;
    }

    /**
     * 递归计算统计信息
     */
    private static void calculateStatsRecursive(DecisionTreeNode node, int[] stats) {
        if (node == null) return;

        // 总节点数
        stats[0]++;

        // 更新最大深度
        stats[3] = Math.max(stats[3], node.getDepth());

        // 统计节点类型
        if (node.getType() == DecisionTreeNode.NodeType.LEAF) {
            stats[1]++;
        } else if (node.getType() == DecisionTreeNode.NodeType.PRUNED) {
            stats[2]++;
        }

        // 递归处理子节点
        for (DecisionTreeNode child : node.getChildren()) {
            calculateStatsRecursive(child, stats);
        }
    }

    /**
     * 打印简化版决策树（水平展开，适合小规模树）
     *
     * @param root 根节点
     */
    public static void printCompactDecisionTree(DecisionTreeNode root) {
        if (root == null) return;
        printCompactRecursive(root, 0);
    }

    /**
     * 递归打印简化版决策树
     */
    private static void printCompactRecursive(DecisionTreeNode node, int level) {
        if (node == null) return;

        String indent = "  ".repeat(level);
        String arrow = level > 0 ? "→ " : "";
        System.out.println(indent + arrow + formatNodeText(node));

        for (DecisionTreeNode child : node.getChildren()) {
            printCompactRecursive(child, level + 1);
        }
    }

    /**
     * 打印纵向树形图（传统教科书风格：根在上，分支向下）
     * 使用tree-printer库实现专业的树形可视化
     *
     * @param root 决策树的根节点
     * @param title 标题
     */
    public static void printDecisionTreeVertical(DecisionTreeNode root, String title) {
        if (root == null) {
            System.out.println("决策树为空");
            return;
        }

        // 打印标题
        System.out.println("\n" + ColorUtils.phase("╔═══════════════════════════════════════════════════════════════════"));
        System.out.println(ColorUtils.phase("║ " + title));
        System.out.println(ColorUtils.phase("╠═══════════════════════════════════════════════════════════════════"));
        System.out.println(ColorUtils.phase("║ 纵向决策树展示（从上到下：根→分支→叶子）"));
        System.out.println(ColorUtils.phase("╚═══════════════════════════════════════════════════════════════════"));
        System.out.println();

        // 打印图例
        System.out.println(ColorUtils.info("【图例】"));
        System.out.println("  " + ColorUtils.node("[]") + " = 路径状态");
        System.out.println("  " + ColorUtils.success("绿色✓") + " = 找到有效结果");
        System.out.println("  " + ColorUtils.error("红色✂") + " = 剪枝终止");
        System.out.println("  " + ColorUtils.info("选:X") + " = 当前步骤的选择");
        System.out.println("  " + ColorUtils.dim("(深度N)") + " = 递归深度");
        System.out.println();

        // 使用自定义ASCII树渲染纵向树（根在上，分支向下）
        System.out.println(ColorUtils.phase("【决策树结构】"));
        Map<DecisionTreeNode, NodePosition> layout = calculateTreeLayout(root);
        renderVerticalTree(root, layout);

        // 打印统计信息
        int[] stats = calculateTreeStats(root);
        System.out.println("\n" + ColorUtils.phase("【统计信息】"));
        System.out.println("  • 总节点数: " + ColorUtils.node(String.valueOf(stats[0])) +
                " " + ColorUtils.dim("(包括根节点、内部节点和叶子节点)"));
        System.out.println("  • 叶子节点: " + ColorUtils.success(String.valueOf(stats[1])) +
                " " + ColorUtils.dim("(找到的有效解)"));
        System.out.println("  • 剪枝节点: " + ColorUtils.error(String.valueOf(stats[2])) +
                " " + ColorUtils.dim("(被提前终止的分支)"));
        System.out.println("  • 最大深度: " + ColorUtils.highlight(String.valueOf(stats[3])) +
                " " + ColorUtils.dim("(从根到最深叶子的距离)"));

        // 计算分支因子
        double branchingFactor = stats[0] > 1 ? (double)(stats[0] - stats[1]) / (stats[0] - stats[1] - stats[2] + 1) : 0;
        System.out.println("  • 平均分支因子: " + ColorUtils.info(String.format("%.2f", branchingFactor)) +
                " " + ColorUtils.dim("(每个非叶节点的平均子节点数)"));

        // 剪枝效率
        if (stats[0] > 1) {
            double pruningRate = (double)stats[2] / stats[0] * 100;
            System.out.println("  • 剪枝率: " + ColorUtils.info(String.format("%.1f%%", pruningRate)) +
                    " " + ColorUtils.dim("(被剪枝的节点占比)"));
        }

        System.out.println();
    }

    /**
     * 节点位置信息
     */
    private static class NodePosition {
        int x;  // 水平位置
        int y;  // 垂直位置（深度）
        int subtreeWidth;  // 子树宽度

        NodePosition(int x, int y, int subtreeWidth) {
            this.x = x;
            this.y = y;
            this.subtreeWidth = subtreeWidth;
        }
    }

    /**
     * 计算树的布局（每个节点的位置）
     */
    private static Map<DecisionTreeNode, NodePosition> calculateTreeLayout(DecisionTreeNode root) {
        Map<DecisionTreeNode, NodePosition> layout = new HashMap<>();
        calculateSubtreeWidth(root);
        assignPositions(root, 0, 0, layout);
        return layout;
    }

    /**
     * 计算子树宽度（后序遍历）
     */
    private static int calculateSubtreeWidth(DecisionTreeNode node) {
        if (node == null) return 0;

        List<DecisionTreeNode> children = node.getChildren();
        if (children.isEmpty()) {
            return 1;  // 叶子节点宽度为1
        }

        int totalWidth = 0;
        for (DecisionTreeNode child : children) {
            totalWidth += calculateSubtreeWidth(child);
        }
        return Math.max(1, totalWidth);
    }

    /**
     * 分配节点位置
     */
    private static void assignPositions(DecisionTreeNode node, int depth, int leftBound,
                                       Map<DecisionTreeNode, NodePosition> layout) {
        if (node == null) return;

        List<DecisionTreeNode> children = node.getChildren();
        int subtreeWidth = calculateSubtreeWidth(node);

        if (children.isEmpty()) {
            // 叶子节点：直接放在左边界
            layout.put(node, new NodePosition(leftBound, depth, 1));
        } else {
            // 非叶子节点：计算子节点位置，然后放在子节点中间
            int currentX = leftBound;
            int minChildX = Integer.MAX_VALUE;
            int maxChildX = Integer.MIN_VALUE;

            for (DecisionTreeNode child : children) {
                int childWidth = calculateSubtreeWidth(child);
                assignPositions(child, depth + 1, currentX, layout);

                NodePosition childPos = layout.get(child);
                minChildX = Math.min(minChildX, childPos.x);
                maxChildX = Math.max(maxChildX, childPos.x);

                currentX += childWidth;
            }

            // 父节点位于子节点中间
            int parentX = (minChildX + maxChildX) / 2;
            layout.put(node, new NodePosition(parentX, depth, subtreeWidth));
        }
    }

    /**
     * 渲染纵向树形图
     */
    private static void renderVerticalTree(DecisionTreeNode root, Map<DecisionTreeNode, NodePosition> layout) {
        // 按层收集节点
        Map<Integer, List<DecisionTreeNode>> layers = new HashMap<>();
        collectLayers(root, layout, layers);

        int maxDepth = layers.keySet().stream().max(Integer::compareTo).orElse(0);

        // 逐层渲染
        for (int depth = 0; depth <= maxDepth; depth++) {
            List<DecisionTreeNode> nodesAtDepth = layers.getOrDefault(depth, new ArrayList<>());
            if (nodesAtDepth.isEmpty()) continue;

            // 渲染节点层
            renderNodeLayer(nodesAtDepth, layout);

            // 渲染连接线层（除了最后一层）
            if (depth < maxDepth) {
                renderConnectionLayer(nodesAtDepth, layout);
            }
        }
    }

    /**
     * 按层收集节点
     */
    private static void collectLayers(DecisionTreeNode node, Map<DecisionTreeNode, NodePosition> layout,
                                     Map<Integer, List<DecisionTreeNode>> layers) {
        if (node == null) return;

        NodePosition pos = layout.get(node);
        layers.computeIfAbsent(pos.y, k -> new ArrayList<>()).add(node);

        for (DecisionTreeNode child : node.getChildren()) {
            collectLayers(child, layout, layers);
        }
    }

    /**
     * 渲染节点层
     */
    private static void renderNodeLayer(List<DecisionTreeNode> nodes, Map<DecisionTreeNode, NodePosition> layout) {
        if (nodes.isEmpty()) return;

        // 找到最大x坐标
        int maxX = nodes.stream()
                .map(layout::get)
                .mapToInt(pos -> pos.x)
                .max()
                .orElse(0);

        // 创建输出行
        StringBuilder line = new StringBuilder();
        int currentX = 0;

        // 按x坐标排序节点
        nodes.sort((a, b) -> layout.get(a).x - layout.get(b).x);

        for (DecisionTreeNode node : nodes) {
            NodePosition pos = layout.get(node);
            String nodeText = getShortNodeText(node);

            // 添加空格直到节点位置
            int nodeWidth = nodeText.length();
            int targetX = pos.x * 8;  // 每个单位8个字符宽度
            while (line.length() < targetX) {
                line.append(" ");
            }

            // 添加节点文本（居中）
            line.append(nodeText);
        }

        System.out.println(line.toString());
    }

    /**
     * 渲染连接线层
     */
    private static void renderConnectionLayer(List<DecisionTreeNode> parentNodes,
                                              Map<DecisionTreeNode, NodePosition> layout) {
        if (parentNodes.isEmpty()) return;

        // 收集所有需要画连接线的父节点
        List<DecisionTreeNode> parentsWithChildren = parentNodes.stream()
                .filter(node -> !node.getChildren().isEmpty())
                .collect(Collectors.toList());

        if (parentsWithChildren.isEmpty()) return;

        // 找到最大x坐标
        int maxX = 0;
        for (DecisionTreeNode parent : parentsWithChildren) {
            for (DecisionTreeNode child : parent.getChildren()) {
                NodePosition childPos = layout.get(child);
                maxX = Math.max(maxX, childPos.x);
            }
        }

        // 创建连接线
        char[] line = new char[(maxX + 1) * 8];
        Arrays.fill(line, ' ');

        for (DecisionTreeNode parent : parentsWithChildren) {
            List<DecisionTreeNode> children = parent.getChildren();
            if (children.isEmpty()) continue;

            NodePosition parentPos = layout.get(parent);
            int parentX = parentPos.x * 8 + 3;  // 调整到节点中心

            if (children.size() == 1) {
                // 单个子节点：画一条直线
                line[parentX] = '│';
            } else {
                // 多个子节点：画分叉
                NodePosition firstChildPos = layout.get(children.get(0));
                NodePosition lastChildPos = layout.get(children.get(children.size() - 1));

                int leftX = firstChildPos.x * 8 + 3;
                int rightX = lastChildPos.x * 8 + 3;

                // 画横线
                for (int x = leftX; x <= rightX; x++) {
                    if (line[x] == ' ') {
                        line[x] = '─';
                    }
                }

                // 画分叉点
                line[leftX] = '┌';
                line[rightX] = '┐';
                line[parentX] = '┴';

                // 画子节点连接点
                for (int i = 1; i < children.size() - 1; i++) {
                    NodePosition childPos = layout.get(children.get(i));
                    int childX = childPos.x * 8 + 3;
                    line[childX] = '┬';
                }
            }
        }

        System.out.println(new String(line).replaceAll(" +$", ""));
    }

    /**
     * 获取节点的简短文本表示
     */
    private static String getShortNodeText(DecisionTreeNode node) {
        String displayText = node.getDisplayText();
        String marker = node.getStatusMarker();

        // 根据节点类型添加颜色
        String coloredText;
        switch (node.getType()) {
            case LEAF:
                coloredText = ColorUtils.success(displayText);
                break;
            case PRUNED:
                coloredText = ColorUtils.error(displayText);
                break;
            default:
                coloredText = ColorUtils.node(displayText);
                break;
        }

        return coloredText + marker;
    }
}
