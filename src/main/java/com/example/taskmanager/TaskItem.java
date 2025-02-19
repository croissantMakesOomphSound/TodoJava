package com.example.taskmanager;

import java.util.Objects;

public class TaskItem {
    private String item;
    private String link;

    public TaskItem(String item, String link) {
        this.item = item;
        this.link = link;
    }

    public String getItem() {
        return item;
    }

    public String getLink() {
        return link;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskItem taskItem = (TaskItem) o;
        return Objects.equals(item, taskItem.item) &&
                Objects.equals(link, taskItem.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, link);
    }

    @Override
    public String toString() {
        return item + "\t-" + link;
    }
}
