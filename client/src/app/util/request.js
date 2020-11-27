import axios from 'axios'
import { message } from 'antd';
import { ACCESS_TOKEN } from '../constants/index'

import { API_BASE_URL } from '../constants'
//消息提示单独配置
message.config({
    duration: 2,
    maxCount: 3,
});


// 请求超时时间，10s
const requestTimeOut = 10 * 1000
//请求地址
const host = API_BASE_URL
const service = axios.create({
    baseURL: host,
    timeout: requestTimeOut,
    // responseType: 'json',
})


//请求拦截
service.interceptors.request.use ( config => {
    if ( localStorage.getItem( ACCESS_TOKEN ) ){
        config.headers['Authorization'] = 'Bearer ' + localStorage.getItem( ACCESS_TOKEN )
    }
    return config;
})

//响应拦截
service.interceptors.response.use( config =>{
        let data = config.data ? config.data : null;
        console.log("data",data )
        if ( data != null && data.code === 200  ){
            if ( !data.code ){
                console.warn( "不存在code" );
             }
            return data.data
        }else{
            switch ( data.code ){
                case 404:
                default:
                    message.error( data.msg );
                    break
            }
            return Promise.reject(data.msg)
        }
        return config.data
    }, ( error ) =>{
        if (error.response) {
            console.error( "请求error" , error.response.data )
            switch ( error.response.status ) {
                case 500||504:
                    console.error('Server error: ' , error.response.data.message ? error.response.data.message : error.response.statusText )
                    message.error( "服务器出现了问题~" )
                    break
                default:
                    message.error( error.response.data.message ? error.response.data.message : error.response.statusText )
                    break
            }
        }
        return Promise.reject(error)
    }
)

const request = {
    post(url, params) {
        return service.post(url, params, {
            // transformRequest: [(params) => {
            //   return tansParams(params)
            // }],
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
    },
    get(url, params) {
        let _params
        if (Object.is(params, undefined)) {
            _params = ''
        } else {
            _params = '?'
            for (const key in params) {
                // eslint-disable-next-line no-prototype-builtins
                if (params.hasOwnProperty(key) && params[key] !== null) {
                    _params += `${key}=${params[key]}&`
                }
            }
        }
        return service.get(`${url}${_params}`)
    },
}

export default request