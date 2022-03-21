package nido.backnido.repository;

import nido.backnido.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Feature f SET f.active = false WHERE f.featureId = :featureId")
    void softDelete(@Param("featureId") Long featureId);

}
