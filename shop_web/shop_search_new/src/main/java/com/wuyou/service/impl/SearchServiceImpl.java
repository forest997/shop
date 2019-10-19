package com.wuyou.service.impl;

import com.wuyou.entity.Goods;
import com.wuyou.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Forest
 * @Date 2019/10/15
 */
@Service
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private SolrClient solrClient;
    @Override
    public boolean insert(Goods goods) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", goods.getId() + "");
        document.addField("subject", goods.getSubject());
        document.addField("info", goods.getInfo());
        document.addField("price", goods.getPrice().doubleValue());
        document.addField("save", goods.getSave());
        document.addField("images", goods.getCoverImg());
        try {
            solrClient.add(document);
            solrClient.commit();
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Goods> getGoodsByKeyword(String keyword) {
        SolrQuery solrQuery = new SolrQuery();
        if(keyword==null){
            solrQuery.setQuery("*:*");
        }else {
            solrQuery.setQuery("subject:" + keyword + " || " + "info:" + keyword);
        }
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.addHighlightField("subject");

        QueryResponse response = null;
        try {
            response = solrClient.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        SolrDocumentList results = response.getResults();
        List<Goods> goodsList = new ArrayList<>();
        for (SolrDocument document : results) {
            Goods goods = new Goods();
            goods.setId(Integer.parseInt(document.getFieldValue("id") + ""));
            goods.setSubject(document.getFieldValue("subject") + "");
            goods.setPrice(BigDecimal.valueOf((double) document.getFieldValue("price")));
            goods.setSave((int) document.getFieldValue("save"));
            goods.setCoverImg(document.getFieldValue("images") + "");
            if(highlighting.containsKey(goods.getId()+"")){
                if(highlighting.get(goods.getId()+"").containsKey("subject"))
                goods.setSubject(highlighting.get(goods.getId() + "").get("subject").get(0));
            }
            goodsList.add(goods);
        }

        return goodsList;
    }
}
