package com.ucweb.tools.monitorTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;

import com.ucweb.tools.infobean.RecodeInfo;
import com.ucweb.tools.utils.UcwebDateUtil;
import com.ucweb.tools.utils.UcwebFileUtils;
import com.ucweb.tools.utils.UcwebInfoQueue;

/***
 * 
 * ������ϸ����ุ�࣬�ṩ�˴�����־��¼����ȡLog tag�������־��¼������ģ�巽��
 *
 */

abstract class AbstractMonitor {
	private Context mContext;
	private final UcwebInfoQueue recodeInfoQueue;
	
	private final SimpleDateFormat sdf;
	
	public AbstractMonitor(Context context){
		mContext = context;
		recodeInfoQueue = UcwebInfoQueue.getInstance();
		sdf = UcwebDateUtil.YMDDateFormat.getYMDFormat();
	}
	
	/***
	 * ��������¼���Ա���ӽ���¼����
	 * @param fileName
	 * @return
	 */
	protected RecodeInfo createRecode(String fileName){
		RecodeInfo recodeInfo = new RecodeInfo();
		
		if (UcwebFileUtils.isSdcardAvailable()) {
			recodeInfo.path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
		} else {
			recodeInfo.path = mContext.getFilesDir().getPath() + File.separator + fileName;
		}
		
		recodeInfo.date = sdf.format(new Date());
		recodeInfo.uploadFlag = RecodeInfo.UploadFlag.NOT_UPLOAD; 
		
		return recodeInfo;
	}
	
	/***
	 * �������¼������
	 * @param info
	 * @return
	 */
	protected boolean addInQueue(RecodeInfo info){
		return recodeInfoQueue.addInfo(info);
	}
	
	/***
	 * ��ȡ��־Tag
	 * @return
	 */
	final protected String getLogTag(){
		return this.getClass().getSimpleName();
	}
	
	/**
	 * ��ʼ��س��󷽷�����Ҫ����ȥʵ��
	 */
	public abstract void startMonitor();
	
	/**
	 * ֹͣ��س��󷽷�
	 */
	public abstract void stopMonitor();
	
}
