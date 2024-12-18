package com.purchasing.health.repositories;

import com.purchasing.health.models.SettingAmount;
import com.purchasing.health.models.TypeOrgunit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SettingAmountRepository extends CrudRepository<SettingAmount,Long> {
    SettingAmount findByStartPeriodeAndEndPeriodeAndTypeOrgunit(Long start, Long end, TypeOrgunit typeOrgunit);
    @Query("select s from SettingAmount s where s.startPeriode< :start and s.endPeriode>= :end")
    Iterable<SettingAmount> findStartEndPeriode(
            @Param("start") Long start,@Param("end") Long end
    );

    Iterable<SettingAmount> findByTypeOrgunit(TypeOrgunit typeOrgunit);
    SettingAmount findByStartPeriodeLessThanAndEndPeriodeGreaterThanEqual(Long start,Long end);
    SettingAmount findByStartPeriodeLessThanAndEndPeriodeGreaterThanEqualAndTypeOrgunit(Long start,Long end,TypeOrgunit typeOrgunit);

}
