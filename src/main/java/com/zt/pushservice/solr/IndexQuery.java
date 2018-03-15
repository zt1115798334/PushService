package com.zt.pushservice.solr;

import com.zkdj.search.er.Search;
import com.zkdj.search.facet.FacetFielding;
import com.zkdj.search.facet.FacetQuerying;
import com.zkdj.search.fielditem.QueryString;
import com.zkdj.search.group.SearchByGroup;
import com.zkdj.search.mltdoc.MltDocsById;
import com.zkdj.search.query.QueryById;
import com.zkdj.search.spread.SpreadByOriginal;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.FacetField.Count;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 全文检索库接口实现类
 * 
 * @author zkdj
 */

public class IndexQuery  {
	private static final Logger logger = Logger
			.getLogger(IndexQuery.class);

	private IndexServer indexServer=new IndexServer("http://183.245.203.10:8081/index-master/","3");

	/**
	 * 获取某篇文章的信息
	 * 
	 * @param id
	 *            文章标识
	 * @return HashMap<String(字段名称),String(字段值)>文章信息
	 */
	public HashMap<String, String> getArticleByID(String id) {
		QueryById queryById = new QueryById();
		return queryById.Query(indexServer.getServer(), id);
	}

	/**
	 * 获取某篇文章的相关文章信息
	 * 
	 * @param id
	 *            文章标识
	 * @param count
	 *            相关文章数
	 * @param mincount
	 *            最少相关文章数
	 * @param score
	 *            相关度指数
	 * @return ArrayList<HashMap<String(字段名称),String(对应的值)>>文章信息
	 */
	public ArrayList<HashMap<String, String>> getMLTArticleByID(String id,
			int count, int mincount, double score) {
		MltDocsById mltDocsById = new MltDocsById();
		mltDocsById.setMincount(mincount);
		mltDocsById.setScore(score);
		mltDocsById.setCount(count);
		return mltDocsById.Query(indexServer.getServer(), id);
	}
	/**
	 * 获取某篇文章的相关文章信息(可显示全部，分页显示)
	 * @param id	文章标识
	 * @param offset	起始条数
	 * @param rows	返回条数
	 * @return
	 */
	public HashMap<String, Serializable> getSameArticleById(String id,int offset,int rows){
		SpreadByOriginal spreadByOriginal = new SpreadByOriginal();
		spreadByOriginal.setOffset(offset);
		spreadByOriginal.setRows(rows);
		ArrayList<HashMap<String,String>> result = spreadByOriginal.Query(indexServer.getServer(), id);
		int count = spreadByOriginal.getCount();
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		// 总页数
		int pageNumber = this.getPageNunber(count, rows);
		// 当前页
		int current = 0;
		// 上一页
		String up = "";
		// 下一页
		String down = "";
		// 当前页 等于点击查询的page参数
		if (offset == 0) {
			offset = 0;
		} else {
			offset = offset / rows + 1;
		}
		current = offset;
		// 如果当前>=总数,则 当前=总数
		if (current >= pageNumber) {
			up = (pageNumber - 1) + "";
			;
			down = pageNumber + "";
		} else {
			up = (current) - 1 + "";
			;
			down = (Integer.parseInt(up) + 2) + "";
		}
		// 如果上一页小于0,则=1
		if (Integer.parseInt(up) <= 0) {
			up = "1";
		}
		// 如果下一页>=总数,则等于最后一页
		if (Integer.parseInt(down) >= pageNumber) {
			down = String.valueOf(pageNumber);
		}
		// 如果当前页>总数,则当前=总数
		if (current > pageNumber) {
			current = pageNumber;
		}
		// 如果<=0 则=1
		if (current <= 0) {
			current = 1;
		}
		// 当前页
		map.put("current", current);
		map.put("list", result);
		map.put("count", pageNumber);
		map.put("allCount", count);
		return map;
	}

