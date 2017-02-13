package com.cooper.appservice.comm.thread.data;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import src.com.cooper.appservice.comm.thread.ServiceSequenceThread;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import com.cooper.proxy.comm.ExecutorFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-27
 * Time: ����1:40
 * To change this template use File | Settings | File Templates.
 * �������Ŷ�ִ�е��߳���������ṩ�࣬������Ҫִ�е�������뵽�����У�Ȼ���ṩ���߳������ִ��
 * �����а���һ��RunnableObject���󹹽������ж������е������洢�ڱ�����
 * �����л�����
 */
public class SequenceThreadObject extends TaskObject {

    private final static RunnableObjectList runList = new RunnableObjectList();
    private static ServiceSequenceThread serviceSequenceThread;
    @Override
    public boolean removeTask(RunnableObject runnableObject) {
        return runList.remove(runnableObject);
    }

    @Override
    public void addJob(RunnableObject runnableObject) {
        runnableObject.setTaskObject(this);
        runList.add(runnableObject);
        notifyThread();
    }

    public synchronized static void notifyThread() {
        if(serviceSequenceThread == null || serviceSequenceThread.isBreak()) {
            final ServiceContext context1 = serviceSequenceThread == null ? new ServiceContext() : serviceSequenceThread.context();
            serviceSequenceThread = new ServiceSequenceThread(context1, runList);
            serviceSequenceThread.setDaemon(true);
            ExecutorFactory.getAppServiceExecutorInstance().execute(serviceSequenceThread);
        }
    }

}
