package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.utils.E3Result;

/**
 * 根据token查询用户信息
 */
public interface TokenService {

    E3Result getUserByToken(String token);
}
