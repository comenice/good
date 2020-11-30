import React, { PureComponent, useEffect,useState } from 'react';
import { getAllPolls, getUserCreatedPolls, getUserVotedPolls } from 'util/APIUtils';
import { getPoll , createVote } from './api'
import Poll from './Poll';
import { Button, Icon, message, notification } from 'antd';
import { POLL_LIST_SIZE } from 'constants';
import LoadingIndicator  from 'components/common/LoadingIndicator';

import './PollList.css';

export default function PollList() {

    const [polls, setPolls] = useState([]);

    const [currentVotes, setCurrentVotes] = useState([]);


    const [page, setPage] = useState({
        current: 0,
        pages: 0
    })
    
    useEffect(() => {
        loadPollList()
        return () => {

        }
    }, [])

    function handleVoteChange(event, pollIndex) {
        const cV = currentVotes.slice();
        cV[pollIndex] = event.target.value;
        setCurrentVotes( cV ) 
    }

    function  handleVoteSubmit(event, pollIndex) {
        const poll = polls[pollIndex];
        const selectedChoice = currentVotes[pollIndex];
        const voteData = {
            pollId: poll.id,
            pollChoiceId: selectedChoice
        };
        createVote(voteData).then(data => {
            const ps = polls.slice();
            ps[pollIndex] = data;
            setPolls( ps )    
            message.success( "投票成功"  ) 
        });
    }

    function handleLoadMore() {
        loadPollList( page.current+1 );
    }

    function loadPollList(page = 0, size = POLL_LIST_SIZE) {
        let promise;
        getPoll(page, size).then(data => {
            const p = polls.slice();
            setPolls( p.concat( data.records ) )
            setPage({
                current: data.current,
                pages:  data.pages
            })
        }).catch(error => {
           
        });
    }

    const pollViews = [];
    
    polls.forEach((poll, pollIndex) => {
        pollViews.push(<Poll 
            key={poll.id} 
            poll={poll}
            currentVote={currentVotes[pollIndex]} 
            handleVoteChange={(event) => handleVoteChange(event, pollIndex)}
            handleVoteSubmit={(event) => handleVoteSubmit(event, pollIndex)} />)            
    });
    return (
        <div className="polls-container">
            {pollViews}
            {
               polls.length === 0 ? (
                    <div className="no-polls-found">
                        <span>No Polls Found.</span>
                    </div>
                ): <></>
            }
            {
                page.pages > page.current ? (
                    <div className="load-more-polls">
                        <Button type="dashed" onClick={ ()=> handleLoadMore() } >
                            <Icon type="plus" /> Load more
                        </Button>
                    </div>): LoadingIndicator
            } 
          
        </div>
    ) 
}