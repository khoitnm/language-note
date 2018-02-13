package org.tnmk.ln.infrastructure.filestorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.ln.infrastructure.filestorage.entity.FileItem;

/**
 * @author khoi.tran on 2/2/17.
 */
@Service
public class FileItemService {
    @Autowired
    private FileItemRepository fileItemRepository;

    public FileItem save(String fileName, String mimeType, byte[] data, long size) {
        FileItem fileItem = new FileItem();
        fileItem.setBytesContent(data);
        fileItem.setName(fileName);
        fileItem.setMimeType(mimeType);
        fileItem.setFileSize(size);
        return save(fileItem);
    }

    public FileItem save(FileItem fileItem) {
        FileItem oldFileItem = fileItemRepository.findOneByNameAndMimeTypeAndFileSize(fileItem.getName(), fileItem.getMimeType(), fileItem.getFileSize());
        if (oldFileItem != null) {
            return oldFileItem;
        } else {
            return fileItemRepository.save(fileItem);
        }
    }

    public FileItem findOneById(String fileId) {
        return fileItemRepository.findOne(fileId);
    }

    public void remove(String fileItemId) {
        fileItemRepository.delete(fileItemId);
    }
}
