package com.example.vocabkhajana.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WordEntry {
    private final String value;
    private final String type;
    private final List<String> meanings;
    private final List<String> examples;

    public WordEntry(String value, String type, List<String> meanings, List<String> examples) {
        this.value = value;
        this.type = type;
        this.meanings = List.copyOf(meanings);
        this.examples = List.copyOf(examples);
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public List<String> getMeanings() {
        return Collections.unmodifiableList(meanings);
    }

    public List<String> getExamples() {
        return Collections.unmodifiableList(examples);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WordEntry wordEntry = (WordEntry) o;
        return Objects.equals(value, wordEntry.value)
                && Objects.equals(type, wordEntry.type)
                && Objects.equals(meanings, wordEntry.meanings)
                && Objects.equals(examples, wordEntry.examples);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, meanings, examples);
    }

    @Override
    public String toString() {
        return "WordEntry{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", meanings=" + meanings +
                ", examples=" + examples +
                '}';
    }
}

