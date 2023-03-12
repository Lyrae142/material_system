package com.Lyrae.material_system.controller;

import com.Lyrae.material_system.domain.Permission;
import com.Lyrae.material_system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/permissions")
@ResponseBody
public class PermissionsController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/findAllByPermissionName")
    public List<Permission> findAllByPermissionName(String permissionName){
        List<Permission> permissionList = null;
        try {
            permissionList = permissionService.findAllByPermissionName(permissionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionList;
    }

    @RequestMapping("/deletePermission")
    public void deletePermission(Integer id){
        try{
            permissionService.deletePermission(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/deletePermissions")
    public void deletePermissions(@RequestBody Integer[] ids){
        try {
            permissionService.deletePermissions(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/addPermission")
    public void addPermission(@RequestBody Permission permission){
        try{
            permissionService.addPermission(permission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/findAll")
    public List<Permission> findAll(){
        List<Permission> permissionList = null;
        try {
            permissionList = permissionService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionList;
    }
}
