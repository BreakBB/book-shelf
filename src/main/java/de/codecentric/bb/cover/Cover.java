package de.codecentric.bb.cover;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cover {
    @Transient
    private final int IMAGE_SIZE_THRESHOLD = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private byte[] image;

    public Cover(String isbn) {
        this.isbn = isbn;
    }

    public boolean hasValidData() {
        return image.length > IMAGE_SIZE_THRESHOLD;
    }

    public int getImageSize() {
        return image.length;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImage(BufferedImage image) throws IOException {
        this.image = getByteArrayFrom(image);
    }

    private byte[] getByteArrayFrom(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }
}
