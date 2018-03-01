package org.tnmk.common.utils.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khoi.tran on 11/30/16.
 */
public class MimeTypeUtils {
    public static final String MIME_TYPE_XLS = "application/vnd.ms-excel";
    public static final String MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String MIME_TYPE_PDF = "application/pdf";
    private static final Map<String, String> FILE_EXTENSION_MAP;

    static {
        Map<String, String> fileExtensionMap = new HashMap<>();
        // MS Office
        fileExtensionMap.put("doc", "application/msword");
        fileExtensionMap.put("dot", "application/msword");
        fileExtensionMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        fileExtensionMap.put("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        fileExtensionMap.put("docm", "application/vnd.ms-word.document.macroEnabled.12");
        fileExtensionMap.put("dotm", "application/vnd.ms-word.template.macroEnabled.12");
        fileExtensionMap.put("xls", MIME_TYPE_XLS);
//        FILE_EXTENSION_MAP.put("xlt", "application/vnd.ms-excel");
//        FILE_EXTENSION_MAP.put("xla", "application/vnd.ms-excel");
        fileExtensionMap.put("xlsx", MIME_TYPE_XLSX);
        fileExtensionMap.put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        fileExtensionMap.put("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
        fileExtensionMap.put("xltm", "application/vnd.ms-excel.template.macroEnabled.12");
        fileExtensionMap.put("xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
        fileExtensionMap.put("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
        fileExtensionMap.put("ppt", "application/vnd.ms-powerpoint");
        fileExtensionMap.put("pot", "application/vnd.ms-powerpoint");
        fileExtensionMap.put("pps", "application/vnd.ms-powerpoint");
        fileExtensionMap.put("ppa", "application/vnd.ms-powerpoint");
        fileExtensionMap.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        fileExtensionMap.put("potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
        fileExtensionMap.put("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
        fileExtensionMap.put("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
        fileExtensionMap.put("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        fileExtensionMap.put("potm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        fileExtensionMap.put("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
        // Open Office
        fileExtensionMap.put("odt", "application/vnd.oasis.opendocument.text");
        fileExtensionMap.put("ott", "application/vnd.oasis.opendocument.text-template");
        fileExtensionMap.put("oth", "application/vnd.oasis.opendocument.text-web");
        fileExtensionMap.put("odm", "application/vnd.oasis.opendocument.text-master");
        fileExtensionMap.put("odg", "application/vnd.oasis.opendocument.graphics");
        fileExtensionMap.put("otg", "application/vnd.oasis.opendocument.graphics-template");
        fileExtensionMap.put("odp", "application/vnd.oasis.opendocument.presentation");
        fileExtensionMap.put("otp", "application/vnd.oasis.opendocument.presentation-template");
        fileExtensionMap.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
        fileExtensionMap.put("ots", "application/vnd.oasis.opendocument.spreadsheet-template");
        fileExtensionMap.put("odc", "application/vnd.oasis.opendocument.chart");
        fileExtensionMap.put("odf", "application/vnd.oasis.opendocument.formula");
        fileExtensionMap.put("odb", "application/vnd.oasis.opendocument.database");
        fileExtensionMap.put("odi", "application/vnd.oasis.opendocument.image");
        fileExtensionMap.put("oxt", "application/vnd.openofficeorg.extension");
        // Other
        fileExtensionMap.put("txt", "text/plain");
        fileExtensionMap.put("rtf", "application/rtf");
        fileExtensionMap.put("pdf", MIME_TYPE_PDF);

        FILE_EXTENSION_MAP = Collections.unmodifiableMap(fileExtensionMap);
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
