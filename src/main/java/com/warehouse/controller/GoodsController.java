package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.*;
import com.warehouse.pojo.vo.GoodsVo;
import com.warehouse.pojo.vo.InportVo;
import com.warehouse.service.*;
import com.warehouse.utils.AppFileUtils;
import com.warehouse.utils.PageUtils;
import com.warehouse.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sachen
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService igs;


    @RequestMapping("/loadAllGoodsForSelect")
    public DataResults loadAllProviderForSelect() {
        QueryWrapper wrapper = new QueryWrapper<>();
        return DataResults.success(ResultCode.SUCCESS, igs.findAllGoodsVo(wrapper));
    }


    @RequestMapping("/loadGoodsByProviderId")
    public DataResults loadGoodsByProviderId(String providerid) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(providerid), "providerid", providerid);
        return DataResults.success(ResultCode.SUCCESS, igs.findAllGoodsVo(wrapper));
    }

    @BehaviorLog("查看商品")
    @PreAuthorize("hasAnyAuthority('goods:view')")
    @RequestMapping("/loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(goodsVo.getProviderid() != null && goodsVo.getProviderid() != 0, "bus_goods.providerid", goodsVo.getProviderid());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getGoodsname()), "bus_goods.goodsname", goodsVo.getGoodsname());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getProductcode()), "bus_goods.productcode", goodsVo.getProductcode());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getPromitcode()), "bus_goods.promitcode", goodsVo.getPromitcode());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getDescription()), "bus_goods.description", goodsVo.getDescription());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getSize()), "bus_goods.size", goodsVo.getSize());
        wrapper.orderByAsc("bus_goods.id");
        List<GoodsVo> goodsVos = igs.findAllGoodsVo(wrapper);
        List<GoodsVo> pageList = PageUtils.getPageList(goodsVos, goodsVo.getPage(), goodsVo.getLimit());
        return new DataGridView((long) goodsVos.size(), pageList);
    }

    @BehaviorLog("新增商品")
    @PreAuthorize("hasAnyAuthority('goods:create')")
    @RequestMapping("/addGoods")
    public DataResults addGoods(Goods goods) {
        return ResultUtils.simpleResult(igs.save(goods));
    }

    @BehaviorLog("修改商品")
    @PreAuthorize("hasAnyAuthority('goods:update')")
    @RequestMapping("/updateGoods")
    public DataResults updateGoods(Goods goods) {
        String oldpath = igs.getById(goods.getId()).getGoodsimg();
        if (!oldpath.equals(goods.getGoodsimg())) {
            //更新了图片
            AppFileUtils.removeFileByPath(oldpath);
        }
        return ResultUtils.simpleResult(igs.updateById(goods));
    }

    @BehaviorLog("删除商品")
    @PreAuthorize("hasAnyAuthority('goods:delete')")
    @RequestMapping("/deleteGoods")
    public DataResults deleteGoods(String id) {
        Goods goods = igs.getById(id);
        AppFileUtils.removeFileByPath(goods.getGoodsimg());
        return ResultUtils.simpleResult(igs.deleteGoodsById(id));
    }

    @BehaviorLog("批量删除商品")
    @PreAuthorize("hasAnyAuthority('goods:batchdelete')")
    @RequestMapping("/batchDeleteGoods")
    public DataResults batchDeleteGoods(String[] ids) {
        List<Goods> goodsList = igs.findGoodsByIds(ids);
        for (Goods goods : goodsList) {
            AppFileUtils.removeFileByPath(goods.getGoodsimg());
        }
        return ResultUtils.simpleResult(igs.batchDeleteGoodsByIds(ids));
    }

    @BehaviorLog("查看缺货商品")
    @PreAuthorize("hasAnyAuthority('goods:view')")
    @RequestMapping("/loadAllWarningGoods")
    public DataGridView loadAllWarningGoods(GoodsVo goodsVo) {
        QueryWrapper wrapper = new QueryWrapper<Goods>();
        wrapper.apply("number < dangernum");
        wrapper.eq("available", 1);
        wrapper.orderByAsc("id");
        List<Goods> voList = igs.findAllWarningGoods(wrapper);
        List<Goods> pageList = PageUtils.getPageList(voList, goodsVo.getPage(), goodsVo.getLimit());
        return new DataGridView((long) voList.size(), pageList);

    }

}