	/**
	 * 获取某篇文章的转载文章信息
	 * 
	 * @param id
	 *            文章标识
	 * @param offset
	 *            起始条数
	 * @param rows
	 *            返回条数
	 * @return ArrayList<HashMap<String(字段名称),String(对应的值)>>文章信息
	 */
	public HashMap<String, Serializable> getRPArticleByID(String id,
			int offset, int rows, String sort) {
		QueryString queryString = new QueryString();
		queryString.setOriginal(id);
		queryString.setProperty("[1 TO *]");
		Search search = new Search();
		search.setOffset(offset);
		search.setRows(rows);
		if (null != sort && !"".equals(sort)) {
			search.setDateSort(true);
		} else {
			search.setDateSortAsc(true);
		}
		ArrayList<HashMap<String, String>> result = null;
		result = search.Query(indexServer.getServer(), queryString
				.getQueryStr(), null);
		int count = search.getCount();
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();

		// 总页数
		int pageNumber = this.getPageNunber(count, rows);
		// 当前页
		int current = 0;
		// 上一页
		String up = "";
		// 下一页
		String down = "";
		// 当前页 等于点击查询的page参数
		if (offset == 0) {
			offset = 0;
		} else {
			offset = offset / rows + 1;
		}
		current = offset;
		// 如果当前>=总数,则 当前=总数
		if (current >= pageNumber) {
			up = (pageNumber - 1) + "";
			;
			down = pageNumber + "";
		} else {
			up = (current) - 1 + "";
			;
			down = (Integer.parseInt(up) + 2) + "";
		}
		// 如果上一页小于0,则=1
		if (Integer.parseInt(up) <= 0) {
			up = "1";
		}
		// 如果下一页>=总数,则等于最后一页
		if (Integer.parseInt(down) >= pageNumber) {
			down = String.valueOf(pageNumber);
		}
		// 如果当前页>总数,则当前=总数
		if (current > pageNumber) {
			current = pageNumber;
		}
		// 如果<=0 则=1
		if (current <= 0) {
			current = 1;
		}
		// 当前页
		map.put("current", current);
		map.put("list", result);
		map.put("count", pageNumber);
		return map;
	}

	/**
	 * 根据所传查询语句和字段名取出所有值和统计数
	 * 
	 * @param queryString
	 *            自定义查询语句
	 * @param facetField
	 *            层面统计字段
	 * @param facetLimit
	 *            返回值的个数
	 * @param facetMinCount
	 *            字段值最小命中数
	 * @param facetSort
	 *            "true" 是否排序
	 * @return ArrayList<Count>某个字段的所有值及统计数
	 */
	public ArrayList<Count> getStatisticsByField(QueryString queryString,
                                                 String facetField, int facetLimit, int facetMinCount,
                                                 String facetSort) {
		FacetFielding facetFielding = new FacetFielding();
		if (queryString != null)
			facetFielding.setQueryStr(queryString.getQueryStr());
		facetFielding.setFacetLimit(facetLimit);
		facetFielding.setFacetMinCount(facetMinCount);
		if ("true".equals(facetSort))
			facetFielding.setFacetSort(facetSort);
		return facetFielding.facetFieldStatisticsList(indexServer.getServer(),
				facetField);
	}

	/**
	 * 根据所传查询语句取出所得文章数
	 * 
	 * @param queryString
	 *            自定义查询语句
	 * @param facetQuery
	 *            层面统计语句
	 * @param facetSort
	 *            "true" 是否排序
	 * @return HashMap<String(每个规则内容),Integer(文章总数)>
	 */
	public HashMap<String, Integer> getStatisticsByExpression(
			QueryString queryString, String[] facetQuery, String facetSort) {
		FacetQuerying facetQuerying = new FacetQuerying();
		if (queryString != null)
			facetQuerying.setQueryStr(queryString.getQueryStr());
		facetQuerying.setFacetQuerys(facetQuery);
		if ("true".equals(facetSort))
			facetQuerying.setFacetSort(facetSort);
		return facetQuerying.facetQueryStatisticsList(indexServer.getServer());
	}

	/**
	 * 根据自定义规则查询数据信息
	 * 
	 * @param queryString
	 *            自定义规则
	 * @param order
	 *            排序方式
	 * @param rows
	 *            每次取出的条数
	 * @return ArrayList<HashMap<String(字段名称),String(对应的值)>>
	 */
	public ArrayList<HashMap<String, String>> QueryByString(
			QueryString queryString, String[] hlFeilds, String order, int rows) {
		ArrayList<HashMap<String, String>> query;

		if (queryString != null) {
			Search search = new Search();
			search.setRows(rows);
			if ("date".equals(order))
				search.setDateSort(true);
			else if ("date asc".equals(order))
				search.setDateSortAsc(true);
			else if ("hot desc".equals(order))
				search.setHotSort(true);
			else if ("hot asc".equals(order))
				search.setHotSortAsc(true);
			else if ("clink desc".equals(order))
				search.setClinkSort(true);
			else if ("clink asc".equals(order))
				search.setClinkSortAsc(true);
			else if ("property desc".equals(order))
				search.setPropertySort(true);
			else if ("property asc".equals(order))
				search.setPropertySortAsc(true);
			else if ("reply desc".equals(order))
				search.setReplySort(true);
			else if ("reply asc".equals(order))
				search.setReplySortAsc(true);
			else if ("timestamp desc".equals(order))
				search.setTimestampSort(true);
			else if ("timestamp asc".equals(order))
				search.setTimestampSortAsc(true);
			query = search.Query(indexServer.getServer(), queryString
					.getQueryStr(), hlFeilds);
			return query;
		}
		return null;
	}

