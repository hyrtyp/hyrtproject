package com.hyrt.cei.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class NewFileDownload {

	private static int fileLength;

	public static void download(final String address, final File saveFile,
			final Handler handler) throws Exception {
		new Thread() {

			@Override
			public void run() {
				try {
					URL url = new URL(address);
					RandomAccessFile file;
					long pos = 0;
					HttpURLConnection cn = (HttpURLConnection) url
							.openConnection();
					fileLength = cn.getContentLength();
					cn.setConnectTimeout(5000);
					/*cn.setRequestProperty("Range", "bytes=" + 0 + "-"
					+ fileLength);*/
					if (cn.getResponseCode() == 200) {
						if (fileLength <= 0) {
							// 文件为空
							Message msg = new Message();
							msg.arg1 = -1;
							handler.sendMessage(msg);
							return;
						} else {
							File thefile = new File(saveFile + "/bg.zip");
							file = new RandomAccessFile(thefile, "rwd");
							byte[] buf = new byte[1024*2];
							BufferedInputStream bis = new BufferedInputStream(
									cn.getInputStream());
							int len;
							file.seek(pos);
							while ((len = bis.read(buf)) != -1) {
								file.write(buf, 0, len);
								pos += len;
								System.out.println(pos * 100
										/ fileLength + "%");
								Message msg = new Message();
								msg.arg1 = (int) (pos * 100 / fileLength);
								handler.sendMessage(msg);
								if (pos * 100 / fileLength == 100) {
									return;
								}
							}
						}

					} else if (cn.getReadTimeout() > 5000) {
						Message msg = new Message();
						msg.arg1 = -2;
						handler.sendMessage(msg);
						return;
					} else {
						Message msg = new Message();
						msg.arg1 = -3;
						handler.sendMessage(msg);
						return;
					}
				} catch (MalformedURLException e) {
					Message msg = new Message();

					msg.arg1 = -4;
					handler.sendMessage(msg);
					e.printStackTrace();
					return;
				} catch (FileNotFoundException e) {
					Message msg = new Message();

					msg.arg1 = -5;
					handler.sendMessage(msg);
					e.printStackTrace();
					return;
				} catch (IOException e) {
					Message msg = new Message();

					msg.arg1 = -6;
					handler.sendMessage(msg);
					e.printStackTrace();
					return;
				}

			}

		}.start();

	}
}
