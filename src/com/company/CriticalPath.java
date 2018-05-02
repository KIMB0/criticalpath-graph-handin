package com.company;

import java.util.*;

public class CriticalPath {
    private int maxCost;
    private static String format = "%1$-10s %2$-5s %3$-5s %4$-5s %5$-5s %6$-5s %7$-10s\n";


    public Task[] criticalPath(Set<Task> tasks) {
        HashSet<Task> completed = new HashSet<Task>();
        HashSet<Task> remaining = new HashSet<Task>(tasks);

        while (!remaining.isEmpty()) {
            boolean progress = false;

            for (Iterator<Task> iterator = remaining.iterator(); iterator.hasNext();) {
                Task task = iterator.next();
                if (completed.containsAll(task.getDependencies())) {
                    int critical = 0;
                    for (Task t : task.getDependencies()) {
                        if (t.getCriticalDuration() > critical) {
                            critical = t.getCriticalDuration();
                        }
                    }
                    task.setCriticalDuration(critical + task.getDuration());

                    completed.add(task);
                    iterator.remove();

                    progress = true;
                }
            }

            if (!progress) {
                throw new RuntimeException("Cyclic dependency, algorithm stopped!");
            }
        }

        maxCost(tasks);
        HashSet<Task> initialNodes = initials(tasks);
        calculateEarliest(initialNodes);

        Task[] ret = completed.toArray(new Task[0]);
        Arrays.sort(ret, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return ret;
    }

    public void calculateEarliest(HashSet<Task> initials) {
        for (Task initial : initials) {
            initial.setEarliestStart(1);
            initial.setEarliestFinish(initial.getDuration());
            setEarliest(initial);
        }
    }

    public void setEarliest(Task initial) {
        int completionTime = initial.getEarliestFinish();
        for (Task task : initial.getDependencies()) {
            if (completionTime >= task.getEarliestStart()) {
                task.setEarliestStart(completionTime + 1);
                task.setEarliestFinish(completionTime + task.getDuration());
            }
            setEarliest(task);
        }
    }

    public HashSet<Task> initials(Set<Task> tasks) {
        HashSet<Task> remaining = new HashSet<Task>(tasks);
        for (Task task : tasks) {
            for (Task t : task.getDependencies()) {
                remaining.remove(t);
            }
        }

        System.out.print("Initial nodes: ");
        for (Task task : remaining) {
            System.out.print(task.getName() + " ");
        }
        System.out.print("\n\n");
        return remaining;
    }

    public void maxCost(Set<Task> tasks) {
        int max = -1;
        for (Task task : tasks) {
            if (task.getCriticalDuration() > max) {
                max = task.getCriticalDuration();
            }
        }
        maxCost = max;
        System.out.println("Critical path length: " + maxCost);
        for (Task task : tasks) {
            task.setLatest(maxCost);
        }
    }

    public void print(Task[] tasks) {
        System.out.format(format, "Task", "ES", "EF", "LS", "LF", "Float", "Critical?");
        for (Task task : tasks) {
            System.out.format(format, (Object[]) task.toStringArray());

        }
    }
}
