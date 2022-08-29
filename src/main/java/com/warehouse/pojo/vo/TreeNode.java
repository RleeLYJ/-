package com.warehouse.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private Integer id; //当前节点
    private Integer parentId;
    private String title;
    private Boolean spread; //是否展开

    /**
     * 0为不选中  1为选中
     */
    private String checkArr = "0";

    private String icon;
    private String href;
    List<TreeNode> children=new ArrayList<TreeNode>();

    // (id,pid,title,icon,href,spread)
    public TreeNode(Integer id, Integer parentId, String title, String icon, String href, Boolean spread) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.spread = spread;
        this.icon = icon;
        this.href = href;
    }

    public TreeNode(Integer id, Integer parentId, String title, boolean spread, String checkArr) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }

    public TreeNode(Integer id, Integer parentId, String title, boolean spread) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.spread = spread;
    }
}
