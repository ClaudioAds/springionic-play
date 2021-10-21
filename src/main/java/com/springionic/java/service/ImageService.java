package com.springionic.java.service;

import com.springionic.java.service.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImageFromFile(MultipartFile uploadFile) {
        String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());//para extrair a extensão do arquivo
        if (!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }

        try {
            BufferedImage img = ImageIO.read(uploadFile.getInputStream());//espera ler uma imagem JPG
            if ("png".equals(ext)) {
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler o arquivo");
        }
    }

    public BufferedImage pngToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);//Esse código WHITE preenche o funco da imagem com branco, caso ela seja transparente
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    /*Esse método é necessário pq o S3 recebe um inputStream no upload das imagens
    Então pegamos o bufferImage para enviar junto*/
    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler o arquivo");
        }
    }

    //método para cortar (cropar) a imagem para o nosso tamanho especificado no properties
    public BufferedImage cropImage(BufferedImage sourceImg) {
        //Um tipo de IF... Se a altura for <= largura então é a altura, se não a largura
        int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
        return Scalr.crop(
                sourceImg,
                (sourceImg.getWidth() / 2) - (min / 2), // vai no meio da largura menos a metade do minimo
                (sourceImg.getHeight() / 2) - (min / 2), // vai no meio da altura menos a metade do minimo
                min,
                min);
    }

    //função para redimensionar uma imagem
    public BufferedImage resize(BufferedImage sourceImage, int size) {
        return Scalr.resize(sourceImage, Scalr.Method.ULTRA_QUALITY, size);//para manter a melhor qualidade da imagem
    }
}