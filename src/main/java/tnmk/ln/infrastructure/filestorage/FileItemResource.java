package tnmk.ln.infrastructure.filestorage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.infrastructure.filestorage.entity.FileItem;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author khoi.tran on 4/6/17.
 */
@RestController
public class FileItemResource {
    @Autowired
    FileItemService fileItemService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/files", method = RequestMethod.POST, consumes = "multipart/form-data")
    public FileItem uploadFile(@RequestParam(value = "file", required = false) MultipartFile mediaFile) throws IOException {
        return fileItemService.save(mediaFile.getOriginalFilename(), mediaFile.getContentType(), mediaFile.getBytes(), mediaFile.getSize());
//        FileItem result = new FileItem();
//        result.setName(mediaFile.getOriginalFilename());
//        return result;
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/files/{fileItemId}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable("fileItemId") String fileItemId, HttpServletResponse response) throws IOException {
        FileItem fileItem = fileItemService.findOneById(fileItemId);
        response.setContentType(fileItem.getMimeType());
        byte[] bytesContent = fileItem.getBytesContent();
        IOUtils.write(bytesContent, response.getOutputStream());
        response.flushBuffer();
    }
}
