package de.codecentric.bb.cover;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class MockMultipartFile implements MultipartFile {
    private final String name;
    private final byte[] bytes;

    public MockMultipartFile(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        if (bytes == null) {
            throw new IOException();
        }
        return bytes;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public void transferTo(File file) throws IllegalStateException {

    }
}
