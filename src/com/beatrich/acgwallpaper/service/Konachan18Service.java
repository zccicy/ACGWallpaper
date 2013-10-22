package com.beatrich.acgwallpaper.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.beatrich.acgwallpaper.model.WallPaper;

public class Konachan18Service {

	public static List<WallPaper> getWallPaperInfoByPageNum(Integer pageNum) {
		List<WallPaper> wallPaperList = new ArrayList<WallPaper>();
		do {
			try {
				Document document = Jsoup.parse(new URL(
						"http://konachan.com/post?page=" + pageNum), 10000);
				Element element = document.getElementById("post-list-posts");
				Elements lis = element.getElementsByTag("li");
				for (int i = 0; i < lis.size(); i++) {
					WallPaper wallPaper = new WallPaper();
					Element li = lis.get(i);
					Element preview = li.getElementsByClass("preview").get(0);

					Element wh = li.getElementsByClass("directlink-res").get(0);
					Element detailPic = li.getElementsByClass("directlink")
							.get(0);
					wallPaper.setThumbUrl(preview.attr("src"));
					wallPaper.setPicUrl(detailPic.attr("href"));
					wallPaper.setInfo(preview.attr("alt"));
					wallPaper.setWh(wh.html());
//					System.out.println("wh"+wh.html());
//					wallPaper.setWidth(Integer.parseInt(wh.html().split("X")[0]
//							.trim()));
//					wallPaper.setHeight(Integer
//							.parseInt(wh.html().split("X")[1].trim()));
					wallPaperList.add(wallPaper);
				}
				return wallPaperList;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} while (true);

	}
}
