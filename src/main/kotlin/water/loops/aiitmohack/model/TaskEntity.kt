package water.loops.aiitmohack.model

import jakarta.persistence.*

@Entity
@Table(name = "tasks")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    val photoId: Long
) {}