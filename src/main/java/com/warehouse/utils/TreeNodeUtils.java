package com.warehouse.utils;

import com.warehouse.pojo.vo.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeUtils {

    public static List<TreeNode> build(List<TreeNode> treeNodes, Integer topPid) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for (TreeNode n1 : treeNodes) {
            if (n1.getParentId() == topPid) {
                nodes.add(n1);
            }
            for (TreeNode n2 : treeNodes) {
                if (n1.getId() == n2.getParentId()) {
                    n1.getChildren().add(n2);
                }
            }
        }
        return nodes;
    }
}

