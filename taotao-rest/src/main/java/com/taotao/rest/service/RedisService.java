package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

public interface RedisService {

	TaotaoResult ContentRedisSync(long contentId);
	TaotaoResult CatListRedisSync();
}
