package water.loops.aiitmohack.photos

import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.MediaType.IMAGE_JPEG_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@RestController
@RequestMapping("/photos")
class PhotoController(
    val photoService: PhotoService
) {

    @GetMapping(path = ["/{photoId}"], produces = [IMAGE_JPEG_VALUE, IMAGE_JPEG_VALUE])
    fun downloadFile(@PathVariable photoId: Long): ResponseEntity<Resource> {
        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(IMAGE_JPEG_VALUE))
            .body(photoService.getContent(photoId))
    }

    @PostMapping(consumes = ["multipart/form-data"])
    fun uploadFiles(@RequestPart("file") requestBody: MultipartFile): ResponseEntity<Photo> {
        if (requestBody.isEmpty) {
            throw RuntimeException("Failed to save empty requestBody")
        }
        try {
            requestBody.inputStream.use { inputStream ->
                return ResponseEntity.ok(
                    photoService.saveFile(InputStreamResource(inputStream))
                )
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to store file", e)
        }
    }
}