package tnmk.ln.infrastructure.filestorage.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author khoi.tran on 2/2/17.
 */
@Service
public class FileItemService {
    @Autowired
    private FileItemRepository fileItemRepository;

    public FileItem save(String fileName, String mimeType, byte[] data) {
        FileItem fileItem = new FileItem();
//        fileItem.setBase64Content(Base64Utils.encodeToString(data));
        fileItem.setBytesContent(data);
        fileItem.setName(fileName);
        fileItem.setMimeType(mimeType);

        return save(fileItem);
    }

    public FileItem save(FileItem fileItem) {
        return fileItemRepository.save(fileItem);
    }

    public FileItem findOneById(String fileId) {
        return fileItemRepository.findOne(fileId);
    }

    public void remove(String fileItemId) {
        fileItemRepository.delete(fileItemId);
    }
}
