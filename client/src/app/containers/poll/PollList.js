import React, { PureComponent, useEffect,useState } from 'react';
import { getAllPolls, getUserCreatedPolls, getUserVotedPolls } from 'util/APIUtils';
import { getPoll } from './api'
import Poll from './Poll';
import { castVote } from 'util/APIUtils';
import { Button, Icon, notification } from 'antd';
import { POLL_LIST_SIZE } from 'constants';
import { withRouter } from 'react-router-dom';
import LoadingIndicator  from 'components/common/LoadingIndicator';

import './PollList.css';

export default function PollList() {

    const [polls, setPolls] = useState([]);
    const [page, setPage] = useState({
        page: 0,
        size: 10,
        totalElements: 0,
        totalPages: 0,
        last: true,
        currentVotes: [],
        isLoading: false
    })
    
    useEffect(() => {
        loadPollList()
        return () => {
            
        }
    }, [])

    function loadPollList(page = 0, size = POLL_LIST_SIZE) {
        let promise;
        getPoll(page, size).then(data => {
            setPolls( data.records )
        }).catch(error => {
           
        });
    }

    const pollViews = [];
    
    polls.forEach((poll, pollIndex) => {
        pollViews.push(<Poll 
            key={poll.id} 
            poll={poll}
            currentVote={poll.choices} 
            handleVoteChange={(event) => this.handleVoteChange(event, pollIndex)}
            handleVoteSubmit={(event) => this.handleVoteSubmit(event, pollIndex)} />)            
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
            {/* {
                last ? (
                    <div className="load-more-polls">
                        <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}>
                            <Icon type="plus" /> Load more
                        </Button>
                    </div>): LoadingIndicator
            } */}
          
        </div>
    ) 
}