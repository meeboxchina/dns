package cn.meebox.dns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import cn.meebox.commons.HttpDownload;


/*
 * APNIC （Asia-Pacific Network Information Center的简称,中文:亚太互联网络信息中心）是世界中操作的五个地区的因特网登记处之一,分配B类IP地址的国际组织。属于开放性、会员制的非营利机构，其主要职责是确保IP地址和其他相关资源的公正分配以及负责管理。APNIC秘书处作为该机构的执行部门，负责维护公共APNICWhois数据库、管理储备DNS区域分派并提供资源认证服务。该机构通过开展培训和教育服务、为域名根服务器配置提供支持等技术活动以及与其他地区性和国际性组织合作等方式积极推动互联网的发展。APNIC负责管理数字互联网资源，但不负责注册域名。它提供全球性的支持互联网操作的分派和注册服务。这些成员包括网络服务提供商、全国互联网登记, 和相似的组织的一个非营利, 基于会员资格的组织。APNIC 负责亚洲太平洋区域,包含 56 经济区。
Internet的IP地址和AS号码分配是分级进行的。ICANN (The Internet Corporation for Assigned Names and Numbers)，负责全球Internet上的IP地址进行编号分配的机构(原来是由IANA负责)。 根据ICANN的规定，ICANN将部分IP地址分配给地区级的Internet注册机构 (Regional Internet Registry),然后由这些RIR负责该地区的登记注册服务。
全球一共有5个RIR：ARIN,RIPE,APNIC,LACNIC,AfriNIC. ARIN主要负责北美地区业务，RIPE主要负责欧洲地区业务，LACNIC主要负责拉丁美洲美洲业务，AfriNIC负责非洲地区业务，亚太地区国家的IP地址和AS号码分配由APNIC管理。在RIR之下还可以存在一些注册机构, 如：国家级注册机构(NIR)，普通地区级注册机构(LIR)。这些注册机构都可以从APNIC那里得到Internet地址及号码, 并可以向其各自的下级进行分配。
 */

public class RIR {
	//全球一共有5个RIR：ARIN,RIPE,APNIC,LACNIC,AfriNIC.
	private static final String ARIN = "ARIN";         //ARIN主要负责北美地区业务
	private static final String RIPE = "RIPE";         //RIPE主要负责欧洲地区业务
	private static final String APNIC = "APNIC";       //APNIC负责管理亚太地区国家的IP地址和AS号码分配
	private static final String URLAPNIC = "http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest";
	private static final String LACNIC = "LACNIC";     //LACNIC主要负责拉丁美洲美洲业务
	private static final String AfriNIC = "AfriNIC";   //AfriNIC负责非洲地区业务
	
	private String dest;
	private String filepath;
	
	
	public RIR() {
	}
	
	public void getAPNIC() throws ClientProtocolException, IOException{
		
		HttpDownload apnic = new HttpDownload(URLAPNIC,dest);
		this.filepath = apnic.down();
	}
	
	public void import2DB() throws IOException{
		//String regex = "^[a-z]{3,5}\\|[A-Z]{2}\\|(ipv4)\\|.*";
		String regex = "^[a-z]{3,5}\\|CN\\|(ipv4)\\|.*";
		Pattern  pattern = Pattern.compile(regex);
		
		File file = new File(this.filepath);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isr);
		
		String line = null;
		Matcher matcher = null;
		while((line = br.readLine()) != null){
			matcher = pattern.matcher(line);
			if(matcher.find()){
				//System.out.print(line);
				String[] net = line.split("[|]");
				System.out.println(net[3]);
			}
		}
	
	}
	
}
