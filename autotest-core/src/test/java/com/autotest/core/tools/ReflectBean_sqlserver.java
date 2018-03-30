package com.autotest.core.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
/*
 * 根据表名生成对应类的属性
 */

public class ReflectBean_sqlserver {
	private Connection connection;
	private PreparedStatement UserQuery;
	/*sqlserver url的连接字符串*/
	private static String url = "jdbc:sqlserver://CarOrder.db.uat.qa.nt.ctripcorp.com:55777;DatabaseName=CarOrderdb";
	//账号
	private static String user = "_temp_carorderdb_safe_20170515";
	//密码
	private static String password = "21396e39f46423ddc1f2Safe";
	private Vector<String> vector = new Vector<String>();
	//sqlserver jdbc的java包驱动字符串
	private String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//数据库中的表名
	String table = "Ord_CustomerInfo";
	//输出文件目录
	String outPath=table+".java";
	//数据库的列名称
	private String[] colnames; // 列名数组
	//列名类型数组  
	private String[] colTypes;
	public ReflectBean_sqlserver(){
		try {//驱动注册
			Class.forName(driverClassName);
			if (connection == null || connection.isClosed())
				//获得链接
				connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				System.out.println("Oh,not");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Oh,not");
			}
	}
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void doAction(){
		String sql = "select * from "+table;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			//获取数据库的元数据 
			ResultSetMetaData metadata = statement.getMetaData();
			//数据库的字段个数
			int len = metadata.getColumnCount();
			//字段名称
			colnames = new String[len+1];
			//字段类型 --->已经转化为java中的类名称了
			colTypes = new String[len+1];
			for(int i= 1;i<=len;i++){
				//System.out.println(metadata.getColumnName(i)+":"+metadata.getColumnTypeName(i)+":"+sqlType2JavaType(metadata.getColumnTypeName(i).toLowerCase())+":"+metadata.getColumnDisplaySize(i));
				//metadata.getColumnDisplaySize(i);
				colnames[i] = metadata.getColumnName(i).substring(0,1).toLowerCase()+metadata.getColumnName(i).substring(1); //获取字段名称
				colTypes[i] = sqlType2JavaType(metadata.getColumnTypeName(i)); //获取字段类型		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * mysql的字段类型转化为java的类型*/
	private String sqlType2JavaType(String sqlType) {  
        
        if(sqlType.equalsIgnoreCase("bit")){  
            return "boolean";  
        }else if(sqlType.equalsIgnoreCase("tinyint")){  
            return "byte";  
        }else if(sqlType.equalsIgnoreCase("smallint")){  
            return "short";  
        }else if(sqlType.equalsIgnoreCase("int")){  
            return "int";  
        }else if(sqlType.equalsIgnoreCase("bigint")){  
            return "long";  
        }else if(sqlType.equalsIgnoreCase("float")){  
            return "float";  
        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")   
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")   
                || sqlType.equalsIgnoreCase("smallmoney")){  
            return "double";  
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")   
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")   
                || sqlType.equalsIgnoreCase("text")){  
            return "String";  
        }else if(sqlType.equalsIgnoreCase("datetime") ||sqlType.equalsIgnoreCase("date")){  
            return "Date";  
        }else if(sqlType.equalsIgnoreCase("image")){  
            return "Blod";  
        }  
          
        return null;  
    }
	/*获取整个类的字符串并且输出为java文件
	 * */
	public  StringBuffer getClassStr(){
		//输出的类字符串
		StringBuffer str = new StringBuffer("");
		//获取表类型和表名的字段名
		this.doAction();
		//校验
		if(null == colnames && null == colTypes) return null;
		//拼接
		str.append("public class "+table+" {\r\n");
		//拼接属性
		for(int index=1; index < colnames.length ; index++){
			str.append("	private ");
			str.append(getAttrbuteString(colnames[index],colTypes[index]));
		}
		//拼接get，Set方法		
		for(int index=1; index < colnames.length ; index++){
			str.append(getGetMethodString(colnames[index],colTypes[index]));
			str.append(getSetMethodString(colnames[index],colTypes[index]));
		}
		str.append("}\r\n");
		//输出到文件中
		if(outPath.equals("")) return str;
		File file = new File(outPath);
		BufferedWriter write = null;

		try {
			write = new BufferedWriter(new FileWriter(file));
			write.write(str.toString());
			write.close();
		} catch (IOException e) {

			e.printStackTrace();
			if (write != null)
				try {
					write.close();
				} catch (IOException e1) {			
					e1.printStackTrace();
				}
		}
		return str;
	}
	
	/*获取字段名=#{字段名}
	 * */
	public  StringBuffer getMyBatisStrByUpdate(){
		//输出的类字符串
		StringBuffer str = new StringBuffer("");
		//获取表类型和表名的字段名
		this.doAction();
		//校验
		if(null == colnames) return null;
		//拼接属性
		for(int index=1; index < colnames.length ; index++){
			str.append(getAttrbuteString("= #{"+colnames[index]+"}",colnames[index]));
		}
		//str.append("}\r\n");
		return str;
	}
	
	
	/*获取字段名=#{字段名}
	 * */
	public  StringBuffer[] getMyBatisStrByInsert(){
		StringBuffer[] s=new StringBuffer[2];
		//输出的类字符串
		StringBuffer str1 = new StringBuffer("");
		StringBuffer str2 = new StringBuffer("");
		//获取表类型和表名的字段名
		this.doAction();
		//校验
		if(null == colnames) return null;
		//拼接属性
		for(int index=1; index < colnames.length ; index++){
			str1.append(colnames[index]+",");
		}
		for(int index=1; index < colnames.length ; index++){
			str2.append("#{"+colnames[index]+"},");
		}
		s[0]=str1;
		s[1]=str2;
		return s;
	}
	
	/*
	 * 获取字段字符串*/
	public StringBuffer getAttrbuteString(String name, String type) {
		if(!check(name,type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		};
		//String format = String.format("    private %s %s;\n\r", new String[]{type,name});
		String format = String.format("%s %s,\n", new String[]{type,name});
		return new StringBuffer(format);
	}
	/*
	 * 校验name和type是否合法*/
	public boolean check(String name, String type) {
		if("".equals(name) || name == null || name.trim().length() ==0){
			return false;
		}
		if("".equals(type) || type == null || type.trim().length() ==0){
			return false;
		}
		return true;
		
	}
	/*
	 * 获取get方法字符串*/
	private StringBuffer getGetMethodString(String name, String type) {
		if(!check(name,type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		};
		String Methodname = "get"+GetTuoFeng(name);
		String format = String.format("	public %s %s(){\n\r", new Object[]{type,Methodname});
		format += String.format("		return this.%s;\r\n", new Object[]{name});
		format += "    }\r\n";
		return new StringBuffer(format);
	}
	//将名称首字符大写
	private String GetTuoFeng(String name) {
		name = name.trim();
		if(name.length() > 1){
			name = name.substring(0, 1).toUpperCase()+name.substring(1);
		}else
		{
			name = name.toUpperCase();
		}
		return name;
	}
	/*
	 * 获取字段的get方法字符串*/
	private Object getSetMethodString(String name, String type) {
		if(!check(name,type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		};
		String Methodname = "set"+GetTuoFeng(name);
		String format = String.format("	public void %s(%s %s){\n\r", new Object[]{Methodname,type,name});
		format += String.format("		this.%s = %s;\r\n", new Object[]{name,name});
		format += "    }\r\n";
		return new StringBuffer(format);
	}

	public static void main(String[] args) {
		ReflectBean_sqlserver bean = new ReflectBean_sqlserver();
		//生成对象的属性
		//System.err.println(bean.getClassStr());
		//生成mybatis配置文件用的字符串给update使用
		System.out.println(bean.getMyBatisStrByUpdate());
		//生成mybatis配置文件用的字符串给insert使用
		System.out.println(bean.getMyBatisStrByInsert()[0]);
		System.out.println(bean.getMyBatisStrByInsert()[1]);
	}
}
