package dev.sudarsan.onehelper.validation.modificationConfigValidators;

import dev.sudarsan.onehelper.config.Action;
import dev.sudarsan.onehelper.config.LineChange;
import dev.sudarsan.onehelper.config.Operation;
import dev.sudarsan.onehelper.exception.ValidationException;
import dev.sudarsan.onehelper.modification.config.LineBasedModificationConfig;
import dev.sudarsan.onehelper.strategy.CommentStrategy;
import dev.sudarsan.onehelper.util.ValueCheckerUtil;

import java.util.List;
import java.util.Map;

public class LineBasedModificationConfigValidator extends FileBasedModificationConfigValidator<LineBasedModificationConfig> {
    private final Map<String, CommentStrategy> commentStrategyMap;

    public LineBasedModificationConfigValidator(Map<String, CommentStrategy> commentStrategyMap) {
        this.commentStrategyMap = commentStrategyMap;
    }

    /*
    * This validator ensures the JSON follows the contract for line based changes
    * The change attributes, neither of them can be null
    * The comment strategy existence is ensured for teh given file type
    * The update operation ensures a new value is present
    * If you give multiple actions with no occurrences, the validator throws an exception*/

    @Override
    public void validateModificationConfig(LineBasedModificationConfig modificationConfig) throws ValidationException {
        validateFilePath(modificationConfig);
        validateChanges(modificationConfig.getChanges());
        checkForCommentStrategies(modificationConfig);
    }

    private void checkForCommentStrategies(LineBasedModificationConfig modificationConfig) throws ValidationException {
        for (LineChange change : modificationConfig.getChanges()) {
            if (hasCommentOperation(change)){
                checkCommentStrategy(modificationConfig.getFilePath());
                break;
            }
        }
    }

    private void checkCommentStrategy(String filePath) throws ValidationException {
        String fileType = filePath.substring(filePath.lastIndexOf('.') + 1);
        if (!commentStrategyMap.containsKey(fileType)) {
            throw new ValidationException("The file type '"+fileType+"' has a comment operation, but comment strategy has not been added into the comment_strategies.json");
        }
    }

    private boolean hasCommentOperation(LineChange change) {
        for (Action action : change.getActions()) {
            if (action.getOperation().equals(Operation.COMMENT)){
                return true;
            }
        }
        return false;
    }

    private void validateChanges(List<LineChange> changes) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(changes)){
            throw new ValidationException("Changes list cannot be null or empty");
        }

        for (LineChange change : changes) {
            validateChange(change);
        }
    }

    private void validateChange(LineChange change) throws ValidationException {
        if (change == null){
            throw new ValidationException("Change cannot be null");
        }

        // target check
        if (ValueCheckerUtil.isNullOrEmpty(change.getTarget())){
            throw new ValidationException("Target line cannot be null or empty");
        }

        // actions check
        validateActions(change.getActions());
    }

    private void validateActions(List<Action> actions) throws ValidationException {
        if (ValueCheckerUtil.isNullOrEmpty(actions)){
            throw new ValidationException("Actions list cannot be null or empty");
        }

        for (Action action : actions) {
            validateAction(action, actions.size());
        }
    }

    private void validateAction(Action action, int actionsSize) throws ValidationException {
        if (action == null){
            throw new ValidationException("Action cannot be null");
        }

        // operation
        validateOperation(action.getOperation());

        // validate occurrences based on operation
        validateActionAndOccurrences(action, actionsSize);

        // new value
        validateNewValue(action);
    }

    private void validateNewValue(Action action) throws ValidationException {
        // if there is an UPDATE operation, then value cannot be null in the action
        if (action.getOperation() == Operation.UPDATE && action.getValue() == null){
            throw new ValidationException("Value cannot be null for UPDATE operation in action");
        }
    }

    private void validateActionAndOccurrences(Action action, int actionsSize) throws ValidationException {
        // if there are multiple actions, then occurrences cannot be null or empty in any action
        if (ValueCheckerUtil.isNullOrEmpty(action.getOccurrences())){
            if (actionsSize > 1){
                throw new ValidationException("Occurrence cannot be null or empty in action when there are multiple actions");
            }
        }
    }

    private void validateOperation(Operation operation) throws ValidationException {
        if (operation == null){
            throw new ValidationException("Action operation cannot be null");
        }

        if (operation.toString().isEmpty()){
            throw new ValidationException("Action operation cannot be empty");
        }
    }
}
