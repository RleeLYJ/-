package com.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataGridView;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Notice;
import com.warehouse.pojo.User;
import com.warehouse.pojo.vo.NoticeVo;
import com.warehouse.service.INoticeService;
import com.warehouse.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sachen
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    INoticeService ins;

    @BehaviorLog("查看公告")
    @PreAuthorize("hasAnyAuthority('notice:view')")
    @RequestMapping("/loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        //模糊查询
        wrapper.like(StringUtils.isNotEmpty(noticeVo.getTitle()), "title", noticeVo.getTitle());
        wrapper.like(StringUtils.isNotEmpty(noticeVo.getOpername()), "opername", noticeVo.getOpername());
        wrapper.le(StringUtils.isNotEmpty(noticeVo.getEndTime()), "createtime", noticeVo.getEndTime());
        wrapper.ge(StringUtils.isNotEmpty(noticeVo.getStartTime()), "createtime", noticeVo.getStartTime());
        //mybatis分页插件
        IPage<Notice> page = ins.page(new Page<>(noticeVo.getPage(), noticeVo.getLimit()), wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @BehaviorLog("增加公告")
    @PreAuthorize("hasAnyAuthority('notice:create')")
    @RequestMapping("/addNotice")
    public DataResults addNotice(Notice notice, HttpServletRequest request) {
        notice.setCreatetime(TimeUtils.getCurrentTime());
        if (!StringUtils.isNotEmpty(notice.getOpername())) {
            User user = (User) request.getSession().getAttribute("user");
            notice.setOpername(user.getName());
        }
        return ResultUtils.simpleResult(ins.save(notice));
    }

    @BehaviorLog("删除公告")
    @PreAuthorize("hasAnyAuthority('notice:delete')")
    @RequestMapping("/deleteNotice")
    public DataResults deleteNotice(String id) {
        return ResultUtils.simpleResult(ins.removeById(id));
    }

    @BehaviorLog("修改公告")
    @PreAuthorize("hasAnyAuthority('notice:update')")
    @RequestMapping("/updateNotice")
    public DataResults updateNotice(Notice notice) {
        return ResultUtils.simpleResult(ins.updateById(notice));
    }

    @BehaviorLog("批量删除公告")
    @PreAuthorize("hasAnyAuthority('notice:batchdelete')")
    @RequestMapping("/batchDeleteNotice")
    public DataResults batchDeleteNotice(String[] ids) {
        return ResultUtils.simpleResult(ins.removeByIds(Arrays.asList(ids)));
    }

    @BehaviorLog("查看公告内容")
    @PreAuthorize("hasAnyAuthority('notice:viewnotice')")
    @RequestMapping("/loadNoticeById")
    public DataResults loadNoticeById(String id) {
        return DataResults.success(ResultCode.SUCCESS, ins.getById(id));
    }
}
