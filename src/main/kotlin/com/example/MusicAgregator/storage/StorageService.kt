package com.example.MusicAgregator.storage

import com.example.MusicAgregator.dto.MusicDto
import com.example.MusicAgregator.service.MusicService
import io.minio.*
import io.minio.errors.MinioException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

@Service
class StorageService(
    private val musicService: MusicService
) {
    fun uploadMp3File(file: MultipartFile) {
        try {
            // Create a MinIO client with the specified server, access key, and secret key.
            val minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("123", "123456789")
                .build()
            val found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("music").build())
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("music").build())
            } else {
                println("Bucket 'music' already exists.")
            }

            // Create a temporary file with an .mp3 extension
            val tempFile: Path = Files.createTempFile("temp", ".mp3")

            // Save the uploaded file to the temporary file
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING)
            }

            // Upload the MP3 file to the specified MinIO bucket
            minioClient.uploadObject(
                UploadObjectArgs.builder()
                    .bucket("music") // Specify your bucket name
                    .`object`(file.originalFilename ?: "default.mp3") // Use the original file name or a default name
                    .filename(tempFile.toString())
                    .build()
            )
            musicService.saveMusic(
                MusicDto(name = file.originalFilename ?: "default.mp3", author = "test", genre = "test", user = 1L)
            )

            println("Successfully uploaded ${file.originalFilename} to bucket 'music'")

            // Optionally, delete the temporary file after upload
            Files.deleteIfExists(tempFile)
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
            e.printStackTrace()
        }
    }

    fun getFile(name: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        try {
            // Создаем клиент MinIO с указанием сервера, access key и secret key.
            val minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("123", "123456789")
                .build()

            // Проверяем существование бакета 'test123'.
            val found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("music").build())
            if (!found) {
                // Создаем новый бакет 'test123'.
                println("Bucket 'test123' not exists.")
                return byteArrayOf()
            }

            // Получаем объект 'test.txt' из бакета 'test123'.
            val response = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket("music")
                    .`object`(name)
                    .build()
            )

            // Копируем содержимое объекта в ByteArrayOutputStream.
            response.transferTo(outputStream)
            response.close()
        } catch (e: MinioException) {
            println("Error occurred: $e")
            println("HTTP trace: ${e.httpTrace()}")
        } catch (e: IOException) {
            println("IO error occurred: $e")
        } catch (e: NoSuchAlgorithmException) {
            println("Algorithm error occurred: $e")
        } catch (e: InvalidKeyException) {
            println("Invalid key error occurred: $e")
        }
        return outputStream.toByteArray()
    }
}