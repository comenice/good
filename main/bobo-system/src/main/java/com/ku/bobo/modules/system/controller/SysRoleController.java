package com.ku.bobo.modules.system.controller;

import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.system.entity.SysRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.system.service.ISysRoleService;
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
@Api(tags = "SysRoleController", description = "")
@RestController
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private ISysRoleService iSysRoleService;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<SysRole> createSysRole( @RequestBody SysRole sysRole ){
        boolean b = iSysRoleService.save( sysRole );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<SysRole> batchCreateSysRole( @RequestBody List<SysRole> sysRole ){
        boolean b = iSysRoleService.saveBatch( sysRole );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param sysRole 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<SysRole>> list( @RequestBody SysRole sysRole,
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>( sysRole );
        Page<SysRole> page = new Page<>( pageNum , pageSize );
        IPage<SysRole> sysRolePage = iSysRoleService.page(page, sysRoleQueryWrapper );
        return CommonResult.success( sysRolePage );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{sysRoleId}")
    @PreAuthorize("@el.check('p:get1')")
    public CommonResult<SysRole> getSysRoleById( @PathVariable(name = "sysRoleId")  Long sysRoleId ){
    SysRole sysRole = iSysRoleService.getById( sysRoleId );
        if ( sysRole == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( sysRole );
    }

    @ApiOperation( "根据Id删除" )
    @PreAuthorize("@el.check('role:delete')")
    @DeleteMapping("/{sysRoleId}")
    public CommonResult<SysRole> deleteSysRoleById( @PathVariable(name = "sysRoleId")  Long sysRoleId ){
        boolean b = iSysRoleService.removeById(sysRoleId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<SysRole> batchDeleteSysRoleByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iSysRoleService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<SysRole> updateSysRoleById( @RequestBody SysRole sysRole ){
        boolean b = iSysRoleService.updateById( sysRole );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<SysRole> batchUpdateUsAdminById( @RequestBody List<SysRole> sysRoles ){
       boolean b = iSysRoleService.updateBatchById( sysRoles );
       return CommonResult.Booleans.update( b );
    }

}
