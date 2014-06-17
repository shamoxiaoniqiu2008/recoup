package com.centling.his.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

//@ResourceDependencies({
//
////@ResourceDependency(name = "js/jquery-1.6.3.min.js", target = "head"),
//@ResourceDependency(name = "js/ime.js", target = "head" )
//
//})


@FacesComponent(value = "IME")
public class UIIME extends UIInput implements AjaxInterface {

	
	public static  class DisplayItem {
		public String displaycolname; // 显示列名
		public String tcolname; // 表字段号
		public boolean bdisplay;//是否隐藏。
		
		public String width; //列宽
	}
	
	public static class SQLResultItem{
		public String displaycolname;//显示列名
		public int     colIndex;	 //列索引 0起始
		public boolean bdisplay;     //是否隐藏。
		
		public String width; //列宽
		
	}

	//private String id; // id
	private String data; // 数据表 视图名称
	//private String fitcol; // 匹配字段 
	private String resultcol; // 结果字段 
	private String forid;  //for id
	private String lock = "true";   //是否锁定
	
	private String totalPageNum;	//总页数
	
	private String multiresult = "null";//返回多个结果
	private String rawSQL = "false";
	private String SQL="null";

	private String enable = "true"; //输入法是否可用
	
	private Long MAXROWS = 10L;
	
	private List<String> fitcols = new ArrayList<String>();  //匹配
	
	private List<String> sortcols = new ArrayList<String>(); //排序列
	
	
	private List<DisplayItem> displayItems = new ArrayList<UIIME.DisplayItem>();// 显示列
	private List<SQLResultItem> sqlResultItems = new ArrayList<UIIME.SQLResultItem>();// 显示列
	private BasicDataSource ds = null ;
	
	
	//heaven 临时优化
	static ResultSet rsCache = null;
	static Statement heavenTempstmt = null;
	static Connection  heavenTempconn = null;
	static String      heavenfinalString = null;

	
	/////////////////////////////////////////////////
	
	public UIIME() {
		setRendererType(null);
		

			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
			ds=(BasicDataSource)wac.getBean("dataSource"); 

	}

	@Override
	public void decode(FacesContext context) {
//		logger.debug("UIIME decode");
		super.decode(context);
	}

