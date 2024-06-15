package water.loops.aiitmohack.model

import jakarta.persistence.*

@Entity
@Table(name = "photos")
class OperationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val type: OperationType,
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_id", referencedColumnName = "id")
    val photos: Set<PhotoEntity>
) {}