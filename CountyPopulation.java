package com.svail.population_mobility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import com.svail.gridprocess.DataPoint;
import com.svail.gridprocess.Price;
import com.svail.population_mobility.CountyPopulation;
import com.svail.test.Demo;
import com.svail.util.FileTool;
import com.svail.util.Tool;

public class PopulationStatistics {
	public static String code;
	public static String countyname;
	public static String countyLN;
	public static String countyLA;
	public static String countyCoor;

	public static void main(String[] args) throws IOException {
		// SearchCode();
		countTotalAmount("D:/人口数据/Task/countFlowout-tidy-countAmounts.txt");
		System.out.println("ok!");
	}

	/**
	 * 统计最终一个区县的人流动到另外一个区县去的总人数，将重复的流向区县的数目叠加
	 * @param folder
	 */
	public static void countTotalAmount(String folder) {

		String poi ="";
		try{
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				String index="";
				if (a == 0) {
					//if(!(from.equals(to))){
						map.put(to, amount);
				//	}
					
				}else {
					index=Tool.getStrByKey(Pois.elementAt(a-1), "<from>", "</from>", "</from>");
					if(from.equals(index)){
					//对每个区域逐个统计	
						//if(!(from.equals(to))){
							map.put(to, amount);
					//	}
                        

						int s=Pois.size();
						if((a+1)==s){
							int[] totalAmounts=new int[map.size()];
							int counts=0;
							String key ="";
							int Total=0;
							for (Map.Entry<String, Integer> entry : map.entrySet()) {
								//key = entry.getKey().toString();
								//String value = entry.getValue().toString();
								int value = entry.getValue();
								totalAmounts[counts]=value;
								counts++;
								
								//String str="<from>"+key+"</from>"+"<to>"+index+"</to>"+"<amounts>"+value+"</amounts>";
								//System.out.println(str);
								//FileTool.Dump(str, folder.replace(".txt", "")+"-countAmounts.txt", "utf-8");
							}
							for(int i=0;i<totalAmounts.length;i++){
								Total+=totalAmounts[i];
							}
							String str="<from>"+index+"</from>"+"<amounts>"+Total+"</amounts>";
							System.out.println(str);
							FileTool.Dump(str, folder.replace(".txt", "")+"-countTotalAmounts.txt", "utf-8");
						}
					
					}else{
						int[] totalAmounts=new int[map.size()];
						int counts=0;
						String key ="";
						int Total=0;
						for (Map.Entry<String, Integer> entry : map.entrySet()) {
							//key = entry.getKey().toString();
							//String value = entry.getValue().toString();
							int value = entry.getValue();
							totalAmounts[counts]=value;
							counts++;
							
							//String str="<from>"+key+"</from>"+"<to>"+index+"</to>"+"<amounts>"+value+"</amounts>";
							//System.out.println(str);
							//FileTool.Dump(str, folder.replace(".txt", "")+"-countAmounts.txt", "utf-8");
						}
						for(int i=0;i<totalAmounts.length;i++){
							Total+=totalAmounts[i];
						}
						String str="<from>"+index+"</from>"+"<amounts>"+Total+"</amounts>";
						System.out.println(str);
						FileTool.Dump(str, folder.replace(".txt", "")+"-countTotalAmounts.txt", "utf-8");
					
						map.clear();
					//	if(!(from.equals(to))){
							map.put(to, amount);
					//	}
						
					}
					
				}
			}
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "")+"-exception.txt", "utf-8");
		}
		
	
		
	}

	public static void countAmount(String folder) {
		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amount>", "</amount>", "</amount>"));
				String index = "";
				if (a == 0) {
					map.put(from, amount);
				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<to>", "</to>", "</to>");
					if (to.equals(index)) {
						// 对每个区域逐个统计
						int count = 0;
						for (int b = 0; b < a; b++) {
							String probe = Tool.getStrByKey(Pois.elementAt(b), "<from>", "</from>", "</from>");
							if (from.equals(probe)) {
								if (map.get(probe) != null) {
									int s = map.get(probe);
									map.put(probe, s + amount);
									break;
								} else {
									map.put(from, amount);
								}
							} else {
								count++;
							}
						}
						if (count == a) {
							map.put(from, amount);
						}
						int s = Pois.size();
						if ((a + 1) == s) {
							int[] totalAmounts = new int[s];
							int counts = 0;
							String key = "";
							int Total = 0;
							for (Map.Entry<String, Integer> entry : map.entrySet()) {
								key = entry.getKey().toString();
								// String value = entry.getValue().toString();
								int value = entry.getValue();
								totalAmounts[counts] = value;
								counts++;

								// String
								// str="<from>"+key+"</from>"+"<to>"+index+"</to>"+"<amounts>"+value+"</amounts>";
								// System.out.println(str);
								// FileTool.Dump(str, folder.replace(".txt",
								// "")+"-countAmounts.txt", "utf-8");
							}
							for (int i = 0; i < totalAmounts.length; i++) {
								Total += totalAmounts[i];
							}
							String str = "<from>" + key + "</from>" + "<amounts>" + Total + "</amounts>";
							System.out.println(str);
							FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");
						}

					} else {
						int[] totalAmounts = new int[map.size()];
						int counts = 0;
						String key = "";
						int Total = 0;
						for (Map.Entry<String, Integer> entry : map.entrySet()) {
							key = entry.getKey().toString();
							// String value = entry.getValue().toString();
							int value = entry.getValue();
							totalAmounts[counts] = value;
							counts++;

							// String
							// str="<from>"+key+"</from>"+"<to>"+index+"</to>"+"<amounts>"+value+"</amounts>";
							// System.out.println(str);
							// FileTool.Dump(str, folder.replace(".txt",
							// "")+"-countAmounts.txt", "utf-8");
						}
						for (int i = 0; i < totalAmounts.length; i++) {
							Total += totalAmounts[i];
						}
						String str = "<from>" + key + "</from>" + "<amounts>" + Total + "</amounts>";
						System.out.println(str);
						FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");

						map.clear();
						map.put(from, amount);
					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}

	/**
	 * 按照区县顺序整理从某个区县流动到另外一个区县的记录，这里不包括统一目的地的数量叠加
	 * 
	 * @param codefolder
	 * @param countfoder
	 */
	public static void countTo(String codefolder, String countfoder) {
		try {
			File file = new File(codefolder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;
			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {

				Vector<String> countfile = FileTool.Load(countfoder, "utf-8");
				for (int i = 0; i < countfile.size(); i++) {
					String poi = countfile.elementAt(i);
					Demo demo = new Demo(poi);
					String code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
					if (code.equals(demo.from)) {
						System.out.println(poi);
						FileTool.Dump(poi, countfoder.replace(".txt", "") + "-tidy.txt", "utf-8");
					}

				}

			}
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 将人口数据转成Jason格式
	 * 
	 * @param folder
	 */
	public static void productJson(String folder) {

		JSONObject jsonObj = new JSONObject();// 创建json格式的数据
		JSONArray jsonArr = new JSONArray();// json格式的数组

		try {
			File file = new File(folder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				Demo demo = new Demo(tempString);
				JSONObject jsonObjArr = new JSONObject();

				jsonObjArr.put("from", demo.code);
				jsonObjArr.put("to", demo.PostCode);

			}

			// 将json格式的数据放到json格式的数组里

			// jsonObj.put("Points", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

			// System.out.println(jsonArr);
			System.out.println("开始写入txt中");
			FileTool.Dump(jsonArr.toString(), folder + "peopleJson .txt", "utf-8");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 统计最终一个区县的人流动到另外一个区县去的总人数，将重复的流向区县的数目叠加,不过该算法太慢了，果断抛弃
	 * 
	 * @param codefolder
	 * @param countfile
	 */
	public static void countAmounts(String codefolder, String countfile) {
		try {
			String Code = "";
			File file = new File(codefolder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				Code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
				Vector<String> CodePois = FileTool.Load(codefolder, "utf-8");
				for (int i = 0; i < CodePois.size(); i++) {
					String probe = Tool.getStrByKey(CodePois.elementAt(i), "<Code>", "</Code>", "</Code>");
					Vector<String> countFile = FileTool.Load(countfile, "utf-8");
					int amounts = 0;
					for (int k = 0; k < countFile.size(); k++) {
						String poi = countFile.elementAt(k);
						String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
						String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>").replace(" ", "");
						String amount = Tool.getStrByKey(poi, "<amount>", "</amount>", "</amount>");
						int n = Integer.parseInt(amount);
						// System.out.println(to);
						// System.out.println(Code);
						if (from.equals(Code)) {
							if (probe.equals(to)) {
								amounts += n;
							}
						}
					}
					if (amounts != 0) {
						String total = "<from>" + Code + "</from>" + "<to>" + probe + "</to>" + "<amounts>" + amounts
								+ "</amounts>";
						FileTool.Dump(total, countfile.replace(".txt", "") + "-countAmounts.txt", "utf-8");
					}
					System.out.println("完成" + Code + "区域" + probe + "的统计");
				}

				// System.out.println("");
			}
			reader.close();
		} catch (NullPointerException e1) {
			e1.printStackTrace();
			e1.getMessage();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询某个区县的人口情况
	 * 
	 * @throws IOException
	 */
	public static void SearchCode() throws IOException {

		String folder1 = "D:/人口数据/";
		for (int j = 1; j <= 89; j++) {
			System.out.println("第" + j + "次将区县数据存到CountyPopulation中：");
			setCounty("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult .txt");

			System.out.println("开始处理第" + j + "个文件：");
			String f = folder1 + j + "-postCode.txt";
			ClassifyStatistic(f);

			System.out.println("开始逐个查询：");
			Vector<String> codes = FileTool.Load("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult .txt", "utf-8");
			for (int s = 0; s < codes.size(); s++) {
				String code = Tool.getStrByKey(codes.elementAt(s), "<Code>", "</Code>", "</Code>");
				for (int i = 0; i < county.size(); i++) {
					if (county.get(i).code.equals(code)) {
						// 查询该地区的户籍人口
						// System.out.println(code+"地区的户籍人口：");
						for (int k = 0; k < county.get(i).homepois.size(); k++) {

							String homeStr = county.get(i).homepois.get(k);
							// System.out.println(homeStr);

						}
						// System.out.println(county.get(i).getpostCodes());
						String postCodes = county.get(i).getpostCodes().toString().replace(", ", ",").replace("[", "")
								.replace("]", "").replace(" ", "");
						countFlowout(postCodes, county.get(i).code, folder1);

						// 查询拥有该地区通讯地址的人口
						// System.out.println("拥有"+code+"地区通讯地址的人口：");
						for (int k = 0; k < county.get(i).postpois.size(); k++) {

							// System.out.println(county.get(i).postpois.get(k));

						}
						// System.out.println(county.get(i).gethomeCodes());
						String homeCodes = county.get(i).gethomeCodes().toString().replace(", ", ",").replace("[", "")
								.replace("]", "").replace(" ", "");
						countFlowin(homeCodes, county.get(i).code, folder1);

					}
				}
			}
			county.clear();
			System.out.println("第" + j + "次清空county");
		}
		// ClassifyStatistic("D:/人口数据/1-postCode.txt");
		System.out.println("OK!");

		// 从CountyPopulation中查询所需要的数据
		// System.out.println("输入所要查询的区县的行政代码：");
		// Scanner input = new Scanner(System.in);
		// String code = input.next();

	}

	/**
	 * 统计某个区县流向各个地区的人数
	 * 
	 * @param line
	 *            流出去的区县的行政代码集合
	 * @param code
	 *            该县的行政代码
	 */
	public static void countFlowout(String line, String code, String folder) {
		if (line.indexOf(",") != -1) {
			String[] Code = line.split(",");
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String i : Code) {
				if (map.get(i) != null) {
					map.put(i, map.get(i) + 1);
				} else {
					map.put(i, 1);
				}
			}
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() > 0) {
					// System.out.println("字符串："+entry.getKey()+";次数:"+entry.getValue());
					String str = "<from>" + code + "</from>" + "<to>" + entry.getKey() + "</to>" + "<amount>"
							+ entry.getValue() + "</amount>";
					// System.out.println(str);
					FileTool.Dump(str, folder + "countFlowout.txt", "utf-8");
				}
			}
		}

	}

	/**
	 * 统计流入该县的人口主要来自于哪些地区
	 * 
	 * @param line
	 *            流入该县的人口来源地的行政区划代码集合
	 * @param code
	 *            该区县的行政代码
	 */
	public static void countFlowin(String line, String code, String folder) {
		if (line.indexOf(",") != -1) {
			String[] Code = line.split(",");
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String i : Code) {
				if (map.get(i) != null) {
					map.put(i, map.get(i) + 1);
				} else {
					map.put(i, 1);
				}
			}
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() > 0) {
					// System.out.println("字符串："+entry.getKey()+";次数:"+entry.getValue());
					String str = "<from>" + entry.getKey() + "</from>" + "<to>" + code + "</to>" + "<amount>"
							+ entry.getValue() + "</amount>";
					// System.out.println(str);
					FileTool.Dump(str, folder + "countFlowin.txt", "utf-8");
				}
			}
		}

	}

	/**
	 * 将每个区县定义成一个county
	 */
	public static ArrayList<CountyPopulation> county = new ArrayList<CountyPopulation>();

	/**
	 * 对每个county进行数据的填充
	 */
	public static void addCountyPopulation(CountyPopulation cp) {
		county.add(cp);

	}

	public static void setCounty(String folder) {

		try {
			// 设置每个区县的区县代码、区县名字以及坐标
			File file = new File(folder);
			FileInputStream fis;
			fis = new FileInputStream(file);

			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
				countyname = Tool.getStrByKey(tempString, "<CodeAddr>", "</CodeAddr>", "</CodeAddr>");
				countyCoor = Tool.getStrByKey(tempString, "<CodeCoor>", "</CodeCoor>", "</CodeCoor>");
				String[] coor = countyCoor.split(";");
				countyLN = coor[0];
				countyLA = coor[1];
				CountyPopulation cp = new CountyPopulation();
				cp.setCode(code);
				cp.setCountyname(countyname);
				double l = Double.parseDouble(countyLN);
				cp.setLongitude(l);
				cp.setLatitude(Double.parseDouble(countyLA));
				addCountyPopulation(cp);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 分区县统计每个区县的人口记录
	 * 
	 * @param folder
	 * @throws IOException
	 */
	public static void ClassifyStatistic(String folder) throws IOException {
		// 将人口的户籍地址的代码分配到各个区县
		File file1 = new File(folder);

		FileInputStream fis1 = new FileInputStream(file1);
		InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");
		BufferedReader reader1 = null;
		String tempString1 = null;

		reader1 = new BufferedReader(isr1);
		while ((tempString1 = reader1.readLine()) != null) {

			String admincode = Tool.getStrByKey(tempString1, "<Code>", "</Code>", "</Code>");
			String postcode = Tool.getStrByKey(tempString1, "<PostCode>", "</PostCode>", "</PostCode>");
			for (int i = 0; i < county.size(); i++) {
				String Code = county.get(i).code;
				if (admincode.equals(Code)) {
					county.get(i).sethomePois(tempString1);
					county.get(i).setpostCodes(postcode);
					break;
				}
			}
			for (int i = 0; i < county.size(); i++) {
				String Code = county.get(i).code;
				if (postcode.equals(Code)) {
					county.get(i).setpostPois(tempString1);
					county.get(i).sethomeCodes(admincode);
					break;
				}
			}

		}
		reader1.close();

	}

}
