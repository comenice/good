import { ACCESS_TOKEN } from '../constants';

export function postPage( pageNum , pageSize  ) {
    return {
        'pageNum':pageNum,
        'pageSize':pageSize
    }
}


export function getPage( pageNum , pageSize  ) {
    return "pageNum=" + pageNum + "&pageSize=" + pageSize
}


export function getToken(){
   return localStorage.getItem(ACCESS_TOKEN);
}