	@Override
	public void encodeEnd(FacesContext context) {
//		logger.debug("UIIME encodeEnd");
		InitAttributes(context);
		
		String clientId = getClientId(context);
		
		ResponseWriter writer = context.getResponseWriter();
		
		try {
		
			writer.startElement("script", getCurrentComponent(context));
			writer.writeAttribute("type", "text/javascript", null);
			String strscriptString = "hisIme_" + forid + "= new IMEHandler(\"" + forid+"\" , \""
																			   + clientId 
																			   +"\" , \""
																			   +lock
																			   +"\" , \""
																			   +multiresult
																			   + "\");";
			writer.write(strscriptString);
			writer.endElement("script");
			
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}

	@Override
	public String getFamily() {
		return "custom";
	}

	@Override
	public void handleAjaxRequest(FacesContext context) {

		// 属性赋值
		InitAttributes(context);
		
		// 根据参数查询数据
		String strdataString = null;
		
		if (this.enable.equals("true")) {
			if (rawSQL.equals("true")) {
				
//				if (SQL.contains("HEAVEN_REQ")) {
//				
//					
//					//strdataString = FetchHeavenDataSQL(context);
//				}else {
					//Date date = new Date();
					strdataString = FetchDataSQL(context);
					//System.out.println("costTime:" + String.valueOf((new Date().getTime() - date.getTime())));
					
//				}

			}else {
				strdataString = FetchData(context);
			}
		}else {
			strdataString = "false";//enable = false;
		}

		// 服务器返回内容。
		WriteRespones(context,strdataString);
		// 终止生命周期
		context.responseComplete();
	}

	
	
	/**
	 * heaven 临时优化     初始化缓存
	 * @Description:
	 * @throws SQLException
	 * @throws:
	 * @author:Patrick.Guo
	 * @Date:2012-5-22下午01:04:38
	 * @version:1.0
	 * @Copyright: Copyright (c) 2012 Company:Centling
	 */
//	public  synchronized static void initHeavenCache() throws SQLException{
//		
//		if (heavenfinalString != null) {
//			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
//			BasicDataSource dstemp=(BasicDataSource)wac.getBean("dataSource"); 
//			
//			Connection  heavenTempconnTemp = dstemp.getConnection();
//			Statement heavenTempstmtTemp = heavenTempconnTemp.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			ResultSet rsCacheTemp = heavenTempstmtTemp.executeQuery(heavenfinalString);
//
//			
//            
//			Connection  heavenTempconnTempClose = heavenTempconn;
//			Statement heavenTempstmtTempClose = heavenTempstmt;
//			ResultSet rsCacheTempClose = rsCache;
//			                              
//			rsCache = rsCacheTemp;                
//			heavenTempconn = heavenTempconnTemp;
//			heavenTempstmt = heavenTempstmtTemp;
//			
//			if (rsCacheTempClose != null) {
//				rsCacheTempClose.close();
//			}				
//			
//			if (heavenTempstmtTempClose != null) {
//				heavenTempstmtTempClose.close();
//			}
//		
//			if (heavenTempconnTempClose != null ) {
//				heavenTempconnTempClose.close();
//			}
//	
//			//System.out.println("init cache!");
//		}
//		
//		
//	}
	
	
	/**
	 * heaven 临时特殊优化。
	 * @Description:
	 * @param context
	 * @return
	 * @throws:
	 * @author:Patrick.Guo
	 * @Date:2012-5-22上午10:10:27
	 * @version:1.0
	 * @Copyright: Copyright (c) 2012 Company:Centling
	 */
//	public String FetchHeavenDataSQL(FacesContext context){
//		
//		// 当前输入内容
//		Map requestMap = context.getExternalContext().getRequestParameterMap();
//		// 当前输入内容
//		String strContent = (String) requestMap.get("content"); 
//		String strret = "";
//				try {
//					
//
//					if (rsCache == null) {
//						heavenfinalString = SQL.replaceFirst("HEAVEN_REQ", "" );
//						initHeavenCache();
//					}else {
//						rsCache.beforeFirst();
//						//System.out.println("using cache!");
//					}
//
//			     	totalPageNum = String.valueOf(1);
//					strret += combineParams();
//					
//					int counter = 0;
//					
//					boolean bfirst = true;
//					StringBuffer whileBuffer = new StringBuffer();
//					
//					//Date temptime1 = new Date();
//					
//					
//					
//
//					// 结果集。。。
//					while(rsCache.next()){
//						if (bfirst) {
//							// 表头s
//							for (int j = 0; j < sqlResultItems.size(); j++) {
//								//strret += sqlResultItems.get(j).displaycolname;
//								whileBuffer.append(sqlResultItems.get(j).displaycolname);
//								
//								if (!sqlResultItems.get(j).bdisplay) {
//								//	strret+= "卍卐";
//									whileBuffer.append("卍卐");
//								}
//								if (j+1 < sqlResultItems.size()) {
//								//	strret +="\t";
//									whileBuffer.append("\t");
//								}
//							}
//							//strret += "\n";
//							whileBuffer.append("\n");
//							bfirst = false;
//						}
//						
//						String tempString = rsCache.getString(6).toLowerCase();
//						if (tempString.indexOf(strContent.toLowerCase()) > -1) {
//							counter ++ ;
//						}else {
//							continue;
//						}
//						
//						if (counter > MAXROWS.intValue()) {
//							break;
//						}
//						for (int i = 0; i < sqlResultItems.size(); i++) {
//							//System.out.println(displayItems.get(i).displaycolname + " : " + rs.getString(i+1));
//							String string = rsCache.getString(sqlResultItems.get(i).colIndex+1);
//							
//							if (rsCache.getObject(sqlResultItems.get(i).colIndex+1) == null) {
//								string = "";
//							}
//							
//							//strret +=  string;
//							whileBuffer.append(string);
//							//不可见列标志
//							if (!sqlResultItems.get(i).bdisplay) {
//								//strret+= "卍卐";
//								whileBuffer.append("卍卐");
//							}
//							
//							if (i+1<sqlResultItems.size()) {
//								//strret +=  "\t";
//								whileBuffer.append("\t");
//							}
//							//数据
//						}
//						
//						
//						//strret += "\n";
//						whileBuffer.append("\n");
//						//System.out.println("\n");
//					}
//					
//					//Date temptime2 = new Date();
//					
//					//System.out.println("match time cost:" + String.valueOf(temptime2.getTime() - temptime1.getTime()) + "ms");
//					
//					
//					strret += whileBuffer.toString();
//
//					//去掉最后一个\n
//					strret = strret.substring(0, strret.length()-1);
//
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	
//		return strret;
//		
//	}
	
	
	private void WriteRespones(FacesContext context,String str) {
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");

		 try {
		
		 response.getWriter().write(str);
		
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
	}

	/**
	 * 组合参数形成参数字符串
	 * @return
	 */
	public String combineParams(){
		
		String retString = "";
		//enable
		retString  += enable + "\t";
		
		//结果列
		retString += resultcol + "\t";
		//lock
		retString += lock + "\t";
		
		//multiresult
		retString += multiresult + "\t";
		
		//总页数
		retString += totalPageNum+"\t";
		
		retString += "\n";
		return retString;
		
	}
	
	
	
	/**
	 * 对SQL语句进行分页
	 * @param context
	 * @param rawSQL
	 * @return
	 */
	private String getPagedSQL(FacesContext context,String rawSQL){
		Map requestMap = context.getExternalContext().getRequestParameterMap();
		String strRet = "";
		
		//分页
		// 当前页
//		String strPageNum = (String) requestMap.get("curpage");
//		
//		Long curPageLong  = Long.valueOf(strPageNum);
//		Long startRow = (curPageLong - 1)*MAXROWS;
//		Long endRow = (curPageLong)*MAXROWS;
			
//		strRet = "select ROWNUM rowno,a.* from (" + rawSQL + ")a where ROWNUM <= " + endRow.toString();
//		strRet = "select * from (" + strRet + ") where ";
//		strRet += "rowno > " + startRow.toString();

		
		/*strRet = "select ROWNUM rowno,a.* from (" + rawSQL + ")a  ";
		strRet = "select * from (" + strRet + ") where ";
		strRet += "rowno > ?" ;
		strRet += " and rowno <= ?" ;
		*/
		
		System.out.println("rawSQL="+rawSQL);
		strRet = "select * from (" + rawSQL + ")a limit ?,? ";
		
		System.out.println("strRet="+strRet);
/*		strRet = "select * from (" + strRet + ") where ";
		strRet += "rowno > ?" ;
		strRet += " and rowno <= ?" ;*/
		
		
		//System.out.println("sqldata:" + finalstr);
		return strRet;
	}
	/**
	 * 取得SQL模式SQL语句
	 * @param context
	 * @return
	 */
	private String getSQLModeSQL(FacesContext context) {

		// Map requestMap =
		// context.getExternalContext().getRequestParameterMap();
		// 当前输入内容
		// String strContent = (String) requestMap.get("content");

		// 组装sql语句 //相似度排序
		String finalstr = "select t.*,(case";

		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < fitcols.size(); i++) {

			temp.append(" when ");

			temp.append("lower(");
			temp.append(fitcols.get(i));
			temp.append(") like lower(?) ");
			temp.append("then 0 ");

			temp.append(" when ");
			temp.append("lower(");
			temp.append(fitcols.get(i));
			temp.append(") like lower(?) ");
			temp.append("then 1 ");

		}

		temp.append(" ELSE 2 ");

		finalstr += temp.toString();

		finalstr += "END) sim48576328 from (" + SQL + ") t where (";

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < fitcols.size(); i++) {

			stringBuffer.append("lower(");
			stringBuffer.append(fitcols.get(i));
			stringBuffer.append(") like lower(?) ");
			if (i < fitcols.size() - 1) {
				stringBuffer.append(" or ");
			}
		}
		finalstr += stringBuffer.toString();
		finalstr += ")  order by sim48576328";

		// 排序

		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < sortcols.size(); i++) {

			strBuffer.append(",");
			strBuffer.append(sortcols.get(i));

		}

