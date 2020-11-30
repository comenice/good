import React from 'react'
import { getToken } from "../../util/util";
import { message } from 'antd';

function AuthLoginWrap  ( fuc ) {
  let n = typeof fuc;
  if ( !getToken() ){
    switch (n) {
      case "function":
        message.error("请先登陆！")
        return  
        break;
      default:
        break;
    }
  }
  return fuc (this) //直接返回函数的返回值，而不是函数引用本身。9
};

export default AuthLoginWrap