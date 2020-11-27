package ${package.Controller};
<#assign name="${table.controllerName?substring(0 , table.controllerName?length-10)}"/>
<#assign uName="${name?uncap_first}"/>

import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.poll.entity.${name};
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.poll.service.I${name}Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.controllerName}", description = "${table.comment}")
<#if restControllerStyle>
@RestController<#else>
@Controller</#if>
@RequestMapping("<#if package.ModuleName??>${package.ModuleName}</#if><#if controllerMappingHyphenStyle??><#if controllerMappingHyphen?index_of("-") gt 0 >${controllerMappingHyphen?substring(controllerMappingHyphen?index_of("-")+1,controllerMappingHyphen?length)}<#else>${controllerMappingHyphen}</#if><#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
        </#if>

    @Autowired
    private I${name}Service i${name}Service;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<${name}> create${name}( @RequestBody ${name} ${uName} ){
        boolean b = i${name}Service.save( ${uName} );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<${name}> batchCreate${name}( @RequestBody List<${name}> ${uName} ){
        boolean b = i${name}Service.saveBatch( ${uName} );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param ${uName} 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<${name}>> list( @RequestBody ${name} ${uName},
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<${name}> ${uName}QueryWrapper = new QueryWrapper<>( ${uName} );
        Page<${name}> page = new Page<>( pageNum , pageSize );
        IPage<${name}> ${uName}Page = i${name}Service.page(page, ${uName}QueryWrapper );
        return CommonResult.success( ${uName}Page );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{${uName}Id}")
    public CommonResult<${name}> get${name}ById( @PathVariable(name = "${uName}Id")  Long ${uName}Id ){
    ${name} ${uName} = i${name}Service.getById( ${uName}Id );
        if ( ${uName} == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( ${uName} );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{${uName}Id}")
    public CommonResult<${name}> delete${name}ById( @PathVariable(name = "${uName}Id")  Long ${uName}Id ){
        boolean b = i${name}Service.removeById(${uName}Id);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<${name}> batchDelete${name}ByIds( @PathVariable( name = "ids") String ids ){
        boolean b = i${name}Service.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<${name}> update${name}ById( @RequestBody ${name} ${uName} ){
        boolean b = i${name}Service.updateById( ${uName} );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<${name}> batchUpdateUsAdminById( @RequestBody List<${name}> ${uName}s ){
       boolean b = i${name}Service.updateBatchById( ${uName}s );
       return CommonResult.Booleans.update( b );
    }

}
</#if>