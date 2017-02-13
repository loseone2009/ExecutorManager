package com.cooper.appservice.comm.thread.task;

import com.cooper.appservice.comm.thread.context.ServiceContext;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-26
 * Time: ����4:40
 * To change this template use File | Settings | File Templates.
 * ʵ����call��stop�����ĸ������
 * �û�����Ҫfuture���ض���ʱ����Ҫʵ�ֱ��ࡣ
 * ������call(Context)�����Ǳ�������Ҫ�������ʵ�ֵķ���
 * �����call����������Ϊ��final����������������������д
 * call����ʵ���Ƕ�Callable�ӿڷ���������д
 */
public abstract class ServiceCallable<E> implements Callable<E>, ServiceRunInterface {

    public abstract E call(ServiceContext context);

    final public E call() throws Exception {
        return call(new ServiceContext());
    }
}
