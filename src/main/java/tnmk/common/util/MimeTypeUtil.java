package tnmk.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 11/30/16.
 */
public class MimeTypeUtil {
    public static final String MIME_TYPE_XLS = "application/vnd.ms-excel";
    public static final String MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String MIME_TYPE_PDF = "application/pdf";
    private static final Map<String, String> FILE_EXTENSION_MAP;

    static {
        FILE_EXTENSION_MAP = new HashMap<>();
        // MS Office
        FILE_EXTENSION_MAP.put("doc", "application/msword");
        FILE_EXTENSION_MAP.put("dot", "application/msword");
        FILE_EXTENSION_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        FILE_EXTENSION_MAP.put("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        FILE_EXTENSION_MAP.put("docm", "application/vnd.ms-word.document.macroEnabled.12");
        FILE_EXTENSION_MAP.put("dotm", "application/vnd.ms-word.template.macroEnabled.12");
        FILE_EXTENSION_MAP.put("xls", MIME_TYPE_XLS);
//        FILE_EXTENSION_MAP.put("xlt", "application/vnd.ms-excel");
//        FILE_EXTENSION_MAP.put("xla", "application/vnd.ms-excel");
        FILE_EXTENSION_MAP.put("xlsx", MIME_TYPE_XLSX);
        FILE_EXTENSION_MAP.put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        FILE_EXTENSION_MAP.put("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
        FILE_EXTENSION_MAP.put("xltm", "application/vnd.ms-excel.template.macroEnabled.12");
        FILE_EXTENSION_MAP.put("xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
        FILE_EXTENSION_MAP.put("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
        FILE_EXTENSION_MAP.put("ppt", "application/vnd.ms-powerpoint");
        FILE_EXTENSION_MAP.put("pot", "application/vnd.ms-powerpoint");
        FILE_EXTENSION_MAP.put("pps", "application/vnd.ms-powerpoint");
        FILE_EXTENSION_MAP.put("ppa", "application/vnd.ms-powerpoint");
        FILE_EXTENSION_MAP.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        FILE_EXTENSION_MAP.put("potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
        FILE_EXTENSION_MAP.put("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
        FILE_EXTENSION_MAP.put("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
        FILE_EXTENSION_MAP.put("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        FILE_EXTENSION_MAP.put("potm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        FILE_EXTENSION_MAP.put("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
        // Open Office
        FILE_EXTENSION_MAP.put("odt", "application/vnd.oasis.opendocument.text");
        FILE_EXTENSION_MAP.put("ott", "application/vnd.oasis.opendocument.text-template");
        FILE_EXTENSION_MAP.put("oth", "application/vnd.oasis.opendocument.text-web");
        FILE_EXTENSION_MAP.put("odm", "application/vnd.oasis.opendocument.text-master");
        FILE_EXTENSION_MAP.put("odg", "application/vnd.oasis.opendocument.graphics");
        FILE_EXTENSION_MAP.put("otg", "application/vnd.oasis.opendocument.graphics-template");
        FILE_EXTENSION_MAP.put("odp", "application/vnd.oasis.opendocument.presentation");
        FILE_EXTENSION_MAP.put("otp", "application/vnd.oasis.opendocument.presentation-template");
        FILE_EXTENSION_MAP.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
        FILE_EXTENSION_MAP.put("ots", "application/vnd.oasis.opendocument.spreadsheet-template");
        FILE_EXTENSION_MAP.put("odc", "application/vnd.oasis.opendocument.chart");
        FILE_EXTENSION_MAP.put("odf", "application/vnd.oasis.opendocument.formula");
        FILE_EXTENSION_MAP.put("odb", "application/vnd.oasis.opendocument.database");
        FILE_EXTENSION_MAP.put("odi", "application/vnd.oasis.opendocument.image");
        FILE_EXTENSION_MAP.put("oxt", "application/vnd.openofficeorg.extension");
        // Other
        FILE_EXTENSION_MAP.put("txt", "text/plain");
        FILE_EXTENSION_MAP.put("rtf", "application/rtf");
        FILE_EXTENSION_MAP.put("pdf", MIME_TYPE_PDF);
    }

    public static String getMimeTypeFromFileExtension(String fileExtension) {
        return FILE_EXTENSION_MAP.get(fileExtension);
    }

    /**
     * @param mimeType
     * @return if not found, return null
     */
    public static String getFileExtensionFromMimeType(String mimeType) {
        for (Map.Entry<String, String> fileExtensionEntry : FILE_EXTENSION_MAP.entrySet()) {
            String imimeType = fileExtensionEntry.getValue();
            if (imimeType.equalsIgnoreCase(mimeType)) {
                return fileExtensionEntry.getKey();
            }
        }
        return null;
    }
}