	/**
	 * 根据自定义语句查询的数据信息（分页显示）
	 * 
	 * @param queryString
	 *            自定义查询语句
	 * @param hlFeilds
	 *            需要高亮显示的字段数组
	 * @param order
	 *            排序方式
	 * @param page
	 * @param pageSize
	 * @return ArrayList<HashMap<String(字段名称),String(对应的值)>>
	 */
	public HashMap<String, Serializable> QueryPageByString(
			QueryString queryString, String[] hlFeilds, String order,
			String page, int pageSize) {
		// 定义返回的map
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		// 执行查询10数据 list
		ArrayList<HashMap<String, String>> query = null;
		// 总条数
		int count = 0;
		// 总页数
		int pageNumber = 0;
		// 上一页
		String up = "";
		// 下一页
		String down = "";
		// 当前页
		String current = "";
		// 页标记
		String pageflag = "";
		if (queryString != null) {
			Search search = new Search();
			if ("".equals(page) || null == page) {
				page = "1";
			}
			if(page.equals("0")){
				page = "1";
				pageflag = "0";
			}
			// 当前页 减1 乘以10 如果当前页是5 则 （5-1）*10=40 查询数据的时候 就是从第40条开始 在查询10条
			search.setOffset((Integer.parseInt(page) - 1) * pageSize);
			// 每次查询多少条数据
			search.setRows(pageSize);
			// --------
			try {
				if ("date".equals(order)) {
					search.setDateSort(true);// 时间
				} else if ("date asc".equals(order)) {
					search.setDateSortAsc(true);
				} else if ("click".equals(order)) {
					search.setClinkSort(true);// 点击
				} else if ("reply".equals(order)) {
					search.setReplySort(true);// 回帖
				} else if ("hot".equals(order)) {
					search.setHotSort(true); // 热度指数
				} else if ("Property".equals(order)) {
					search.setPropertySort(true);// 转载
				} else if ("score".equals(order)) {
					// 默认按相关度
				} else {
					search.setDateSort(true);// 时间
				}
				query = search.Query(indexServer.getServer(), queryString
						.getQueryStr(), hlFeilds);
				// 获得总条数
				count = search.getCount();
				// 获得总页数
				pageNumber = this.getPageNunber(count, pageSize);
				// 当前页 等于点击查询的page参数
				current = page;
				// 如果当前>=总数,则 当前=总数
				if (Integer.parseInt(current) >= pageNumber) {
					up = (pageNumber - 1) + "";
					;
					down = pageNumber + "";
				} else {
					up = (Integer.parseInt(current) - 1) + "";
					;
					down = (Integer.parseInt(up) + 2) + "";
				}
				// 如果上一页小于0,则=1
				if (Integer.parseInt(up) <= 0) {
					up = "1";
				}
				// 如果下一页>=总数,则等于最后一页
				if (Integer.parseInt(down) >= pageNumber) {
					down = String.valueOf(pageNumber);
				}
				// 如果当前页>总数,则当前=总数
				if (Integer.parseInt(current) > pageNumber) {
					current = String.valueOf(pageNumber);
				}
				// 如果<=0 则=1
				if (Integer.parseInt(current) <= 0) {
					current = "1";
				}
				if(pageflag.equals("0")){
					search.setOffset(0);
					if(count<=10000)
						search.setRows(count);
					else
						search.setRows(100);
					query = search.Query(indexServer.getServer(), queryString
							.getQueryStr(), hlFeilds);
				}
			} catch (Exception e) {
				logger.error(e + "IndexQueryDAOImpl中QueryPageByString有异常");
			}
			// ------------
		}
		// 查询10条数据的list
		map.put("list", query);
		// 一共有多少页
		map.put("pageNumber", pageNumber);
		// 一共有多少条数据
		map.put("count", count);
		// 当前页
		map.put("current", current);
		// 上一页是多少
		map.put("up", up);
		// 下一页是多少
		map.put("down", down);
		return map;
	}

