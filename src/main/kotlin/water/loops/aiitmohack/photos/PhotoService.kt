package water.loops.aiitmohack.photos

import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import water.loops.aiitmohack.model.OperationEntity
import water.loops.aiitmohack.model.OperationType
import water.loops.aiitmohack.model.PhotoEntity
import water.loops.aiitmohack.operations.OperationRepository
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class PhotoService(
    private val photoRepository: PhotoRepository,
    private val operationRepository: OperationRepository
) {

    @Transactional
    fun saveFile(resource: Resource): Photo {
        val filename = resource.filename + UUID.randomUUID()
        val operation = operationRepository.save(OperationEntity(
            type = OperationType.CLASSIFY, photos = emptySet()
        ))
        val photo = photoRepository.save(PhotoEntity(
            filename = filename,
            path = "storage/files/$filename",
            operationId = operation.id!!
        )).toDTO()

        try {
            Files.copy(
                resource.inputStream,
                Paths.get(filename),
                StandardCopyOption.REPLACE_EXISTING
            )
        } catch (e: IOException) {
            throw RuntimeException("Failed to store file", e)
        }

        startProcessing()

        return photo
    }

    fun getContent(photoId: Long): Resource {
        val photo =  photoRepository.findById(photoId)
            .orElseThrow()

        return FileSystemResource(Paths.get(photo.path))
    }

    private fun startProcessing() {
        TODO("Not yet implemented")
    }


}