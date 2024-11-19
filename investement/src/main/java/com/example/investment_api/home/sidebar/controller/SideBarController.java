package com.example.investment_api.home.sidebar.controller;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.home.sidebar.controller.dto.SideBarAccountCount;
import com.example.investment_api.home.sidebar.controller.dto.SideBarAccountDTO;
import com.example.investment_api.home.sidebar.controller.dto.SideBarDTO;
import com.example.investment_api.home.sidebar.service.SideBarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/sidebar")
public class SideBarController {

    private final SideBarService sideBarService;

    public SideBarController(final SideBarService sideBarService) {
        this.sideBarService = sideBarService;
    }

    @GetMapping("/myStock")
    public List<SideBarDTO> sendSideBarDATA(@Member Long memberId) {
        return sideBarService.getAccount(memberId);
    }

    @GetMapping("/myStockCount")
    public SideBarAccountCount sendSideBarCount(@Member Long memberId) {
        return sideBarService.sendStockCount(memberId);
    }

    @GetMapping("/asset")
    public SideBarAccountDTO sendAsst(@Member Long memberId) {
        return sideBarService.sendAsset(memberId);
    }

}
