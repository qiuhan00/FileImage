package com.cfang.FileImage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;


public class ImageUtils {
	
	private static final Color color = new Color(36, 114, 162);
	private static final String templateUrl = "e:\\image\\签购单模板.png";
	
	 public final static boolean pressText(String destImageFile,Map<String, Map<String, Object[]>> map) {
	        try {
	            File img = new File(templateUrl);
	            Image src = ImageIO.read(img);
	            int width = src.getWidth(null);
	            int height = src.getHeight(null);
	            System.out.println("width:" + width + ", height:" + height);
	            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = image.createGraphics();
	            g.drawImage(src, 0, 0, width, height, null);
	            Iterator iterator_0 = map.keySet().iterator();
	            while(iterator_0.hasNext()){
	            	Map<String, Object[]> valueMap = map.get(iterator_0.next());
	            	Iterator iterator_1 = valueMap.keySet().iterator();
	            	while(iterator_1.hasNext()){
	            		Object key = iterator_1.next();
	            		Object[] value = valueMap.get(key);
	            		String itemName = value[0]+"";
	            		int fontSize = Integer.parseInt(value[4]+"");
	            		int x = Integer.parseInt(value[5]+"");
	            		int y = Integer.parseInt(value[6]+"");
	            		int fontStyle = Integer.parseInt(value[2]+"");
	            		g.setColor((Color)value[3]);
			            g.setFont(new Font(value[1]+"", fontStyle, fontSize));
			            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,Float.parseFloat(value[7]+"")));
			            // 在指定坐标绘制水印文字
//			            g.drawString(itemName, (width - (getLength(itemName) * fontSize))/ 2 + x, (height - fontSize) / 2 + y);
			            if((key+"").contains("1")){ //设置右对齐
			            	FontMetrics fm = g.getFontMetrics();
			            	int stringWidth = fm.stringWidth(itemName); 
//			            	g.drawString(itemName,  width - getLength(itemName) - 160,  y);
			            	g.drawString(itemName,  width - 48 - stringWidth,  y);
			            }else {
			            	g.drawString(itemName, x, y);
						}
	            	}
	            }
	            g.dispose();
	            ImageIO.write((BufferedImage) image, "png", new File(destImageFile));// 输出到文件流
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }
	 
	 public final static boolean pressImage(String pressImg, String srcImageFile,String destImageFile,
	            int x, int y, float alpha){
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
	            g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
	            g.dispose();
	            ImageIO.write((BufferedImage) image,  "png", new File(destImageFile));
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		 return true;
	    }
	 
	 public final static boolean pressImage(String srcImageFile,String destImageFile,
	            int x, int y, float alpha, Image srcImage){
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
//	            Image src_biao = ImageIO.read(new File(pressImg));
	            int wideth_biao = srcImage.getWidth(null);
	            int height_biao = srcImage.getHeight(null);
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
	                    alpha));
	            g.drawImage(srcImage, x, y, wideth_biao, height_biao, null);
	            g.dispose();
	            ImageIO.write((BufferedImage) image,  "png", new File(destImageFile));
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		 return true;
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
	        return length;
	  }
	 
	
	 
	 private static Map<String, Map<String, Object[]>> getMap(OrderInfo info){
		 Map<String, Map<String, Object[]>> map = new HashMap<String, Map<String, Object[]>>();
		 final int fontSize = 14;
		 final String merchantName = info.getMerchantName();
		 map.put("merchantName", new HashMap<String, Object[]>(){
			 {put("merchantName1", new Object[]{merchantName, "微软雅黑",Font.BOLD,color,fontSize, 100, 70, 0.5f});
		 }});
		 final String merchantNo = info.getMerchantNo();
		 map.put("merchantNo", new HashMap<String, Object[]>(){
			 {put("merchantno1", new Object[]{merchantNo,"微软雅黑",Font.BOLD,color,fontSize, 248, 115, 0.5f});
		 }});
		 final String terminalId = info.getTerminalId(); 
		 map.put("terminalId", new HashMap<String, Object[]>(){
			 {put("terminalId1", new Object[]{terminalId,"微软雅黑",Font.BOLD,color,fontSize, 400, 160, 0.5f});
		 }});
		 final String issuer = info.getIssuer();
		 map.put("issuer", new HashMap<String, Object[]>(){
			 {put("issuer0", new Object[]{issuer,"微软雅黑",Font.BOLD,color,fontSize, 105, 200, 0.5f});
		 }});
		 final String acquirer = info.getAcquirer();
		 map.put("acquirer", new HashMap<String, Object[]>(){
			 { put("acquirer1", new Object[]{acquirer,"微软雅黑",Font.BOLD,color,fontSize, 550, 200, 0.5f});
		 }});
		 final String cardNo = info.getCardNo();
		 map.put("cardNo", new HashMap<String, Object[]>(){
			 {put("cardNo0", new Object[]{cardNo,"微软雅黑",Font.BOLD,color,fontSize, 140, 250, 0.5f});
		 }});
		 final String transType = info.getTransType();
		 map.put("transType", new HashMap<String, Object[]>(){
			 {put("transType0", new Object[]{transType,"微软雅黑",Font.BOLD,color,fontSize, 120, 285, 0.5f});
		 }});
		 final String expDate = info.getExpDate();
		 map.put("expDate", new HashMap<String, Object[]>(){
			 {put("expDate0", new Object[]{expDate,"微软雅黑",Font.BOLD,color,fontSize, 250, 285, 0.5f});
		 }});
		 final String batchNo = info.getBatchNo();
		 map.put("batchNo", new HashMap<String, Object[]>(){
			 {put("batchNo0", new Object[]{batchNo,"微软雅黑",Font.BOLD,color,fontSize, 120, 330, 0.5f});
		 }});
		 final String voucherNo = info.getVoucherNo();
		 map.put("voucherNo", new HashMap<String, Object[]>(){
			 {put("voucherNo0", new Object[]{voucherNo,"微软雅黑",Font.BOLD,color,fontSize, 250, 330, 0.5f});
		 }});
		 final String authNo = info.getAuthNo();
		 map.put("authNo", new HashMap<String, Object[]>(){
			 {put("authNo0", new Object[]{authNo,"微软雅黑",Font.BOLD,color,fontSize, 120, 370, 0.5f});
		 }});
		 final String tradeNo = info.getTradeNo();
		 map.put("tradeNo", new HashMap<String, Object[]>(){
			 {put("tradeNo0", new Object[]{tradeNo,"微软雅黑",Font.BOLD,color,fontSize, 250, 370, 0.5f});
		 }});
		 final String time = info.getTime();
		 map.put("time", new HashMap<String, Object[]>(){
			 {put("time0", new Object[]{time,"微软雅黑",Font.BOLD,color,fontSize, 120, 415, 0.5f});
		 }});
		 final String orderNo = info.getOrderNo();
		 map.put("orderNo", new HashMap<String, Object[]>(){
			 {put("orderNo0", new Object[]{orderNo,"微软雅黑",Font.BOLD,color,fontSize, 120, 460, 0.5f});
		 }});
		 final String referNo = info.getReferNo();
		 map.put("referNo", new HashMap<String, Object[]>(){
			 {put("referNo0", new Object[]{referNo,"微软雅黑",Font.BOLD,color,fontSize, 120, 505, 0.5f});
		 }});
		 final String amount = info.getAmount();
		 map.put("amount", new HashMap<String, Object[]>(){
			 {put("amount0", new Object[]{"RMB " + amount,"微软雅黑",Font.BOLD,color,fontSize, 120, 550, 0.5f});
		 }});
		 return map;
	 }
	 
	 public final static Image scale2(String srcImageFile, String result, int height, int width, boolean bb) {
	        try {
	            double ratio = 0.0; // 缩放比例
	            File f = new File(srcImageFile);
	            BufferedImage bi = ImageIO.read(f);
	            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
	            // 计算比例
	            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
	                if (bi.getHeight() > bi.getWidth()) {
	                    ratio = (new Integer(height)).doubleValue()
	                            / bi.getHeight();
	                } else {
	                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
	                }
	                AffineTransformOp op = new AffineTransformOp(AffineTransform
	                        .getScaleInstance(ratio, ratio), null);
	                itemp = op.filter(bi, null);
	            }
	            if (bb) {//补白
	                BufferedImage image = new BufferedImage(width, height,
	                        BufferedImage.TYPE_INT_RGB);
	                Graphics2D g = image.createGraphics();
	                g.setColor(Color.white);
	                g.fillRect(0, 0, width, height);
	                if (width == itemp.getWidth(null))
	                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
	                            itemp.getWidth(null), itemp.getHeight(null),
	                            Color.white, null);
	                else
	                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
	                            itemp.getWidth(null), itemp.getHeight(null),
	                            Color.white, null);
	                g.dispose();
	                itemp = image;
	            }