	/**
	 * 根据自定义语句查询排重过的数据信息（分页显示）
	 * 
	 * @param queryString
	 *            自定义查询语句
	 * @param hlFeilds
	 *            需要高亮显示的字段数组
	 * @param order
	 *            排序方式
	 * @param page
	 * @param pageSize
	 * @return ArrayList<HashMap<String(字段名称),String(对应的值)>>
	 */

	public HashMap<String, Serializable> QueryGroupPageByString(
			QueryString queryString, String[] hlFeilds, String order,
			String page, int pageSize) {
		// 定义返回的map
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		// 执行查询10数据 list
		ArrayList<HashMap<String, String>> query = null;
		// 总条数
		int count = 0;
		// 总页数
		int pageNumber = 0;
		// 上一页
		String up = "";
		// 下一页
		String down = "";
		// 当前页
		String current = "";
		// 页标记
		String pageflag = "";
		if (queryString != null) {
			SearchByGroup search = new SearchByGroup();
			if ("".equals(page) || null == page) {
				page = "1";
			}
			if(page.equals("0")){
				page = "1";
				pageflag = "0";
			}
			// 当前页 减1 乘以10 如果当前页是5 则 （5-1）*10=40 查询数据的时候 就是从第40条开始 在查询10条
			search.setOffset((Integer.parseInt(page) - 1) * pageSize);
			// 每次查询多少条数据
			search.setRows(pageSize);
			// --------
			try {
				if ("date".equals(order)) {
					search.setDateSort(true);// 时间
				} else if ("click".equals(order)) {
					search.setClinkSort(true);// 点击
				} else if ("reply".equals(order)) {
					search.setReplySort(true);// 回帖
				} else if ("hot".equals(order)) {
					search.setHotSort(true); // 热度指数
				} else if ("Property".equals(order)) {
					search.setPropertySort(true);// 转载
				} else if ("score".equals(order)) {
					// 默认按相关度
				} else {
					search.setDateSort(true);// 时间
				}
				search.setGroupField("original");
				query = search.Query(indexServer.getServer(), queryString
						.getQueryStr(), hlFeilds);
				// 获得总条数
				count = search.getCount();
				// 获得总页数
				pageNumber = this.getPageNunber(count, pageSize);
				// 当前页 等于点击查询的page参数
				current = page;
				// 如果当前>=总数,则 当前=总数
				if (Integer.parseInt(current) >= pageNumber) {
					up = (pageNumber - 1) + "";
					;
					down = pageNumber + "";
				} else {
					up = (Integer.parseInt(current) - 1) + "";
					;
					down = (Integer.parseInt(up) + 2) + "";
				}
				// 如果上一页小于0,则=1
				if (Integer.parseInt(up) <= 0) {
					up = "1";
				}
				// 如果下一页>=总数,则等于最后一页
				if (Integer.parseInt(down) >= pageNumber) {
					down = String.valueOf(pageNumber);
				}
				// 如果当前页>总数,则当前=总数
				if (Integer.parseInt(current) > pageNumber) {
					current = String.valueOf(pageNumber);
				}
				// 如果<=0 则=1
				if (Integer.parseInt(current) <= 0) {
					current = "1";
				}
				if(pageflag.equals("0")){
					search.setOffset(0);
					if(count<=10000)
						search.setRows(count);
					else
						search.setRows(10000);
					query = search.Query(indexServer.getServer(), queryString
							.getQueryStr(), hlFeilds);
				}
			} catch (Exception e) {
				logger.error(e + "IndexQueryDAOImpl中QueryPageByString有异常");
			}
			// ------------
		}
		// 查询10条数据的list
		map.put("list", query);
		// 一共有多少页
		map.put("pageNumber", pageNumber);
		// 一共有多少条数据
		map.put("count", count);
		// 当前页
		map.put("current", current);
		// 上一页是多少
		map.put("up", up);
		// 下一页是多少
		map.put("down", down);
		return map;
	}

	public int getPageNunber(int count, int pageSize) {
		int pageNum = 0;
		if (count == 0) {
			return pageNum;
		}
		double recordCount = new Double(count);
		pageNum = (int) Math.ceil(recordCount / pageSize);
		return pageNum;
	}
}