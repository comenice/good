import React from 'react'
import { getToken } from "../../util/util";
import { message } from 'antd';

const AuthLoginWrap = ( fuc ) => {
  let n = typeof fuc;

  if ( !getToken() ){
    switch (n) {
      case "function":
         return  ()=>{
           message.error("请先登陆！")
        }
        break;
      default:
        break;
    }
  }
  return fuc
};

export default AuthLoginWrap