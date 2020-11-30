package com.ku.bobo.modules.poll.controller;

import java.util.List;
import java.util.Arrays;

import com.ku.bobo.api.ResponseResult;
import com.ku.bobo.modules.poll.payload.PollRequest;
import com.ku.bobo.modules.poll.payload.vo.PollVO;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.poll.entity.Poll;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.poll.service.IPollService;
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
 * @since 2020-11-22
 */
@Api(tags = "PollController", description = "")
@ResponseResult
@RequestMapping("poll")
public class PollController {

    @Autowired
    private IPollService iPollService;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<Poll> createPoll( @RequestBody PollRequest poll ){
        System.out.println("poll = " + poll);
        iPollService.savePollChoices( poll );
        return CommonResult.Booleans.save( true );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<Poll> batchCreatePoll( @RequestBody List<Poll> poll ){
        boolean b = iPollService.saveBatch( poll );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @GetMapping("/list")
    public IPage<PollVO> list(
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
        Page<Poll> page = new Page<>( pageNum , pageSize );
        return iPollService.listPollVO(page);
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{pollId}")
    public CommonResult<Poll> getPollById( @PathVariable(name = "pollId")  Long pollId ){
    Poll poll = iPollService.getById( pollId );
        if ( poll == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( poll );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{pollId}")
    public CommonResult<Poll> deletePollById( @PathVariable(name = "pollId")  Long pollId ){
        boolean b = iPollService.removeById(pollId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<Poll> batchDeletePollByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iPollService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<Poll> updatePollById( @RequestBody Poll poll ){
        boolean b = iPollService.updateById( poll );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<Poll> batchUpdateUsAdminById( @RequestBody List<Poll> polls ){
       boolean b = iPollService.updateBatchById( polls );
       return CommonResult.Booleans.update( b );
    }

}
