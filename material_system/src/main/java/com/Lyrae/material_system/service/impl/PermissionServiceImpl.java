package com.Lyrae.material_system.service.impl;

import com.Lyrae.material_system.dao.IPermissionDao;
import com.Lyrae.material_system.domain.Permission;
import com.Lyrae.material_system.domain.Role;
import com.Lyrae.material_system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements IPermissionService, FilterInvocationSecurityMetadataSource {

    @Autowired
    private IPermissionDao permissionDao;

    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }

    @Override
    public void addPermission(Permission permission) throws Exception {
        permissionDao.addPermission(permission);
    }

    @Override
    public List<Permission> findAllByPermissionName(String permissionName) throws Exception {
        return permissionDao.findAllByPermissionName(permissionName);
    }

    @Override
    public void deletePermission(Integer id) throws Exception {
        permissionDao.deletePermission(id);
    }

    @Override
    public void deletePermissions(Integer[] ids) throws Exception {
        permissionDao.deletePermissions(ids);
    }

    @Override
    public List<Role> getRoleByUrl(String url) throws Exception {
        return permissionDao.getRoleByUrl(url);
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取当前的URL地址
        System.out.println("---------进入拦截---------");
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String url = filterInvocation.getRequest().getServletPath();
        System.out.println("访问路径为:"+url);
        List<Role> roleList = new ArrayList<Role>();//调用自己实现的方法来url
        try{
            roleList = getRoleByUrl(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(url + "允许访问的角色为:" + roleList);
        if (roleList!=null && roleList.size() > 0){
            ArrayList<ConfigAttribute> c = new ArrayList<ConfigAttribute>();
            c.addAll(roleList);
            System.out.println("---------放行---------");
            return c;//将privilege中的roles改为Collection<ConfigAttribute> , role需要实现ConfigAttribute接口
        }else {
            throw new AccessDeniedException("权限越界!");
        }

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
