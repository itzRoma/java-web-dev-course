package com.itzroma.kpi.semester5;

public class TaskResult {
    private Integer deletedWords;

    public TaskResult() {
        deletedWords = 0;
    }

    public TaskResult(Integer deletedWords) {
        this.deletedWords = deletedWords;
    }

    public void addDeletedWords(int count) {
        deletedWords += count;
    }

    public Integer getDeletedWords() {
        return deletedWords;
    }
}
