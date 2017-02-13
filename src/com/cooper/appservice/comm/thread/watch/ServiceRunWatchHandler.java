package com.cooper.appservice.comm.thread.watch;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import com.cooper.appservice.comm.thread.constant.ThreadConfig;
import com.cooper.appservice.comm.thread.log.LogPrint;
import com.cooper.proxy.comm.ExecutorFactory;

import java.text.DecimalFormat;

/**
 * Created by ZZQ on 2017/1/24.
 */
public class ServiceRunWatchHandler {

    public static void checkRunnableObject(RunnableObject runnableObject) {
        if(runnableObject.isOver()) {
            ServiceRunObjectList.removeObject(runnableObject);
            return;
        }
        runnableObject.updateTakenTime();
        if(runnableObject.getTakenTime() > ThreadConfig.threadWarnTime) {
            if(ThreadConfig.showWarningFlg) {
                LogPrint.warn(runnableObject, getTakenTimeStr(runnableObject.getTakenTime()));
            }
        }
        //ֻ�е����������˳�ʱʱ�䣬����Ҫ��ǿ��ֹͣ�Ĵ���
        //����ϵͳ���������ʱ���ѵ����ƣ������޶������ľ����������Ҫ�󳬹�����ֹ���̣߳���Ҳ��ִ����ֹ
        if((runnableObject.getOverTime() != 0 && (runnableObject.getOverTime() * 1000) <= runnableObject.getTakenTime())
                || (ThreadConfig.warnKillCount > 0 && runnableObject.getWarnCount() >= ThreadConfig.warnKillCount && ThreadConfig.warnKill)) {
            stopThread(runnableObject);
        }
    }
    /**
     * ��ȡʹ��ʱ��ĳ��ȵ��ַ���
     * @param takenTime
     * @return
     */
    private static String getTakenTimeStr(long takenTime) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("#");
        double takenTimeS = takenTime/1000d;
        double takenTimeM = takenTimeS/60;
        String _s = df.format(takenTimeM);
        int n = Integer.valueOf(_s);
        double d = n - takenTimeM;
        int s = Integer.valueOf(df.format(d * 60));
        int taken_s = 60 - s;
        return (n - 1) + " min " + taken_s + " s";
    }

    private static void stopThread(RunnableObject runnableObject) {
        Thread thread = ExecutorFactory.getThread(runnableObject.getThreadId());
        if(thread != null) {
            ServiceStopThread st = new ServiceStopThread(runnableObject);
            st.setDaemon(true);
            st.start();
            try {
                for(int i = 0; i < 3; i ++) {
                    if(checkThreadStop(runnableObject)) {
                        return;
                    }
                    Thread.sleep(10 * 1000);
                }
                thread.stop();
                st.stop();
                if(!thread.isAlive() || thread.isInterrupted()) {
                    runnableObject.breakThread();
                    ServiceRunObjectList.removeObject(runnableObject);
                    runnableObject.updateThreadDetailStopFlg(ThreadConfig.SYSTEM_SHUTDOWN_STOP);
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    private static boolean checkThreadStop(RunnableObject runnableObject) {
        return runnableObject.isOver();
    }
}
