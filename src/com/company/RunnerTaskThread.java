package com.company;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RunnerTaskThread extends Thread {

    private ConcurrentHashMap<String, Task> tasks = null;

    public RunnerTaskThread(ConcurrentHashMap<String, Task> tasks) {
        this.tasks = tasks;
    }

    public void run() {
        while (true){
            if (tasks.size() != 0){
                Iterator<ConcurrentHashMap.Entry<String, Task>> itr = tasks.entrySet().iterator();
                while (itr.hasNext()) {
                    ConcurrentHashMap.Entry<String, Task> entry = itr.next();
                    String key = entry.getKey();
                    Task t = entry.getValue();

                    String[] arr = t.task.split(" ", 3);
                    int a = Integer.parseInt(arr[0]);
                    int b = Integer.parseInt(arr[2]);
                    switch (arr[1]) {
                        case "+":
                            t.result = (a + b);
                            t.flag = true;
                            break;
                        case "-":
                            t.result = (a - b);
                            t.flag = true;
                            break;
                        case "*":
                            t.result = (a * b);
                            t.flag = true;
                            break;
                        case "/":
                            t.result = (a / b);
                            t.flag = true;
                            break;
                        default:
                            System.err.println("Ошибка введенных данных");
                    }
                }
            }
        }
    }
}
