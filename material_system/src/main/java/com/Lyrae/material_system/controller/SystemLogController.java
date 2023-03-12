package com.Lyrae.material_system.controller;

import com.Lyrae.material_system.domain.SystemLog;
import com.Lyrae.material_system.service.ISystemLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/systemLog")
public class SystemLogController {

    @Autowired
    private ISystemLogService systemLogService;

    @RequestMapping("/findAll")
    public PageInfo findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "5") Integer size){
        PageInfo pageInfo = null;
        try {
            List<SystemLog> systemLogList = systemLogService.findAll(page,size);
            pageInfo = new PageInfo(systemLogList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageInfo;
    }

    @RequestMapping("/saveEmail")
    public void saveEmail(String email){
        try{
            systemLogService.saveEmail(email);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/getEmail")
    public String saveEmail(){
        String email = null;
        try {
            email = systemLogService.getEmail();
        }catch (Exception e){
            e.printStackTrace();
        }
        return email;
    }
}
