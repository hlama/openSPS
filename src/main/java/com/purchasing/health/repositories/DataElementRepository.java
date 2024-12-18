package com.purchasing.health.repositories;

import com.purchasing.health.models.DataElement;
import com.purchasing.health.models.Dataset;
import org.springframework.data.repository.CrudRepository;


public interface DataElementRepository extends CrudRepository<DataElement,Long> {
    
    DataElement findByDatalelementnameAndDataset(String datalelementname, Dataset dataset);
    Iterable<DataElement> findByDataset(Dataset dataset);
    //Iterable<DataElement> findByDatasetAndstartperiodeAndendperiode(Dataset dataset,String startdate,String enddate);
   Iterable<DataElement> findByDatasetAndStartperiodeGreaterThanEqualAndEndperiodeLessThanEqual(Dataset dataset,String startPeriode,String endperiode);

}
