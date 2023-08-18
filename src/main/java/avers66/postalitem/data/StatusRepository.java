package avers66.postalitem.data;

import org.springframework.data.repository.CrudRepository;

/**
 * StatusRepository
 *
 * @Author Tretyakov Alexandr
 */

public interface StatusRepository extends CrudRepository<StatusHistory, Long> {
    Iterable<StatusHistory> findByPostalDelivery(PostalDelivery postalDelivery);
}
