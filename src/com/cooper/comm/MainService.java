package com.cooper.comm;

import com.cooper.appservice.comm.thread.task.ServiceCallable;
import com.cooper.appservice.comm.thread.task.ServiceRunnable;
import com.cooper.appservice.comm.thread.manager.RunManager;
import com.cooper.appservice.comm.thread.task.UncaughtExceptionHandler;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: ����8:12
 * To change this template use File | Settings | File Templates.
 */
public class MainService {

    public MainService() {
        super();
    }


    /*********�̵߳��ÿ�ʼ*******/
    /**
     * �����ͨ�߳�����
     * @param serviceRunnable ���������Ҫ����ʵ��
     */
    public void addRunTask(ServiceRunnable serviceRunnable) {
        addRunTask(serviceRunnable, 0);
    }

    /**
     * �����û���Ӹ��û��������Ŷ�ִ�е�����
     * @param serviceRunnable
     * @param userId ������������û�id
     * @param queueAble �������Ƿ������Ŷӣ�����������Ŷӣ���ǰ����ִ�����ǰ������������ӽ����ᱻ��������������Ŷӣ���ᱻ���������
     */
    public void addUserRunTask(ServiceRunnable serviceRunnable, String userId, boolean queueAble) {
        addUserRunTask(serviceRunnable, userId, queueAble, 0);
    }

    /**
     * ������������������������Ķ��߳�����
     * @param serviceRunnable
     */
    public void addClassLimitRunTask(ServiceRunnable serviceRunnable) {
        addClassLimitRunTask(serviceRunnable, 0);
    }
    /**
     * ���������������Ψһ�����Ķ��߳�����ֻ��һ���߳��Ŷ�ִ��
     * @param serviceRunnable
     * @param queueAble ������ִ���У��Ƿ���������µ��������Ϊfalse�����¼��������ᱻ����
     */
    public void addClassSingleLimitRunTask(ServiceRunnable serviceRunnable, boolean queueAble) {
        addClassSingleLimitRunTask(serviceRunnable, queueAble, 0);
    }
    /**
     * ��Ӷ����񵥶���ִ���߳�
     * @param serviceRunnable
     */
    public void addSequenceRunTask(ServiceRunnable serviceRunnable) {
        addSequenceRunTask(serviceRunnable, 0);
    }

    /**
     * �����ͨ�߳�����
     * @param serviceRunnable ���������Ҫ����ʵ��
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public void addRunTask(ServiceRunnable serviceRunnable, int overTime) {
        RunManager.addTask(serviceRunnable, overTime);
    }

    /**
     * �����û���Ӹ��û��������Ŷ�ִ�е�����
     * @param serviceRunnable
     * @param userId ������������û�id
     * @param queueAble �������Ƿ������Ŷӣ�����������Ŷӣ���ǰ����ִ�����ǰ������������ӽ����ᱻ��������������Ŷӣ���ᱻ���������
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public void addUserRunTask(ServiceRunnable serviceRunnable, String userId, boolean queueAble, int overTime) {
        RunManager.addUserTask(userId, serviceRunnable, queueAble, overTime, null);
    }
    /**
     * �����û���Ӹ��û��������Ŷ�ִ�е�����
     * @param serviceRunnable
     * @param userId ������������û�id
     * @param queueAble �������Ƿ������Ŷӣ�����������Ŷӣ���ǰ����ִ�����ǰ������������ӽ����ᱻ��������������Ŷӣ���ᱻ���������
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     * @param handler �쳣����ӿ���
     */
    public void addUserRunTask(ServiceRunnable serviceRunnable, String userId, boolean queueAble, int overTime, UncaughtExceptionHandler handler) {
        RunManager.addUserTask(userId, serviceRunnable, queueAble, overTime, handler);
    }
    /**
     * ������������������������Ķ��߳�����
     * @param serviceRunnable
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public void addClassLimitRunTask(ServiceRunnable serviceRunnable, int overTime) {
        RunManager.addLimitTask(serviceRunnable, overTime, null);
    }
    /**
     * ������������������������Ķ��߳�����
     * @param serviceRunnable
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     * @param handler �쳣����ӿ���
     */
    public void addClassLimitRunTask(ServiceRunnable serviceRunnable, int overTime, UncaughtExceptionHandler handler) {
        RunManager.addLimitTask(serviceRunnable, overTime, handler);
    }
    /**
     * ���������������Ψһ�����Ķ��߳�����ֻ��һ���߳��Ŷ�ִ��
     * @param serviceRunnable
     * @param queueAble ������ִ���У��Ƿ���������µ��������Ϊfalse�����¼��������ᱻ����
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public void addClassSingleLimitRunTask(ServiceRunnable serviceRunnable, boolean queueAble, int overTime) {
        RunManager.addSingleLimitTask(serviceRunnable, queueAble, overTime, null);
    }
    /**
     * ���������������Ψһ�����Ķ��߳�����ֻ��һ���߳��Ŷ�ִ��
     * @param serviceRunnable
     * @param queueAble ������ִ���У��Ƿ���������µ��������Ϊfalse�����¼��������ᱻ����
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     * @param handler �쳣����ӿ���
     */
    public void addClassSingleLimitRunTask(ServiceRunnable serviceRunnable, boolean queueAble, int overTime,
                                           UncaughtExceptionHandler handler) {
        RunManager.addSingleLimitTask(serviceRunnable, queueAble, overTime, handler);
    }
    /**
     * ��Ӷ����񵥶���ִ���߳�
     * @param serviceRunnable
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public void addSequenceRunTask(ServiceRunnable serviceRunnable, int overTime) {
        RunManager.addSequenceTask(serviceRunnable, overTime, null);
    }
    /**
     * ��Ӷ����񵥶���ִ���߳�
     * @param serviceRunnable
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     * @param handler �쳣������
     */
    public void addSequenceRunTask(ServiceRunnable serviceRunnable, int overTime, UncaughtExceptionHandler handler) {
        RunManager.addSequenceTask(serviceRunnable, overTime, handler);
    }
    public <T> Future<T> submitRunTask(ServiceCallable<T> callable) {
        return RunManager.submitRunTask(callable);
    }

