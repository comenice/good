package com.ku.bobo.modules.system.controller;

import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.system.entity.SysRolePermission;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.system.service.ISysRolePermissionService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxb
 * @since 2020-11-01
 */
@Api(tags = "SysRolePermissionController", description = "")
@RestController
@RequestMapping("role-permission")
public class SysRolePermissionController {

    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<SysRolePermission> createSysRolePermission( @RequestBody SysRolePermission sysRolePermission ){
        boolean b = iSysRolePermissionService.save( sysRolePermission );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<SysRolePermission> batchCreateSysRolePermission( @RequestBody List<SysRolePermission> sysRolePermission ){
        boolean b = iSysRolePermissionService.saveBatch( sysRolePermission );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param sysRolePermission 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<SysRolePermission>> list( @RequestBody SysRolePermission sysRolePermission,
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<SysRolePermission> sysRolePermissionQueryWrapper = new QueryWrapper<>( sysRolePermission );
        Page<SysRolePermission> page = new Page<>( pageNum , pageSize );
        IPage<SysRolePermission> sysRolePermissionPage = iSysRolePermissionService.page(page, sysRolePermissionQueryWrapper );
        return CommonResult.success( sysRolePermissionPage );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{sysRolePermissionId}")
    public CommonResult<SysRolePermission> getSysRolePermissionById( @PathVariable(name = "sysRolePermissionId")  Long sysRolePermissionId ){
    SysRolePermission sysRolePermission = iSysRolePermissionService.getById( sysRolePermissionId );
        if ( sysRolePermission == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( sysRolePermission );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{sysRolePermissionId}")
    public CommonResult<SysRolePermission> deleteSysRolePermissionById( @PathVariable(name = "sysRolePermissionId")  Long sysRolePermissionId ){
        boolean b = iSysRolePermissionService.removeById(sysRolePermissionId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<SysRolePermission> batchDeleteSysRolePermissionByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iSysRolePermissionService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<SysRolePermission> updateSysRolePermissionById( @RequestBody SysRolePermission sysRolePermission ){
        boolean b = iSysRolePermissionService.updateById( sysRolePermission );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<SysRolePermission> batchUpdateUsAdminById( @RequestBody List<SysRolePermission> sysRolePermissions ){
       boolean b = iSysRolePermissionService.updateBatchById( sysRolePermissions );
       return CommonResult.Booleans.update( b );
    }

}
