import  request from 'util/request'
import  { page } from 'util/util'


export function createPoll( p ) {
    return request.post( "/poll" , p )
}


export function getPoll( pageNum , pageSize ) {
    return request.post( "/poll/list" , page( pageNum , pageSize ) )
}
