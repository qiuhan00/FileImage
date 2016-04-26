package com.cfang.FileImage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;


public class TestImage {
	
	 public final static boolean pressText(String srcImageFile, String destImageFile,Map<String, Map<String, Object[]>> map) {
	        try {
	            File img = new File(srcImageFile);
	            Image src = ImageIO.read(img);
	            int width = src.getWidth(null);
	            int height = src.getHeight(null);
	            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = image.createGraphics();
	            g.drawImage(src, 0, 0, width, height, null);
	            Iterator iterator_0 = map.keySet().iterator();
	            while(iterator_0.hasNext()){
	            	Map<String, Object[]> valueMap = map.get(iterator_0.next());
	            	Iterator iterator_1 = valueMap.keySet().iterator();
	            	while(iterator_1.hasNext()){
	            		Object[] value = valueMap.get(iterator_1.next());
	            		int fontSize = Integer.parseInt(value[4]+"");
	            		int x = Integer.parseInt(value[5]+"");
	            		int y = Integer.parseInt(value[6]+"");
	            		int fontStyle = Integer.parseInt(value[2]+"");
	            		g.setColor((Color)value[3]);
			            g.setFont(new Font(value[1]+"", fontStyle, fontSize));
			            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,Float.parseFloat(value[7]+"")));
			            // 在指定坐标绘制水印文字
			            g.drawString(value[0]+"", (width - (getLength(value[0]+"") * fontSize))/ 2 + x, (height - fontSize) / 2 + y);
	            	}
	            }
	            g.dispose();
	            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }
	 
	 public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
	            int x, int y, float alpha) {
	        try {
	            File img = new File(srcImageFile);
	            Image src = ImageIO.read(img);
	            int wideth = src.getWidth(null);
	            int height = src.getHeight(null);
	            BufferedImage image = new BufferedImage(wideth, height,
	                    BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = image.createGraphics();
	            g.drawImage(src, 0, 0, wideth, height, null);
	            // 水印文件
	            Image src_biao = ImageIO.read(new File(pressImg));
	            int wideth_biao = src_biao.getWidth(null);
	            int height_biao = src_biao.getHeight(null);
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
	                    alpha));
	            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
	                    (height - height_biao) / 2, wideth_biao, height_biao, null);
	            // 水印文件结束
	            g.dispose();
	            ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static int getLength(String text) {
	        int length = 0;
	        for (int i = 0; i < text.length(); i++) {
	            if (new String(text.charAt(i) + "").getBytes().length > 1) {
	                length += 2;
	            } else {
	                length += 1;
	            }
	        }
	        return length / 2;
	  }
	 
	 public static boolean printSalePic(OrderInfo info, String imagePath, String resultPath){
		 Map<String, Map<String, Object[]>> map = getMap(info);
		 return pressText(imagePath,resultPath,map);
	 }
	 
	 private static Map<String, Map<String, Object[]>> getMap(OrderInfo info){
		 final String merchantName = info.getMerchantName();
		 final String merchantNo = info.getMerchantNo();
		 final String terminalId = info.getTerminalId(); 
		 final String issuer = info.getIssuer();
		 final String acquirer = info.getAcquirer();
		 final String cardNo = info.getCardNo();
		 final String expDate = info.getExpDate();
		 final String transType = info.getTransType();
		 final String batchNo = info.getBatchNo();
		 final String voucherNo = info.getVoucherNo();
		 Map<String, Map<String, Object[]>> map = new HashMap<String, Map<String, Object[]>>();
		 map.put("order", new HashMap<String, Object[]>(){
			 {put("order0", new Object[]{"签购单", "微软雅黑",Font.BOLD, Color.BLUE, 80, 0, -1300, 0.5f});
		 }});
		 map.put("merchantName", new HashMap<String, Object[]>(){
			 {put("merchantName0", new Object[]{"商户名称", "宋体",Font.BOLD,Color.BLUE,50, -500, -1140, 0.5f});
			 put("merchantName1", new Object[]{merchantName, "宋体",Font.BOLD,Color.BLUE,50, 400, -1140, 0.5f});
			  put("merchantName2", new Object[]{"MERCHANT NAME","宋体",Font.BOLD,Color.BLACK,50, -500, -1090, 0.5f});
		 }});
		 map.put("merchantNo", new HashMap<String, Object[]>(){
			 {put("merchantno0", new Object[]{"商户编号","宋体",Font.BOLD,Color.BLACK,50, -500, -990, 0.5f});
			 put("merchantno1", new Object[]{merchantNo,"宋体",Font.BOLD,Color.BLUE,50, 400, -990, 0.5f});
			  put("merchantno2", new Object[]{"MERCHANT NO","宋体",Font.BOLD,Color.BLACK,50, -500, -940, 0.5f});
		 }});
		 map.put("terminalId", new HashMap<String, Object[]>(){
			 {put("terminalId0", new Object[]{"终端编号","宋体",Font.BOLD,Color.BLACK,50, -500, -840, 0.5f});
			 put("terminalId1", new Object[]{terminalId,"宋体",Font.BOLD,Color.BLUE,50, 400, -840, 0.5f});
			  put("terminalId2", new Object[]{"TERMINAL ID","宋体",Font.BOLD,Color.BLACK,50, -500, -790, 0.5f});
		 }});
		 map.put("issuer", new HashMap<String, Object[]>(){
			 {put("issuer0", new Object[]{"发卡行","宋体",Font.BOLD,Color.BLACK,50, -500, -690, 0.5f});
			 put("issuer1", new Object[]{issuer,"宋体",Font.BOLD,Color.BLUE,50, -300, -690, 0.5f});
			  put("issuer2", new Object[]{"ISSUER","宋体",Font.BOLD,Color.BLACK,50, -550, -640, 0.5f});
		 }});
		 map.put("acquirer", new HashMap<String, Object[]>(){
			 {put("acquirer0", new Object[]{"收单行","宋体",Font.BOLD,Color.BLACK,50, 300, -690, 0.5f});
			 put("acquirer1", new Object[]{acquirer,"宋体",Font.BOLD,Color.BLUE,50, 550, -690, 0.5f});
			  put("acquirer2", new Object[]{"ACQUIRER","宋体",Font.BOLD,Color.BLACK,50, 300, -640, 0.5f});
		 }});
		 map.put("cardNo", new HashMap<String, Object[]>(){
			 {put("cardNo0", new Object[]{"卡号","宋体",Font.BOLD,Color.BLACK,50, -550, -540, 0.5f});
			 put("cardNo1", new Object[]{cardNo,"宋体",Font.BOLD,Color.BLUE,50, 100, -440, 0.5f});
			  put("cardNo2", new Object[]{"CARDNO","宋体",Font.BOLD,Color.BLACK,50, -550, -490, 0.5f});
		 }});
		 map.put("transType", new HashMap<String, Object[]>(){
			 {put("transType0", new Object[]{"交易类型","宋体",Font.BOLD,Color.BLACK,50, -500, -340, 0.5f});
			 put("transType1", new Object[]{transType,"宋体",Font.BOLD,Color.BLUE,50, -300, -340, 0.5f});
			  put("transType2", new Object[]{"TRANS TYPE","宋体",Font.BOLD,Color.BLACK,50, -500, -290, 0.5f});
		 }});
		 map.put("expDate", new HashMap<String, Object[]>(){
			 {put("expDate0", new Object[]{"有效期","宋体",Font.BOLD,Color.BLACK,50, 300, -340, 0.5f});
			 put("expDate1", new Object[]{expDate,"宋体",Font.BOLD,Color.BLUE,50, 550, -340, 0.5f});
			  put("expDate2", new Object[]{"EXP DATE","宋体",Font.BOLD,Color.BLACK,50, 300, -290, 0.5f});
		 }});
		 map.put("batchNo", new HashMap<String, Object[]>(){
			 {put("batchNo0", new Object[]{"批次号","宋体",Font.BOLD,Color.BLACK,50, -500, -190, 0.5f});
			 put("batchNo1", new Object[]{batchNo,"宋体",Font.BOLD,Color.BLUE,50, -300, -190, 0.5f});
			  put("batchNo2", new Object[]{"BATCH NO","宋体",Font.BOLD,Color.BLACK,50, -500, -140, 0.5f});
		 }});
		 map.put("voucherNo", new HashMap<String, Object[]>(){
			 {put("voucherNo0", new Object[]{"凭证号","宋体",Font.BOLD,Color.BLACK,50, 250, -190, 0.5f});
			 put("voucherNo1", new Object[]{voucherNo,"宋体",Font.BOLD,Color.BLUE,50, 550, -190, 0.5f});
			  put("voucherNo2", new Object[]{"VOUCHER NO","宋体",Font.BOLD,Color.BLACK,50, 250, -140, 0.5f});
		 }});
		 final String authNo = info.getAuthNo();
		 map.put("authNo", new HashMap<String, Object[]>(){
			 {put("authNo0", new Object[]{"授权码","宋体",Font.BOLD,Color.BLACK,50, -500, -40, 0.5f});
			 put("authNo1", new Object[]{authNo,"宋体",Font.BOLD,Color.BLUE,50, -300, -40, 0.5f});
			  put("authNo2", new Object[]{"AUTH NO","宋体",Font.BOLD,Color.BLACK,50, -500, 10, 0.5f});
		 }});
		 final String tradeNo = info.getTradeNo();
		 map.put("tradeNo", new HashMap<String, Object[]>(){
			 {put("tradeNo0", new Object[]{"流水号","宋体",Font.BOLD,Color.BLACK,50, 250, -40, 0.5f});
			 put("tradeNo1", new Object[]{tradeNo,"宋体",Font.BOLD,Color.BLUE,50, 550, -40, 0.5f});
			  put("tradeNo2", new Object[]{"TRADE NO","宋体",Font.BOLD,Color.BLACK,50, 250, 10, 0.5f});
		 }});
		 final String time = info.getTime();
		 map.put("time", new HashMap<String, Object[]>(){
			 {put("time0", new Object[]{"日期/时间","宋体",Font.BOLD,Color.BLACK,50, -500, 110, 0.5f});
			 put("time1", new Object[]{time,"宋体",Font.BOLD,Color.BLUE,50, 100, 110, 0.5f});
			  put("time2", new Object[]{"DATE/TIME","宋体",Font.BOLD,Color.BLACK,50, -500, 160, 0.5f});
		 }});
		 final String orderNo = info.getOrderNo();
		 map.put("orderNo", new HashMap<String, Object[]>(){
			 {put("orderNo0", new Object[]{"订单编号","宋体",Font.BOLD,Color.BLACK,50, -500, 250, 0.5f});
			 put("orderNo1", new Object[]{orderNo,"宋体",Font.BOLD,Color.BLUE,50, 100, 250, 0.5f});
			  put("orderNo2", new Object[]{"ORDER NO","宋体",Font.BOLD,Color.BLACK,50, -500, 290, 0.5f});
		 }});
		 final String referNo = info.getReferNo();
		 map.put("referNo", new HashMap<String, Object[]>(){
			 {put("referNo0", new Object[]{"参考号","宋体",Font.BOLD,Color.BLACK,50, -500, 370, 0.5f});
			 put("referNo1", new Object[]{referNo,"宋体",Font.BOLD,Color.BLUE,50, 100, 370, 0.5f});
			  put("referNo2", new Object[]{"REFER NO","宋体",Font.BOLD,Color.BLACK,50, -500, 410, 0.5f});
		 }});
		 final String amount = info.getAmount();
		 map.put("amount", new HashMap<String, Object[]>(){
			 {put("amount0", new Object[]{"交易金额","宋体",Font.BOLD,Color.BLACK,50, -500, 470, 0.5f});
			 put("amount1", new Object[]{referNo,"宋体",Font.BOLD,Color.BLUE,50, 100, 470, 0.5f});
			  put("amount2", new Object[]{"AMOUNT","宋体",Font.BOLD,Color.BLACK,50, -500, 530, 0.5f});
		 }});
		 map.put("agree", new HashMap<String, Object[]>(){
			 {put("agreer0", new Object[]{"同意条款信息。。。。。。。", "微软雅黑",Font.BOLD, Color.BLACK, 50, 0, 600, 0.5f});
		 }});
		 map.put("writeName", new HashMap<String, Object[]>(){
			 {put("writeName0", new Object[]{"持卡人签字", "微软雅黑",Font.BOLD, Color.BLACK, 50, -500, 700, 0.5f});
		 }});
		 map.put("comment", new HashMap<String, Object[]>(){
			 {put("comment0", new Object[]{"备注信息", "微软雅黑",Font.BOLD, Color.BLACK, 50, -550, 1130, 0.5f});
		 }});
		 return map;
	 }
	 
	 public static void main(String[] args){
	    OrderInfo orderInfo = new OrderInfo();
	    orderInfo.setMerchantName("上海商家信息测试");
	    orderInfo.setMerchantNo("123321");
	    orderInfo.setCardNo("1111 1111 1111 1111");
	    printSalePic(orderInfo, "e:\\image\\2.png","e:\\image\\3.png");
	    System.out.println("e:\\image\\2.png".substring("e:\\image\\2.png".lastIndexOf(".")+1));
	    
	 }
}
