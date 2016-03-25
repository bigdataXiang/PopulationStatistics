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
import java.util.List;
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
	public static String codeReg;

	public static void main(String[] args) throws IOException {
		// SearchCode();
		// countTotalAmount("D:/人口数据/Task/countFlowout-tidy-countAmounts.txt");

		// getSortFlow("D:/人口数据/Task/countFlowin-tidy-countAmounts.txt");

		// getMainIngredients("D:/人口数据/Task/countFlowout-tidy-countAmounts-sort.txt");

		// ProductJson("D:/人口数据/Task/countFlowout-tidy-countAmounts-sort-MainIngredients.txt");

		// getMax("D:/人口数据/Task/countFlowin-tidy-countAmounts-sort-MainIngredients.txt");

		// CheckRepeatCode("D:/人口数据/Task/排查重复code/countFlowin-tidy-countAmounts-sort-MainIngredients-Max.txt");
		
		//GatherBigCity("D:/人口数据/Task/汇总大城市各区/countFlowin-tidy-countAmounts-sort.txt");
		
		countAmount("D:/人口数据/4级数据-统计某区县流入或者流出的另一区县的总人口/countFlowin-tidy.txt");

		System.out.println("ok!");
	}
	/**
	 * 比较两个文件中的记录是否相同，并且将相同的记录写下来，从而找出两个文件是否存在不相同的记录
	 * @param folder1
	 * @param folder2
	 */
		public static void CompareAmouts(String folder1, String folder2) {
			Map<String, Integer> map1 = new HashMap<String, Integer>();
			Map<String, Integer> map2 = new HashMap<String, Integer>();
			String poi1 = "";
			String poi2 = "";
			Vector<String> Pois1 = FileTool.Load(folder1, "utf-8");
			for (int a1 = 0; a1 < Pois1.size(); a1++) {
				poi1 = Pois1.elementAt(a1);
				String[] arr1 = poi1.split(":");
				map1.put(arr1[0], Integer.parseInt(arr1[1]));
			}
			Vector<String> Pois2 = FileTool.Load(folder2, "utf-8");
			for (int a2 = 0; a2 < Pois2.size(); a2++) {
				poi2 = Pois2.elementAt(a2);
				String[] arr2 = poi2.split(":");
				if(map2.get(arr2[0])!=null){
					int s=map2.get(arr2[0]);
					System.out.println(arr2[0]+":"+s+arr2[1]);
					map2.put(arr2[0], Integer.parseInt(s+arr2[1]));
				}else{
					map2.put(arr2[0], Integer.parseInt(arr2[1]));
				}
			    map2.put(arr2[0], Integer.parseInt(arr2[1]));
			}
				Iterator<String> it1 = map1.keySet().iterator();
				System.out.println("map1.size()="+map1.size());
				while (it1.hasNext()) {

					String key1;
					int value1;

					key1 = (String) it1.next();
					value1 = map1.get(key1);

					//System.out.println("map1-" + key1 + ":" + value1);
					Iterator<String> it2 = map2.keySet().iterator();
					System.out.println("map2.size()="+map2.size());
					while (it2.hasNext()) {
						String key2;
						int value2;
						key2 = (String) it2.next();
						value2 = map2.get(key2);
						//System.out.println("map2-" + key2 + ":" + value2);
						if ((key1.equals(key2)) && (value1 == value2)) {
							//FileTool.Dump(key1 + ":" + value1, "D:/人口数据/3级数据-统计人口流入流出数据/key1=key2.txt", "utf-8");
							//FileTool.Dump(key2 + ":" + value2, "D:/人口数据/3级数据-统计人口流入流出数据/key2.txt", "utf-8");
							it2.remove();
							break;
						}else if((key1.equals(key2)) && (value1 != value2)){
							System.out.println("map1-" + key1 + ":" + value1);
							System.out.println("map2-" + key2 + ":" + value2);
							break;
	                     }
					}
	               

				}
			}
	/**
	 * 通过检查countFlowout.txt文件中每个区县的poi条数来找出多出的poi
	 */
	public static void Check_countFlowout(String folder){
		String poi="";
		setCounty("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt");
		Vector<String> Pois = FileTool.Load(folder, "utf-8");
		for (int a = 0; a < Pois.size(); a++) {
			poi = Pois.elementAt(a);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			for (int i = 0; i < county.size(); i++) {
				if(from.equals(county.get(i).code)){
					county.get(i).setPoiamounts(1);
				}
			}
		}
		int acount=0;
		for (int i = 0; i < county.size(); i++) {
			System.out.println(county.get(i).poiamounts);
			
			for(int k=0;k<county.get(i).poiamounts.size();k++){
				acount+=county.get(i).poiamounts.get(k);	
			}
			System.out.println(county.get(i).code+":"+acount);
			FileTool.Dump(county.get(i).code+":"+acount, folder+"", "utf-8");
			acount=0;
		}
	}
	/**
	 * 通过检查-tidy.txt文件中每个区县的poi条数来找出多出的poi
	 */
	public static void Check_countFlowout_tidy(String folder){
		String poi = "";
		int count=1;
		Vector<String> Pois = FileTool.Load(folder, "utf-8");
		for (int a = 0; a < Pois.size(); a++) {
			poi = Pois.elementAt(a);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String beforefrom="";
			if(a>0){
				beforefrom=Tool.getStrByKey(Pois.elementAt(a-1), "<from>", "</from>", "</from>");
				if(from.equals(beforefrom)){
					count++;
				}else{
					System.out.println(beforefrom+":"+count);
					count=0;
				}
			}
		}
	}
	
	/**
	 *利用countFlowin-tidy-countAmounts-sort.txt文件和bigcityCode.txt文件，将主要省会城市的code合并起来，重新对数据进行统计
	 * @param folder
	 */

	public static void GatherBigCity(String folder) {
		String poi = "";
		// Tool.ID_Hashtable("D:/人口数据/Task/汇总大城市各区/bigcityCode.txt");
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			int tempNum=0;
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				int tolen=to.length();
				String subto="";
				int sub;
				if((to.indexOf("110100")!=-1)||(to.indexOf("5132")!=-1)){
					 subto=to.substring(1, tolen-2);
					 sub=Integer.parseInt(subto);
				}else{
					subto=to.substring(0,4);
					sub=Integer.parseInt(subto);
				}
				
				
				int citycode ;
				String city = "";
				Vector<String> CityPois = FileTool.Load("D:/人口数据/Task/汇总大城市各区/bigcityCode.txt", "utf-8");
				int tag=0;
				int CityPoisindex;
				for (CityPoisindex=0; CityPoisindex < CityPois.size();) {
					//判断sub是否已经找到过合适的code，如果找到了则不执行if里面的语句，没找到才执行
					if(tag==0){
						city = CityPois.elementAt(CityPoisindex);
						citycode = Integer.parseInt(Tool.getStrByKey(city, "<code>", "</code>", "</code>"));				
						if (sub==citycode) {
							tag++;   //表示该sub找到了合适的code
							// 对每个区域逐个统计
							int count = 0;
							for (int b =0; b < a; b++) {
								String probe = Tool.getStrByKey(Pois.elementAt(b), "<from>", "</from>", "</from>");
								if (from.equals(probe)){
									if ((map.get(probe) != null)) {//如果前一组数中有两个code与这一组数的code相同，则会有问题
										int s = map.get(probe);
										map.put(probe, s + amount);
										tempNum++;    //表示该poi被处理
										break;              //跳出最里层循环
									}else{
										map.put(from, amount);
										break;  
									}
								}else{
									count++;
								}
								
							}
							if (count == a) {
								map.put(from, amount);
								break;
							}
						} else {
							CityPoisindex++;
							String before=Tool.getStrByKey(Pois.elementAt(a-1), "<to>", "</to>", "</to>"); //上一次执行的poi
							int beforelen=before.length();
							String subbefore="";
							int subindex=0;
							if((to.indexOf("110100")!=-1)||(to.indexOf("5132")!=-1)){
								 subbefore=before.substring(1, beforelen-2);
								 subindex=Integer.parseInt(subbefore);
							}else{
								subbefore=before.substring(0,4);
								subindex=Integer.parseInt(subbefore);
							}
							//判断此次的sub与上次是否相同，如果不同则把上次的map值全都打印出来，然后再清空
							if(sub!=subindex){
								String key = "";
								for (Map.Entry<String, Integer> entry : map.entrySet()) {
									key = entry.getKey().toString();
									int value = entry.getValue();
									
									String str="<from>"+key+"</from>"+"<to>"+subindex+"</to>"+"<amounts>"+value+"</amounts>";
									System.out.println(str);
									FileTool.Dump(str, folder.replace(".txt", "")+"-gatherBigCity.txt", "utf-8");
								}
								map.clear();
							   //map.put(from, amount);
							}						
							
						}
					}else{
						break;
					}
					

				}
				if(CityPoisindex==CityPois.size()){
					System.out.println(poi);
					FileTool.Dump(poi, folder.replace(".txt", "")+"-gatherBigCity.txt", "utf-8");
				}

			}

		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
	/**
	 * 利用-Max.txt文件和CodeResult.txt文件排查一个区县的新旧code
	 * 如果from和to的code不一样，但是他们code对应的name一致，则视这两个code为新旧code
	 * @param folder
	 */

	public static void CheckRepeatCode(String folder) {
		String poi = "";
		try {
			setCounty("D:/人口数据/Task/排查重复code/CodeResult.txt");
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				String frompoi = "";
				String topoi = "";
				String fromReg = "";
				String toReg = "";
				for (int i = 0; i < county.size(); i++) {
					String code = county.get(i).code;
					if (from.equals(code)) {
						fromReg = county.get(i).codereg.replace("null", "").replace(",", "");
						frompoi = "<from>" + from + "</from>" + "<fromName>" + county.get(i).countyname + "</fromName>";
					}
				}
				for (int i = 0; i < county.size(); i++) {
					String code = county.get(i).code;
					if (to.equals(code)) {
						toReg = county.get(i).codereg.replace("null", "").replace(",", "");
						topoi = "<to>" + to + "</to>" + "<toName>" + county.get(i).countyname + "</toName>" + "<toReg>"
								+ toReg + "<toReg>";
					}
				}
				if (fromReg.equals(toReg)) {
					System.out.println(frompoi + topoi);
					FileTool.Dump(frompoi + topoi, folder.replace(".txt", "") + "-重复code2.txt", "utf-8");
				}
			}

		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
/**
 * 利用-MainIngredients.txt文件提取每个区县流动人口数目最大值的记录
 * @param folder
 */
	public static void getMax(String folder) {

		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			List<Integer> Amounts = new ArrayList<Integer>();
			List<String> FromTo = new ArrayList<String>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));

				String index = "";
				if (a == 0) {

					Amounts.add(0, amount);
					FromTo.add(0, poi);

				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<to>", "</to>", "</to>"); // from和to
					int counter = 0;
					if (to.equals(index)) { //// from和to
						// 对每个区域逐个统计分析
						if (to.equals(Tool.getStrByKey(Pois.elementAt(0), "<to>", "</to>", "</to>"))) {// from和to
							counter++;
							Amounts.add(counter, amount);
							FromTo.add(counter, poi);
						} else {

							Amounts.add(counter, amount);
							FromTo.add(counter, poi);
							counter++;
						}

						int s = Pois.size();
						if ((a + 1) == s) {
							double max = 0;
							String maxStr = "";
							max = Amounts.get(Amounts.size() - 1);
							maxStr = FromTo.get(Amounts.size() - 1);
							System.out.println(maxStr);
							FileTool.Dump(maxStr, folder.replace(".txt", "") + "-Max.txt", "utf-8");

						}

					} else {
						double max = 0;
						String maxStr = "";
						max = Amounts.get(Amounts.size() - 1);
						maxStr = FromTo.get(Amounts.size() - 1);
						System.out.println(maxStr);
						FileTool.Dump(maxStr, folder.replace(".txt", "") + "-Max.txt", "utf-8");
						Amounts.clear();
						FromTo.clear();

						Amounts.add(0, amount);
						FromTo.add(0, poi);

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
/**
 * 生成json文件
 * @param Folder
 */
	public static void ProductJson(String Folder) {
		JSONObject jsonObj = new JSONObject();// 创建json格式的数据

		JSONArray jsonArr = new JSONArray();// json格式的数组

		try {
			Vector<String> rds = FileTool.Load(Folder, "UTF-8");
			for (int i = 0; i < rds.size(); i++) {
				String element = rds.elementAt(i);
				JsonData jsondata = new JsonData(element);
				JSONObject jsonObjArr = new JSONObject();

				jsonObjArr.put("from", jsondata.from);
				jsonObjArr.put("to", jsondata.to);
				jsonObjArr.put("amounts", jsondata.amounts);
				jsonArr.put(jsonObjArr);

			}
			System.out.println("开始写入txt中");
			FileTool.Dump(jsonArr.toString(), Folder.replace(".txt", "") + "-Json.txt", "utf-8");

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}
	/**
	 * 提取每个区县的主要流动方向
	 * @param folder
	 */

	@SuppressWarnings("unchecked")
	public static void getMainIngredients(String folder) {

		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			List<Integer> Amounts = new ArrayList<Integer>();
			List<String> FromTo = new ArrayList<String>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));

				String index = "";
				if (a == 0) {

					Amounts.add(0, amount);
					FromTo.add(0, poi);

				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>"); // from和to
					int counter = 0;
					if (from.equals(index)) { //// from和to
						// 对每个区域逐个统计分析
						if (from.equals(Tool.getStrByKey(Pois.elementAt(0), "<from>", "</from>", "</from>"))) {// from和to
							counter++;
							Amounts.add(counter, amount);
							FromTo.add(counter, poi);
						} else {

							Amounts.add(counter, amount);
							FromTo.add(counter, poi);
							counter++;
						}

						int s = Pois.size();
						if ((a + 1) == s) {
							int num = Amounts.size();
							double average = 0;
							if (Amounts.size() > 3) {
								int a1 = Amounts.get(1);
								int b = Amounts.get(2);
								int c = Amounts.get(3);
								average = (Amounts.get(1) + Amounts.get(2) + Amounts.get(3)) / 30;
							} else {
								average = Amounts.get(0);
							}
							for (int i = 0; i < Amounts.size(); i++) {
								double db = Amounts.get(i);
								if (db > average) {
									System.out.println(FromTo.get(i));
									FileTool.Dump(FromTo.get(i),
											folder.replace(".txt", "") + "-MainIngredients1111.txt", "utf-8");
								}
							}
						}

					} else {
						int num = Amounts.size();
						double average;
						if (Amounts.size() > 3) {
							int a1 = Amounts.get(1);
							int b = Amounts.get(2);
							int c = Amounts.get(3);
							average = ((Amounts.get(1) + Amounts.get(2) + Amounts.get(3)) / 30);
						} else {
							average = Amounts.get(0);
						}

						for (int i = 0; i < Amounts.size(); i++) {
							double db = Amounts.get(i);
							if (db > average) {
								System.out.println(FromTo.get(i));
								FileTool.Dump(FromTo.get(i), folder.replace(".txt", "") + "-MainIngredients1111.txt",
										"utf-8");
							}
						}
						Amounts.clear();
						FromTo.clear();

						Amounts.add(0, amount);
						FromTo.add(0, poi);

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
	/**
	 * 利用-countAmounts.txt文件，将每个区县的记录按照流动人口数目的大小顺序排列
	 * 同时删除from和to的code一样的数据
	 * @param folder
	 */

	public static void getSortFlow(String folder) {

		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				String index = "";
				if (a == 0) {
					if (!(from.equals(to))) {
						map.put(from, amount);// from和to
					}

				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<to>", "</to>", "</to>");// from和to
					if (to.equals(index)) { // from和to
						// 对每个区域逐个统计分析
						if (!(from.equals(to))) {
							map.put(from, amount); // from和to
						}

						int s = Pois.size();
						if ((a + 1) == s) {
							int[] Amounts = new int[map.size()];
							String[] FromTo = new String[map.size()];
							int counts = 0;
							String key = "";
							for (Map.Entry<String, Integer> entry : map.entrySet()) {
								key = entry.getKey().toString();
								// String value = entry.getValue().toString();
								int value = entry.getValue();
								Amounts[counts] = value;
								FromTo[counts] = "<from>" + key + "</from>" + "<to>" + index + "</to>" + "<amounts>" // from和to
										+ value + "</amounts>";
								counts++;
							}
							Tool.InsertSortArray(Amounts.length, Amounts, FromTo);
							for (int i = 0; i < FromTo.length; i++) {
								System.out.println(FromTo[i]);
								FileTool.Dump(FromTo[i], folder.replace(".txt", "") + "-sort.txt", "utf-8");
							}
						}

					} else {

						int[] Amounts = new int[map.size()];
						String[] FromTo = new String[map.size()];
						int counts = 0;
						String key = "";
						for (Map.Entry<String, Integer> entry : map.entrySet()) {
							key = entry.getKey().toString();
							// String value = entry.getValue().toString();
							int value = entry.getValue();
							Amounts[counts] = value;
							FromTo[counts] = "<from>" + key + "</from>" + "<to>" + index + "</to>" + "<amounts>" + value // from和to
									+ "</amounts>";
							counts++;

						}
						Tool.InsertSortArray(Amounts.length, Amounts, FromTo);
						for (int i = 0; i < FromTo.length; i++) {
							System.out.println(FromTo[i]);
							FileTool.Dump(FromTo[i], folder.replace(".txt", "") + "-sort.txt", "utf-8");
						}

						map.clear();
						if (!(from.equals(to))) {
							map.put(to, amount);
						}

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}

	/**
	 * 统计最终一个区县的人流动到另外一个区县去的总人数
	 * 
	 * @param folder
	 */
	public static void countTotalAmount(String folder) {

		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				String index = "";
				if (a == 0) {
					// if(!(from.equals(to))){
					map.put(to, amount);
					// }

				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>");
					if (from.equals(index)) {
						// 对每个区域逐个统计
						// if(!(from.equals(to))){
						map.put(to, amount);
						// }

						int s = Pois.size();
						if ((a + 1) == s) {
							int[] totalAmounts = new int[map.size()];
							int counts = 0;
							String key = "";
							int Total = 0;
							for (Map.Entry<String, Integer> entry : map.entrySet()) {
								
								int value = entry.getValue();
								totalAmounts[counts] = value;
								counts++;
							}
							for (int i = 0; i < totalAmounts.length; i++) {
								Total += totalAmounts[i];
							}
							String str = "<from>" + index + "</from>" + "<amounts>" + Total + "</amounts>";
							System.out.println(str);
							FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");
						}

					} else {
						int[] totalAmounts = new int[map.size()];
						int counts = 0;
						String key = "";
						int Total = 0;
						for (Map.Entry<String, Integer> entry : map.entrySet()) {
							
							int value = entry.getValue();
							totalAmounts[counts] = value;
							counts++;

							
						}
						for (int i = 0; i < totalAmounts.length; i++) {
							Total += totalAmounts[i];
						}
						String str = "<from>" + index + "</from>" + "<amounts>" + Total + "</amounts>";
						System.out.println(str);
						FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");

						map.clear();
						// if(!(from.equals(to))){
						map.put(to, amount);
						// }

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
/**
 * 统计某个区县流向其他区县的人口总数，将相同的流向叠加起来，使得该区县的所有流向不重复
 * @param folder
 */
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
					map.put(from, amount);  //from和to
				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<to>", "</to>", "</to>"); //from和to
					if (to.equals(index)) {//from和to
						// 对每个区域逐个统计
						int count = 0;
						for (int b = 0; b < a; b++) {
							String probe = Tool.getStrByKey(Pois.elementAt(b), "<from>", "</from>", "</from>");//from和to
							if (from.equals(probe)) {//from和to
								if (map.get(probe) != null) {
									int s = map.get(probe);
									map.put(probe, s + amount);
									break;
								} else {
									map.put(from, amount);//from和to
									break; //此处的break必须要加，要不然可能会出现同一个from统计两次的情况
								}
							} else {
								count++;
							}
						}
						if (count == a) {
							map.put(from, amount);//from和to
						}
						int s = Pois.size();
						if ((a + 1) == s) {
							String key = "";
							String value ="";
							for (Map.Entry<String, Integer> entry : map.entrySet()) {
								key = entry.getKey().toString();
							    value = entry.getValue().toString();

							    String str="<from>"+key+"</from>"+"<to>"+index+"</to>"+"<amounts>"+value+"</amounts>";
								//System.out.println(str);
								FileTool.Dump(str, folder.replace(".txt","")+"-countAmounts.txt", "utf-8");
							}
							
						}

					} else {
                        String key = "";
						String value ="";
						for (Map.Entry<String, Integer> entry : map.entrySet()) {
							key = entry.getKey().toString();
						    value = entry.getValue().toString();

						    String str="<from>"+key+"</from>"+"<to>"+index+"</to>"+"<amounts>"+value+"</amounts>";
							//System.out.println(str);
							FileTool.Dump(str, folder.replace(".txt","")+"-countAmounts.txt", "utf-8");
						}
						map.clear();
						map.put(from, amount);//from和to
					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}

	/**
	 * 按照区县顺序整理从某个区县流动到另外一个区县的记录，这里不包括同一目的地的数量叠加
	 * 
	 * @param codefolder 有每个区县code的CodeResult.txt文件
	 * @param countfoder 需要整理的文件的路径
	 */
	// countTo("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt","D:/人口数据/countFlowout.txt");
	public static void countTo(String codefolder, String countfoder) {
		try {
			File file = new File(codefolder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;
			reader = new BufferedReader(isr);
			
			//读取CodeResult.txt中的一条数据：
			//<Code>110100</Code><CodeAddr>北京市</CodeAddr><CodeCoor>116.3847599;39.90230163</CodeCoor><CodeReg>北京市,null,null,null</CodeReg>
			while ((tempString = reader.readLine()) != null) {

				//读取countFlowout.txt文件中的数据
				Vector<String> countfile = FileTool.Load(countfoder, "utf-8");
				//从countFlowout.txt文件中的第一条记录开始判断比较
				for (int i = 0; i < countfile.size(); i++) {
					String poi = countfile.elementAt(i);
					Demo demo = new Demo(poi);
					String code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
					
					//如果CodeResult.txt中的code与countFlowout.txt中的from相同，则将countFlowout.txt中的poi写下来
				    //其实是按照CodeResult.txt中code顺序，将countFlowout.txt中的poi排列，先将code为110101的poi写下，再将code为110102的poi写下来
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
						String total = "<from>" + Code + "</from>" + "<to>" + probe + "</to>" + "<amounts>" + amounts+ "</amounts>";
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
	 * 统计各个区县的流入流出人口的数量情况
	 * 
	 * @throws IOException
	 */
	public static void SearchCode() throws IOException {

		String folder1 = "D:/人口数据/";
		for (int j = 1; j <= 89; j++) {
			System.out.println("第" + j + "次将区县数据存到CountyPopulation中：");
			setCounty("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt");

			System.out.println("开始处理第" + j + "个文件：");
			String f = folder1 + j + "-postCode.txt";
			ClassifyStatistic(f);

			System.out.println("开始逐个查询：");
			Vector<String> codes = FileTool.Load("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt", "utf-8");
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
						String postCodes = county.get(i).getpostCodes().toString().replace(", ", ",").replace("[", "").replace("]", "").replace(" ", "");
						
//统计该区县人口流出外地的情况						
						countFlowout(postCodes, county.get(i).code, folder1);

						// 查询拥有该地区通讯地址的人口
						// System.out.println("拥有"+code+"地区通讯地址的人口：");
						for (int k = 0; k < county.get(i).postpois.size(); k++) {

							// System.out.println(county.get(i).postpois.get(k));

						}
						// System.out.println(county.get(i).gethomeCodes());
						String homeCodes = county.get(i).gethomeCodes().toString().replace(", ", ",").replace("[", "").replace("]", "").replace(" ", "");
						
//统计外地人口流入该区县的情况
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
	 * @param folder   
	 *            统计后的数据存放路径  
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
	 * @param folder   
	 *            统计后的数据存放路径      
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
					String str = "<from>" + entry.getKey() + "</from>" + "<to>" + code + "</to>" + "<amount>"+ entry.getValue() + "</amount>";
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
    /**
     * 设置虚拟的区县盒子，每一个区县相当于一个盒子，每个盒子有code、名称、坐标等基本信息的设置
     * @param folder 利用CodeResult.txt中的数据来设置不同的区县盒子
     */
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
				codeReg = Tool.getStrByKey(tempString, "<CodeReg>", "</CodeReg>", "</CodeReg>");
				String[] coor = countyCoor.split(";");
				countyLN = coor[0];
				countyLA = coor[1];
				CountyPopulation cp = new CountyPopulation();
				cp.setCode(code);
				cp.setCountyname(countyname);
				cp.setCodeReg(codeReg);
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
	 * 将人口的户籍地址或者通讯地址的代码分配到各个区县
	 * @param folder
	 * @throws IOException
	 */
	public static void ClassifyStatistic(String folder) throws IOException {
        //该folder是“D:\人口数据\原始数据”中的数据
		File file1 = new File(folder);

		FileInputStream fis1 = new FileInputStream(file1);
		InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");
		BufferedReader reader1 = null;
		String tempString1 = null;

		reader1 = new BufferedReader(isr1);
		while ((tempString1 = reader1.readLine()) != null) {
            //tempString1:<Name>冯喜强</Name><PostCoor>109.536324;22.299784</PostCoor><PostReg>广西壮族自治区,钦州市,浦北县,小江镇</PostReg><Code>452826</Code><CodeAddr>广西壮族自治区钦州地区浦北县</CodeAddr><CodeCoor>109.552798;22.27452501</CodeCoor><CodeReg>广西壮族自治区,钦州市,浦北县,null</CodeReg><CtfTp>ID</CtfTp><CtfId>452826197802134612</CtfId><Home>广西壮族自治区钦州地区浦北县</Home><Gender>M</Gender><Birth>19780213</<Birth><PostAddr>广西浦北县小江镇沙江村委会下车塘村20号</PostAddr><Mobile>no</Mobile><Nation>汉</Nation><Version>2011-12-166:41:57</Version>

			//获取该tempString1的户籍地code
			String admincode = Tool.getStrByKey(tempString1, "<Code>", "</Code>", "</Code>");
			
			//获取该tempString1的通讯地code
			String postcode = Tool.getStrByKey(tempString1, "<PostCode>", "</PostCode>", "</PostCode>");
			
			for (int i = 0; i < county.size(); i++) {
				String Code = county.get(i).code;
				//在county中找到与户籍地code一样的code，并且把该tempString1及其包含的通讯地址放到county中对应的那个code的盒子中
				//该盒子就像虚拟的区县，每个盒子有一个行政区划代码code，该盒子管理着户籍地为该地区的人的记录以及他们的通讯地址code
				//此外，该盒子还管理着通讯地是该地区的人的记录以及这些的户籍地址code
				if (admincode.equals(Code)) {
					county.get(i).sethomePois(tempString1);
					county.get(i).setpostCodes(postcode);
					break;
				}
			}
			
			//在county中找到与通讯地code一样的code，并且把该tempString1及其包含的户籍地址放到county中对应的那个code的盒子中
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
