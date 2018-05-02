package com.company;

import java.util.HashSet;

public class Main {
    public static CriticalPath criticalPath;


    public static void main(String[] args) {
        criticalPath = new CriticalPath();
        HashSet<Task> allTasks = new HashSet<Task>();
        Task E = new Task("E", 20);
        Task G = new Task("G", 5, E);
        Task D = new Task("D", 10, E);
        Task H = new Task("H", 15, E);
        Task C = new Task("C", 5, D, G);
        Task F = new Task("F", 15, G);
        Task B = new Task("B", 20, C);
        Task start = new Task("A", 10, B, F, H);
        allTasks.add(E);
        allTasks.add(G);
        allTasks.add(D);
        allTasks.add(H);
        allTasks.add(C);
        allTasks.add(F);
        allTasks.add(B);
        allTasks.add(start);
        Task[] result = criticalPath.criticalPath(allTasks);
        criticalPath.print(result);
    }
}