    /**
     * ������������������������Ķ��߳�����
     * @param callable
     */
    public <T> Future<T> submitClassLimitRunTask(ServiceCallable<T> callable) {
        return submitClassLimitRunTask(callable, 0);
    }

    /**
     * ������������������������Ķ��߳�����
     * @param callable
     */
    public <T> Future<T> submitClassLimitRunTask(ServiceCallable<T> callable, int overTime) {
        return RunManager.submitLimitTask(callable, overTime);
    }

    /**
     * ���������������Ψһ�����Ķ��߳�����ֻ��һ���߳��Ŷ�ִ��
     * @param callable
     * @param queueAble ������ִ���У��Ƿ���������µ��������Ϊfalse�����¼��������ᱻ����
     */
    public <T> Future<T> submitClassSingleLimitRunTask(ServiceCallable<T> callable, boolean queueAble) {
        return submitClassSingleLimitRunTask(callable, queueAble, 0);
    }

    /**
     * ���������������Ψһ�����Ķ��߳�����ֻ��һ���߳��Ŷ�ִ��
     * @param callable
     * @param queueAble ������ִ���У��Ƿ���������µ��������Ϊfalse�����¼��������ᱻ����
     */
    public <T> Future<T> submitClassSingleLimitRunTask(ServiceCallable<T> callable, boolean queueAble, int overTime) {
        return RunManager.submitSingleLimitTask(callable, queueAble, overTime, null);
    }
    /**
     * ���������������Ψһ�����Ķ��߳�����ֻ��һ���߳��Ŷ�ִ��
     * @param callable
     * @param queueAble ������ִ���У��Ƿ���������µ��������Ϊfalse�����¼��������ᱻ����
     * @param overTime �ȴ���ʱʱ��
     * @param handler �쳣����ӿ���
     */
    public <T> Future<T> submitClassSingleLimitRunTask(ServiceCallable<T> callable, boolean queueAble, int overTime, UncaughtExceptionHandler handler) {
        return RunManager.submitSingleLimitTask(callable, queueAble, overTime, handler);
    }
    /**
     * ��Ӷ����񵥶���ִ���߳�
     * @param callable
     */
    public <T> Future<T> submitSequenceRunTask(ServiceCallable<T> callable) {
        return submitSequenceRunTask(callable, 0);
    }
    /**
     * ��Ӷ����񵥶���ִ���߳�
     * @param callable
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public <T> Future<T> submitSequenceRunTask(ServiceCallable<T> callable, int overTime) {
        return RunManager.submitSequenceTask(callable, overTime, null);

    }
    /**
     * ��Ӷ����񵥶���ִ���߳�
     * @param callable
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     * @param handler �쳣����ӿ���
     */
    public <T> Future<T> submitSequenceRunTask(ServiceCallable<T> callable, int overTime, UncaughtExceptionHandler handler) {
        return RunManager.submitSequenceTask(callable, overTime, handler);

    }
    /**
     * �����û���Ӹ��û��������Ŷ�ִ�е�����
     * @param callable
     * @param userId ������������û�id
     * @param queueAble �������Ƿ������Ŷӣ�����������Ŷӣ���ǰ����ִ�����ǰ������������ӽ����ᱻ��������������Ŷӣ���ᱻ���������
     */
    public <T> Future<T> submitUserRunTask(ServiceCallable<T> callable, String userId, boolean queueAble) {
        return submitUserRunTask(callable, userId, queueAble, 0);
    }
    /**
     * �����û���Ӹ��û��������Ŷ�ִ�е�����
     * @param callable
     * @param userId ������������û�id
     * @param queueAble �������Ƿ������Ŷӣ�����������Ŷӣ���ǰ����ִ�����ǰ������������ӽ����ᱻ��������������Ŷӣ���ᱻ���������
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public <T> Future<T> submitUserRunTask(ServiceCallable<T> callable, String userId, boolean queueAble, int overTime) {
        return RunManager.submitUserTask(userId, callable, queueAble, overTime, null);
    }
    /**
     * �����û���Ӹ��û��������Ŷ�ִ�е�����
     * @param callable
     * @param userId ������������û�id
     * @param queueAble �������Ƿ������Ŷӣ�����������Ŷӣ���ǰ����ִ�����ǰ������������ӽ����ᱻ��������������Ŷӣ���ᱻ���������
     * @param overTime ����ʱʱ�䣬��λ���룬������ʱ�䣬��������stop����������һ��ʱ�䲻��ֹͣ�������ǿ����ֹ
     */
    public <T> Future<T> submitUserRunTask(ServiceCallable<T> callable, String userId, boolean queueAble, int overTime,
                                           UncaughtExceptionHandler handler) {
        return RunManager.submitUserTask(userId, callable, queueAble, overTime, handler);
    }
    /*********�̵߳��ý���*******/

}
