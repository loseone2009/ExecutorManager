package com.cooper.appservice.comm.thread.data.manager;

import com.cooper.appservice.comm.thread.data.UserThreadObject;
import src.com.cooper.appservice.comm.thread.RunnableObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: ����1:52
 * To change this template use File | Settings | File Templates.
 */
public class UserTaskThreadManager {
    /**
     * String �û���
     * String ������
     * UserThreadObject  ��Ӧ���߳���
     *
     * */
    private final static ConcurrentHashMap<String, ConcurrentHashMap<String, UserThreadObject>> dataMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, UserThreadObject>>();

    private static ConcurrentHashMap<String, UserThreadObject> initServiceUserMap(String userId) {
        synchronized (dataMap) {
            ConcurrentHashMap<String, UserThreadObject> map = dataMap.get(userId);
            if(map == null) {
                map = new ConcurrentHashMap<String, UserThreadObject>();
                dataMap.put(userId, map);
            }
            return map;
        }
    }
    /**
     * ��ȡ�����û�������߳�ʵ��
     * @param userId
     * @param taskName
     * @return
     */
    public static void addJob(String userId, String taskName, RunnableObject runnableObject, boolean queueAble) {
        ConcurrentHashMap<String, UserThreadObject> map = dataMap.get(userId);
        if(map == null) {
            map = initServiceUserMap(userId);
        }
        UserThreadObject userThreadPojo = map.get(taskName);
        if(userThreadPojo == null) {
            userThreadPojo = initUserThreadPojo(taskName, queueAble, map);
        } else {
            userThreadPojo.setQueueFlg(queueAble);
        }
        userThreadPojo.addJob(runnableObject);
    }

    private static UserThreadObject initUserThreadPojo(String taskName, boolean queueAble, ConcurrentHashMap<String, UserThreadObject> map) {
        synchronized (map) {
            UserThreadObject userThreadPojo = map.get(taskName);
            if(userThreadPojo == null) {
                userThreadPojo = new UserThreadObject(queueAble);
                map.put(taskName, userThreadPojo);
            }
            return userThreadPojo;
        }
    }
}
