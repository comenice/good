package com.ku.bobo.modules.system.controller;

import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.system.entity.SysUserRole;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.system.service.ISysUserRoleService;
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
@Api(tags = "SysUserRoleController", description = "")
@RestController
@RequestMapping("user-role")
public class SysUserRoleController {

    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<SysUserRole> createSysUserRole( @RequestBody SysUserRole sysUserRole ){
        boolean b = iSysUserRoleService.save( sysUserRole );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<SysUserRole> batchCreateSysUserRole( @RequestBody List<SysUserRole> sysUserRole ){
        boolean b = iSysUserRoleService.saveBatch( sysUserRole );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param sysUserRole 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<SysUserRole>> list( @RequestBody SysUserRole sysUserRole,
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>( sysUserRole );
        Page<SysUserRole> page = new Page<>( pageNum , pageSize );
        IPage<SysUserRole> sysUserRolePage = iSysUserRoleService.page(page, sysUserRoleQueryWrapper );
        return CommonResult.success( sysUserRolePage );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{sysUserRoleId}")
    public CommonResult<SysUserRole> getSysUserRoleById( @PathVariable(name = "sysUserRoleId")  Long sysUserRoleId ){
    SysUserRole sysUserRole = iSysUserRoleService.getById( sysUserRoleId );
        if ( sysUserRole == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( sysUserRole );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{sysUserRoleId}")
    public CommonResult<SysUserRole> deleteSysUserRoleById( @PathVariable(name = "sysUserRoleId")  Long sysUserRoleId ){
        boolean b = iSysUserRoleService.removeById(sysUserRoleId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<SysUserRole> batchDeleteSysUserRoleByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iSysUserRoleService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<SysUserRole> updateSysUserRoleById( @RequestBody SysUserRole sysUserRole ){
        boolean b = iSysUserRoleService.updateById( sysUserRole );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<SysUserRole> batchUpdateUsAdminById( @RequestBody List<SysUserRole> sysUserRoles ){
       boolean b = iSysUserRoleService.updateBatchById( sysUserRoles );
       return CommonResult.Booleans.update( b );
    }

}
