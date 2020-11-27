package com.ku.bobo.modules.system.controller;

import java.util.List;
import java.util.Arrays;

import com.ku.bobo.api.ResponseResult;
import com.ku.bobo.modules.security.CurrentUser;
import com.ku.bobo.modules.security.service.dto.AuthUserDto;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.system.entity.SysUser;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.system.service.ISysUserService;
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
@Api(tags = "SysUserController", description = "")
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private ISysUserService iSysUserService;

    @PostMapping("/me")
    @ResponseResult
    public SysUser me( @CurrentUser AuthUserDto authUser ){
        SysUser sysUser = new SysUser();
        sysUser.setUsername( authUser.getUsername() );
        return sysUser;
    }

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<SysUser> createSysUser( @RequestBody SysUser sysUser ){
        boolean b = iSysUserService.save( sysUser );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<SysUser> batchCreateSysUser( @RequestBody List<SysUser> sysUser ){
        boolean b = iSysUserService.saveBatch( sysUser );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param sysUser 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<SysUser>> list( @RequestBody SysUser sysUser,
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>( sysUser );
        Page<SysUser> page = new Page<>( pageNum , pageSize );
        IPage<SysUser> sysUserPage = iSysUserService.page(page, sysUserQueryWrapper );
        return CommonResult.success( sysUserPage );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{sysUserId}")
    public CommonResult<SysUser> getSysUserById( @PathVariable(name = "sysUserId")  Long sysUserId ){
    SysUser sysUser = iSysUserService.getById( sysUserId );
        if ( sysUser == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( sysUser );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{sysUserId}")
    public CommonResult<SysUser> deleteSysUserById( @PathVariable(name = "sysUserId")  Long sysUserId ){
        boolean b = iSysUserService.removeById(sysUserId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<SysUser> batchDeleteSysUserByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iSysUserService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<SysUser> updateSysUserById( @RequestBody SysUser sysUser ){
        boolean b = iSysUserService.updateById( sysUser );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<SysUser> batchUpdateUsAdminById( @RequestBody List<SysUser> sysUsers ){
       boolean b = iSysUserService.updateBatchById( sysUsers );
       return CommonResult.Booleans.update( b );
    }

}
