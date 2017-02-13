package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.data.RunnableObjectList;
import com.cooper.appservice.comm.thread.data.UserThreadObject;
import com.cooper.appservice.comm.thread.context.ServiceContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: ����10:31
 * To change this template use File | Settings | File Templates.
 * �����ǰ�������id�����̶߳��е��߳���
 */
public class ServiceUserThread extends ServiceThread {

    /**
     * ������е�����id�߳̿��������,��������Ŀ�����ڵ�ǰ����ִ�����֮�󣬻�ȡ��һ�������Լ�����������в�������
     * ������Ϊ�����UserThreadObject��ӵ�����ӳ��й�ϵ
     */
    private RunnableObjectList runnableObjectList;

    /**
     * ������
     * @param context ��ǰ���͵������Ķ���
     * @param runnableObjectList ��ǰ����е��������
     */
    public ServiceUserThread(ServiceContext context, RunnableObjectList runnableObjectList) {
        this.context = context;
        this.runnableObjectList = runnableObjectList;
    }

    public void run() {
        this.setThreadId(Thread.currentThread().getId());
        try {
            while(!isInterrupted()) {
                RunnableObject runnableObject = this.runnableObjectList.getNextQueueJob();
                if(runnableObject == null) {
                    break;
                }
                runnableObject.run(this);
                this.runnableObjectList.unLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            breakFlg = true;
            this.runnableObjectList.unLock();
        }
    }


}
