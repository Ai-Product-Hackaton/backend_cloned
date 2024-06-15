package water.loops.aiitmohack.photos

import org.springframework.data.jpa.repository.JpaRepository
import water.loops.aiitmohack.model.PhotoEntity

interface PhotoRepository: JpaRepository<PhotoEntity, Long> {
}