//	            ImageIO.write((BufferedImage) itemp, "png", new File(result));
	            return itemp;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 /**
	  * 
	  * @param info  -- 签购单信息
	  * @param imagePath  -- 签名图片地址 eg:"e:\\image\\name.png"
	  * @param resultPath -- 成功生成的图片文件地址 eg:"e:\\image\\签购单20160420.png"
	  * @return
	  */
	 public static boolean printSalePic(OrderInfo info, String imagePath, String resultPath){
		 Map<String, Map<String, Object[]>> map = getMap(info);
		 boolean flag = false;
		 if(pressText(resultPath,map)){
			 Image image = scale2(imagePath, "", 82, 120, true);
			 flag = pressImage(resultPath, resultPath, 105, 690, 0.5f, image);
		 }
		 return flag;
	 }
	 /**
	  * 签购单审核
	  * @param pressImg  -- 水印图片地址 eg:"e:\\image\\通过.png"
	  * @param signPicPath -- 待签名图片地址 eg:"e:\\image\\签购单20160420.png"
	  * @param salePicPath  -- 生成的图片地址 eg: "e:\\image\\签购单20160420_success.png"
	  * @return
	  */
	 public final static boolean printSalePic(String pressImg, String signPicPath, String salePicPath){
		 try {
			pressImage(pressImg, signPicPath, salePicPath, 206, 678, 0.5f);
		} catch (Exception e) {
			//记录失败日志
			return Boolean.FALSE;
		}
		 return Boolean.TRUE;
	 }
	 
	 public static void main(String[] args){
		    OrderInfo orderInfo = new OrderInfo();
		    orderInfo.setMerchantName("上海商家信息测试");
		    orderInfo.setMerchantNo("123321");
		    orderInfo.setCardNo("1111 1111 1111 1111");
		    orderInfo.setAcquirer("交通银行");
		    orderInfo.setAmount("10");
		    orderInfo.setAuthNo("authNo");
		    orderInfo.setBatchNo("batchno");
		    orderInfo.setExpDate("expDate");
		    orderInfo.setIssuer("上海农商银行");
		    orderInfo.setOrderNo("orderNo");
		    orderInfo.setReferNo("referNo");
		    orderInfo.setTerminalId("terminalId");
		    orderInfo.setTime("time");
		    orderInfo.setTradeNo("trandNo");
		    orderInfo.setTransType("transType");
		    orderInfo.setVoucherNo("voucherNo");
		    printSalePic(orderInfo, "e:\\image\\name1.png","e:\\image\\3.png");
		    printSalePic("e:\\image\\通过.png","e:\\image\\3.png","e:\\image\\4.png");
//		    scale2("e:\\image\\name1.png", "e:\\image\\name_2.png", 82, 120, true);
		    System.out.println(templateUrl.substring(0, templateUrl.lastIndexOf(".")));
		    
		 }
}
