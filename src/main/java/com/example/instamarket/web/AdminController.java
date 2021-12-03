package com.example.instamarket.web;

import com.example.instamarket.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/approve")
    public String showApproveSellerPage(Model model){
        model.addAttribute("requestsToApprove", adminService.sellerApprovalRequests());
        model.addAttribute("approvedRequests", adminService.approvedSellers());

        return "admin/approve";
    }

    @GetMapping("/approve/{requestId}")
    public String approveSeller(@PathVariable Long requestId){
        adminService.approveSeller(requestId);

        return "redirect:/admin/approve";
    }

    @GetMapping("/disapprove/{requestId}")
    public String disapproveSeller(@PathVariable Long requestId){
        adminService.disapproveSeller(requestId);

        return "redirect:/admin/approve";
    }
}