		finalstr += strBuffer.toString();
		return finalstr;
	}
	
	
	/**
	 * row sql 模式取得数据
	 * @param context
	 * @return
	 */
	private String FetchDataSQL(FacesContext context) {


		String strret = "";       //返回值

		ResultSet rs = null;
		// Statement stmt = null;
		PreparedStatement psTotal = null;
		PreparedStatement psContent = null;
		Connection conn = null;

		// 返回参数 resultcol \n
		try {

			conn = ds.getConnection();
			// stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String finalstr = getSQLModeSQL(context);

			Map requestMap = context.getExternalContext()
					.getRequestParameterMap();
			String strContent = (String) requestMap.get("content");
System.out.println("finalstr="+finalstr);
			String tempSql = "select count(*) as total from (" + finalstr+ ") v";        //总条数sql

			//String tempSql = "select count(*) as total from user_info where  user_id = ? ";        //总条数sql";  
			//String tempSql = " SELECT COUNT(*) AS total FROM (SELECT t.*,(CASE WHEN LOWER(user_id) LIKE LOWER(?) THEN 0  WHEN LOWER(user_id) LIKE LOWER(?) THEN 1  ELSE 2 END) sim48576328 FROM (SELECT * FROM user_info) t WHERE (LOWER(user_id) LIKE LOWER(?) )  ORDER BY sim48576328,user_id) v  ";        //总条数sql"; 
			psTotal = conn.prepareStatement(tempSql);

			int n = fitcols.size();

			//给参数set值
			for (int i = 1; i <= n * 2; i++) {
				if (i % 2 == 1) {
					psTotal.setString(i, strContent);
				} else {
					psTotal.setString(i, strContent + "%");
				}
			}
			for (int i = 1; i <= n; i++) {
				psTotal.setString(2 * n + i, "%" + strContent + "%");
			}

			// rs = stmt.executeQuery(tempSql);
			rs = psTotal.executeQuery();          //执行sql

			rs.next();
			String totalRowNumberString = rs.getString(1);

			int totalRowNum = Integer.valueOf(totalRowNumberString);
			int pageCount = (int) Math.ceil((double) totalRowNum/(double) MAXROWS);
			totalPageNum = String.valueOf(pageCount);

			finalstr = getPagedSQL(context, finalstr); // 分页sql
			//logger.debug("输入法SQL:" + finalstr);

			psContent = conn.prepareStatement(finalstr);

			for (int i = 1; i <= n * 2; i++) {
				if (i % 2 == 1) {
					psContent.setString(i, strContent);
				} else {
					psContent.setString(i, strContent + "%");
				}
			}
			for (int i = 1; i <= n; i++) {
				psContent.setString(2 * n + i, "%" + strContent + "%");
			}

			String strPageNum = (String) requestMap.get("curpage");
			Long curPageLong = Long.valueOf(strPageNum);
			Long startRow = (curPageLong - 1) * MAXROWS;
			Long endRow = MAXROWS;;//(curPageLong) * MAXROWS;

			psContent.setInt(3 * n + 1, startRow.intValue());
			psContent.setInt(3 * n + 2, endRow.intValue());
			//psContent.setString(3 * n + 1, startRow.toString());
			//psContent.setString(3 * n + 2, endRow.toString());
			System.out.println("#########################################");
			System.out.println("");
			System.out.println(psContent);
			System.out.println("");
			System.out.println("#########################################");
			rs = psContent.executeQuery();

			strret += combineParams();
			boolean bfirst = true;

			StringBuffer whileBuffer = new StringBuffer();
			// 结果集。。。
			while (rs.next()) {

				if (bfirst) {
					// 表头s
					for (int j = 0; j < sqlResultItems.size(); j++) {

						whileBuffer	.append(sqlResultItems.get(j).displaycolname);

						if (!sqlResultItems.get(j).bdisplay) {
							 
							whileBuffer.append("卍卐");
							
						} else {
							if (sqlResultItems.get(j).width != null	&& !sqlResultItems.get(j).width.equals("")) {

								whileBuffer.append("卐卍");
								whileBuffer.append(sqlResultItems.get(j).width);

							}
						}

						if (j + 1 < sqlResultItems.size()) {
						 
							whileBuffer.append("\t");
						}
					}
			 
					whileBuffer.append("\n");
					bfirst = false;
				}

				for (int i = 0; i < sqlResultItems.size(); i++) {
	 
//					String string = rs.getString(sqlResultItems.get(i).colIndex + 2);
//					if (rs.getObject(sqlResultItems.get(i).colIndex + 2) == null) {
//						string = "";
//					}

					String string = "";
					Object object = rs.getObject(sqlResultItems.get(i).colIndex + 1); //00000000000000000000000000000000000000
					if (object != null) {
						string = object.toString();
					}
					
					string = string.trim();
	 
					whileBuffer.append(string);
					// 不可见列标志
					if (!sqlResultItems.get(i).bdisplay) {
						// strret+= "卍卐";
						whileBuffer.append("卍卐");
					} else {
						if (sqlResultItems.get(i).width != null	&& !sqlResultItems.get(i).width.equals("")) {
							whileBuffer.append("卐卍");
							whileBuffer.append(sqlResultItems.get(i).width);
						}
					}

					if (i + 1 < sqlResultItems.size()) {

						whileBuffer.append("\t");
					}
					// 数据
				}
				// strret += "\n";
				whileBuffer.append("\n");
				// System.out.println("\n");
			}

			strret += whileBuffer.toString();

			// 去掉最后一个\n
			strret = strret.substring(0, strret.length() - 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (psTotal != null) {
			try {
				// stmt.close();
				psTotal.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// if (stmt != null) {
		if (psContent != null) {
			try {
				// stmt.close();
				psContent.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return strret;
	}
	
	/**
	 * data 模式取得sql数据
	 * @param context
	 * @return
	 */
	private String getDataModeSQL(FacesContext context) {

		// Map requestMap =
		// context.getExternalContext().getRequestParameterMap();
		// // 当前输入内容
		// String strContent = (String) requestMap.get("content");

		String strsqlString = "select ";

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < displayItems.size(); i++) {

			stringBuffer.append(displayItems.get(i).tcolname);
			if (i + 1 < displayItems.size()) {
				stringBuffer.append(",");
			}
		}
		
		strsqlString += stringBuffer.toString();

		strsqlString += " , (case ";
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < fitcols.size(); i++) {

			temp.append(" when ");
			temp.append("lower(");
			temp.append(fitcols.get(i));
			temp.append(") like lower(?) ");
			temp.append("then 0 ");

			temp.append(" when ");
			temp.append("lower(");
			temp.append(fitcols.get(i));
			temp.append(") like lower(?) ");
			temp.append("then 1 ");

		}

		temp.append(" ELSE 2 ");

		strsqlString += temp.toString();

		strsqlString += "END) sim48576328  from ";
		strsqlString += data;
		strsqlString += " where (";

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < fitcols.size(); i++) {

			buffer.append("lower(");
			buffer.append(fitcols.get(i));
			buffer.append(") like lower(?) ");

			if (i < fitcols.size() - 1) {
				// strsqlString += " or ";
				buffer.append(" or ");
			}
		}
		strsqlString += buffer.toString();

		strsqlString += ") order by sim48576328";

		// 排序
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < sortcols.size(); i++) {
			strBuffer.append(",");
			strBuffer.append(sortcols.get(i));
		}
		strsqlString += strBuffer.toString();

		return strsqlString;
	}
	
	private String FetchData(FacesContext context) {

		String strret = "";

		ResultSet rs = null;
		// Statement stmt = null;
		PreparedStatement psTotal = null;
		PreparedStatement psContent = null;
		Connection conn = null;

		Map requestMap = context.getExternalContext().getRequestParameterMap();
		// 当前输入内容
		String strContent = (String) requestMap.get("content");

		try {
			conn = ds.getConnection();
			// stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String strsqlString = getDataModeSQL(context);

			String tempSql = "select count(*) as total from (" + strsqlString + ") v";

			psTotal = conn.prepareStatement(tempSql);

			int n = fitcols.size();

			for (int i = 1; i <= n * 2; i++) {
				if (i % 2 == 1) {
					psTotal.setString(i, strContent);
				} else {
					psTotal.setString(i, strContent + "%");
				}
			}
			for (int i = 1; i <= n; i++) {
				psTotal.setString(2 * n + i, "%" + strContent + "%");
			}

			rs = psTotal.executeQuery();

			// rs = stmt.executeQuery(tempSql);
			rs.next();
			String totalRowNumberString = rs.getString(1);

			int totalRowNum = Integer.valueOf(totalRowNumberString);
			int pageCount = (int) Math.ceil((double) totalRowNum / (double) MAXROWS);
			totalPageNum = String.valueOf(pageCount);

			String strPageNum = (String) requestMap.get("curpage");
			Long curPageLong = Long.valueOf(strPageNum);
			Long startRow = (curPageLong - 1) * MAXROWS;
			Long endRow = MAXROWS;//(curPageLong) * MAXROWS;

			strsqlString = getPagedSQL(context, strsqlString);

			//logger.debug("输入法SQL:" + strsqlString);

			// rs = stmt.executeQuery(strsqlString);
			psContent = conn.prepareStatement(strsqlString);

			for (int i = 1; i <= n * 2; i++) {
				if (i % 2 == 1) {
					psContent.setString(i, strContent);
				} else {
					psContent.setString(i, strContent + "%");
				}
			}
			for (int i = 1; i <= n; i++) {
				psContent.setString(2 * n + i, "%" + strContent + "%");
			}
			psContent.setString(3 * n + 1, startRow.toString());
			psContent.setString(3 * n + 2, endRow.toString());

			rs = psContent.executeQuery();

			strret += combineParams();
			boolean bfirst = true;
			StringBuffer whileBuffer = new StringBuffer();
			// 结果集。。。
			while (rs.next()) {
				if (bfirst) {
					// 表头s
					for (int j = 0; j < displayItems.size(); j++) {
					 
						whileBuffer.append(displayItems.get(j).displaycolname);
						if (!displayItems.get(j).bdisplay) {
						 
							whileBuffer.append("卍卐");
						} else {
							if (displayItems.get(j).width != null && !displayItems.get(j).width.equals("")) {
								whileBuffer.append("卐卍");
								whileBuffer.append(displayItems.get(j).width);
							}
						}

						if (j + 1 < displayItems.size()) {
						 
							whileBuffer.append("\t");
						}
					}
					 
					whileBuffer.append("\n");
					bfirst = false;
				}

				for (int i = 0; i < displayItems.size(); i++) {
					 String string ="";
					Object obj = rs.getObject(i + 2);
					if (obj != null) {
						string = obj.toString().trim();
					}
					whileBuffer.append(string);

					// 不可见列标志
					if (!displayItems.get(i).bdisplay) {
					 
						whileBuffer.append("卍卐");
					} else {
						if (displayItems.get(i).width != null && !displayItems.get(i).width.equals("")) {
							whileBuffer.append("卐卍");
							whileBuffer.append(displayItems.get(i).width);
						}
					}

					if (i + 1 < displayItems.size()) {
					 
						whileBuffer.append("\t");
					}
					// 数据
				}
				 
				whileBuffer.append("\n");
			}

			strret += whileBuffer.toString();

			// 去掉最后一个\n
			strret = strret.substring(0, strret.length() - 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (psTotal != null) {
			try {
				// stmt.close();
				psTotal.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// if (stmt != null) {
		if (psContent != null) {
			try {
				// stmt.close();
				psContent.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return strret;
	}

	
	/**
	 * 取得表达式值
	 * @param context
	 * @return
	 */
	private String getAttr(FacesContext context,String attrName){
		
		ValueExpression valueExpression = getValueExpression(attrName);
		if (valueExpression != null) {
			//el 表达式
			Object object = valueExpression.getValue(context.getELContext());
			if (object != null) {
				return object.toString();
			}else {
				return "";
			}
		}else {
			Object object =  getAttributes().get(attrName);
			
			if (object != null) {
				return object.toString();
			}else {
				return "";
			}
		}
	}
	
	
	
	
	/**
	 * 取得标签设置属性
	 */
	private void InitAttributes(FacesContext context) {
		String forId = getAttr(context, "for");
		if (getAttr(context, "rawSQL").equals("true") ) {
			
			rawSQL = "true";
			
			//直接sql模式
			if (!getAttr(context, "SQL").equals("")) {
				SQL = getAttr(context, "SQL");
			}else {
			    
			    //logger.error("The SQL of "+forId+"is null");
				throw new IllegalArgumentException(
				"Component["+forId+"]"+"his:IME SQL must be specified!");
			}
			
			if (!getAttr(context, "SQLResult").equals("")) {
				
				String strSQLResult = getAttr(context, "SQLResult");
				sqlResultItems.clear();
				String strarray[] = strSQLResult.split(",");
				for (String string : strarray) {
					SQLResultItem sqlResultItem = new SQLResultItem();
					String stritem[] = string.split(":");
					
					sqlResultItem.displaycolname = stritem[0];
					sqlResultItem.colIndex = Integer.valueOf(stritem[1]);
					sqlResultItem.bdisplay  = true;
					
					if (stritem.length == 3) {
						if (stritem[2].equals("hide")) {
							sqlResultItem.bdisplay = false;
						}else{
							sqlResultItem.width = stritem[2];
						}
					}
					sqlResultItems.add(sqlResultItem);
				}
			}else {
				throw new IllegalArgumentException(
						"Component["+forId+"]"+"his:IME SQLResult must be specified!");
			}
		}
		else {
			//指定数据源模式
			if (!getAttr(context, "data").equals("")) {
				data = getAttr(context, "data");
			} else {
				throw new IllegalArgumentException(
						"Component["+forId+"]"+"his:IME data must be specified!");
			}
			
			if (!getAttr(context, "displaycol").equals("")) {
				String strdisplaycolString = getAttr(context, "displaycol");

				displayItems.clear();
				String strarray[] = strdisplaycolString.split(",");
				for (String string : strarray) {
					DisplayItem displayItem = new DisplayItem();
					String stritem[] = string.split(":");
					displayItem.displaycolname = stritem[0];
					displayItem.tcolname = stritem[1];
					displayItem.bdisplay  = true;
					if (stritem.length == 3) {
						if (stritem[2].equals("hide")) {
							displayItem.bdisplay = false;
						}else{
							displayItem.width=stritem[2];
						}
					}
					
					displayItems.add(displayItem);
				}
			}
			else {
				throw new IllegalArgumentException(
						"Component["+forId+"]"+"his:IME displaycol must be specified!");
			}
		}
		
		
		if (!getAttr(context, "for").equals("")) {
			forid = getAttr(context, "for");
		} else {
			throw new IllegalArgumentException(
					"Component["+forId+"]"+"his:IME forid must be specified!");
		}
		
		
		if (!getAttr(context, "fitcol").equals("")) {
			String fitcolstr = getAttr(context, "fitcol");
			fitcols.clear();
			String strarray[] = fitcolstr.split(",");
			for (String string : strarray) {
				fitcols.add(string);
			}
		}else {
			throw new IllegalArgumentException(
					"Component["+forId+"]"+"his:IME fitcol must be specified!");
		}
		
		
		
		
		if (!getAttr(context, "sortcol").equals("")) {
			String fitcolstr = getAttr(context, "sortcol");
			sortcols.clear();
			String strarray[] = fitcolstr.split(",");
			for (String string : strarray) {
				sortcols.add(string);
			}
		}else {
			sortcols.clear();
			//使用fitcols
			for (int i = 0; i < fitcols.size(); i++) {
				sortcols.add(fitcols.get(i));
			}
		}
		
		
//		if (!getAttr(context, "id").equals("")) {
//			id = getAttr(context, "id");
//		}

		if (!getAttr(context, "resultcol").equals("")) {
			resultcol = getAttr(context, "resultcol");
		}else{
			throw new IllegalArgumentException(
					"Component["+forId+"]"+"his:IME resultcol must be specified!");
		}
		
		if (!getAttr(context, "lock").equals("")) {
			lock = getAttr(context, "lock");
		}
		
		if (!getAttr(context, "multiresult").equals("")) {
			multiresult = getAttr(context, "multiresult");
		}
		
		if (!getAttr(context, "enable").equals("")) {
			enable = getAttr(context, "enable");
		}
		
	}

//	public static HisLogger<?> getLogger() {
//		return logger;
//	}
//
//	public static void setLogger(HisLogger<?> logger) {
//		UIIME.logger = logger;
//	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getResultcol() {
		return resultcol;
	}

	public void setResultcol(String resultcol) {
		this.resultcol = resultcol;
	}

	public String getForid() {
		return forid;
	}

	public void setForid(String forid) {
		this.forid = forid;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getMultiresult() {
		return multiresult;
	}

	public void setMultiresult(String multiresult) {
		this.multiresult = multiresult;
	}

	public String getRawSQL() {
		return rawSQL;
	}

	public void setRawSQL(String rawSQL) {
		this.rawSQL = rawSQL;
	}

	public String getSQL() {
		return SQL;
	}

	public void setSQL(String sQL) {
		SQL = sQL;
	}

//	public String getForname() {
//		return forname;
//	}
//
//	public void setForname(String forname) {
//		this.forname = forname;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getUser() {
//		return user;
//	}
//
//	public void setUser(String user) {
//		this.user = user;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public Long getMAXROWS() {
		return MAXROWS;
	}

	public void setMAXROWS(Long mAXROWS) {
		MAXROWS = mAXROWS;
	}

	public List<String> getFitcols() {
		return fitcols;
	}

	public void setFitcols(List<String> fitcols) {
		this.fitcols = fitcols;
	}

	public List<DisplayItem> getDisplayItems() {
		return displayItems;
	}

	public void setDisplayItems(List<DisplayItem> displayItems) {
		this.displayItems = displayItems;
	}

	public List<SQLResultItem> getSqlResultItems() {
		return sqlResultItems;
	}

	public void setSqlResultItems(List<SQLResultItem> sqlResultItems) {
		this.sqlResultItems = sqlResultItems;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getEnable() {
		return enable;
	}

	public void setSortcols(List<String> sortcols) {
		this.sortcols = sortcols;
	}

	public List<String> getSortcols() {
		return sortcols;
	}

}
