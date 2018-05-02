package com.company;

import java.util.HashSet;

public class Task {
    private int duration;
    private int criticalDuration;
    private String name;
    private int earliestStart;
    private int earliestFinish;
    private int latestStart;
    private int latestFinish;
    private HashSet<Task> dependencies = new HashSet<Task>();

    public Task(String name, int duration, Task...dependencies) {
        this.duration = duration;
        this.name = name;
        for (Task task: dependencies){
            this.dependencies.add(task);
        }
        this.earliestFinish = -1;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCriticalDuration() {
        return criticalDuration;
    }

    public void setCriticalDuration(int criticalDuration) {
        this.criticalDuration = criticalDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEarliestStart() {
        return earliestStart;
    }

    public void setEarliestStart(int earliestStart) {
        this.earliestStart = earliestStart;
    }

    public int getEarliestFinish() {
        return earliestFinish;
    }

    public void setEarliestFinish(int earliestFinish) {
        this.earliestFinish = earliestFinish;
    }

    public int getLatestStart() {
        return latestStart;
    }

    public void setLatestStart(int latestStart) {
        this.latestStart = latestStart;
    }

    public int getLatestFinish() {
        return latestFinish;
    }

    public void setLatestFinish(int latestFinish) {
        this.latestFinish = latestFinish;
    }

    public HashSet<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(HashSet<Task> dependencies) {
        this.dependencies = dependencies;
    }

    public void setLatest(int maxCost){
        setLatestStart(maxCost - getCriticalDuration() + 1);
        setLatestFinish(getLatestStart() + getDuration() - 1);
    }

    public String[] toStringArray() {
        String criticalCond = earliestStart == latestStart ? "Yes" : "No";
        String[] toString = { name, earliestStart + "", earliestFinish + "", latestStart + "", latestFinish + "",
                latestStart - earliestStart + "", criticalCond };
        return toString;
    }

    public boolean isDependent(Task task) {
        if (dependencies.contains(task)) {
            return true;
        } else {
            for (Task dep: dependencies) {
                if (dep.isDependent(task)) {
                    return true;
                }
            }
        }
        return false;
    }
}
