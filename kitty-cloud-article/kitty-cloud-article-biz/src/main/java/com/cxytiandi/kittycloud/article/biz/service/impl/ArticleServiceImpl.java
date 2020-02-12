package com.cxytiandi.kittycloud.article.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxytiandi.kittycloud.article.biz.bo.ArticleBO;
import com.cxytiandi.kittycloud.article.biz.convert.ArticleBoConvert;
import com.cxytiandi.kittycloud.article.biz.dao.ArticleDao;
import com.cxytiandi.kittycloud.article.biz.dataobject.ArticleDO;
import com.cxytiandi.kittycloud.article.biz.manager.ArticleManager;
import com.cxytiandi.kittycloud.article.biz.service.ArticleService;
import com.cxytiandi.kittycloud.common.base.ResponseCode;
import com.cxytiandi.kittycloud.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


/**
 * 文章业务实现
 *
 * @作者 尹吉欢
 * @个人微信 jihuan900
 * @微信公众号 猿天地
 * @GitHub https://github.com/yinjihuan
 * @作者介绍 http://cxytiandi.com/about
 * @时间 2020-02-12 20:01:04
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleBoConvert articleBoConvert;

    @Autowired
    private ArticleManager articleManager;

    @Override
    public ArticleBO getArticle(Long articleId) {
        if (articleId == null) {
            throw new BizException(ResponseCode.PARAM_ERROR_CODE);
        }

        ArticleDO articleDO = articleDao.selectById(articleId);
        if (articleDO == null) {
            throw new BizException(ResponseCode.NOT_FOUND_CODE);
        }

        String nickname = articleManager.getNickname(articleDO.getUserId());
        return articleBoConvert.convertPlus(articleDO, nickname);
    }

    @Override
    public Page<ArticleBO> listHotArticles(int page, int pageSize) {
        QueryWrapper<ArticleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("heat");

        Page queryPage = new Page<>(page, pageSize);

        IPage<ArticleDO> articleDoPage = articleDao.selectPage(queryPage, queryWrapper);

        Page pageResponse = new Page(page, pageSize, articleDoPage.getTotal());
        pageResponse.setRecords(articleDoPage.getRecords().stream().map(articleBoConvert::convert).collect(Collectors.toList()));

        return pageResponse;
    }

    @Override
    public Page<ArticleBO> listNewestArticles(int page, int pageSize) {
        QueryWrapper<ArticleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("heat");

        Page queryPage = new Page<>(page, pageSize);

        IPage<ArticleDO> articleDoPage = articleDao.selectPage(queryPage, queryWrapper);

        Page pageResponse = new Page(page, pageSize, articleDoPage.getTotal());
        pageResponse.setRecords(articleDoPage.getRecords().stream().map(articleBoConvert::convert).collect(Collectors.toList()));

        return pageResponse;
    }

}