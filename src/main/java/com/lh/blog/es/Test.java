package com.lh.blog.es;

import java.io.File;

public class Test {
	public static void main(String args[]) {
		File file = new File("cilin/synonyms.txt");
		System.out.println(file.getAbsolutePath());
		String word1 = "西红柿", word2 = "";
//		double sim = 0;
//		sim = CiLin.calcWordsSimilarity(word1, word2);//计算两个词的相似度
//		System.out.println(word1 + "  " + word2 + "的相似度为：" + sim);
		System.out.println(word1 + "的同义词：" +CiLin.get_sym(word1,CiLin.TYPE_SYM));
		System.out.println(word1 + "的近义词：" + CiLin.get_sym(word1,CiLin.TYPE_SYMCLASS));

	}
}
