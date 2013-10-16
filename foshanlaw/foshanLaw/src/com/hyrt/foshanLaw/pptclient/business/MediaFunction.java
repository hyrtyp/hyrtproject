/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.business;

import java.io.IOException;
import java.util.List;

import com.hyrt.foshanLaw.pptclient.common.FileObj;
import com.hyrt.foshanLaw.pptclient.common.FileOperator;
import com.hyrt.foshanLaw.pptclient.common.GlobalFunction;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;

/**
 * Description:媒体播放类
 * 
 * @author 郑伟
 * @Date 2013-1-11
 * @Copyright:2013-1-11
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class MediaFunction {
	Context context;
	MediaRecorder recorder;
	MediaPlayer mplayer;
	long stamp;
	boolean isplaying;
	FileObj fileobj;
	boolean isrecording;

	public MediaFunction(Context _context) {
		context = _context;
	}

	public boolean isRecording(){
		return isrecording;
	}
	
	// 开始录音
	public void RecordStart(boolean isgroup, String uid, String sessionid,
			List<String> receobj) {
		//先停止
		RecordStop();
		stamp = System.currentTimeMillis();
		String filename = "PPT_" + stamp + ".amr";
		String filepath = GlobalFunction.GetTmpPath(filename);
		
		try {
			recorder = new MediaRecorder();
			recorder.setAudioSource(AudioSource.MIC);// 声音采集来源(话筒)
			recorder.setOutputFormat(OutputFormat.RAW_AMR);// 输出的格式
			recorder.setAudioEncoder(AudioEncoder.AMR_NB);// 音频编码方式
			recorder.setOutputFile(filepath);// 输出方向
			recorder.prepare();
			recorder.start();
			isrecording=true;
			fileobj = new FileObj();
			fileobj.setGroup(isgroup);
			fileobj.setTime(GlobalFunction.GetDateTime());
			fileobj.setReceobj(receobj);
			fileobj.setFilename(filename);
			fileobj.setFilepath(filepath);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fileobj = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fileobj = null;
		}catch ( Exception e){
			e.printStackTrace();
			fileobj = null;
		}

	}

	// 停止录音
	public FileObj RecordStop() {

		if (recorder != null) {
			// Log.i("send", "----录音结束----");
			recorder.stop();
			recorder.release();
			recorder = null;
		}
		stamp = System.currentTimeMillis() - stamp;
		int second=(int) (stamp / 1000);
		//录音时间四舍五入
		if(stamp%1000>400){
			second++;
		}
		if (fileobj != null) {
			if(second>1&&second<61)
				fileobj.setSecond(second);
			else
				fileobj=null;
		}
		isrecording=false;
		return fileobj;
	}

	// 播放
	public void mpStart(final String ftppath,final String filepath ) {
		if (isplaying) {
			// 正在播放
			mpStop();
		}
		isplaying = false;
		// 检查本地文件是否存在
		if(GlobalFunction.fileExsit(filepath)==false){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					FileOperator fop=new FileOperator(context);
					//String re=fop.download(ftppath, filepath);
					 fop.download(ftppath, filepath);
					fop.close();
				}
			}).start();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*	if("ok".equals(re)==false){
				Toast.makeText(context, re,Toast.LENGTH_SHORT).show();
				return;
			}*/
		}
		// 播放
		mplayer = new MediaPlayer();
		try {
			mplayer.setDataSource(filepath);
			mplayer.prepare();
			mplayer.start();
			isplaying = true;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 停止播放
	public void mpStop() {
		if (mplayer != null&&mplayer.isPlaying()) {
			mplayer.stop();
			mplayer.release();
		}
		mplayer = null;
		isplaying = false;
	}
	
	public void close(){
		this.mpStop();
		this.RecordStop();
	}
}
