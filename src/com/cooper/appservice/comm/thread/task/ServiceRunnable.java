package com.cooper.appservice.comm.thread.task;
import com.cooper.appservice.comm.thread.context.ServiceContext;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: ����8:19
 * To change this template use File | Settings | File Templates.
 * �̵߳��ýӿ�
 */
public interface ServiceRunnable extends ServiceRunInterface {
    /**
     * ����ǰ���񱻵���ʱ������ô˷���
     * @param context context���̵߳��õ�������
     */
    public void run(ServiceContext context);


}
