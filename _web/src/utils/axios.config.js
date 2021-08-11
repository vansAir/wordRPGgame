import Vue from "vue"
import axios from "axios"
import { Loading } from 'element-ui';
import Cookies from 'js-cookie';

Vue.use(Loading);
let LoadingFlag;

var instance = axios.create({
	baseURL: 'http://localhost:8080', //根目录
	headers: {
		'Content-Type': 'application/json;charset=UTF-8',
	},
});

instance.interceptors.request.use(function(request) {
	// 处理请求数据
	LoadingFlag = Loading.service();
	request.headers['token']=Cookies.get('token');
	return request;
}, function(error) {
	// 处理请求失败
	LoadingFlag.close();
	return Promise.reject(error);
});

// 响应拦截（配置请求回来的信息）
instance.interceptors.response.use(function(response) {
	// 处理响应数据
	LoadingFlag.close();
	return response;
}, function(error) {
	// 处理响应失败
	LoadingFlag.close();
	return Promise.reject(error);
});

export default instance;