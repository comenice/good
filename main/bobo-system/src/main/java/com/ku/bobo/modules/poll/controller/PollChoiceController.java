package com.ku.bobo.modules.poll.controller;

import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.poll.entity.PollChoice;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.poll.service.IPollChoiceService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 投票选项及内容 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Api(tags = "PollChoiceController", description = "投票选项及内容")
@RestController
@RequestMapping("choice")
public class PollChoiceController {

    @Autowired
    private IPollChoiceService iPollChoiceService;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<PollChoice> createPollChoice( @RequestBody PollChoice pollChoice ){
        boolean b = iPollChoiceService.save( pollChoice );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<PollChoice> batchCreatePollChoice( @RequestBody List<PollChoice> pollChoice ){
        boolean b = iPollChoiceService.saveBatch( pollChoice );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param pollChoice 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<PollChoice>> list( @RequestBody PollChoice pollChoice,
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<PollChoice> pollChoiceQueryWrapper = new QueryWrapper<>( pollChoice );
        Page<PollChoice> page = new Page<>( pageNum , pageSize );
        IPage<PollChoice> pollChoicePage = iPollChoiceService.page(page, pollChoiceQueryWrapper );
        return CommonResult.success( pollChoicePage );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{pollChoiceId}")
    public CommonResult<PollChoice> getPollChoiceById( @PathVariable(name = "pollChoiceId")  Long pollChoiceId ){
    PollChoice pollChoice = iPollChoiceService.getById( pollChoiceId );
        if ( pollChoice == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( pollChoice );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{pollChoiceId}")
    public CommonResult<PollChoice> deletePollChoiceById( @PathVariable(name = "pollChoiceId")  Long pollChoiceId ){
        boolean b = iPollChoiceService.removeById(pollChoiceId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<PollChoice> batchDeletePollChoiceByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iPollChoiceService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<PollChoice> updatePollChoiceById( @RequestBody PollChoice pollChoice ){
        boolean b = iPollChoiceService.updateById( pollChoice );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<PollChoice> batchUpdateUsAdminById( @RequestBody List<PollChoice> pollChoices ){
       boolean b = iPollChoiceService.updateBatchById( pollChoices );
       return CommonResult.Booleans.update( b );
    }

}
