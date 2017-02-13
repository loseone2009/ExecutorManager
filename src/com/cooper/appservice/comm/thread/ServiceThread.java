package src.com.cooper.appservice.comm.thread;
import com.cooper.appservice.comm.thread.context.ServiceContext;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: ����2:53
 * To change this template use File | Settings | File Templates.
 * ���������߳���ĸ���
 */
public abstract class ServiceThread extends Thread implements Comparable<ServiceThread> {
    /**
     * �����Ķ���
     */
    protected ServiceContext context;
    /**
     * �Ƿ��Ѿ�����ѭ���ı��
     */
    protected boolean breakFlg = false;
    /**
     * ��ǰ�̵߳��߳�id
     */
    protected long threadId;

    /**
     * ��ȡ��ǰ��context����ķ���
     * @return
     */
    public ServiceContext context() {
        return this.context;
    }

    public boolean isBreak() {
        return breakFlg;
    }

    protected void setThreadId(long id) {
        this.threadId = id;
    }

    public long getThreadId() {
        return this.threadId;
    }

    public void breakThread() {
        this.breakFlg = true;
    }

    public int compareTo(ServiceThread o) {
        return threadId > o.threadId ? 1 : (threadId < o.threadId ? -1 : 0);
    }
}
