package com.ku.bobo.modules.poll.controller;

import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.Api;
import com.ku.bobo.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import com.ku.bobo.modules.poll.entity.PollVote;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.poll.service.IPollVoteService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户投票选决 前端控制器
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Api(tags = "PollVoteController", description = "用户投票选决")
@RestController
@RequestMapping("vote")
public class PollVoteController {

    @Autowired
    private IPollVoteService iPollVoteService;

    @ApiOperation( "创建" )
    @PostMapping("")
    public CommonResult<PollVote> createPollVote( @RequestBody PollVote pollVote ){
        boolean b = iPollVoteService.save( pollVote );
        return CommonResult.Booleans.save( b );
    }

    @ApiOperation( "批量创建" )
    @PostMapping("/_mcreate")
    public CommonResult<PollVote> batchCreatePollVote( @RequestBody List<PollVote> pollVote ){
        boolean b = iPollVoteService.saveBatch( pollVote );
        return CommonResult.Booleans.msave( b );
    }

    /**
    * 分页查询
    * @param pollVote 用户参数
    * @param pageNum 分页-> 第几页
    * @param pageSize 分页-> 一页多少数量
    * @return
    */
    @ApiOperation( "条件分页查询" )
    @PostMapping("/list")
    public CommonResult<IPage<PollVote>> list( @RequestBody PollVote pollVote,
    @RequestParam(name = "pageNum", defaultValue = "1")  Integer pageNum ,
    @RequestParam(name = "pageSize", defaultValue = "10")  Integer pageSize ){
    QueryWrapper<PollVote> pollVoteQueryWrapper = new QueryWrapper<>( pollVote );
        Page<PollVote> page = new Page<>( pageNum , pageSize );
        IPage<PollVote> pollVotePage = iPollVoteService.page(page, pollVoteQueryWrapper );
        return CommonResult.success( pollVotePage );
    }

    @ApiOperation( "根据ID查询" )
    @GetMapping("/{pollVoteId}")
    public CommonResult<PollVote> getPollVoteById( @PathVariable(name = "pollVoteId")  Long pollVoteId ){
    PollVote pollVote = iPollVoteService.getById( pollVoteId );
        if ( pollVote == null ){
        return CommonResult.failed( "该实体不存在" );
        }
        return CommonResult.success( pollVote );
    }

    @ApiOperation( "根据Id删除" )
    @DeleteMapping("/{pollVoteId}")
    public CommonResult<PollVote> deletePollVoteById( @PathVariable(name = "pollVoteId")  Long pollVoteId ){
        boolean b = iPollVoteService.removeById(pollVoteId);
        return CommonResult.Booleans.delete( b );
    }

    @ApiOperation( "根据Ids批量删除" )
    @DeleteMapping("/_mdelete/{ids}")
    public CommonResult<PollVote> batchDeletePollVoteByIds( @PathVariable( name = "ids") String ids ){
        boolean b = iPollVoteService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        return CommonResult.Booleans.mdelete( b );
    }


    @ApiOperation( "根据id修改" )
    @PutMapping("")
    public CommonResult<PollVote> updatePollVoteById( @RequestBody PollVote pollVote ){
        boolean b = iPollVoteService.updateById( pollVote );
        return CommonResult.Booleans.update( b );
    }

    @ApiOperation( "根据id批量修改" )
    @PutMapping("/_mupdate")
    public CommonResult<PollVote> batchUpdateUsAdminById( @RequestBody List<PollVote> pollVotes ){
       boolean b = iPollVoteService.updateBatchById( pollVotes );
       return CommonResult.Booleans.update( b );
    }

}
