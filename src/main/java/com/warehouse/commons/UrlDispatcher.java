package com.warehouse.commons;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UrlDispatcher {

    @PreAuthorize("hasAnyAuthority('notice:view')")
    @GetMapping("/notice/topage")
    public String toLoadAllNotice() {
        return "system/notice/noticeManager";
    }

    @PreAuthorize("hasAnyAuthority('role:view')")
    @GetMapping("/role/topage")
    public String toLoadAllRole() {
        return "system/role/roleManager";
    }

    @PreAuthorize("hasAnyAuthority('customer:view')")
    @GetMapping("/customer/topage")
    public String toLoadAllCustomer() {
        return "business/customer/customerManager";
    }

    @PreAuthorize("hasAnyAuthority('goods:view')")
    @GetMapping("/goods/topage")
    public String toLoadAllGoods() {
        return "business/goods/goodsManager";
    }

    @PreAuthorize("hasAnyAuthority('provider:view')")
    @GetMapping("/provider/topage")
    public String toLoadAllProvider() {
        return "business/provider/providerManager";
    }

    @PreAuthorize("hasAnyAuthority('inport:view')")
    @GetMapping("/inport/topage")
    public String toLoadAllInport() {
        return "business/inport/inportManager";
    }

    @PreAuthorize("hasAnyAuthority('outport:view')")
    @GetMapping("/outport/topage")
    public String toLoadAllOutport() {
        return "business/outport/outportManager";
    }

    @PreAuthorize("hasAnyAuthority('sales:view')")
    @GetMapping("/sales/topage")
    public String toLoadAllSales() {
        return "business/sales/salesManager";
    }

    @PreAuthorize("hasAnyAuthority('salesback:view')")
    @GetMapping("/salesback/topage")
    public String toLoadAllSalesback() {
        return "business/salesback/salesbackManager";
    }

    @PreAuthorize("hasAnyAuthority('icon:view')")
    @GetMapping("/icon/topage")
    public String toLoadAllIcon() {
        return "system/icon/icon";
    }

    @PreAuthorize("hasAnyAuthority('loginfo:view')")
    @GetMapping("/loginfo/topage")
    public String toLoadAllLoginfo() {
        return "system/loginfo/loginfoManager";
    }

    @GetMapping("/sys/toDeptLeft")
    public String todeptLeft() {
        return "system/dept/deptLeft";
    }

    @GetMapping("/sys/toDeptRight")
    public String todeptRight() {
        return "system/dept/deptRight";
    }

    @PreAuthorize("hasAnyAuthority('dept:view')")
    @GetMapping("/dept/topage")
    public String todeptManager() {
        return "system/dept/deptManager";
    }

    @GetMapping("/sys/toMenuLeft")
    public String toMenuLeft() {
        return "system/menu/menuLeft";
    }

    @GetMapping("/sys/toMenuRight")
    public String toMenuRight() {
        return "system/menu/menuRight";
    }

    @PreAuthorize("hasAnyAuthority('menu:view')")
    @GetMapping("/menu/topage")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }

    @GetMapping("/sys/toPermissionLeft")
    public String toPermissionLeft() {
        return "system/permission/permissionLeft";
    }

    @GetMapping("/sys/toPermissionRight")
    public String toPermissionRight() {
        return "system/permission/permissionRight";
    }

    @PreAuthorize("hasAnyAuthority('permission:view')")
    @GetMapping("/permission/topage")
    public String toPermissionManager() {
        return "system/permission/permissionManager";
    }

    @PreAuthorize("hasAnyAuthority('user:view')")
    @GetMapping("/user/topage")
    public String toUserManager() {
        return "system/user/userManager";
    }

    @PreAuthorize("hasAnyAuthority('behaviorlog:view')")
    @GetMapping("/behaviorlog/topage")
    public String toBehaviorlog() {
        return "system/behaviorlog/behaviorlogManager";
    }

    @GetMapping("/sys/index")
    public String toIndex() {
        return "system/index/index";
    }

    @GetMapping("/sys/toDeskManager")
    public String toDeskManager() {
        return "system/index/deskManager";
    }

    @GetMapping("/")
    public String toLogin() {
        return "system/index/login";
    }

    @GetMapping("/sys/toUserInfo")
    public String toUserInfo() {
        return "system/user/userInfo";
    }

    @GetMapping("/sys/toChangePassword")
    public String toChangePassword() {
        return "system/user/changePassword";
    }
    @GetMapping("/error/403")
    public String toError403() {
        return "system/error/403";
    }
    @GetMapping("/error/404")
    public String toError404() {
        return "system/error/404";
    }

    @GetMapping("/error/500")
    public String toError500() {
        return "system/error/500";
    }

}
