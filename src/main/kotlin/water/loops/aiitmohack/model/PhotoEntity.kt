package water.loops.aiitmohack.model

import jakarta.persistence.*
import water.loops.aiitmohack.photos.Photo

@Entity
@Table(name = "photos")
class PhotoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val filename: String,
    @Column(nullable = false)
    val path: String,
    @Column(name = "operation_id", nullable = false)
    val operationId: Long
) {

    fun toDTO() = Photo(id!!, path)

}