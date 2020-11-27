import  request from 'util/request'


export function login( p ) {
    return request.post( "/auth/login" , p )
}
