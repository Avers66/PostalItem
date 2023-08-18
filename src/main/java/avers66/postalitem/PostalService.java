package avers66.postalitem;

import avers66.postalitem.data.*;
import avers66.postalitem.dto.PostOfficeDto;
import avers66.postalitem.dto.PostalDeliveryDto;
import avers66.postalitem.dto.StatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * PostalServiice
 *
 * @Author Tretyakov Alexandr
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostalService {

    private final PostalRepository postalRepo;
    private final PostRepository postRepo;
    private final StatusRepository statusRepo;

    public Iterable<PostalDelivery> getPostalItemAll() {
        Iterable<PostalDelivery> postalItemList = postalRepo.findAll();
        if (postalItemList.iterator().hasNext()) return postalItemList;
        return null;
    }

    public PostalDelivery getPostalItemById(Long id) {
        Optional<PostalDelivery> postalDelivery = postalRepo.findById(id);
        if (postalDelivery.isPresent()) return postalDelivery.get();
        return null;
    }

    public Long createPostalItem(PostalDeliveryDto dto) {

        PostalDelivery postalDelivery = new PostalDelivery();
        postalDelivery.setType(dto.getType());
        postalDelivery.setPostalCode(dto.getPostalCode());
        postalDelivery.setAddress(dto.getAddress());
        postalDelivery.setRecipientName(dto.getRecipientName());
        postalDelivery.setCurrentStatus(Status.REGISTRATION);
        postalDelivery.setDateTime(ZonedDateTime.now());
        Optional<PostOffice> postOffice = postRepo.findById(dto.getPostId());
        if (postOffice.isEmpty()) return null;
        postalDelivery.setPostOffice(postOffice.get());
        postalRepo.save(postalDelivery);
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus(postalDelivery.getCurrentStatus());
        statusHistory.setDate(postalDelivery.getDateTime());
        statusHistory.setPostalDelivery(postalDelivery);
        statusHistory.setPostOffice(postOffice.get());
        statusRepo.save(statusHistory);
        return postalDelivery.getId();
    }

    public void newStatus(StatusDto dto) {
        Optional<PostalDelivery> postalDeliveryOpt = postalRepo.findById(dto.getPostalId());
        if (postalDeliveryOpt.isEmpty()) return;
        PostalDelivery postalDelivery = postalDeliveryOpt.get();
        postalDelivery.setCurrentStatus(dto.getStatus());
        postalDelivery.setDateTime(ZonedDateTime.now());
        Optional<PostOffice> postOffice = postRepo.findById(dto.getPostId());
        if (postOffice.isEmpty()) return ;
        postalDelivery.setPostOffice(postOffice.get());
        postalRepo.save(postalDelivery);
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus(postalDelivery.getCurrentStatus());
        statusHistory.setDate(postalDelivery.getDateTime());
        statusHistory.setPostalDelivery(postalDelivery);
        statusHistory.setPostOffice(postOffice.get());
        statusRepo.save(statusHistory);
    }

    public void deletePostalItemById(Long id) {
        postalRepo.deleteById(id);
    }


    public Iterable<PostOffice> getPostOfficeAll() {
        Iterable<PostOffice> postOfficeList = postRepo.findAll();
        if (postOfficeList.iterator().hasNext()) return postOfficeList;
        return null;
    }

    public PostOffice getPostOfficeById(Long id) {
        Optional<PostOffice> postOffice = postRepo.findById(id);
        if (postOffice.isPresent()) return postOffice.get();
        return null;
    }

    public Long createPostOffice(PostOfficeDto dto){
        PostOffice postOffice = new PostOffice();
        postOffice.setAddress(dto.getAddress());
        postOffice.setPostalCode(dto.getPostalCode());
        postOffice.setName(dto.getName());
        postRepo.save(postOffice);
        return postOffice.getId();
    }

    public void deletePostOfficeById(Long id) {
        postRepo.deleteById(id);
    }

    public Iterable<StatusHistory> getStatusHistoryById(Long id) {
        PostalDelivery postalDelivery = getPostalItemById(id);
        if (postalDelivery == null) return null;
        Iterable<StatusHistory> statusHistoryList = statusRepo.findByPostalDelivery(postalDelivery);
        if (statusHistoryList.iterator().hasNext()) return statusHistoryList;
        return null;
    }
}
