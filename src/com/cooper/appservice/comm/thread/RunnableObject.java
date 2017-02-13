package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.constant.ThreadConfig;
import com.cooper.appservice.comm.thread.data.TaskObject;
import com.cooper.appservice.comm.thread.task.ServiceCallable;
import com.cooper.appservice.comm.thread.task.ServiceRunInterface;
import com.cooper.appservice.comm.thread.task.ServiceRunnable;
import com.cooper.appservice.comm.thread.log.LogPrint;
import com.cooper.appservice.comm.thread.log.ThreadLogCreateTask;
import com.cooper.appservice.comm.thread.log.ThreadLogDetailCreateTask;
import com.cooper.appservice.comm.thread.task.UncaughtExceptionHandler;
import com.cooper.appservice.comm.thread.watch.ServiceRunWatchThread;
import com.cooper.proxy.db.HiDbThreadLogDetail;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-22
 * Time: ����11:07
 * To change this template use File | Settings | File Templates.
 * ����������������İ�װ��
 */
public class RunnableObject<E> {

    /**�����id��������������������㷨Ϊ���������Ӧ��id������־�п��Խ��ж��ղ鿴*/
    private static int runCount = 0;
    /**
     * ��������Future���͵ķ��ض����ִ����
     */
    CallableObject<E> callableObject;
    /**
     * ��ǰ�����̵߳�id
     */
    private int id;
    /**
     * ֹͣ����ִ�еĶ�����
     */
    ServiceRunInterface serviceRunInterface;

    TaskObject taskObject;
    /**ִ�з����ķ�ʽ true ����ʹ����callable�ӿ�  false����ʹ����runnable�ӿ�*/
    private boolean callTaskFlg = false;

    String taskName;
    Date date;
    boolean taskOverFlg = false;
    ServiceThread runningThread;
    int overTime;//����ʱ�䡣��Ϊ��λ,0Ϊ������

    int warnCount = 0;

    StackTraceElement[] createStackElements;

    long takenTime = 0l;

    private HiDbThreadLogDetail threadLogDetail;

    private String stackString;

    private final UncaughtExceptionHandler uncaughtExceptionHandler;

    public int getOverTime() {
        return overTime;
    }

    public void setTaskObject(TaskObject taskObject) {
        this.taskObject = taskObject;
    }

    public boolean removeFromTaskCollection() {
        if(this.taskObject != null) {
            return this.taskObject.removeTask(this);
        }
        return false;
    }

    public RunnableObject(CallableObject<E> callable, ServiceCallable<E> serviceCallable, int overTime, String userId,
                          boolean queueFlg, UncaughtExceptionHandler uncaughtExceptionHandler) {
        callTaskFlg = true;
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        this.callableObject = callable;
        this.serviceRunInterface = serviceCallable;
        Throwable ex = new Throwable();
        createStackElements = ex.getStackTrace();
        this.taskName = this.serviceRunInterface.getClass().getName();
        this.overTime = overTime < 0 ? 0 : overTime;
        setId(this);
        createDetail(userId, queueFlg);
    }

    public RunnableObject(ServiceRunnable runnable, int overTime, String userId, boolean queueFlg,
                          UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        callTaskFlg = false;
        Throwable ex = new Throwable();
        createStackElements = ex.getStackTrace();
        this.serviceRunInterface = runnable;
        this.taskName = this.serviceRunInterface.getClass().getName();
        this.overTime = overTime < 0 ? 0 : overTime;
        setId(this);
        createDetail(userId, queueFlg);
    }

    private void createDetail(String userId, boolean queueFlg) {
        threadLogDetail = new HiDbThreadLogDetail();
        threadLogDetail.setUserId(userId);
        threadLogDetail.setQueueAble(queueFlg ? ThreadConfig.DO_FLG : ThreadConfig.UNDO_FLG);
        stackString = createStackString();
        threadLogDetail.setCallStack(stackString);
        threadLogDetail.setCallClass(createStackElements[4].getClassName());//TODO
        threadLogDetail.setCallMethod(createStackElements[4].getMethodName());//TODO
        threadLogDetail.setThreadClassName(serviceRunInterface.getClass().getName());
        threadLogDetail.setStopFlg(ThreadConfig.UNDO_FLG);
    }

