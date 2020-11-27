package com.ku.bobo.modules.system.controller;

import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.system.entity.SysPermission;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.system.service.ISysPermissionService;
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
@Api(tags = "SysPermissionController", description = "")
@RestController
@RequestMapping("permission")
public class SysPermissionController {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<SysPermission> createSysPermission( @RequestBody SysPermission sysPermission ){
        boolean b = iSysPermissionService.save( sysPermission );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<SysPermission> batchCreateSysPermission( @RequestBody List<SysPermission> sysPermission ){
        boolean b = iSysPermissionService.saveBatch( sysPermission );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param sysPermission 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<SysPermission>> list( @RequestBody SysPermission sysPermission,
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<SysPermission> sysPermissionQueryWrapper = new QueryWrapper<>( sysPermission );
        Page<SysPermission> page = new Page<>( pageNum , pageSize );
        IPage<SysPermission> sysPermissionPage = iSysPermissionService.page(page, sysPermissionQueryWrapper );
        return CommonResult.success( sysPermissionPage );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{sysPermissionId}")
    public CommonResult<SysPermission> getSysPermissionById( @PathVariable(name = "sysPermissionId")  Long sysPermissionId ){
    SysPermission sysPermission = iSysPermissionService.getById( sysPermissionId );
        if ( sysPermission == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( sysPermission );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{sysPermissionId}")
    public CommonResult<SysPermission> deleteSysPermissionById( @PathVariable(name = "sysPermissionId")  Long sysPermissionId ){
        boolean b = iSysPermissionService.removeById(sysPermissionId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<SysPermission> batchDeleteSysPermissionByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iSysPermissionService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<SysPermission> updateSysPermissionById( @RequestBody SysPermission sysPermission ){
        boolean b = iSysPermissionService.updateById( sysPermission );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<SysPermission> batchUpdateUsAdminById( @RequestBody List<SysPermission> sysPermissions ){
       boolean b = iSysPermissionService.updateBatchById( sysPermissions );
       return CommonResult.Booleans.update( b );
    }

}
