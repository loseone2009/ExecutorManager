package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.data.RunnableObjectList;
import com.cooper.appservice.comm.thread.data.SequenceThreadObject;
import com.cooper.appservice.comm.thread.context.ServiceContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-21
 * Time: ����1:41
 * To change this template use File | Settings | File Templates.
 * �����������ִ���Ŷ��߳�������߳���
 */
public class ServiceSequenceThread extends ServiceThread {

    final private ServiceContext context;
    private RunnableObjectList runnableObjectList;

    public ServiceSequenceThread(ServiceContext context, RunnableObjectList runnableObjectList) {
        this.context = context;
        this.runnableObjectList = runnableObjectList;
    }

    public void run() {
        this.setThreadId(Thread.currentThread().getId());
        try {
            while(!isInterrupted()) {
                RunnableObject runnableObject = runnableObjectList.poll();
                if(runnableObject == null) break;
                runnableObject.run(this);
            }
        } finally {
            breakFlg = true;
        }
    }
}
