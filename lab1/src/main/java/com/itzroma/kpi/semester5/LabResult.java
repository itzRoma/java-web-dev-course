package com.itzroma.kpi.semester5;

public final class LabResult {
    private final String filePath;
    private Integer deletedWords;

    public LabResult(String filePath) {
        this.filePath = filePath;
        deletedWords = 0;
    }

    public void addDeletedWords(int count) {
        deletedWords += count;
    }

    public Integer getDeletedWords() {
        return deletedWords;
    }

    @Override
    public String toString() {
        return filePath + " : deleted " + deletedWords + " words";
    }
}
