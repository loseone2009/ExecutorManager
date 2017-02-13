package com.cooper.appservice.comm.thread.watch;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import org.apache.http.annotation.GuardedBy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-22
 * Time: ����11:20
 * To change this template use File | Settings | File Templates.
 * �������̼߳������߳���
 * �����еļ��ֹͣ����Ӧ�ý��н�һ���Ĳ�֣�������Ϊ��װ����һ��������У�ʹ�ñ��༯�����̶߳�����
 * �����е��߳�ֹͣ�������ܴ���������գ���Ҫ��������
 * �����г����������������б����
 * �̼߳�ص�ѭ��ʱ����1����
 * ����������������谭��������ʣ��ʱ����п۳�
 * ����������ִ�����֮�󣬱����ֹͣѭ��
 *
 */
public class ServiceRunWatchThread extends Thread {


    @GuardedBy("this")
    private static ServiceRunWatchThread serviceRunWatchThread;

    private ServiceRunWatchThread() {}

    public synchronized static void addObject(RunnableObject runnableObject) {
        ServiceRunObjectList.addJob(runnableObject);
        if(serviceRunWatchThread == null || !serviceRunWatchThread.isAlive() || serviceRunWatchThread.isInterrupted()) {
            serviceRunWatchThread = new ServiceRunWatchThread();
            serviceRunWatchThread.setDaemon(true);
            serviceRunWatchThread.start();
        }
    }



    public void run() {
        long startTime = 0;
        long endTime = 0;
        while (true) {
            long waitTime = 60 * 1000 - (endTime - startTime);
            try {
                if(waitTime > 0) {
                    Thread.sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            List<RunnableObject> list;
            list = ServiceRunObjectList.getRunnableList();
            if(list.isEmpty()) {
                break;
            }
            startTime = System.currentTimeMillis();
            for(RunnableObject runnableObject : list) {
                ServiceRunWatchHandler.checkRunnableObject(runnableObject);
            }
            endTime = System.currentTimeMillis();
        }
    }
}
