package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.context.ServiceContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-23
 * Time: ����5:16
 * To change this template use File | Settings | File Templates.
 * ѭ��ִ�е��߳�Ӧ�ü̳б�����
 */
public abstract class LongTimeRunningThread {

    protected boolean breakFlg;

    public boolean getBreakFlg() {
        return this.breakFlg;
    }

    protected void breakRunning() {
        this.breakFlg = true;
    }

    /**
     * �����Ҫ��ȡcontext����Ĳ�������Ҫ��д������
     * @param context
     */
    public void stopTask(ServiceContext context) {
        this.breakRunning();
    }
}
