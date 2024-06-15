package water.loops.aiitmohack.operations

import org.springframework.data.jpa.repository.JpaRepository
import water.loops.aiitmohack.model.OperationEntity

interface OperationRepository: JpaRepository<OperationEntity, Long> {
}