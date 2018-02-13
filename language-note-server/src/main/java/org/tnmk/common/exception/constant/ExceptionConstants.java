package org.tnmk.common.exception.constant;

/**
 * Created by khoi.tran on 4/15/16.
 * TODO this part will be input in the specification API with more detail
 * information, this is the first draft idea.<br/>
 * The error code should never use 0 as prefix to avoid problem when convert to number (if any). So it should always begin by 1XXX, 2XXX...<br/>
 * Don't use ErrorCodeFactory because need to see the final error code inside the code.<br/>
 * Don't need to make the error code is calculated throughout a complicated process.<br/>
 * <ul>
 * <li>1) General / Infrastructure: 10XXXX -> 39XXXX (bean validation, cannot send AWS Email, cannot save files...)</li>
 * <li>- General: 10XXXX</li>
 * <li>- File: 1101XX </li>
 * <li>- Device: 12XXXX ...</li>
 * <li>- AWS: 20XXXX -> 29XXXX</li>
 * <li>- AWS SES: 2001XX </li>
 * <li>- AWS SQS: 2002XX </li>
 * <li>- AWS SNS: 2003XX </li>
 * <li>- AWS S3: 2004XX</li>
 * <p>
 * <li>2) Security: 40XXXX -> 49XXXX </li>
 * <li>+ General security: 40XXXX </li>
 * <li>+ Authentication(Wrong credentials, AccessToken invalid...): 41XXXX -> 43XXXX </li>
 * <li>- Credentials: 4101XX; </li>
 * <li>- AccessToken: 4102XX; </li>
 * <li>+ Authorization: 44XXXX -> 49XXXX </li>
 * <p>
 * <li>3) Domain: 50XXXX </li>
 * <li>+ Enterprise: 51XXXX </li>
 * <li>+ Shop: 52XXXX </li>
 * <li>+ Shift: 53XXXX </li>
 * <li>+ Emergency: 54XXXX ...</li>
 * <li>+ Employee: 56XXXX ...</li>
 * <li>+ Manager: 57XXXX ...</li>
 * <li>+ Contract: 58XXXX ...</li>
 * </ul>
 */
public interface ExceptionConstants {

    public interface General {
        String BadRequest = "100001";
        String UnexpectedError = "100101";
        String BeanValidationInValid = "100102";
        String BadArgument = "100103";
        String JsonConvertionError = "100104";
    }

    public interface File {
        String FileNotFound = "110100";
        String FileIOError = "110101";
        String FileTransfer = "110102";
        String FileSize = "110103";
        String FileType = "110104";
    }

    public interface Device {
        String NotExist = "120101";
        String AlreadyExist = "120102";
    }

    public interface AWS {

        public interface SES {
            String CannotSend = "200101";
        }

        public interface SQS {
            String General = "200200";
            String PolicyInvalid = "200201";
            String StatementInvalid = "200202";
        }

        public interface SNS {
            String General = "200300";
        }

        public interface S3 {
            String General = "200400";
            String CannotSend = "200401";
            String CannotDelete = "200403";
        }
    }

    public interface Security {
        String AuthenticationInvalid = "400000";
        String AccessTokenInvalid = "400101";
        String AccessTokenExpired = "400102";

        String AuthorizationInvalid = "410000";
    }

    public interface Domain {
        String RequiredField = "500101";
        String MissingData = "500102";
        String MinLength = "500103";
        String NotSupportPostToUpdate = "500104";
        String NotSupportPutToCreate = "500105";
        String FieldValueInvalid = "560106"; //Ex: StartDate > EndDate
    }

}
