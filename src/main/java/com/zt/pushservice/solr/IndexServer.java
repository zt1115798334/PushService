package com.zt.pushservice.solr;

import com.google.common.collect.Lists;
import com.zt.pushservice.bean.MyBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * 单例实现索引服务
 * 
 * @author zkdj
 */
@Component
public class IndexServer {

	@Autowired
	private MyBean myBean;

	private static Log logger = LogFactory.getLog(IndexServer.class);

	private ArrayList<SolrServer> servers = null;
	private ArrayList<SolrServer> serversInUse = null;

//	@Autowired
//	public IndexServer() {
//		logger.info("索引服务器启动初始化信息开始......");
//		String indexSlaveUrl = myBean.getSolrUrl();
//		String initCount = myBean.getInitCount();
//		try {
//			logger.info(indexSlaveUrl);
//			servers = Lists.newArrayList();
//			serversInUse = Lists.newArrayList();
//			for(int i =0 ; i<Integer.parseInt(initCount) ; i++){
//				SolrServer server = new CommonsHttpSolrServer(indexSlaveUrl);
//				servers.add(server);
//			}
//
//		} catch (MalformedURLException e) {
//			logger.error("索引服务器启动初始化信息失败，地址是：" + indexSlaveUrl);
//			return;
//		}
//		logger.info("索引服务器启动初始化信息成功。");
//	}

	public SolrServer getServer() {

		logger.info("索引服务器启动初始化信息开始......");
		String indexSlaveUrl = myBean.getSolrUrl();
		String initCount = myBean.getInitCount();
		try {
			logger.info(indexSlaveUrl);
			servers = Lists.newArrayList();
			serversInUse = Lists.newArrayList();
			for(int i =0 ; i<Integer.parseInt(initCount) ; i++){
				SolrServer server = new CommonsHttpSolrServer(indexSlaveUrl);
				servers.add(server);
			}

		} catch (MalformedURLException e) {
			logger.error("索引服务器启动初始化信息失败，地址是：" + indexSlaveUrl);
		}
		logger.info("索引服务器启动初始化信息成功。");

		if(servers != null && servers.size() > 0){
			SolrServer server = servers.get(0);
			serversInUse.add(server);
			servers.remove(server);
			return server;
		}else{
			SolrServer server = serversInUse.get(0);
			servers.add(server);
			serversInUse.remove(server);
			return server;
		}
	}
}