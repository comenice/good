import  request from 'util/request'
import  { getPage } from 'util/util'


export function createPoll( p ) {
    return request.post( "/poll" , p )
}


export function getPoll( pageNum , pageSize ) {
    let p = getPage( pageNum , pageSize );
    return request.get( "/poll/list?" + p )
}


export function createVote( p ){
    return request.post( "/vote" , p )
}