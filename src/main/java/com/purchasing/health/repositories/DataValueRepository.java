package com.purchasing.health.repositories;

import com.purchasing.health.models.DataElement;
import com.purchasing.health.models.DataValue;
import com.purchasing.health.models.Dataset;
import org.springframework.data.repository.CrudRepository;


public interface DataValueRepository extends CrudRepository<DataValue,Long> {
    Iterable<DataValue>findByMonthAndYear(String month,Integer year);
    Iterable<DataValue>findByDataset(Dataset dataset);
    Iterable<DataValue>findByDataelement(DataElement dataelement);
    DataValue findByDatasetAndDataelementAndMonthAndYear(Dataset dataset,DataElement dataelement,String month,Integer year);
    Iterable<DataValue> findByDataelementAndMonthAndYear(DataElement dataElement,String month,Integer year);
    Iterable<DataValue>findByIdorgunit(Long idorgunit);
    DataValue findByIdorgunitAndDataelementAndDatasetAndMonthAndYear(Long idorgunit, DataElement dataelement, Dataset dataset, String month, Integer year);
    Iterable<DataValue> findByDatasetAndMonthAndYearAndIdorgunitOrderById(Dataset ds,String month,Integer year,Long idorgunit);


}
