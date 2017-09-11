package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

public interface InterceptorService {

	TbUser getUserByToken(String token);
}
