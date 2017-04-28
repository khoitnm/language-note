package tnmk.common.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnmk.common.exception.BaseException;
import tnmk.common.exception.constant.ExceptionConstants;

/**
 * @author khoi.tran on 11/22/16.
 *         This is the wrapper of action.
 *         The method {@link #execute()} will never throw any Exception. If there's something wrong, it will return the corresponding resultCode and resultMessage.
 *         If success, it will store result in {@link #resultObject}.
 */
public abstract class ActionWithResult<T> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ActionWithResult.class);

    private String actionName;
    protected T resultObject;
    protected String resultCode;
    protected String resultMessage;

    public ActionWithResult() {
        //Just the default constructor.
    }

    public ActionWithResult(String actionName) {
        this.actionName = actionName;
    }

    public ActionWithResult execute() {
        try {
            this.executeSuccess();
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                BaseException baseException = (BaseException) ex;
                this.resultCode = baseException.getErrorCode();
                this.resultMessage = baseException.getErrorMessage();
            } else {
                this.resultCode = ExceptionConstants.General.UnexpectedError;
                this.resultMessage = ex.getMessage();
            }
            LOGGER.error(ex.getMessage(), ex);
        }
        return this;
    }

    protected abstract void executeSuccess();

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getResultObject() {
        return resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }
}