    private String createStackString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this).append(" call from:\r\n");
        for(StackTraceElement stackTraceElement : createStackElements) {
            sb.append("    ").append(stackTraceElement).append("\r\n");
        }
        return sb.toString();
    }

    private void updateDetail() {
        threadLogDetail.setInvokeClassName(runningThread.getClass().getName());
        threadLogDetail.setInvokeStartDate(new Date());
        threadLogDetail.setThreadLogId("");

    }

    private static synchronized void setId(RunnableObject r) {
        runCount ++;
        r.id = runCount;
    }

    public void startRun(ServiceThread serviceThread) {
        this.date = new Date();
        startRunPrint();
        this.runningThread = serviceThread;
        this.updateDetail();
        if(saveLogDetail()) {
            ThreadLogDetailCreateTask.addDetailTask(this.threadLogDetail);
        }
        ServiceRunWatchThread.addObject(this);
    }
    private boolean saveLogDetail() {
        if(serviceRunInterface instanceof ThreadLogDetailCreateTask) return false;
        if(serviceRunInterface instanceof ThreadLogCreateTask) return false;
        return true;
    }
    public void endRun() {
        this.takenTime = System.currentTimeMillis() - date.getTime();
        taskOverFlg = true;
        updateThreadDetailStopFlg(ThreadConfig.DO_FLG);
    }

    public void updateThreadDetailStopFlg(int stopFlg) {
        printEndLog();
        this.threadLogDetail.setInvokeEndDate(new Date());
        synchronized (threadLogDetail) {
            if(threadLogDetail.getStopFlg() == ThreadConfig.UNDO_FLG) {
                this.threadLogDetail.setStopFlg(stopFlg);
            }
        }
        if(saveLogDetail()) {
            ThreadLogDetailCreateTask.addDetailTask(this.threadLogDetail);
        }
    }

    public void run(ServiceThread serviceThread) {
        this.startRun(serviceThread);
        try {
            if(callTaskFlg) {
                ServiceCallable<E> serviceRunnable = (ServiceCallable<E>) serviceRunInterface;
                E o = serviceRunnable.call(serviceThread.context());
                synchronized (callableObject) {
                    callableObject.setReturnValue(o);
                    callableObject.notify();
                }
            } else {
                ServiceRunnable serviceRunnable = (ServiceRunnable) serviceRunInterface;
                serviceRunnable.run(serviceThread.context());
            }
        } catch (Exception e) {
            this.writeExceptionStack(e);
            if(this.uncaughtExceptionHandler != null) {
                try {
                    this.uncaughtExceptionHandler.uncaughtException(e, serviceRunInterface);
                } catch (Exception e2) {}
            }
        }
        this.endRun();
    }

    public void stopTask() {
        this.serviceRunInterface.stopTask(runningThread.context());
//        this.endRun();
        updateThreadDetailStopFlg(ThreadConfig.SYSTEM_CALL_STOP);
    }

    public String getTaskName() {
        return taskName;
    }

    public void updateTakenTime() {
        this.takenTime = System.currentTimeMillis() - date.getTime();
    }

    public long getTakenTime() {
        return this.takenTime;
    }

    public boolean isOver() {
        return this.taskOverFlg;
    }

    public long getThreadId() {
        return runningThread.getThreadId();
    }

    public void breakThread() {
        this.runningThread.breakThread();
    }

    private void startRunPrint() {
        printStartLog();
        printStack();
    }

    /**
     * ��ӡ������־
     */
    private void printStack() {
        LogPrint.printStack(stackString);
    }

    /**
     * ��ӡ������־
     */
    private void printStartLog() {
        String s = this + " run start\r\n";
        LogPrint.printLog(s);
    }
    /**
     * ��ӡ������־
     */
    private void printEndLog() {
        String s = this + " run end last " + this.takenTime + "ms\r\n";
        LogPrint.printLog(s);
    }



    public String toString() {
        return "task ("+ id + ") " +taskName;
    }

    public void updateWarnCount() {
        this.warnCount ++;
    }

    public int getWarnCount() {
        return this.warnCount;
    }

    public void writeExceptionStack(Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append("Caused by: " + e.getMessage()).append("\r\n");
        StackTraceElement[] trace = e.getStackTrace();
        for (int i=0; i < trace.length; i++) {
            sb.append(trace[i]).append("\r\n");
        }
        threadLogDetail.setExceptionStack(sb.toString());
    }
}
