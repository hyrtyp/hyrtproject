package com.hyrt.mwpm.util;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

import org.apache.commons.lang.builder.CompareToBuilder;

import sun.misc.BASE64Encoder;

/**
 * DES���ܺͽ��ܹ���,���Զ��ַ������м��ܺͽ��ܲ���  �� 
 * @author ��Ң��
 * <p>2009-12-5</p>
 */
public class DesUtils implements Comparable {
  
  /** �ַ���Ĭ�ϼ�ֵ     */
  private static String strDefaultKey = "national";

  /** ���ܹ���     */
  private static Cipher encryptCipher = null;

  /** ���ܹ���     */
  private static Cipher decryptCipher = null;

  /**  
   * ��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[]  
   * hexStr2ByteArr(String strIn) ��Ϊ�����ת������  
   *   
   * @param arrB  
   *            ��Ҫת����byte����  
   * @return ת������ַ���  
   * @throws Exception  
   *             �������������κ��쳣�������쳣ȫ���׳�  
   */
  public static String byteArr2HexStr(byte[] arrB) throws Exception {
    int iLen = arrB.length;
    // ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�����   
    StringBuffer sb = new StringBuffer(iLen * 2);
    for (int i = 0; i < iLen; i++) {
      int intTmp = arrB[i];
      // �Ѹ���ת��Ϊ����   
      while (intTmp < 0) {
        intTmp = intTmp + 256;
      }
      // С��0F������Ҫ��ǰ�油0   
      if (intTmp < 16) {
        sb.append("0");
      }
      sb.append(Integer.toString(intTmp, 16));
//      System.out.println("ִ��sb.append:"+"��ʱintTmp��"+intTmp+"  Integer.toString(intTmp,16):"+Integer.toString(intTmp,16));
    }
//    System.out.println(sb.toString()+"sb.toString::");
    return sb.toString();
  }

  /**  
   * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB)  
   * ��Ϊ�����ת������  
   *   
   * @param strIn  
   *            ��Ҫת�����ַ���  
   * @return ת�����byte����  
   * @throws Exception  
   *             �������������κ��쳣�������쳣ȫ���׳�  
   * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>  
   */
  public static byte[] hexStr2ByteArr(String strIn) throws Exception {
    byte[] arrB = strIn.getBytes();
    int iLen = arrB.length;

    // �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2   
    byte[] arrOut = new byte[iLen / 2];
    for (int i = 0; i < iLen; i = i + 2) {
      String strTmp = new String(arrB, i, 2);
      arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
    }
    return arrOut;
  }

  /**  
   * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ  
   *   
   * @throws Exception  
   */
  public DesUtils() throws Exception {
    this(strDefaultKey);
  }

  /**  
   * ָ����Կ���췽��  
   *   
   * @param strKey  
   *            ָ������Կ  
   * @throws Exception  
   */
  public DesUtils(String strKey) throws Exception {
    Security.addProvider(new com.sun.crypto.provider.SunJCE());
    Key key = getKey(strKey.getBytes());

    encryptCipher = Cipher.getInstance("DES");
    encryptCipher.init(Cipher.ENCRYPT_MODE, key);

    decryptCipher = Cipher.getInstance("DES");
    decryptCipher.init(Cipher.DECRYPT_MODE, key);
  }

  /**  
   * �����ֽ�����  
   *   
   * @param arrB  
   *            ����ܵ��ֽ�����  
   * @return ���ܺ���ֽ�����  
   * @throws Exception  
   */
  BASE64Encoder b=new BASE64Encoder();
  public static byte[] encrypt(byte[] arrB) throws Exception {
    return encryptCipher.doFinal(arrB);
    
  }

  /**  
   * �����ַ���  
   *   
   * @param strIn  
   *            ����ܵ��ַ���  
   * @return ���ܺ���ַ���  
   * @throws Exception  
   */
  public static String encrypt(String strIn) throws Exception {
    return byteArr2HexStr(encrypt(strIn.getBytes()));
  }

  /**  
   * �����ֽ�����  
   *   
   * @param arrB  
   *            ����ܵ��ֽ�����  
   * @return ���ܺ���ֽ�����  
   * @throws Exception  
   */
  public static byte[] decrypt(byte[] arrB) throws Exception {
    return decryptCipher.doFinal(arrB);
  }

  /**  
   * �����ַ���  
   *   
   * @param strIn  
   *            ����ܵ��ַ���  
   * @return ���ܺ���ַ���  
   * @throws Exception  
   */
  public static String decrypt(String strIn) throws Exception {
    return new String(decrypt(hexStr2ByteArr(strIn)));
  }

  /**  
   * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ  
   *   
   * @param arrBTmp  
   *            ���ɸ��ַ������ֽ�����  
   * @return ���ɵ���Կ  
   * @throws java.lang.Exception  
   */
  private Key getKey(byte[] arrBTmp) throws Exception {
    // ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��   
    byte[] arrB = new byte[8];

    // ��ԭʼ�ֽ�����ת��Ϊ8λ   
    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
      arrB[i] = arrBTmp[i];
    }

    // ������Կ   
    Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

    return key;
  }

  /**
   * main����  ��
   * @author ��Ң��
   * @param args
   */
  public static void main(String[] args) {
    try {
    	//15�����Ļ���45�����ȵ�����ĸΪ���ݵ��ַ���
      String test = "377721976@qq.com";
//      String test = "asdfghjklzxcvbnasdfghjklzxcvbnasdfghjklzxcvbn";
      DesUtils des = new DesUtils();//�Զ�����Կ   
      System.out.println("����ǰ���ַ���" + test+"    ���ȣ�"+test.length());
      System.out.println("���ܺ���ַ���" + DesUtils.encrypt(test));
      System.out.println("���ܺ���ַ�������Ϊ����"+DesUtils.encrypt(test).length());
      System.out.println("���ܺ���ַ���" + DesUtils.decrypt("25b7f5afee0d962a"));
      System.out.println("����ǰ���Ƿ���ȣ�" + test.equals(DesUtils.decrypt(DesUtils.encrypt(test))));
     
//���޶������ַ���������������޾���
//������ڿ����㷨,�����һһ��Ӧԭ��
//�Ӷ��ó�ѹ��/���ܺ�����ͬ���������޾���
//��������������޹̵Ķ������޷���ʾ
//�����������㷨������ 
    	/*MwpmSysUserinfo msu=new MwpmSysUserinfo();
    	 Class c = Class.forName("com.hyrt.mwpm.vo.MwpmSysUserinfo");
    	 Field[] ms = c.getFields();
    	for(int v=0;v<ms.length;v++){
    		System.out.println(ms[v]);
    	}*/
    	
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

/**
 * @see java.lang.Comparable#compareTo(Object)
 */
public int compareTo(Object object) {
	DesUtils myClass = (DesUtils) object;
	return new CompareToBuilder().append(this.b, myClass.b).toComparison();
}
}
