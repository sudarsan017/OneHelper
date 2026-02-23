package dev.sudarsan.onehelper.modification;

import dev.sudarsan.onehelper.commmentstrategy.CommentStrategy;
import dev.sudarsan.onehelper.dto.Action;
import dev.sudarsan.onehelper.dto.LineChange;
import dev.sudarsan.onehelper.entities.ChangeMatch;
import dev.sudarsan.onehelper.entities.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineBasedModification implements Modification {

    private final String relativePath;
    private final List<LineChange> changes;
    private final Map<LineChange, Integer> changeCounts;
    private final CommentStrategy commentStrategy;

    public LineBasedModification(String relativePath, List<LineChange> changes, CommentStrategy commentStrategy) {
        this.relativePath = relativePath;
        this.changes = changes;
        this.changeCounts = new HashMap<>();
        this.commentStrategy = commentStrategy;
    }

    @Override
    public void apply(ProjectContext context) throws ModificationException {
        Path file = context.resolveFile(relativePath);
        List<String> lines = getLinesFromFile(file);

        List<String> newLines = new ArrayList<>();

        for (String line : lines) {
            newLines.add(processLine(line));
        }

        writeFile(file, newLines);
    }

    private void writeFile(Path file, List<String> lines) throws ModificationException {
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new ModificationException("Error writing the file: " + file);
        }
    }

    private String processLine(String line) throws ModificationException {
        String strippedLine = line.trim().strip();

        ChangeMatch match = findMatchingChange(strippedLine);

        if (match == null) {
            return line;
        }

        Action action = selectAction(match);

        if (action == null) {
            return line;
        }

        return applyAction(action, line, strippedLine);
    }

    private String applyAction(Action action, String line, String strippedLine) throws ModificationException {
        switch (action.getOperation()) {
            case COMMENT:
                String commentedLine = comment(strippedLine);
                return line.replace(strippedLine, commentedLine);
            case UPDATE:
                return line.replace(strippedLine, action.getValue());
            default:
                throw new RuntimeException("Unsupported operation: " + action.getOperation());
        }
    }

    private String comment(String line) throws ModificationException {
        if (commentStrategy == null) {
            throw new ModificationException("Comment strategy is not defined");
        }

        return commentStrategy.comment(line);
    }

    private Action selectAction(ChangeMatch match) {
        LineChange change = match.getChange();
        List<Action> actions = change.getActions();

        if (actions.size() == 1) {
            Action action = actions.get(0);
            return applies(action, match.getOccurrence()) ? action : null;
        }

        for (Action action : actions) {
            if (applies(action, match.getOccurrence())) {
                return action;
            }
        }

        return null;
    }

    private boolean applies(Action action, int occurrence) {
        if (action.getOccurrences() == null) {
            return true;
        } else {
            return action.getOccurrences().contains(occurrence);
        }
    }

    private ChangeMatch findMatchingChange(String strippedLine) {
        for (LineChange change : changes) {
            if (change.getTarget().equals(strippedLine)) {
                int occurrence = incrementAndGetOccurrence(change);
                return new ChangeMatch(occurrence, change);
            }
        }
        return null;
    }

    private int incrementAndGetOccurrence(LineChange change) {
        int count = changeCounts.getOrDefault(change, 0) + 1;
        changeCounts.put(change, count);
        return count;
    }

    private List<String> getLinesFromFile(Path file) throws ModificationException {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new ModificationException("Error reading the file: " + file);
        }
    }
}
