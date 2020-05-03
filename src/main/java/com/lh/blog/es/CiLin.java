package com.lh.blog.es;
import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CiLin {

	private static Logger logger = LoggerFactory.getLogger(CiLin.class);
	// “=”代表“相等”、“同义”
	private static final String CILIN_SYM = "=";
	// “#”代表“不等”、“同类”，属于相关词语；
	private static final String CILIN_SYMCLASS = "#";
    // “@”代表“自我封闭”、“独立”，它在词典中既没有同义词，也没有相关词。
    private static final String CILIN_SYMNOT = "@";

	// 同义词
	public static final String TYPE_SYM = "sym";
    public static List<List<String>> sym_words;
	// 近义词
	public static final String TYPE_SYMCLASS = "sym_class";
    public static List<List<String>> sym_class_words;

	public int zero_KeyWord_Depth = 12;
	public static HashMap<String, List<String>> keyWord_Identifier_HashMap;//<关键词，编号List集合>哈希
	public static HashMap<String, Integer> first_KeyWord_Depth_HashMap;//<第一层编号，深度>哈希
	public static HashMap<String, Integer> second_KeyWord_Depth_HashMap;//<前二层编号，深度>哈希
	public static HashMap<String, Integer> third_KeyWord_Depth_HashMap;//<前三层编号，深度>哈希
	public static HashMap<String, Integer> fourth_KeyWord_Depth_HashMap;//<前四层编号，深度>哈希
 	public HashMap<String, HashSet<String>> ciLin_Sort_keyWord_HashMap = new HashMap<String, HashSet<String>>();//<(同义词)编号，关键词Set集合>哈希
	
//	static{
//		keyWord_Identifier_HashMap = new HashMap<String, List<String>>();
//		first_KeyWord_Depth_HashMap = new HashMap<String, Integer>();
//		second_KeyWord_Depth_HashMap = new HashMap<String, Integer>();
//		third_KeyWord_Depth_HashMap = new HashMap<String, Integer>();
//		fourth_KeyWord_Depth_HashMap = new HashMap<String, Integer>();
//        sym_words = Collections.synchronizedList(new ArrayList<>());
//        sym_class_words = Collections.synchronizedList(new ArrayList<>());
//	}

	/**
	 * 初始化同义词
	 */
	public static void initWords() throws IOException {
		sym_words = Collections.synchronizedList(new ArrayList<>());
		sym_class_words = Collections.synchronizedList(new ArrayList<>());
		try {
			String str;
			String[] strs;
			String filename = "cilin/synonyms.txt";
			BufferedReader inFile = new BufferedReader(FileUtil.getReader(filename, "utf-8"));
			while ((str = inFile.readLine()) != null) {
				strs = str.split(" ");
				String first = strs[0];
				List<String> strings = new ArrayList<>();

				if (first.indexOf(CILIN_SYM) > 0) {
					for (int j = 1; j < strs.length; j++) {
						strings.add(strs[j]);
					}
					sym_words.add(strings);
				} else if (first.indexOf(CILIN_SYMCLASS) > 0) {
					for (int j = 1; j < strs.length; j++) {
						strings.add(strs[j]);
					}
					sym_class_words.add(strings);
				}
			}
			logger.info("[初始化词林] 成功! 同义词:{},近义词:{}", sym_words.size(), sym_class_words.size());
			inFile.close();
		} catch (IOException e) {
			logger.error("[初始化词林] 失败!", e);
			throw e;
		}
	}

	//3.初始化词林相关
	public static void initCiLin(){
		try {
//			File file = new File("cilin/keyWord_Identifier_HashMap.txt");
			//初始化<关键词， 编号set>哈希
			readFile("cilin/keyWord_Identifier_HashMap.txt");
			//初始化<第一层编号，高度>哈希
			readFile("cilin/first_KeyWord_Depth_HashMap.txt");
			//初始化<前二层编号，高度>哈希
			readFile("cilin/second_KeyWord_Depth_HashMap.txt");
			//初始化<前三层编号，高度>哈希
			readFile("cilin/third_KeyWord_Depth_HashMap.txt");
			//初始化<前四层编号，高度>哈希
			readFile("cilin/fourth_KeyWord_Depth_HashMap.txt");
			//初始化同义词
			readFile("cilin/synonyms.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取近义词
	public static List<String> get_sym(String key, String type){
		List<List<String>> lists= null;
		if (type.equals(CiLin.TYPE_SYM)){
			lists = sym_words;
		}else {
			lists = sym_class_words;
		}
		List<String> result = new ArrayList<>();
		if (key.length() == 1){
			for (List<String> strings: lists) {
				for (String sym: strings) {
					if (sym.equals(key)){
						result.addAll(strings);
						break;
					}
				}
			}
		}
		else {
			for (List<String> strings: lists) {
				for (String sym: strings) {
					if (sym.contains(key)){
						result.addAll(strings);
						break;
					}
				}
			}
		}
		return result;
	}

	private static void readFile(String filename)  {
		// 读取文本
		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			String str = "";
			while ((str = inFile.readLine()) != null){
				String[] strs = str.split(" ");
				fourth_KeyWord_Depth_HashMap.put(strs[0], Integer.valueOf(strs[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inFile != null) {
					inFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//根据两个关键词计算相似度
	public static double calcWordsSimilarity(String key1, String key2){
		List<String> identifierList1 = null, identifierList2 = null;//词林编号list
		if(key1.equals(key2))
			return 1.0;
		
		if (!keyWord_Identifier_HashMap.containsKey(key1) || !keyWord_Identifier_HashMap.containsKey(key2)) {//其中有一个不在词林中，则返回相似度为0.1
			//System.out.println(key1 + "  " + key2 + "有一个不在同义词词林中！");
			return 0.1;
		}
		identifierList1 = keyWord_Identifier_HashMap.get(key1);//取得第一个词的编号集合
		identifierList2 = keyWord_Identifier_HashMap.get(key2);//取得第二个词的编号集合
		
		return getMaxIdentifierSimilarity(identifierList1, identifierList2);
	}
		
	public static double getMaxIdentifierSimilarity(List<String> identifierList1, List<String> identifierList2){
		int i, j;
		double maxSimilarity = 0, similarity = 0;
		for(i = 0; i < identifierList1.size(); i++){
			j = 0;
			while(j < identifierList2.size()){
				similarity = getIdentifierSimilarity(identifierList1.get(i), identifierList2.get(j));
				System.out.println(identifierList1.get(i) + "  " + identifierList2.get(j) + "  " + similarity);
				if(similarity > maxSimilarity)
					maxSimilarity = similarity;
				if(maxSimilarity == 1.0)	
					return maxSimilarity;
				j++;
			}
		}
		return maxSimilarity;
	}
		
	public static double getIdentifierSimilarity(String identifier1, String identifier2){
		int n = 0, k = 0;//n是分支层的节点总数, k是两个分支间的距离.
		//double a = 0.5, b = 0.6, c = 0.7, d = 0.96;
		double a = 0.65, b = 0.8, c = 0.9, d = 0.96;
		if(identifier1.equals(identifier2)){//在第五层相等
			if(identifier1.substring(7).equals("="))
				return 1.0;
			else 
				return 0.5;
		}
		else if(identifier1.substring(0, 5).equals(identifier2.substring(0, 5))){//在第四层相等 Da13A01=
			n = fourth_KeyWord_Depth_HashMap.get(identifier1.substring(0, 5));
			k = Integer.valueOf(identifier1.substring(5, 7)) - Integer.valueOf(identifier2.substring(5, 7));
			if(k < 0) k = -k;
			return Math.cos(n * Math.PI / 180) * ((double)(n - k + 1) / n) * d;
		}
		else if(identifier1.substring(0, 4).equals(identifier2.substring(0, 4))){//在第三层相等 Da13A01=
			n = third_KeyWord_Depth_HashMap.get(identifier1.substring(0, 4));
			k = identifier1.substring(4, 5).charAt(0) - identifier2.substring(4, 5).charAt(0);
			if(k < 0) k = -k;
			return Math.cos(n * Math.PI / 180) * ((double)(n - k + 1) / n) * c;
		}
		else if(identifier1.substring(0, 2).equals(identifier2.substring(0, 2))){//在第二层相等
			n = second_KeyWord_Depth_HashMap.get(identifier1.substring(0, 2));
			k = Integer.valueOf(identifier1.substring(2, 4)) - Integer.valueOf(identifier2.substring(2, 4));
			if(k < 0) k = -k;
			return Math.cos(n * Math.PI / 180) * ((double)(n - k + 1) / n) * b;
		}
		else if(identifier1.substring(0, 1).equals(identifier2.substring(0, 1))){//在第一层相等
			n = first_KeyWord_Depth_HashMap.get(identifier1.substring(0, 1));
			k = identifier1.substring(1, 2).charAt(0) - identifier2.substring(1, 2).charAt(0);
			if(k < 0) k = -k;
			return Math.cos(n * Math.PI / 180) * ((double)(n - k + 1) / n) * a;
		}
		
		return 0.1;
	}
}
